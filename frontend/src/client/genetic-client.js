import axios from 'axios';

export const URL = '/api/genetic';

export async function getExtremum(
    lowerInterval,
    upperInterval,
    chromosomesCount,
    maxIterationsCount,
    mutationChance,
    crossingChance,
) {
    const response = await axios.get(`${URL}/extremum`, {
        params: {
            lowerInterval,
            upperInterval,
            chromosomesCount,
            maxIterationsCount,
            mutationChance,
            crossingChance,
        },
    });
    return response.data;
}
