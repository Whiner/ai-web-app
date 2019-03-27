<template>
    <v-card>
        <v-card-title>
            <p class="mb-0 headline">
                x<sub>3</sub> - 4x<sub>2</sub> + 7
            </p>
        </v-card-title>
        <v-card-text>
            <LineChart :chartdata="chartData"/>
        </v-card-text>
    </v-card>
</template>

<script>
    import LineChart from './LineChart.vue';

    export default {
        name: 'FunctionChart',
        components: { LineChart },
        props: {
            lowerInterval: {
                type: Number,
                default: null,
            },
            upperInterval: {
                type: Number,
                default: null,
            },
            extremumX: {
                type: Number,
                default: null,
            },
        },
        data() {
            return {
                chartData: {},
            };
        },
        created() {
            const step = (this.upperInterval - this.lowerInterval) / 50;
            const data = [];
            const labels = [];

            for (let i = this.lowerInterval; i < this.upperInterval + step / 2; i += step * 10) {
                labels.push(`${i}`);
            }

            for (let i = this.lowerInterval; i < this.upperInterval + step / 2; i += step) {
                data.push({ x: i, y: this.fitnessFunction(i) });
            }

            this.chartData = {
                labels,
                datasets: data,
            };
        },
        methods: {
            fitnessFunction(x) {
                return (x ** 3) - 4 * (x ** 2) + 7.0;
            },
        },
    };
</script>

<style scoped>

</style>
