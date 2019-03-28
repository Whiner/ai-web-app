<template>
    <v-container fluid>
        <v-layout row>
            <v-flex xs4 mr-3>
                <v-card>
                    <v-card-text py-2 px-0>
                        <v-container fluid pa-0>
                            <v-layout row>
                                <v-flex>
                                    <p class="mb-0 text-xs-center">
                                        Диапазон поиска
                                    </p>
                                </v-flex>
                            </v-layout>
                            <v-layout row mt-5>
                                <v-flex xs2 pr-4>
                                    <v-text-field
                                            v-model="value[0]"
                                            class="mt-0 number-text-field"
                                            step="0.1"
                                            hide-details
                                            single-line
                                            type="number"
                                    />
                                </v-flex>

                                <v-flex>
                                    <v-range-slider
                                            v-model="value"
                                            max="50"
                                            min="0"
                                            step="0.1"
                                            thumb-label="always"
                                            color="info"
                                    />
                                </v-flex>

                                <v-flex xs2 pl-4>
                                    <v-text-field
                                            v-model="value[1]"
                                            class="mt-0 number-text-field"
                                            step="0.1"
                                            hide-details
                                            single-line
                                            type="number"
                                    />
                                </v-flex>
                            </v-layout>
                            <v-divider />
                            <v-layout row mt-3>
                                <v-flex align-self-center xs5 pl-3>
                                    Количество хромосом
                                </v-flex>
                                <v-flex offset-xs2 align-self-center xs2>
                                    <v-text-field
                                            v-model="chromosomesCount"
                                            class="mt-0 pt-0 number-text-field"
                                            hide-details
                                            type="number"
                                    />
                                </v-flex>
                            </v-layout>
                            <v-layout row mt-3>
                                <v-flex align-self-center xs5 pl-3>
                                    Количество итераций
                                </v-flex>
                                <v-flex offset-xs2 align-self-center xs2>
                                    <v-text-field
                                            v-model="maxIterationsCount"
                                            class="mt-0 pt-0 number-text-field"
                                            hide-details
                                            type="number"
                                    />
                                </v-flex>
                            </v-layout>
                            <v-layout row mt-3>
                                <v-flex align-self-center xs5 pl-3>
                                    Шанс мутации
                                </v-flex>
                                <v-flex offset-xs2 align-self-center xs2>
                                    <v-text-field
                                            v-model="mutationChance"
                                            class="mt-0 pt-0 number-text-field"
                                            hide-details
                                            type="number"
                                    />
                                </v-flex>
                            </v-layout>
                            <v-layout row mt-3 mb-4>
                                <v-flex align-self-center xs5 pl-3>
                                    Шанс скрещивания
                                </v-flex>
                                <v-flex offset-xs2 align-self-center xs2>
                                    <v-text-field
                                            v-model="crossingChance"
                                            class="mt-0 pt-0 number-text-field"
                                            hide-details
                                            type="number"
                                    />
                                </v-flex>
                            </v-layout>
                            <v-divider />
                            <v-layout row justify-center mt-3>
                                <v-flex xs5>
                                    <v-btn block
                                           color="secondary"
                                           :loading="loading"
                                           @click="onSearch"
                                    >
                                        Поиск
                                    </v-btn>
                                </v-flex>
                            </v-layout>
                        </v-container>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex ml-3>
                <FunctionChart v-if="x"
                               :lower-interval="value[0]"
                               :upper-interval="value[1]"
                               :extremum-x="x"
                />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import { getExtremum } from '../client/genetic-client';
    import FunctionChart from '../components/charts/FunctionChart.vue';
    import eventBus from '../eventBus';

    export default {
        name: 'Genetic',
        components: { FunctionChart },
        data() {
            return {
                value: [0, 15],
                chromosomesCount: 10,
                maxIterationsCount: 10000,
                crossingChance: 0.5,
                mutationChance: 0.5,
                x: null,
                loading: false,
            };
        },
        methods: {
            async onSearch() {
                try {
                    this.loading = true;
                    this.x = await getExtremum(
                        this.value[0],
                        this.value[1],
                        this.chromosomesCount,
                        this.maxIterationsCount,
                        this.mutationChance,
                        this.crossingChance,
                    );
                } catch (e) {
                    eventBus.$emit('message', `Ошибка: ${e.response.data.message}`);
                } finally {
                    this.loading = false;
                }
            },
        },
    };
</script>

<style scoped lang="stylus">
  .number-text-field >>> input[type='number'] {
    -moz-appearance: textfield;
  }

  .number-text-field >>> input::-webkit-outer-spin-button,
  .number-text-field >>> input::-webkit-inner-spin-button {
    -webkit-appearance: none;
  }

</style>
