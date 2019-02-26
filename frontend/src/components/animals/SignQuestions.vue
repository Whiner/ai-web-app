<template>
    <v-card>
        <v-card-title>
            <p class="mb-0 ml-2 header-text">
                Ответьте на вопрос
            </p>
        </v-card-title>
        <v-divider />
        <v-card-text class="my-4">
            <p class="content-text px-4 mb-0 text-xs-center">
                У этого животного есть
                <span class="question">
                    {{ sign.toUpperCase() }}?
                </span>
            </p>
        </v-card-text>
        <v-divider />
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

  .question {
    color #2a7a35
  }
</style>
