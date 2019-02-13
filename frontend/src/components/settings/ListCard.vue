<template>
    <v-card>
        <v-card-title class="pb-0">
            <v-container fluid class="pa-10px pb-0">
                <v-layout row>
                    <font-awesome-icon icon="ambulance" class="fa-lg" />
                    <span class="title font-weight-light ml-3">
                        {{ title }}
                    </span>
                </v-layout>
                <v-layout row mt-2>
                    <v-divider />
                </v-layout>
            </v-container>
        </v-card-title>
        <v-card-text class="py-0">
            <v-container fluid class="pt-0 pl-2 pr-0">
                <v-layout row>
                    <v-flex>
                        <v-list>
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
                                                    <v-icon color="error">
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
            };
        },
        methods: {
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
                        this.$emit('save', this.name);
                    } else {
                        this.$emit('save', this.editingItem.id, this.editingItem.name);
                    }
                }
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
</style>
