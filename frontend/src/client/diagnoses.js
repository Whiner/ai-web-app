import axios from 'axios';

const URL = '/api/diagnoses';

export async function getAllSymptoms() {
    const response = await axios.get(`${URL}/symptoms`);
    return response.data;
}

export async function getAllDiagnoses() {
    const response = await axios.get(`${URL}/all`);
    return response.data;
}

export async function addNewSymptom(name) {
    const response = await axios.post(`${URL}/symptom/add`, null, {
        params: {
            name,
        },
    });
    return response.data;
}

export async function getDiagnosesBySymptoms(symptoms) {
    const response = await axios.post(
        `${URL}/diagnoses-by-symptoms`,
        { symptoms },
        null,
    );
    return response.data;
}

export async function addNewDiagnosis(item) {
    const response = await axios.post(`${URL}/add`,
        {
            name: item.name,
            symptoms: item.symptoms,
        });
    return response.data;
}

export async function updateDiagnosis(item) {
    const response = await axios.post(`${URL}/update`, item);
    return response.data;
}
