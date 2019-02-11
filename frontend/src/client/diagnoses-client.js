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
    await axios.post(`${URL}/symptom/add`, null, {
        params: {
            name,
        },
    });
}

export async function getDiagnosesBySymptoms(symptoms, confidence) {
    const response = await axios.post(
        `${URL}/diagnoses-by-symptoms`,
        { symptoms },
        {
            params: {
                confidence,
            },
        },
    );
    return response.data;
}

export async function addNewDiagnosis(item) {
    await axios.post(`${URL}/add`,
        {
            name: item.name,
            symptoms: item.symptoms,
        });
}

export async function updateDiagnosis(item) {
    await axios.post(`${URL}/${item.id}/update`, item);
}

export async function updateSymptom(item) {
    await axios.post(`${URL}/symptom/update`, item);
}

export async function deleteDiagnosis(id) {
    await axios.post(`${URL}/${id}/del`);
}

export async function deleteSymptom(id) {
    await axios.post(`${URL}/symptom/${id}/del`);
}
