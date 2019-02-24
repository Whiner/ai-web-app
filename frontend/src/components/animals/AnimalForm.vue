<template>
    <v-card>
        <v-card-title>
            <p class="mb-0">
                Добавить животное
            </p>
        </v-card-title>
        <v-card-text class="pt-0">
            <v-container fluid pt-0 px-3>
                <v-layout v-for="sign in signs" :key="sign.id" row mb-2>
                    <v-flex>
                        <v-switch
                                v-model="sign.checked"
                                :label="sign.name"
                                hide-details
                        />
                    </v-flex>
                </v-layout>
                <v-layout row>
                    <v-flex>
                        <v-text-field v-model="name" label="Название" />
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
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
    };
</script>

<style scoped>

</style>
