<template>
    <v-card>
        <v-card-title>
            <p class="mb-0 title">
                Добавить животное
            </p>
        </v-card-title>
        <v-card-text class="py-0">
            <v-container fluid py-0 px-3>
                <v-layout v-for="sign in signs"
                          :key="sign.id"
                          row
                          mb-2
                >
                    <v-flex>
                        <v-checkbox
                                v-model="sign.checked"
                                :label="sign.name"
                                :disabled="!sign.checked && checkedCount === 2"
                                hide-details
                        />
                    </v-flex>
                </v-layout>
                <v-layout row mt-3 ml-1>
                    <v-flex>
                        <v-text-field v-model="name" label="Название" />
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-card-actions class="layout justify-center mt-2 pt-0">
            <v-flex xs4 mr-3>
                <v-btn flat block color="success">
                    Добавить
                </v-btn>
            </v-flex>
            <v-flex xs4 ml-3>
                <v-btn flat block color="error">
                    Отменить
                </v-btn>
            </v-flex>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: 'AnimalForm',
        props: {
            signs: {
                type: Array,
                default: null,
            },
        },
        data() {
            return {
                name: '',
            };
        },
        computed: {
            checkedCount() {
                return this.signs.filter(v => v.checked).length;
            },
        },
    };
</script>

<style scoped lang="stylus">
  .title {
    font-size 1.3rem
  }
</style>
