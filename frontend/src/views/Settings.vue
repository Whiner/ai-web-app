<template>
    <v-container fluid>
        <v-layout row>
            <v-flex xs6 mr-3>
                <DiagnosisSettings
                        :symptoms="symptoms"
                        :diagnoses="diagnoses"
                        @save="saveDiagnosis"
                />
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
    import { addNewDiagnosis, getAllDiagnoses, getAllSymptoms, updateDiagnosis } from '../client/diagnoses';
    import compareById from '../utils/compareUtil';

    export default {
        name: 'Settings',
        components: { SymptomsSettings, DiagnosisSettings },
        data() {
            return {
                symptoms: null,
                diagnoses: null,
            };
        },

        beforeRouteEnter(to, from, next) {
            const allSymptoms = getAllSymptoms();
            const allDiagnoses = getAllDiagnoses();
            next(async (vm) => {
                vm.symptoms = (await allSymptoms).symptoms.sort(compareById);
                vm.diagnoses = (await allDiagnoses).diagnoses.sort(compareById);
            });
        },
        methods: {
            async saveDiagnosis(item) {
                let result;
                if (item.id) {
                    const newVar = (await updateDiagnosis(item));
                    result = newVar.code;
                    console.log(newVar.message);
                } else {
                    result = await addNewDiagnosis(item);
                }
                console.log(result);
            },
        },

    };
</script>

<style scoped>

</style>
