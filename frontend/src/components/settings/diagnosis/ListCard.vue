<template>
    <div>
        <v-card>
            <v-card-title class="pb-0 pt-0">
                <v-container
                        fluid
                        pt-3
                        px-0
                        pb-0
                >
                    <v-layout row>
                        <v-flex xs1 align-self-center class="text-xs-center">
                            <font-awesome-icon icon="ambulance" class="fa-lg" />
                        </v-flex>
                        <v-flex xs5 align-self-center>
                            <span class="title font-weight-light">
                                {{ title }}
                            </span>
                        </v-flex>
                        <v-flex offset-xs5 xs1 class="text-xs-right">
                            <v-btn icon class="my-0" @click="openAddDialog">
                                <v-icon>add</v-icon>
                            </v-btn>
                        </v-flex>
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
    import List from '../List.vue';

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
                dialog: false,
                removingItem: null,
            };
        },
        methods: {
            openAddDialog() {
                this.$emit('add');
            },
            onRemove(item) {
                this.dialog = true;
                this.removingItem = item;
            },
            onEdit(item) {
                this.$emit('edit', item);
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
