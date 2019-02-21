<template>
    <ListCard
            :items="symptoms"
            title="Список симптомов"
            text-field-label="Введите симптом"
            @save-new="addSymptom"
            @save-old="updateSymptom"
            @remove="remove"
    />
</template>

<script>
  import ListCard from './ListCard.vue';

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
            addSymptom(name) {
                this.$emit('save', { name });
            },
            updateSymptom(item) {
                this.$emit('save', item);
            },
            remove(item) {
                this.$emit('remove', item.id);
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
