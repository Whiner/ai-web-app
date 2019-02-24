<template>
    <v-card class="fill-height">
        <v-card-title class="card-title">
            <v-container fluid class="pa-10px">
                <v-layout row>
                    <font-awesome-icon icon="comment-medical" class="fa-lg" />
                    <span class="title font-weight-light ml-3">
                        Ваш диагноз
                    </span>
                </v-layout>
                <v-layout row mt-2>
                    <v-divider />
                </v-layout>
            </v-container>
        </v-card-title>
        <v-card-text class="card-content">
            <v-container v-if="diagnoses.length > 0" fluid>
                <v-layout row justify-center>
                    <v-flex>
                        <p class="text-xs-center diagnosis">
                            Возможно, это -
                            <span v-for="(diagnosis, index) in diagnoses" :key="diagnosis.id">
                                {{ diagnosis.name }}
                                <span v-if="index !== diagnoses.length - 1">
                                    или
                                </span>
                            </span>
                        </p>
                    </v-flex>
                </v-layout>
                <v-layout row justify-center>
                    <img src="../../assets/diagnosis.png" alt="Диагноз">
                </v-layout>
            </v-container>
            <v-container v-else fluid>
                <v-layout row>
                    <v-flex>
                        <p class="text-xs-center diagnosis">
                            К сожалению, ваш диагноз неизвестен.
                            Если нагуглите - добавьте его в пункте настроек.
                        </p>
                    </v-flex>
                </v-layout>
                <v-layout row justify-center>
                    <v-flex xs8>
                        <v-btn outline to="settings" block>
                            Перейти в настройки
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-card-actions />
    </v-card>
</template>

<script>
    export default {
        name: 'DiagnosisResult',
        props: {
            diagnoses: {
                type: Array,
                default: () => [],
            },
        },
    };
</script>

<style lang="stylus" scoped>
  .pa-10px {
    padding 10px
  }

  .card-title {
    padding-bottom 0
  }

  .card-content {
    padding-top 0
    padding-left 30px
    padding-right 30px
  }

  .diagnosis {
    font-size 1.4rem
  }
</style>
