<template>
    <v-card>
        <v-card-title>
            <p>
                Выберите симптомы
            </p>
        </v-card-title>
        <v-card-text>
            <v-container fluid class="pa-0 ma-0">
                <v-layout v-for="symptom in symptoms"
                          :key="symptom.id"
                          row
                >
                    <v-flex>
                        <v-checkbox
                                v-model="symptom.checked"
                                :label="symptom.name"
                        />
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
    </v-card>
</template>

<script>
    export default {
        name: 'SymptomsChoice',
        props: {
            symptoms: {
                type: Array,
                default: () => [],
            },
        },
    };
</script>

<style scoped>

</style>
