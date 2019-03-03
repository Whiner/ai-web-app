<template>
    <v-dialog v-model="show"
              persistent
              max-width="350"
              max-height="200"
    >
        <v-card>
            <v-card-text class="container fluid pb-0">
                <v-layout row justify-center>
                    <v-flex xs12>
                        <v-text-field
                                v-model="localName"
                                label="Название"
                                prepend-icon="build"
                                :rules="rules"
                        />
                    </v-flex>
                </v-layout>
            </v-card-text>
            <v-card-actions class="justify-center">
                <v-btn color="success"
                       flat
                       :loading="loading"
                       @click="accept"
                >
                    Сохранить
                </v-btn>
                <v-btn color="error" flat @click="dismiss">
                    Отмена
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: 'SignEditDialog',
        props: {
            show: {
                type: Boolean,
            },
            name: {
                type: String,
                default: '',
            },
            loading: {
                type: Boolean,
            },
        },
        data() {
            return {
                localName: '',
                rules: [
                    v => (v && v.length > 0 ? true : 'Поле не должно быть пустым'),
                    v => (v && v.trim().length > 0 ? true : 'Поле не должно состоять только из пробелов'),
                ],
            };
        },
        watch: {
            name(newVal) {
                this.localName = newVal;
            },
        },
        methods: {
            accept() {
                this.$emit('accept', this.localName);
            },
            dismiss() {
                this.$emit('dismiss');
            },
        },
    };
</script>

<style scoped>

</style>
