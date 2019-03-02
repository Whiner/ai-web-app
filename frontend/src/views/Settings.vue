<template>
    <v-container fluid>
        <v-layout row>
            <v-flex xs6 pr-3>
                <DiagnosisSettings
                        :symptoms="symptoms"
                        :diagnoses="diagnoses"
                        @save="saveDiagnosis"
                        @remove="removeDiagnosis"
                />
            </v-flex>
            <v-flex xs6 pl-3>
                <SymptomsSettings
                        :symptoms="symptoms"
                        @save="saveSymptom"
                        @remove="removeSymptom"
                />
            </v-flex>
        </v-layout>
        <v-layout row mt-3>
            <v-flex xs6 pr-3>
                <AnimalSettings
                        :signs="signs"
                        :animals="animals"
                        @update="updateAnimalsData"
                />
            </v-flex>
            <v-flex xs6 pl-3>
                <SignSettings
                        :signs="signs"
                        @update="updateSignsAndAnimalsData"
                />
            </v-flex>
        </v-layout>
        <v-snackbar
                v-model="snackbar"
                :timeout="timeout"
                right
                bottom
        >
            {{ text }}
            <v-btn
                    color="pink"
                    flat
                    @click="snackbar = false"
            >
                Закрыть
            </v-btn>
        </v-snackbar>
    </v-container>
</template>
<script>
  //TODO: придумать где добавлять признаки и животных
    import DiagnosisSettings from '../components/settings/DiagnosisSettings.vue';
    import SymptomsSettings from '../components/settings/SymptomsSettings.vue';
    import AnimalSettings from '../components/settings/AnimalSettings.vue';
    import {
        addNewDiagnosis,
        addNewSymptom,
        deleteDiagnosis,
        deleteSymptom,
        getAllDiagnoses,
        getAllSymptoms,
        updateDiagnosis,
        updateSymptom,
    } from '../client/diagnoses-client';
    import compareById from '../utils/compareUtil';
    import { getAllAnimals, getAllSigns } from '../client/animals-client';
    import SignSettings from '../components/settings/signs/SignSettings.vue';

    export default {
        name: 'Settings',
        components: {
            SignSettings,
            SymptomsSettings,
            DiagnosisSettings,
            AnimalSettings,
        },
        data() {
            return {
                symptoms: null,
                diagnoses: null,
                animals: null,
                signs: null,
                snackbar: false,
                timeout: 3000,
                text: '',
            };
        },
        beforeRouteEnter(to, from, next) {
            const allSymptoms = getAllSymptoms();
            const allDiagnoses = getAllDiagnoses();
            const allAnimals = getAllAnimals();
            const allSigns = getAllSigns();
            next(async (vm) => {
                vm.symptoms = (await allSymptoms).symptoms.sort(compareById);
                vm.diagnoses = (await allDiagnoses).diagnoses.sort(compareById);
                vm.animals = (await allAnimals).sort(compareById);
                vm.signs = (await allSigns).sort(compareById);
            });
        },
        methods: {
            async saveDiagnosis(item) {
                const result = {
                    code: 0,
                    message: '',
                };
                if (item.id) {
                    const response = await updateDiagnosis(item);
                    console.log(response);
                    result.code = response.code;
                    result.message = response.message;
                } else {
                    const response = await addNewDiagnosis(item);
                    console.log(response);
                    result.code = response.code;
                    result.message = response.message;
                }
                if (result.code === 200) {
                    this.text = 'Успешно';
                    this.snackbar = true;
                    this.updateDiagnosesData();
                } else {
                    if (result.message) {
                        this.text = `Ошибка: ${result.message}`;
                    } else {
                        this.text = 'Ошибка';
                    }
                    this.snackbar = true;
                }
            },
            async saveSymptom(item) {
                let result;
                if (item.id) {
                    result = await updateSymptom(item);
                } else {
                    result = await addNewSymptom(item.name);
                }

                if (result === true || result.code === 200) {
                    this.showSuccessSnackBar();
                    this.updateSymptomsData();
                } else {
                    this.showErrorSnackBar(`Ошибка: ${result.message}`);
                }
            },
            async removeSymptom(id) {
                const response = await deleteSymptom(id);
                if (response.code === 200) {
                    this.showSuccessSnackBar();
                    this.updateSymptomsData();
                } else {
                    this.showErrorSnackBar(`Ошибка: ${response.message}`);
                }
            },

            async removeDiagnosis(id) {
                const response = await deleteDiagnosis(id);
                if (response === true || response.code === 200) {
                    this.showSuccessSnackBar();
                    this.updateDiagnosesData();
                } else {
                    this.showErrorSnackBar(`Ошибка: ${response.message}`);
                }
            },

            showSuccessSnackBar() {
                this.text = 'Успешно';
                this.snackbar = true;
            },
            showErrorSnackBar(message) {
                this.text = message;
                this.snackbar = true;
            },
            async updateAnimalsData() {
                this.animals = (await getAllAnimals()).sort(compareById);
            },

            async updateSignsData() {
                this.signs = (await getAllSigns()).sort(compareById);
            },

            updateSignsAndAnimalsData() {
                this.updateSignsData();
                this.updateAnimalsData();
            },

            async updateSymptomsData() {
                this.symptoms = (await getAllSymptoms()).symptoms.sort(compareById);
            },

            async updateDiagnosesData() {
                this.diagnoses = (await getAllDiagnoses()).diagnoses.sort(compareById);
            },


        },

    };
</script>

<style scoped>

</style>
