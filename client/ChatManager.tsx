import firestore from '@react-native-firebase/firestore';
import {localRead} from './LocalDataManager';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {userId} from './LoginManager';
import messaging from '@react-native-firebase/messaging';

/* globals */
let myChatDB : chatDB = {
    chatRooms: []
};

/* export functions */
export async function chatInitialize(isNew : boolean) {
    if(isNew) {
        docInitialize();
    }
    //requestUserPermission(); //알림을 위해 토큰 정보를 파이어베이스에 저장. 현재 알림 비활성
    loadChatDBLocal(); //로컬에 저장된 채팅 내용 불러오기
    updateChatDB(); //새로운 채팅내용 + 채팅방 업데이트
    subscribeUserChats(); // userChats : 채팅방 목록, 즉 새로운 채팅방 생성 감지
    subscribeChats(); // Chats : 채팅방, 즉 새로운 채팅 메세지 감지
    saveChatDBLocal();
}

export async function saveChatDBLocal() {
    try {
        const jsonValue = JSON.stringify(chatDB, (key, value) =>
            key === 'time' ? value.toISOString() : value
        );
        await AsyncStorage.setItem('chatDB', jsonValue);
    } catch (e) {
        console.error('Failed to save the chat data.');
    }
}

export async function getChatDB() {
    return myChatDB;
}

/*
* 애플리케이션 초기화 단계에서 updateChatDB 이후 모든 채팅방을 구독
*/
export async function subscribeChats() {
    for(let room of myChatDB.chatRooms) {
        const unused = firestore().collection("Chats").doc(room.chatRoomName).onSnapshot((doc) => {
            if(doc.exists) {
                const data = doc.data();
                updateChatRoom(room,data);
            } else {
                console.error("error in subscribeChats");
            }
        });
    }
}

export async function createNewChat(opponentId : string) {
    const newChatRoomName = createChatRoomName(userId,opponentId);
    //create chat room
    const docRef = firestore().collection("chats").doc(newChatRoomName);
    try {
        await docRef.set({
            "chatRoomName" : newChatRoomName,
            "topChatId" : 0,
            "messages" : [],
        });
    } catch (error) {
        console.error(error);
    }


    //update userChats
    const myDocRef = firestore().collection('userChats').doc(userId);
    const opponentDocRef = firestore().collection('userChats').doc(opponentId);
    try {
        await myDocRef.update({
            chatRoomNames: firestore.FieldValue.arrayUnion(newChatRoomName),
        });
        await opponentDocRef.update({
            chatRoomNames: firestore.FieldValue.arrayUnion(newChatRoomName),
        });
    } catch(error) {
        console.error(error);
    }
}

export async function sendChat(opponentId : string, message : string) {
    //서버에만 전송하고, 로컬DB에 반영하지않음. 로컬DB에는 자동적으로 네트워킹을 통해 반영될 것임
    for(let room of chatDB.chatRooms) {
        if(getOpponentId(room.chatRoomName) == opponentId) {
            const docRef = firestore().collection('chats').doc(room.chatRoomName);
            const newMessage = {
                "chatId" : room.topChatId+1,
                "senderId" : userId,
                "receiverId" : opponentId,
                "time" : new Date(),
                "text" : message,
            };
            try {
                docRef.update({
                    topChatId: room.topChatId+1,
                    messages: firestore.FieldValue.arrayUnion(newMessage),
                })
                room.messages.push(newMessage);
                room.topChatId += 1;
            } catch(error) {
                console.error(error);
            }

            break;
        }
    }
}

/* helper functions */
interface chatMessage {
    "chatId" : number,
    "senderId" : string,
    "receiverId" : string,
    "time" : Date,
    "text" : string,
}
interface chatRoom {
    "chatRoomName" : string,
    "topChatId" : number,
    "messages" : chatMessage[],
}
interface chatDB {
    "chatRooms" : chatRoom[],
}

/*
* 매 애플리케이션 실행시 실행하여 로컬에 저장된 DB보다 업데이트된 정보가 있을 경우 이 정보를 내려받음.
*/
async function updateChatDB() {
    //update previous chatrooms
    for (let room of myChatDB.chatRooms) {
        try {
                const docRef = firestore().collection('chats').doc(room.chatRoomName);
                const docSnapshot = await docRef.get();

                if (docSnapshot.exists) {
                    console.log("Document read successfully!");
                    const data = docSnapshot.data();
                    updateChatRoom(room,data);
                } else {
                    console.error("chatroom doesn't exist");
                }
        } catch (error) {
                console.error("Error reading document:", error);
        }
    }
    //search new chatrooms
    try {
        const docRef = firestore().collection('userChats').doc(userId);
        const docSnapshot = await docRef.get();

        if(docSnapshot.exists) {
            const data = docSnapshot.data();
            addNewChatRoom(data);
        } else {
            console.error("UserChat document doesn't exist");
        }
    } catch(error) {
        console.error("Error reading document:", error);
    }
}

/*
* 애플리케이션 초기화 단계에서 updateChatDB 이후에 실행하여 문서 변경사항 감지
*/
async function subscribeUserChats() {
    //subscribe userChats
    const unsubscribeUserChats = firestore().collection("userChats").doc(userId).onSnapshot((doc) => {
        if(doc.exists) {
            const data = doc.data();
            addNewChatRoom(data);
        } else {
            console.error("subscribe error");
        }
    });
}

/*
* 파이어베이스 로그인 단계에서 토큰 등록 (푸쉬 알림용)
*
*/
async function requestUserPermission() {
  const authStatus = await messaging().requestPermission();
  const enabled = authStatus === messaging.AuthorizationStatus.AUTHORIZED || authStatus === messaging.AuthorizationStatus.PROVISIONAL;

  if (enabled) {
    console.log('Authorization status:', authStatus);
    const token = await messaging().getToken();
    // Firestore에 사용자의 토큰 저장
    firestore().collection('users').doc(userId).set({
      pushToken: token,
    }, { merge: true });
  }
}

/*
* firebase 계정 생성 단계에서 실행하여 최초 문서 설정
*
*/
async function docInitialize() {
    //create userChats
    try {
        await firestore().collection('userChats').doc(userId).set({
            "chatRoomNames" : [],
        });
    } catch(error) {
        console.error(error);
    }
}

async function loadChatDBLocal() {
    try {
        const jsonValue = await AsyncStorage.getItem('chatDB');
        if(jsonValue != null) {
            myChatDB = JSON.parse(jsonValue, (key, value) =>key === 'time' ? new Date(value) : value);
        }
    } catch (e) {
        console.error('Failed to load the chat data.');
    }
}

function createChatRoomName(Aid: string, Bid: string): string {
    const sortedIds = [Aid, Bid].sort();

    const Id1 = sortedIds[0];
    const Id2 = sortedIds[1];

    const chatRoomName = `${Id1}_${Id2}`;
    return chatRoomName;
}

function getOpponentId(chatRoomName : string): string {
    const ids = chatRoomName.split('_');
    if(ids[0] === userId) return ids[1];
    else return ids[0];
}

function addNewChatRoom(data) {
    const nameList = data.chatRoomNames;
    for(let name of nameList) {
        if(!myChatDB.chatRooms.some(room => room.chatRoomName === name)) {
            //must add
            try{
                const docRef2 = firestore().collection('chats').doc(name);
                const docSnapshot2 = await docRef2.get();
                if(docSnapshot2.exists) {
                    const data2 = docSnapshot2.data();
                    let newChatRoom : chatRoom = {
                        "chatRoomName" : data2.chatRoomName,
                        "topChatId" : data2.topChatId,
                        "messages" : data2.messages,
                    }
                    myChatDB.chatRooms.push(newChatRoom);
                    const unused = firestore().collection('chats').doc(name).onSnapshot((doc) => {
                        if(doc.exists) {
                            data = doc.data();
                            updateChatRoom(newChatRoom,data);
                        } else {
                            console.error("error in addNewChatRoom");
                        }
                    });
                }
            } catch(error) {
                console.error(error);
            }
        }
    }
}

function updateChatRoom(room, data) {
    if(data.topChatId != room.topChatId) {
        //need update
        room.messages = data.messages;
        room.topChatId = data.topChatId;
    }
}
/* test functions */

