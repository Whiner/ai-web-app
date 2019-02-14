<template>
    <div>
        <ListCard
                :items="diagnoses"
                title="Список диагнозов"
                text-field-label="Введите диагноз"
                @save="showSymptomChoice"
        />
        <v-dialog v-model="dialog" persistent max-width="350">
            <v-card>
                <v-card-title class="headline">
                    Выберите симптомы данного заболевания
                </v-card-title>
                <v-card-text v-if="symptoms" class="container fluid">
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
                <v-card-actions>
                    <v-btn color="green darken-1" flat @click="dialog = false">
                        Сохранить
                    </v-btn>
                    <v-btn color="green darken-1" flat @click="dialog = false">
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
        },
        data() {
            return {
                dialog: false,
                diagnoses: [
                    {
                        id: 1,
                        name: 'Диагноз 1',
                    },
                    {
                        id: 2,
                        name: 'Диагноз 2',
                    },
                    {
                        id: 3,
                        name: 'Диагноз 3',
                    },
                    {
                        id: 4,
                        name: 'Диагноз 4',
                    },
                    {
                        id: 5,
                        name: 'Диагноз 5',
                    },
                    {
                        id: 6,
                        name: 'Диагноз 6',
                    },
                ],
            };
        },
        methods: {
            showSymptomChoice(name) {
                this.dialog = true;
                console.log(name);
            },
        },
    };
</script>

<style scoped lang="stylus">
  .py-10px {
    padding-top 10x
    padding-bottom 10px
  }
</style>
