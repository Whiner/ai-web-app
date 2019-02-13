<template>
    <ListCard :items="diagnoses" title="Список диагнозов" text-field-label="Введите диагноз" />
</template>

<script>
    import ListCard from './ListCard.vue';

    export default {
        name: 'DiagnosisSettings',
        components: { ListCard },
        data() {
            return {
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
