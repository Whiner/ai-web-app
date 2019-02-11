<template>
    <div>
        <v-dialog v-model="show"
                  persistent
                  max-width="450"
                  max-height="350"
        >
            <v-card>
                <v-card-title class="pb-0">
                    <v-container fluid pa-0>
                        <v-layout row>
                            <v-flex class="title">
                                Симптом
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-card-title>
                <v-divider />
                <v-card-text class="my-0">
                    <v-container fluid
                                 py-0
                                 px-0
                    >
                        <v-layout row>
                            <v-form ref="form" class="flex">
                                <v-text-field
                                        v-model="name"
                                        label="Название"
                                        :rules="rules"
                                        prepend-icon="build"
                                />
                            </v-form>
                        </v-layout>
                        <transition name="component-fade" mode="out-in">
                            <v-layout v-show="alert" row mt-1>
                                <v-flex>
                                    <v-alert
                                            :value="true"
                                            color="error"
                                            icon="warning"
                                            outline
                                    >
                                        {{ alertText }}
                                    </v-alert>
                                </v-flex>
                            </v-layout>
                        </transition>
                    </v-container>
                </v-card-text>
                <v-divider />
                <v-card-actions class="justify-center">
                    <v-btn color="green darken-1"
                           flat
                           :loading="loading"
                           @click="save"
                    >
                        Сохранить
                    </v-btn>
                    <v-btn color="red darken-1" flat @click="cancel">
                        Отмена
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    import { addNewSymptom, updateSymptom } from '../../../client/diagnoses-client';
    import eventBus from '../../../eventBus';

    export default {
        name: 'SymptomDialog',
        data() {
            return {
                name: '',
                editingItem: null,
                show: false,
                rules: [
                    value => !!value || 'Поле не должно быть пустым',
                ],
                alertText: '',
                alert: false,
                loading: false,
            };
        },
        methods: {
            openToEdit(item) {
                this.editingItem = item;
                this.name = item.name;
                this.show = true;
            },
            openToAdd() {
                this.editingItem = {};
                this.name = '';
                this.show = true;
            },
            close() {
                this.show = false;
            },
            async save() {
                if (this.$refs.form.validate()) {
                    try {
                        if (this.editingItem.id) {
                            this.editingItem.name = this.name;
                            await updateSymptom(this.editingItem);
                        } else {
                            await addNewSymptom(this.name);
                        }
                        this.$emit('accept');
                        eventBus.$emit('message', 'Успешно');
                    } catch (e) {
                        this.alertText = e.response.data.message;
                        this.showAlert();
                    } finally {
                        this.show = false;
                        this.loading = false;
                    }
                }
            },
            cancel() {
                this.close();
                this.$emit('cancel');
            },
            showAlert() {
                this.alert = true;
                setTimeout(() => {
                    this.alert = false;
                }, 3000);
            },
        },
    };
</script>

<style scoped lang="stylus">
  .theme--light >>> .v-label {
    color var(--secondary-base)
  }
</style>
