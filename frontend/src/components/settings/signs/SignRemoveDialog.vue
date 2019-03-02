<template>
    <v-dialog v-model="show"
              persistent
              max-width="350"
              max-height="200"
    >
        <v-card>
            <v-card-title>
                <p class="pb-1 mb-0 headline">
                    Вы уверены?
                </p>
            </v-card-title>
            <v-card-text>
                <p>Удаление данного признака повлечет за собой удаление всех животных, имеющих данный признак</p>
            </v-card-text>
            <v-card-actions class="justify-center">
                <v-btn color="error" flat @click="accept">
                    Удалить
                </v-btn>
                <v-btn flat @click="dismiss">
                    Отмена
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: 'SignRemoveDialog',
        props: {
            show: {
                type: Boolean,
            },
        },
        methods: {
            accept() {
                this.$emit('accept');
            },
            dismiss() {
                this.$emit('dismiss');
            },
        },
    };
</script>

<style scoped>

</style>
