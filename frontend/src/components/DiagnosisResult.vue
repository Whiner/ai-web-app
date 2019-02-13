<template>
    <v-card>
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
            <v-layout v-for="symptom in symptoms"
                      :key="symptom.id"
                      row
            >
                <v-flex>
                    <v-checkbox
                            v-model="symptom.checked"
                            color="primary"
                            hide-details
                            :label="symptom.name"
                    />
                </v-flex>
            </v-layout>
        </v-card-text>
        <v-card-actions>
            <v-container fluid>
                <v-layout row>
                    <v-flex xs2>
                        <v-btn color="primary">
                            Узнать диагноз
                        </v-btn>
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: 'DiagnosisResult',
        props: {
            diagnosis: {
                type: Object,
                default: null,
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
</style>
