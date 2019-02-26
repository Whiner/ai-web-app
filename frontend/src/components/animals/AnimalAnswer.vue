<template>
    <v-card>
        <v-card-title>
            <p class="mb-0 ml-3 header-text">
                Ответ
            </p>
        </v-card-title>
        <v-divider />
        <v-card-text class="mb-4">
            <p class="content-text px-4 mb-0 mt-4 text-xs-center">
                Я думаю, что это животное -
                <span class="answer">
                    {{ answer.name.toUpperCase() }}
                </span>
            </p>
        </v-card-text>
        <v-divider />
        <v-card-actions class="layout justify-space-between">
            <v-flex xs3 />
            <v-flex xs3>
                <v-btn block
                       flat
                       color="info"
                       @click="refresh"
                >
                    Начать сначала
                </v-btn>
            </v-flex>
            <v-flex xs3>
                <v-btn block
                       flat
                       color="grey"
                       @click="edit"
                >
                    Это неправильно?
                </v-btn>
            </v-flex>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: 'AnimalAnswer',
        props: {
            answer: {
                type: Object,
                default: null,
            },
        },
        methods: {
            refresh() {
                this.$emit('refresh');
            },
            edit() {
                this.$emit('edit');
            },
        },
    };
</script>

<style scoped lang="stylus">
  .header-text {
    font-size 1.3rem
  }

  .content-text {
    font-size 1.5rem
  }

  .answer {
    color #2a7a35
  }
</style>
