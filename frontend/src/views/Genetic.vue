<template>
    <v-container fluid>
        <v-layout row>
            <v-flex xs4 mr-3>
                <v-card>
                    <v-card-text py-2 px-0>
                        <GeneticForm @found="onFound" />
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex ml-3>
                <FunctionChart v-if="x"
                               :lower-interval="lower"
                               :upper-interval="upper"
                               :extremum-x="x"
                />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import FunctionChart from '../components/charts/FunctionChart.vue';
    import GeneticForm from '../components/genetic/GeneticForm.vue';
    //TODO: обработка ошибок и проверка корректности введенных значений

    export default {
        name: 'Genetic',
        components: { GeneticForm, FunctionChart },
        data() {
            return {
                x: null,
                lower: null,
                upper: null,
            };
        },
        methods: {
            async onFound(x, lower, upper) {
                this.x = x;
                this.lower = lower;
                this.upper = upper;
            },
        },
    };
</script>
