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
                <transition name="component-fade" mode="out-in">
                    <FunctionChart v-if="x"
                                   :lower-interval="lower"
                                   :upper-interval="upper"
                                   :extremum-x="x"
                    />
                </transition>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import FunctionChart from '../components/charts/FunctionChart.vue';
    import GeneticForm from '../components/genetic/GeneticForm.vue';

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

<style lang="stylus" scoped>
    .component-fade-enter-active, .component-fade-leave-active {
        transition: opacity .5s ease;
    }
    .component-fade-enter, .component-fade-leave-to {
        opacity: 0;
    }
</style>
