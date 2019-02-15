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
        props: {
            symptoms: {
                type: Array,
                default: null,
            },
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
