
export function compareByField(a, b, fieldName) {
    if (a[fieldName] > b[fieldName]) return 1;
    if (a[fieldName] < b[fieldName]) return -1;
    return 0;
}

export function compareById(a, b) {
    return compareByField(a, b, 'id');
}

export function compareByConfidence(a, b) {
    return compareByField(a, b, 'confidence');
}
