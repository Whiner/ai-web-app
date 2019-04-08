<template>
    <v-container fluid>
        <v-layout row>
            <v-flex>
                <v-card>
                    <v-card-text py-2>
                        <v-switch
                                v-model="confidence"
                                class="ma-1"
                                value
                                hide-details
                                label="Использовать коэфиценты доверия?"
                                color="secondary"
                        />
                    </v-card-text>
                </v-card>
            </v-flex>
        </v-layout>
        <v-layout row mt-3>
            <v-flex xs6 mr-3>
                <SymptomsChoice :symptoms="symptoms" @check="checkDiagnoses" />
            </v-flex>
            <transition name="component-fade" mode="out-in">
                <v-flex v-if="diagnoses" xs6 ml-3>
                    <DiagnosisResult :diagnoses="diagnoses" :confidence="confidence" @add="addNewDiagnosis" />
                </v-flex>
            </transition>
        </v-layout>
        <DiagnosisDialog ref="addDialog" />
    </v-container>
</template>

<script>
    import SymptomsChoice from '../components/diagnoses/SymptomsChoice.vue';
    import DiagnosisResult from '../components/diagnoses/DiagnosisResult.vue';
    import { getAllSymptoms, getDiagnosesBySymptoms } from '../client/diagnoses-client';
    import { compareById } from '../utils/compare-util';
    import DiagnosisDialog from '../components/settings/diagnosis/DiagnosisDialog.vue';

    export default {
        name: 'Diagnosis',
        components: {
            DiagnosisDialog,
            DiagnosisResult,
            SymptomsChoice,
        },
        data() {
            return {
                confidence: false,
                diagnoses: null,
                symptoms: null,
            };
        },
        async beforeRouteEnter(to, from, next) {
            const allSymptoms = await getAllSymptoms();
            next((vm) => {
                vm.symptoms = allSymptoms.sort(compareById);
            });
        },
        methods: {
            async checkDiagnoses(symptoms) {
                this.diagnoses = await getDiagnosesBySymptoms(symptoms, this.confidence);
            },
            addNewDiagnosis() {
                this.$refs.addDialog.openToAdd(this.symptoms);
            },
        },
    };
</script>

<style scoped lang="stylus">
    .theme--light >>> .v-label {
        color var(--secondary-base)
    }

    .component-fade-enter-active, .component-fade-leave-active {
        transition: opacity .5s ease;
    }

    .component-fade-enter, .component-fade-leave-to {
        opacity: 0;
    }
</style>
