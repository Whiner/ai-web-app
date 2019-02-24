import axios from 'axios';

const URL = '/api/animals';

export async function nextQuestion(lastAnswer) {
    const response = await axios.get(`${URL}/nextQuestion`, {
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
