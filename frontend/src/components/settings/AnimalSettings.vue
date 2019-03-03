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
                            <font-awesome-icon icon="cat" class="fa-lg" />
                        </v-flex>
                        <v-flex xs5 align-self-center>
                            <span class="title font-weight-light">
                                Список животных
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
                                    :items="animals"
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
                  max-width="650"
                  max-height="300"
        >
            <AnimalForm
                    v-if="editingAnimal && signs"
                    :editing-animal="editingAnimal"
                    :signs="signs"
                    @close="onClose"
            />
        </v-dialog>
    </div>
</template>

<script>
    import AnimalForm from '../animals/AnimalForm.vue';
    import List from './List.vue';
    import { deleteAnimal } from '../../client/animals-client';

    export default {
        name: 'AnimalSettings',
        components: { List, AnimalForm },
        props: {
            signs: {
                type: Array,
                default: () => [],
            },
            animals: {
                type: Array,
                default: () => [],
            },
        },
        data() {
            return {
                editingAnimal: null,
                dialog: false,
            };
        },
        methods: {
            onEdit(item) {
                this.editingAnimal = item;
                this.dialog = true;
            },
            async onRemove(item) {
                await deleteAnimal(item.id);
                this.$emit('update');
            },

            onClose() {
                this.dialog = false;
                this.editingAnimal = null;
                this.$emit('update');
            },
            openAddDialog() {
                this.editingAnimal = {
                    signs: [],
                };
                this.dialog = true;
            },
        },
    };
</script>

<style scoped>

</style>
