import axios from 'axios';

const URL = '/api/animals';

export async function nextQuestion(lastAnswer) {
    const response = await axios.get(`${URL}/next-question`, {
        params: {
            lastAnswer,
        },
    });
    return response.data;
}

export async function getAnswer() {
    const response = await axios.get(`${URL}/answer`);
    return response.data;
}

export async function clear() {
    await axios.post(`${URL}/clear`);
}

export async function getAllSigns() {
    const response = await axios.get(`${URL}/signs`);
    return response.data;
}
