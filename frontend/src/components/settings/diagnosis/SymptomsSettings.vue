<template>
    <div>
        <ListCard
                :items="symptoms"
                title="Список симптомов"
                text-field-label="Введите симптом"
                @add="openAddSymptomDialog"
                @edit="openEditSymptomDialog"
                @remove="onRemove"
        />
        <SymptomDialog ref="symptomAddDialog" @accept="$emit('update')" />
    </div>
</template>

<script>
    import ListCard from './ListCard.vue';
    import SymptomDialog from './SymptomDialog.vue';
    import { deleteSymptom } from '../../../client/diagnoses-client';

    export default {
        name: 'SymptomsSettings',
        components: {
            SymptomDialog,
            ListCard,
        },
        props: {
            symptoms: {
                type: Array,
                default: null,
            },
        },
        methods: {
            openAddSymptomDialog() {
                this.$refs.symptomAddDialog.openToAdd();
            },
            openEditSymptomDialog(item) {
                this.$refs.symptomAddDialog.openToEdit(item);
            },

            async onRemove(item) {
                try {
                    await deleteSymptom(item.id);
                    this.showSuccessSnackBar();
                    this.$emit('update');
                } catch (e) {
                    this.$emit('message', `Ошибка: ${e.response.data.message}`);
                }
            },
            showSuccessSnackBar() {
                this.$emit('message', 'Успешно');
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
