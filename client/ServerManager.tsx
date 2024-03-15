import axios from 'axios';


export async function serverRequest(url) {
    await axios.get(url)
    .then((response) => {
        console.log(response.data);
    })
    .catch((error) => {
        console.log("serverRequest error : ",error);
    }
}
