<template>
    <ListCard :items="symptoms" title="Список симптомов" text-field-label="Введите симптом" />
</template>

<script>
    import ListCard from './ListCard.vue';

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
    };
</script>

<style scoped lang="stylus">
  .py-10px {
    padding-top 10x
    padding-bottom 10px
  }
</style>
