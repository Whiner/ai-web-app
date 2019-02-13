<template>
    <v-container fluid>
        <v-layout mt-3 row>
            <v-flex>
                <p class="header">
                    Хочешь узнать что с тобой?
                </p>
            </v-flex>
        </v-layout>
        <v-layout row>
            <SymptomsChoice :symptoms="symptoms" />
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
                        name: 'Шишка на жопе',
                        checked: false,
                    },
                    {
                        id: 3,
                        name: 'Шишка на жопе',
                        checked: false,
                    },
                    {
                        id: 4,
                        name: 'Шишка на жопе',
                        checked: false,
                    },
                    {
                        id: 5,
                        name: 'Шишка на жопе',
                        checked: false,
                    },
                ],
            };
        },
    };
</script>

<style scoped>

</style>
