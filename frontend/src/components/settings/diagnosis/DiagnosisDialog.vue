<template>
    <div>
        <v-dialog v-model="show"
                  persistent
                  max-width="650"
                  max-height="350"
        >
            <v-card>
                <v-card-title class="pb-0">
                    <v-container fluid pa-0>
                        <v-layout row>
                            <v-flex class="title">
                                Выберите симптомы данного заболевания
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-card-title>
                <v-card-text class="py-0 my-0 px-0">
                    <v-container v-if="symptoms"
                                 fluid
                                 py-0
                                 px-0
                    >
                        <v-layout row mt-3 px-2>
                            <v-flex xs7 pl-4>
                                Симптом
                            </v-flex>
                            <v-flex xs2 class="text-xs-center">
                                Доверие
                            </v-flex>
                            <v-flex xs2 class="text-xs-center">
                                Недоверие
                            </v-flex>
                        </v-layout>
                        <v-divider />
                        <v-layout row pl-3>
                            <v-flex class="container fluid py-0 pl-0 pr-2 no-overflow">
                                <v-layout
                                        v-for="symptom in symptoms"
                                        :key="symptom.id"
                                        row
                                        :class="getItemColor(symptom)"
                                >
                                    <v-flex xs8 ml-3 align-self-center>
                                        <v-checkbox
                                                v-model="symptom.checked"
                                                hide-details
                                                color="info"
                                                :label="symptom.name"
                                                class="mt-0"
                                        />
                                    </v-flex>
                                    <v-flex xs1 align-self-center>
                                        <p class="ma-0">
                                            {{ symptom.confidence }}
                                        </p>
                                    </v-flex>
                                    <v-flex xs1 offset-xs1 align-self-center>
                                        <p class="ma-0">
                                            {{ symptom.mistrust }}
                                        </p>
                                    </v-flex>
                                    <v-flex xs1 class="text-xs-right" align-self-center>
                                        <v-btn icon
                                               color="info"
                                               flat
                                               :disabled="!symptom.checked"
                                               @click="editFactors(symptom)"
                                        >
                                            <v-icon>edit</v-icon>
                                        </v-btn>
                                    </v-flex>
                                </v-layout>
                            </v-flex>
                        </v-layout>
                        <v-divider />
                        <v-layout row
                                  mt-2
                                  mb-2
                                  ml-1
                                  pr-4
                                  pl-3
                        >
                            <v-form ref="form" class="flex">
                                <v-text-field
                                        v-model="name"
                                        label="Название"
                                        :rules="rules"
                                        prepend-icon="build"
                                />
                            </v-form>
                        </v-layout>
                        <transition name="component-fade" mode="out-in">
                            <v-layout v-show="alert" row mt-1>
                                <v-flex>
                                    <v-alert
                                            :value="true"
                                            color="error"
                                            icon="warning"
                                            outline
                                    >
                                        {{ alertText }}
                                    </v-alert>
                                </v-flex>
                            </v-layout>
                        </transition>
                    </v-container>
                </v-card-text>
                <v-divider />
                <v-card-actions class="justify-center">
                    <v-btn color="green darken-1"
                           flat
                           :loading="loading"
                           @click="save"
                    >
                        Сохранить
                    </v-btn>
                    <v-btn color="red darken-1" flat @click="cancel">
                        Отмена
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <FactorDialog ref="factorDialog" @accept="onFactorDialogAccept" />
    </div>
</template>

<script>
    import { addNewDiagnosis, updateDiagnosis } from '../../../client/diagnoses-client';
    import { cloneArray, getById } from '../../../utils/array-util';
    import eventBus from '../../../eventBus';
    import FactorDialog from './FactorDialog.vue';

    export default {
        name: 'DiagnosisDialog',
        components: { FactorDialog },
        data() {
            return {
                name: '',
                symptoms: null,
                editingItem: null,
                show: false,
                rules: [
                    value => !!value || 'Поле не должно быть пустым',
                ],
                alertText: '',
                alert: false,
                loading: false,
                editingSymptomFactor: null,
            };
        },
        watch: {
            symptoms() {
                this.fillCheckedField(this.symptoms);
                if (this.editingItem.symptoms) {
                    this.editingItem.symptoms.forEach((checkedSymptom) => {
                        const byId = getById(this.symptoms, checkedSymptom.id);
                        byId.checked = true;
                        byId.confidence = checkedSymptom.confidence;
                        byId.mistrust = checkedSymptom.mistrust;
                    });
                }
            },
        },
        methods: {
            getItemColor(item) {
                return item.checked ? 'active-item' : 'inactive-item';
            },
            openToEdit(item, symptoms) {
                this.symptoms = cloneArray(symptoms);
                this.editingItem = item;
                this.name = this.editingItem.name;
                this.show = true;
            },
            openToAdd(symptoms) {
                this.editingItem = {};
                this.name = '';
                this.symptoms = cloneArray(symptoms);
                this.show = true;
            },
            editFactors(item) {
                this.editingSymptomFactor = item;
                this.$refs.factorDialog.open(item.confidence, item.mistrust);
            },
            close() {
                this.show = false;
            },
            onFactorDialogAccept(confidence, mistrust) {
                this.editingSymptomFactor.confidence = confidence;
                this.editingSymptomFactor.mistrust = mistrust;
            },
            async save() {
                if (this.$refs.form.validate()) {
                    try {
                        this.loading = true;
                        this.editingItem.symptoms = this.symptoms.filter(value => value.checked);
                        this.editingItem.name = this.name;
                        if (this.editingItem.id) {
                            await updateDiagnosis(this.editingItem);
                        } else {
                            await addNewDiagnosis(this.editingItem);
                        }
                        this.close();
                        this.$emit('update');
                        eventBus.$emit('message', 'Успешно');
                    } catch (e) {
                        eventBus.$emit('message', `Ошибка: ${e.response.data.message}`);
                    } finally {
                        this.loading = false;
                    }
                }
            },
            cancel() {
                this.close();
            },
            fillCheckedField(array) {
                array.forEach((value) => {
                    console.log(value);
                    this.$set(value, 'checked', value.checked);
                    this.$set(value, 'confidence', 0.5);
                    this.$set(value, 'mistrust', 0.25);
                });
            },
        },

    };
</script>

<style scoped lang="stylus">
    .no-overflow {
        max-height 55vh
        min-height 20vh
        overflow-y auto
    }

    .inactive-item {
        transition 0.1s
        color #9f9f9f
    }

    .active-item {
        transition 0.1s
        color var(--secondary-base)
    }

    .theme--light >>> .v-label {
        color var(--secondary-base)
    }
</style>
