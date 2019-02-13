<template>
    <v-container fluid>
        <v-layout v-if="false" row>
            <v-flex>
                <v-card>
                    <v-card-text py-2>
                        <v-switch
                                class="ma-1"
                                value
                                hide-details
                                label="Использовать коэфиценты доверия?"
                                color="primary"
                        />
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
        <v-layout row mt-3>
            <v-flex xs6 mr-3>
                <SymptomsChoice :symptoms="symptoms" />
            </v-flex>
            <v-flex v-if="diagnosis" xs6 ml-3>
                <DiagnosisResult :diagnosis="diagnosis" />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import SymptomsChoice from '../components/SymptomsChoice.vue';
    import DiagnosisResult from '../components/DiagnosisResult.vue';

    export default {
        name: 'Diagnosis',
        components: { DiagnosisResult, SymptomsChoice },
        data() {
            return {
                diagnosis: {
                    id: 1,
                    name: 'Острая хроническая жопная боль',
                },
                symptoms: [
                    {
                        id: 1,
                        name: 'Шишка на жопе',
                        checked: false,
                    },
                    {
                        id: 2,
                        name: 'Жопа на шишке',
                        checked: false,
                    },
                    {
                        id: 3,
                        name: 'Жишка на шопе',
                        checked: false,
                    },
                    {
                        id: 4,
                        name: 'Нишка жа шопе',
                        checked: false,
                    },
                    {
                        id: 5,
                        name: 'Жишка ша нопе',
                        checked: false,
                    },
                    {
                        id: 6,
                        name: 'Головная боль',
                        checked: false,
                    },
                ],
            };
        },
    };
</script>

<style scoped>

</style>
