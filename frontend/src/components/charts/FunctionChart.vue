<template>
    <v-card>
        <v-card-title class="justify-space-between">
            <p class="mb-0 headline ml-3">
                x<sup>3</sup> - 4x<sup>2</sup> + 7
            </p>
            <p class="mb-0 ml-5">
                MAX [ {{ extremumX.toFixed(2) }}, {{ fitnessFunction(extremumX).toFixed(2) }} ]
            </p>
        </v-card-title>
        <v-divider />
        <v-card-text>
            <LineChart :chart-data="chartData"
                       :options="options"
            />
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
                options: {
                    responsive: true,
                    legend: {
                        display: false,
                    },
                    fill: true,
                    scales: {
                        xAxes: [{
                            type: 'linear',
                            position: 'bottom',
                        }],
                    },
                    maintainAspectRatio: false,
                    tooltips: {
                        enabled: false,
                    },
                },
            };
        },
        watch: {
            extremumX: {
                immediate: true,
                handler() {
                    this.initChart();
                },
            },
        },
        methods: {
            fitnessFunction(x) {
                return (x ** 3) - 4 * (x ** 2) + 7.0;
            },
            initChart() {
                const step = (this.upperInterval - this.lowerInterval) / 50;
                const data = [];
                const labels = [];

                for (let i = this.lowerInterval; i < this.upperInterval + step / 2; i += step * 10) {
                    labels.push(`${i.toFixed(3)}`);
                }

                for (let i = this.lowerInterval; i < this.upperInterval + step / 2; i += step) {
                    data.push({ x: i.toFixed(3), y: this.fitnessFunction(i).toFixed(3) });
                }

                this.chartData = {
                    labels,
                    datasets: [
                        {
                            data: [{ x: this.extremumX, y: this.fitnessFunction(this.extremumX).toFixed(3) }],
                            backgroundColor: '#ff0001',
                        },
                        {
                            data,
                            backgroundColor: 'rgb(33,150,243, 0.25)',
                        },
                    ],
                };
            },
        },
    };
</script>

<style scoped>

</style>
