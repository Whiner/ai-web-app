<template>
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
</template>

<script>
    export default {
        name: 'List',
        props: {
            items: {
                type: Array,
                default: () => [],
            },
        },
        methods: {
            edit(item) {
                this.$emit('edit', item);
            },
            remove(item) {
                this.$emit('remove', item);
            },
        },
    };
</script>

<style scoped lang="stylus">
  .v-list {
    height: 310px;
    overflow-y: auto;
  }
</style>
