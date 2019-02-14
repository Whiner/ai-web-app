<template>
    <ListCard
            :items="symptoms"
            title="Список симптомов"
            text-field-label="Введите симптом"
            @save="addSymptom"
    />
</template>

<script>
    import ListCard from './ListCard.vue';
    import { addNewSymptom } from '../../client/diagnoses';

    export default {
        name: 'SymptomsSettings',
        components: { ListCard },
        data() {
            return {
                symptoms: [
                    {
                        id: 1,
                        name: 'Шишка на жопе',
                    },
                    {
                        id: 2,
                        name: 'Жопа на шишке',
                    },
                    {
                        id: 3,
                        name: 'Жишка на шопе',
                    },
                    {
                        id: 4,
                        name: 'Нишка жа шопе',
                    },
                    {
                        id: 5,
                        name: 'Жишка ша нопе',
                    },
                ],
            };
        },
        methods: {
            async addSymptom(name) {
                await addNewSymptom(name);
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
