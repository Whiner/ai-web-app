<template>
    <div>
        <v-card>
            <v-card-title class="pb-0 pt-0">
                <v-container fluid class="pa-10px pb-0">
                    <v-layout row>
                        <font-awesome-icon icon="ambulance" class="fa-lg" />
                        <span class="title font-weight-light ml-3">
                            {{ title }}
                        </span>
                    </v-layout>
                </v-container>
            </v-card-title>
            <v-card-text class="py-0">
                <v-container fluid class="pt-0 pl-2 pr-0">
                    <v-layout row mt-2>
                        <v-divider />
                    </v-layout>
                    <v-layout row>
                        <v-flex>
                            <v-list class="grey lighten-4">
                                <v-list-tile
                                        v-for="item in items"
                                        :key="item.id"
                                >
                                    <v-list-tile-avatar>
                                        <v-icon size="15">
                                            star
                                        </v-icon>
                                    </v-list-tile-avatar>
                                    <v-list-tile-content>
                                        <v-list-tile-title>
                                            <p>{{ item.name }}</p>
                                        </v-list-tile-title>
                                    </v-list-tile-content>
                                    <v-list-tile-action>
                                        <v-container fluid class="pa-0">
                                            <v-layout row>
                                                <v-flex mr-2>
                                                    <v-btn icon @click="edit(item)">
                                                        <v-icon color="info">
                                                            edit
                                                        </v-icon>
                                                    </v-btn>
                                                </v-flex>
                                                <v-flex ml-2>
                                                    <v-btn icon>
                                                        <v-icon color="error" @click="remove(item)">
                                                            delete
                                                        </v-icon>
                                                    </v-btn>
                                                </v-flex>
                                            </v-layout>
                                        </v-container>
                                    </v-list-tile-action>
                                </v-list-tile>
                            </v-list>
                            <v-divider />
                        </v-flex>
                    </v-layout>
                    <v-layout row mt-3>
                        <v-flex xs8 mr-3 align-self-center>
                            <v-text-field
                                    v-model="name"
                                    :label="textFieldLabel"
                                    :rules="[rules.required]"
                                    clearable
                            />
                        </v-flex>
                        <v-flex ml-3 align-self-center>
                            <v-btn block outline @click="save">
                                {{ editingItem !== null ? 'Сохранить': 'Добавить' }}
                            </v-btn>
                        </v-flex>
                        <v-flex v-if="editingItem !== null"
                                xs2
                                ml-1
                                align-self-center
                        >
                            <v-btn block outline @click="cancel">
                                Отмена
                            </v-btn>
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-card-text>
        </v-card>
        <v-dialog v-model="dialog"
                  persistent
                  max-width="350"
                  max-height="300"
        >
            <v-card>
                <v-card-title class="title pb-2">
                    Точно?
                </v-card-title>
                <v-divider />

                <v-card-actions class="layout justify-center">
                    <v-btn color="green darken-1" flat @click="acceptDelete">
                        Да
                    </v-btn>
                    <v-btn color="red darken-1" flat @click="cancelDelete">
                        Нет
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    export default {
        name: 'ListCard',
        props: {
            items: {
                type: Array,
                default: () => [],
            },
            title: {
                type: String,
                default: '',
            },
            textFieldLabel: {
                type: String,
                default: '',
            },
        },
        data() {
            return {
                name: '',
                editingItem: null,
                rules: {
                    required: value => !!value || 'Поле не должно быть пустым',
                },
                dialog: false,
                removingItem: null,
            };
        },
        watch: {
            items() {
                this.name = '';
            },
        },
        methods: {
            remove(item) {
                this.dialog = true;
                this.removingItem = item;
            },
            edit(item) {
                this.editingItem = item;
                this.name = item.name;
            },
            cancel() {
                this.editingItem = null;
                this.name = '';
            },
            save() {
                if (this.name && this.name.length > 0) {
                    if (this.editingItem === null) {
                        this.$emit('save-new', this.name);
                    } else {
                        this.editingItem.name = this.name;
                        this.$emit('save-old', this.editingItem);
                        this.editingItem = null;
                    }
                }
            },
            cancelDelete() {
                this.dialog = false;
                this.removingItem = null;
            },

            acceptDelete() {
                this.dialog = false;
                this.$emit('remove', this.removingItem);
            },
        },
    };
</script>

<style scoped lang="stylus">
  .py-10px {
    padding-top 10x
    padding-bottom 10px
  }

  .v-list {
    height: 310px;
    overflow-y: auto;
  }

  .list-layout {
    background-color #b4b4b4
  }
</style>
