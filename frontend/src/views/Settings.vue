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
                        @message="showSnackBar"
                />
            </v-flex>
            <v-flex xs6 pl-3>
                <SignSettings
                        :signs="signs"
                        @update="updateSignsAndAnimalsData"
                        @message="showSnackBar"
                />
            </v-flex>
        </v-layout>
    </v-container>
</template>
<script>
    import eventBus from '../eventBus';
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
            };
        },
        beforeRouteEnter(to, from, next) {
            try {
                const allSymptoms = getAllSymptoms();
                const allDiagnoses = getAllDiagnoses();
                const allAnimals = getAllAnimals();
                const allSigns = getAllSigns();
                next(async (vm) => {
                    vm.symptoms = (await allSymptoms).sort(compareById);
                    vm.diagnoses = (await allDiagnoses).sort(compareById);
                    vm.animals = (await allAnimals).sort(compareById);
                    vm.signs = (await allSigns).sort(compareById);
                });
            } catch (e) {
                next(false);
                this.showSnackBar(`Ошибка: ${e.response.data.message}`);
            }
        },
        methods: {
            async saveDiagnosis(item) {
                try {
                    if (item.id) {
                        await updateDiagnosis(item);
                    } else {
                        await addNewDiagnosis(item);
                    }
                    this.showSuccessSnackBar();
                    this.updateDiagnosesData();
                } catch (e) {
                    this.showSnackBar(`Ошибка: ${e.response.data.message}`);
                }
            },
            async saveSymptom(item) {
                try {
                    if (item.id) {
                        await updateSymptom(item);
                    } else {
                        await addNewSymptom(item.name);
                    }
                    this.showSuccessSnackBar();
                    this.updateSymptomsData();
                } catch (e) {
                    this.showSnackBar(`Ошибка: ${e.response.data.message}`);
                }
            },
            async removeSymptom(id) {
                try {
                    await deleteSymptom(id);
                    this.showSuccessSnackBar();
                    this.updateSymptomsData();
                } catch (e) {
                    this.showSnackBar(`Ошибка: ${e.response.data.message}`);
                }
            },

            async removeDiagnosis(id) {
                try {
                    await deleteDiagnosis(id);
                    this.showSuccessSnackBar();
                    this.updateDiagnosesData();
                } catch (e) {
                    this.showSnackBar(`Ошибка: ${e.response.data.message}`);
                }
            },

            showSuccessSnackBar() {
                this.showSnackBar('Успешно');
            },

            showSnackBar(message) {
                eventBus.$emit('message', message);
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
                this.symptoms = (await getAllSymptoms()).sort(compareById);
            },

            async updateDiagnosesData() {
                this.diagnoses = (await getAllDiagnoses()).sort(compareById);
            },
        },

    };
</script>

<style scoped>

</style>
