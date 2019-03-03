<template>
    <div>
        <v-card>
            <v-card-title class="pb-0 pt-0">
                <v-container
                        fluid
                        px-3
                        pt-4
                        pb-0
                >
                    <v-layout row>
                        <font-awesome-icon icon="ambulance" class="fa-lg" />
                        <span class="title font-weight-light ml-3">
                            {{ title }}
                        </span>
                    </v-layout>
                </v-container>
            </v-card-title>
            <v-card-text class="py-0">
                <v-container fluid
                             pt-0
                             pl-0
                             pr-0
                >
                    <v-layout row mt-2>
                        <v-divider />
                    </v-layout>
                    <v-layout row>
                        <v-flex xs12>
                            <List
                                    :items="items"
                                    @edit="onEdit"
                                    @remove="onRemove"
                            />
                            <v-divider />
                        </v-flex>
                    </v-layout>
                    <v-layout row mt-3>
                        <v-flex xs8 mr-3 align-self-center>
                            <v-text-field
                                    v-model="name"
                                    :label="textFieldLabel"
                                    :rules="[rules.required]"
                                    prepend-icon="build"
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
    import List from './List.vue';

    export default {
        name: 'ListCard',
        components: { List },
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
            onRemove(item) {
                this.dialog = true;
                this.removingItem = item;
            },
            onEdit(item) {
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

</style>
