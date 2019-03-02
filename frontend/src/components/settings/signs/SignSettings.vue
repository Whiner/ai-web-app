<template>
    <div>
        <v-card>
            <v-card-title class="pb-0 pt-0">
                <v-container
                        fluid
                        px-4
                        pt-4
                        pb-0
                >
                    <v-layout row>
                        <font-awesome-icon icon="cat" class="fa-lg" />
                        <span class="title font-weight-light ml-3">
                            Список признаков
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
        <SignEditDialog :show="editDialog"
                        :name="editingSign ? editingSign.name : ''"
                        @accept="editSign"
                        @dismiss="closeEditDialog"
        />
        <SignRemoveDialog :show="removeDialog" @accept="removeSign" @dismiss="closeRemoveDialog" />
    </div>
</template>

<script>
    import List from '../List.vue';
    import SignRemoveDialog from './SignRemoveDialog.vue';
    import { deleteSign, updateSign } from '../../../client/animals-client';
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
                editDialog: false,
                removingItemId: null,
                editingSign: null,

            };
        },
        methods: {
            onEdit(item) {
                this.editingSign = item;
                this.editDialog = true;
            },
            async onRemove(item) {
                this.removeDialog = true;
                this.removingItemId = item.id;
            },

            async removeSign() {
                await deleteSign(this.removingItemId);
                this.closeRemoveDialog();
                this.$emit('update');
            },

            async editSign(name) {
                await updateSign(this.editingSign.id, name);
                this.closeEditDialog();
                this.$emit('update');
            },

            closeRemoveDialog() {
                this.removeDialog = false;
                this.removingItemId = null;
            },

            closeEditDialog() {
                this.editDialog = false;
                this.editingSign = null;
            },
        },
    };
</script>

<style scoped>

</style>
