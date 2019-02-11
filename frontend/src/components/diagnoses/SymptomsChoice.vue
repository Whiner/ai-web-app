<template>
    <v-card class="symptoms-choice-card">
        <v-card-title class="card-title">
            <v-container fluid class="pa-10px">
                <v-layout row>
                    <font-awesome-icon icon="stethoscope" class="fa-lg" />
                    <span class="title font-weight-light ml-3">
                        Выберите симптомы
                    </span>
                </v-layout>
            </v-container>
        </v-card-title>
        <v-card-text class="py-0">
            <v-divider />
            <v-container fluid class="pt-0 pl-3 pr-0 card-content">
                <v-layout v-for="symptom in symptoms"
                          :key="symptom.id"
                          row
                >
                    <v-flex>
                        <v-checkbox
                                v-model="symptom.checked"
                                hide-details
                                color="secondary"
                                :label="symptom.name"
                        />
                    </v-flex>
                </v-layout>
            </v-container>
            <v-divider />
        </v-card-text>
        <v-card-actions>
            <v-container fluid>
                <v-layout row>
                    <v-flex xs2>
                        <v-btn color="secondary" @click="checkDiagnoses">
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
        name: 'SymptomsChoice',
        props: {
            symptoms: {
                type: Array,
                default: () => [],
            },
        },
        methods: {
            checkDiagnoses() {
                this.$emit('check', this.symptoms.filter(value => value.checked));
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
    height: 360px;
    overflow-y: auto;
  }

  .card-text-container{
    background-color #b4b4b4
  }

  .theme--light >>> .v-label {
    color var(--secondary-base)
  }

</style>
