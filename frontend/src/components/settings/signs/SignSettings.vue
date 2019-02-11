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
                                Список признаков
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
                                    :items="signs"
                                    @edit="onEdit"
                                    @remove="onRemove"
                            />
                            <v-divider />
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-card-text>
        </v-card>
        <SignEditDialog ref="editDialog"
                        @accept="editSign"
                        @dismiss="closeEditDialog"
        />
        <SignRemoveDialog :show="removeDialog" @accept="removeSign" @dismiss="closeRemoveDialog" />
    </div>
</template>

<script>
    import List from '../List.vue';
    import SignRemoveDialog from './SignRemoveDialog.vue';
    import { addSign, deleteSign, updateSign } from '../../../client/animals-client';
    import SignEditDialog from './SignEditDialog.vue';

    export default {
        name: 'SignSettings',
        components: {
            SignEditDialog,
            SignRemoveDialog,
            List,
        },
        props: {
            signs: {
                type: Array,
                default: () => [],
            },
        },
        data() {
            return {
                removeDialog: false,
                removingItemId: null,
                editingSign: null,
                editingAcceptButtonLoading: false,
            };
        },
        methods: {
            onEdit(item) {
                this.editingSign = item;
                this.$refs.editDialog.open(this.editingSign ? this.editingSign.name : '');
            },
            async onRemove(item) {
                this.removeDialog = true;
                this.removingItemId = item.id;
            },

            async removeSign() {
                try {
                    await deleteSign(this.removingItemId);
                    this.closeRemoveDialog();
                    this.$emit('message', 'Успешно');
                } catch (e) {
                    this.$emit('message', `Ошибка: ${e.response.data.message}`);
                } finally {
                    this.$emit('update');
                }
            },

            async editSign(name) {
                try {
                    this.editingAcceptButtonLoading = true;
                    if (this.editingSign) {
                        await updateSign(this.editingSign.id, name);
                    } else {
                        await addSign(name);
                    }
                    this.closeEditDialog();
                    this.$emit('message', 'Успешно');
                    this.$emit('update');
                } catch (e) {
                    this.$emit('message', `Ошибка: ${e.response.data.message}`);
                } finally {
                    this.editingAcceptButtonLoading = false;
                }
            },

            closeRemoveDialog() {
                this.removeDialog = false;
                this.removingItemId = null;
            },

            closeEditDialog() {
                this.editingSign = null;
            },

            openAddDialog() {
                this.$refs.editDialog.open('');
            },
        },
    };
</script>

<style scoped>

</style>
