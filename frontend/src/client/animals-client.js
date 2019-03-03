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
    const response = await axios.post(`${URL}/animal/add`, animal);
    return response.data;
}

export async function addSign(sign) {
    const response = await axios.post(`${URL}/sign/add`, null, {
        params: {
            name: sign,
        },
    });
    return response.data;
}

export async function updateAnimal(id, animal) {
    const response = await axios.post(`${URL}/animal/${id}`, animal);
    return response.data;
}

export async function updateSign(id, sign) {
    const response = await axios.post(`${URL}/sign/${id}`, null, {
        params: {
            name: sign,
        },
    });
    return response.data;
}

export async function deleteAnimal(id) {
    const response = await axios.post(`${URL}/animal/${id}/del`);
    return response.data;
}

export async function deleteSign(id) {
    const response = await axios.post(`${URL}/sign/${id}/del`);
    return response.data;
}
