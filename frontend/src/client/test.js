import axios from 'axios';

export default async function test() {
    const newVar = await axios.get('/test');
    return newVar.data;
}
