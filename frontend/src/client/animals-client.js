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

export async function getAllAnimals() {
    const response = await axios.get(`${URL}/animals`);
    return response.data;
}

export async function addAnimal(animal) {
    await axios.post(`${URL}/animal/add`, animal);
}

export async function addSign(sign) {
    await axios.post(`${URL}/sign/add`, sign);
}

export async function updateAnimal(id, animal) {
    await axios.post(`${URL}/animal/${id}`, animal);
}

export async function updateSign(id, sign) {
    await axios.post(`${URL}/sign/${id}`, null, {
        params: {
            name: sign,
        },
    });
}

export async function deleteAnimal(id) {
    await axios.post(`${URL}/animal/${id}/del`);
}

export async function deleteSign(id) {
    await axios.post(`${URL}/sign/${id}/del`);
}
