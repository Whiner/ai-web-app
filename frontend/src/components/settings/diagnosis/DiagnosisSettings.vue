<template>
    <div>
        <ListCard
                :items="diagnoses"
                title="Список диагнозов"
                text-field-label="Введите диагноз"
                @add="onAdd"
                @edit="onEdit"
                @remove="onRemove"
        />
        <DiagnosisDialog
                ref="symptomChoiceDialog"
                @update="update"
        />
    </div>
</template>

<script>
    import ListCard from './ListCard.vue';
    import DiagnosisDialog from './DiagnosisDialog.vue';
    import { deleteDiagnosis } from '../../../client/diagnoses-client';

    export default {
        name: 'DiagnosisSettings',
        components: { DiagnosisDialog, ListCard },
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
            };
        },

        methods: {
            onAdd() {
                this.$refs.symptomChoiceDialog.openToAdd(this.symptoms);
            },
            onEdit(item) {
                this.$refs.symptomChoiceDialog.openToEdit(item, this.symptoms);
            },

            update() {
                this.$emit('update');
            },
            async onRemove(item) {
                try {
                    await deleteDiagnosis(item.id);
                    this.$emit('message', 'Успешно');
                    this.update();
                } catch (e) {
                    this.$emit('message', `Ошибка: ${e.response.data.message}`);
                }
            },
        },
    };
</script>

<style scoped lang="stylus">


</style>
