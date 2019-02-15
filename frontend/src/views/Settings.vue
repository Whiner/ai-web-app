<template>
    <v-container fluid>
        <v-layout row>
            <v-flex xs6 mr-3>
                <DiagnosisSettings :symptoms="symptoms" :diagnoses="diagnoses" />
            </v-flex>
            <v-flex xs6 ml-3>
                <SymptomsSettings :symptoms="symptoms" />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import DiagnosisSettings from '../components/settings/DiagnosisSettings.vue';
    import SymptomsSettings from '../components/settings/SymptomsSettings.vue';
    import { getAllDiagnoses, getAllSymptoms } from '../client/diagnoses';

    export default {
        name: 'Settings',
        components: { SymptomsSettings, DiagnosisSettings },
        data() {
            return {
                symptoms: null,
                diagnoses: null,
            };
        },
        async created() {
            const allSymptoms = getAllSymptoms();
            const allDiagnoses = getAllDiagnoses();

            this.symptoms = (await allSymptoms).symptoms;
            this.diagnoses = (await allDiagnoses).diagnoses;
        },
    };
</script>

<style scoped>

</style>
