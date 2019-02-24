<template>
    <v-card>
        <v-card-title class="elevation-1">
            <p class="mb-0 header-text">
                Ответьте на вопрос
            </p>
        </v-card-title>
        <v-card-text>
            <p class="content-text px-4 mb-0">
                У этого животного есть {{ sign }}?
            </p>
        </v-card-text>
        <v-card-actions class="layout justify-center">
            <v-flex xs4 mr-3>
                <v-btn block
                       flat
                       color="success"
                       @click="accept"
                >
                    Да
                </v-btn>
            </v-flex>
            <v-flex xs4 ml-3>
                <v-btn block
                       flat
                       color="error"
                       @click="dismiss"
                >
                    Нет
                </v-btn>
            </v-flex>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: 'SignQuestions',
        props: {
            sign: {
                type: String,
                default: '',
            },
        },
        methods: {
            accept() {
                this.$emit('answer', true);
            },
            dismiss() {
                this.$emit('answer', false);
            },
        },
    };
</script>

<style scoped lang="stylus">
  .header-text {
    font-size 1.3rem
  }

  .content-text {
    font-size 1.2rem
  }
</style>
