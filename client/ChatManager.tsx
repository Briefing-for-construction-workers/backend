import firestore from '@react-native-firebase/firestore';
import {localRead} from './LocalDataManager';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {userId} from './LoginManager';

/* globals */
let myChatDB : chatDB = {
    chatRooms: []
};

/* export functions */
export async function saveChatDB() {
    try {
        const jsonValue = JSON.stringify(chatDB, (key, value) =>
            key === 'time' ? value.toISOString() : value
        );
        await AsyncStorage.setItem('chatDB', jsonValue);
    } catch (e) {
        console.error('Failed to save the chat data.');
    }
}
export async function loadChatDB() {
    try {
        const jsonValue = await AsyncStorage.getItem('chatDB');
        if(jsonValue != null) {
            myChatDB = JSON.parse(jsonValue, (key, value) =>key === 'time' ? new Date(value) : value);
        }
    } catch (e) {
        console.error('Failed to load the chat data.');
    }
}

export async function updateChatDB() {
    //update previous chatrooms
    for (let room of myChatDB.chatRooms) {
        try {
                const docRef = firestore().collection('chats').doc(room.chatRoomName);
                const docSnapshot = await docRef.get();

                if (docSnapshot.exists) {
                    console.log("Document read successfully!");
                    const data = docSnapshot.data();
                    if(data.topChatId != room.topChatId) {
                        //need update
                        room.messages = data.messages;
                        room.topChatId = data.topChatId;
                    }
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
                                "opponentId" : getOpponentId(data2.chatRoomName),
                                "topChatId" : data2.topChatId,
                                "messages" : data2.messages,
                            }
                            myChatDB.chatRooms.push(newChatRoom);
                        }
                    } catch(error) {
                        console.error(error);
                    }
                }
            }
        } else {
            console.error("UserChat document doesn't exist");
        }
    } catch(error) {
        console.error("Error reading document:", error);
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
        if(room.opponentId == opponentId) {
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
    "opponentId" : string,
    "topChatId" : number,
    "messages" : chatMessage[],
}
interface chatDB {
    "chatRooms" : chatRoom[],
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
/* test functions */

