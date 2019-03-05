<template>
    <v-card>
        <v-card-title>
            <p class="mb-0 ml-2 title">
                {{ title }}
            </p>
        </v-card-title>
        <v-divider />
        <v-card-text class="py-0 my-3">
            <v-container fluid
                         py-0
                         pl-3
                         pr-0
            >
                <v-layout row>
                    <v-flex class="container fluid pa-0 no-overflow">
                        <v-layout v-for="sign in localSigns"
                                  :key="sign.id"
                                  row
                                  mb-2
                        >
                            <v-flex>
                                <v-checkbox
                                        v-model="sign.checked"
                                        :label="sign.name"
                                        color="secondary"
                                        :disabled="!sign.checked && currentChecked.length === 2"
                                        hide-details
                                />
                            </v-flex>
                        </v-layout>
                    </v-flex>
                </v-layout>
                <v-layout row mt-3 ml-1>
                    <v-flex>
                        <v-text-field
                                v-model="animalName"
                                label="Название"
                                :rules="rules"
                                prepend-icon="build"
                        />
                    </v-flex>
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
        <v-card-actions class="layout justify-center mt-2 pt-0">
            <v-flex xs4 mr-3>
                <v-btn flat
                       block
                       color="success"
                       :loading="loading"
                       @click="add"
                >
                    Сохранить
                </v-btn>
            </v-flex>
            <v-flex xs4 ml-3>
                <v-btn flat
                       block
                       color="error"
                       @click="dismiss"
                >
                    Отменить
                </v-btn>
            </v-flex>
        </v-card-actions>
    </v-card>
</template>

<script>
    import { addAnimal, updateAnimal } from '../../client/animals-client';
    import eventBus from '../../eventBus';

    export default {
        name: 'AnimalForm',
        props: {
            signs: {
                type: Array,
                default: null,
            },
            editingAnimal: {
                type: Object,
                default: null,
            },
        },
        data() {
            return {
                animalName: '',
                localSigns: null,
                loading: false,
                alert: false,
                alertText: 'Ошибка',
                rules: [
                    v => (v && v.length > 0 ? true : 'Поле не должно быть пустым'),
                    v => (v && v.trim().length > 0 ? true : 'Поле не должно состоять только из пробелов'),
                ],
                title: '',
            };
        },
        computed: {
            currentChecked() {
                return this.localSigns.filter(v => v.checked);
            },
        },
        created() {
            if (this.signs && this.editingAnimal) {
                this.localSigns = this.signs.map(v => ({
                    id: v.id,
                    name: v.name,
                    checked: false,
                }));
                this.localSigns.forEach((v) => {
                    if (this.editingAnimal.signs.find(value => v.id === value.id)) {
                        v.checked = true;
                    }
                });

                if (this.editingAnimal.id) {
                    this.title = 'Изменение животного';
                    this.animalName = this.editingAnimal.name;
                } else {
                    this.title = 'Добавление животного';
                }
            }
        },
        methods: {
            async add() {
                if (this.currentChecked.length !== 2) {
                    this.alertText = 'Необходимо выбрать 2 признака';
                    this.showAlert();
                } else if (this.animalName.length > 1 && this.currentChecked.length === 2) {
                    try {
                        this.loading = true;
                        if (this.editingAnimal.id) {
                            await updateAnimal(
                                this.editingAnimal.id,
                                {
                                    name: this.animalName,
                                    signs: this.currentChecked,
                                },
                            );
                        } else {
                            await addAnimal({
                                name: this.animalName,
                                signs: this.currentChecked,
                            });
                        }
                        eventBus.$emit('message', 'Успешно');
                        this.$emit('close');
                    } catch (e) {
                        console.log(e);
                    } finally {
                        this.loading = false;
                    }
                }
            },
            dismiss() {
                this.$emit('close');
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
  .title {
    font-size 1.3rem
  }

  .theme--light >>> .v-label {
    color var(--secondary-base)
  }
  .no-overflow {
    height: 56vh;
    overflow-y: auto;
  }
</style>
