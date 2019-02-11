
export function getById(array, id) {
    for (let i = 0; i < array.length; i += 1) {
        if (array[i].id === id) {
            return array[i];
        }
    }
    return null;
}
export function cloneArray(array) {
    return JSON.parse(JSON.stringify(array));
}
