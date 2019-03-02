<template>
    <div>
        <ListCard
                :items="diagnoses"
                title="Список диагнозов"
                text-field-label="Введите диагноз"
                @save-new="saveNew"
                @save-old="saveOld"
                @remove="remove"
        />
        <v-dialog v-model="dialog"
                  persistent
                  max-width="650"
                  max-height="300"
        >
            <v-card>
                <v-card-title class="title pb-2">
                    Выберите симптомы данного заболевания
                </v-card-title>
                <v-divider />
                <v-card-text v-if="symptoms" class="container fluid pt-2 no-overflow">
                    <v-layout
                            v-for="symptom in symptoms"
                            :key="symptom.id"
                            row
                    >
                        <v-flex>
                            <v-checkbox
                                    v-model="symptom.checked"
                                    class="dark-color"
                                    hide-details
                                    color="secondary"
                                    :label="symptom.name"
                            />
                        </v-flex>
                    </v-layout>
                </v-card-text>
                <v-divider />
                <v-card-actions>
                    <v-btn color="green darken-1" flat @click="save">
                        Сохранить
                    </v-btn>
                    <v-btn color="red darken-1" flat @click="cancel">
                        Отмена
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    import ListCard from './ListCard.vue';


    export default {
        name: 'DiagnosisSettings',
        components: { ListCard },
        props: {
            symptoms: {
                type: Array,
                default: null,
            },
            diagnoses: {
                type: Array,
                default: null,
            },
        },
        data() {
            return {
                editingItem: null,
                dialog: false,
            };
        },
        watch: {
            symptoms() {
                this.fillCheckedField();
            },
        },
        methods: {
            saveNew(name) {
                this.editingItem = {
                    name,
                };
                this.showSymptomChoice();
            },
            saveOld(item) {
                this.editingItem = item;
                this.showSymptomChoice();
            },
            showSymptomChoice() {
                if (this.editingItem.symptoms) {
                    this.editingItem.symptoms.forEach((checkedSymptom) => {
                        const filtered = this.symptoms.filter(value => value.id === checkedSymptom.id);
                        filtered.forEach((filteredSymptom) => {
                            filteredSymptom.checked = true;
                        });
                    });
                } else {
                    this.fillCheckedField();
                }
                this.dialog = true;
            },

            fillCheckedField() {
                this.symptoms.forEach((value) => {
                    value.checked = false;
                });
            },

            save() {
                this.dialog = false;
                if (this.editingItem) {
                    this.editingItem.symptoms = this.symptoms.filter(v => v.checked);
                    this.$emit('save', this.editingItem);
                }
            },
            cancel() {
                this.dialog = false;
            },
            remove(item) {
                this.$emit('remove', item.id);
            },
        },
    };
</script>

<style scoped lang="stylus">
  .no-overflow {
    height: 500px;
    overflow-y: auto;
  }
</style>
