<template>
    <v-container fluid>
        <v-layout row>
            <v-flex>
                <v-card>
                    <v-card-text py-2>
                        <v-switch
                                class="ma-1"
                                value
                                hide-details
                                label="Использовать коэфицент доверия?"
                                color="primary"
                        />
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
        <v-layout row mt-3 justify-center>
            <v-flex xs6>
                <SymptomsChoice :symptoms="symptoms" />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import SymptomsChoice from '../components/SymptomsChoice.vue';

    export default {
        name: 'Diagnosis',
        components: { SymptomsChoice },
        data() {
            return {
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
                ],
            };
        },
    };
</script>

<style scoped>

</style>
