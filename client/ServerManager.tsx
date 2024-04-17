import axios from 'axios';
import {loadListScreen} from './LocalDataManager';
import {userId} from './LoginManager';

const path = 'http://34.64.142.137:8080/'; // GCP path

/* export functions */

export async function postConstruction(data) {
    //convert construction
    await serverPost(path + 'api/construction',convertConstruction(data));
}


/* helper functions */

function convertConstruction(data) {
    const newData = {
        //"userId" : userId,
        "userId" : 1, //tmp
        "kind" : 0, //tmp
        "schedule" : data.schedule,
        "constructionSite" : data.constructionSite,
        "memo" : data.memo,
    };
    return newData;
}

async function serverRequest(url) {
    try {
        const response = await axios.get(url);
        console.log("Server Response : ", response.data);
    } catch (error) {
        console.error("serverRequest error : ", error);
        throw error;
    }
}

async function serverPost(url,data) {
    try {
        const response = await axios.post(url, data);
        console.log("Server Response : ", response.data);
    } catch (error) {
        console.error("serverPost error : ", error.response);
    }
}


/* test functions */

export async function test_construction_upload() {
    const constructionDB = loadListScreen();
    postConstruction(constructionDB[0]);
}

export async function say_hello() {
    try {
        await serverRequest(path + "test/hello");
    } catch (error) {
        console.error("say_hello error: ", error);
    }
}
