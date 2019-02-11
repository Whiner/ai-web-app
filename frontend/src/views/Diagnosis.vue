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
            <v-flex v-if="diagnoses" xs6 ml-3>
                <DiagnosisResult :diagnoses="diagnoses" :confidence="confidence" />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import SymptomsChoice from '../components/diagnoses/SymptomsChoice.vue';
    import DiagnosisResult from '../components/diagnoses/DiagnosisResult.vue';
    import { getAllSymptoms, getDiagnosesBySymptoms } from '../client/diagnoses-client';
    import { compareById } from '../utils/compare-util';

    export default {
        name: 'Diagnosis',
        components: {
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
        },
    };
</script>

<style scoped lang="stylus">
  .theme--light >>> .v-label {
    color var(--secondary-base)
  }
</style>
