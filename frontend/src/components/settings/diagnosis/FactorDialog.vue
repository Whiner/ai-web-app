<template>
    <v-dialog v-model="show"
              persistent
              max-width="550"
              max-height="200"
    >
        <v-card>
            <v-card-text class="container fluid pb-0">
                <v-layout row justify-center>
                    <v-flex xs2 align-self-center>
                        Доверие
                    </v-flex>
                    <v-flex xs10>
                        <v-slider
                                v-model="confidence"
                                min="0"
                                max="1"
                                step="0.001"
                                thumb-label="always"
                                color="info"
                        />
                    </v-flex>
                </v-layout>
                <v-layout row justify-center>
                    <v-flex xs2 align-self-center>
                        Недоверие
                    </v-flex>
                    <v-flex xs10>
                        <v-slider
                                v-model="mistrust"
                                min="0"
                                :max="confidence"
                                step="0.001"
                                thumb-label="always"
                                color="info"
                        />
                    </v-flex>
                </v-layout>
            </v-card-text>
            <v-card-actions class="justify-center">
                <v-btn color="success"
                       flat
                       @click="accept"
                >
                    Сохранить
                </v-btn>
                <v-btn color="error"
                       flat
                       @click="cancel"
                >
                    Отмена
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: 'FactorDialog',
        data() {
            return {
                show: false,
                confidence: -1,
                mistrust: -1,
            };
        },
        methods: {
            open(confidence, mistrust) {
                this.confidence = confidence;
                this.mistrust = confidence === 0 ? 0 : mistrust;
                this.show = true;
            },
            accept() {
                this.$emit('accept', this.confidence, this.mistrust);
                this.show = false;
            },
            cancel() {
                this.show = false;
            },
        },
    };
</script>

<style scoped lang="stylus">
  .theme--light >>> .v-label {
    color var(--secondary-base)
  }
</style>
