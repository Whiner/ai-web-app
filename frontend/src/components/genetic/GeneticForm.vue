<template>
    <v-form ref="form">
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
                            :rules="lowerIntervalRule"
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
                            :rules="upperIntervalRule"
                    />
                </v-flex>
            </v-layout>
            <v-divider />
            <v-layout row mt-3>
                <v-flex align-self-center xs8 pl-3>
                    Количество хромосом
                </v-flex>
                <v-flex offset-xs2 align-self-center xs2>
                    <v-text-field
                            v-model="chromosomesCount"
                            class="mt-0 pt-0 number-text-field"
                            hide-details
                            type="number"
                            :rules="chromosomesCountRule"
                    />
                </v-flex>
            </v-layout>
            <v-layout row mt-3>
                <v-flex align-self-center xs8 pl-3>
                    Количество итераций
                </v-flex>
                <v-flex offset-xs2 align-self-center xs2>
                    <v-text-field
                            v-model="maxIterationsCount"
                            class="mt-0 pt-0 number-text-field"
                            hide-details
                            type="number"
                            :rules="maxIterationsCountRule"
                    />
                </v-flex>
            </v-layout>
            <v-layout row mt-3>
                <v-flex align-self-center xs8 pl-3>
                    Шанс мутации
                </v-flex>
                <v-flex offset-xs2 align-self-center xs2>
                    <v-text-field
                            v-model="mutationChance"
                            class="mt-0 pt-0 number-text-field"
                            hide-details
                            type="number"
                            :rules="mutationChanceRule"
                    />
                </v-flex>
            </v-layout>
            <v-layout row mt-3 mb-4>
                <v-flex align-self-center xs8 pl-3>
                    Шанс скрещивания
                </v-flex>
                <v-flex offset-xs2 align-self-center xs2>
                    <v-text-field
                            v-model="crossingChance"
                            class="mt-0 pt-0 number-text-field"
                            hide-details
                            type="number"
                            :rules="crossingChanceRule"
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
    </v-form>
</template>

<script>
    import { getExtremum } from '../../client/genetic-client';
    import eventBus from '../../eventBus';

    export default {
        name: 'GeneticForm',
        data() {
            return {
                value: [0, 15],
                chromosomesCount: 10,
                maxIterationsCount: 10000,
                crossingChance: 0.5,
                mutationChance: 0.5,
                loading: false,
                delay: 50,
                lowerIntervalRule: [
                    value => (value >= 0 ? true : ''),
                ],
                upperIntervalRule: [
                    value => (value <= 50 ? true : ''),
                ],
                chromosomesCountRule: [
                    value => (value >= 5 && value <= 100 ? true : 'Должно быть больше 5 и меньше 100'),
                ],
                maxIterationsCountRule: [
                    value => (value >= 100 && value <= 1000000 ? true : 'Должно быть больше 100 и меньше 1млн'),
                ],
                crossingChanceRule: [
                    value => (value >= 0 && value <= 1 ? true : 'Должно быть в пределах от 0 до 1'),
                ],
                mutationChanceRule: [
                    value => (value >= 0 && value <= 1 ? true : 'Должно быть в пределах от 0 до 1'),
                ],
            };
        },
        methods: {
            async onSearch() {
                try {
                    if (this.$refs.form.validate()) {
                        this.loading = true;
                        const x = await getExtremum(
                            this.value[0],
                            this.value[1],
                            this.chromosomesCount,
                            this.maxIterationsCount,
                            this.mutationChance,
                            this.crossingChance,
                        );
                        this.$emit('found', x, this.value[0], this.value[1]);
                    }
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
