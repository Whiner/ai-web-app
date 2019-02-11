<template>
    <v-container fluid mt-5>
        <v-layout column mt-5>
            <v-flex xs3 class="text-xs-center" mt-5>
                <v-icon color="primary" size="120">
                    build
                </v-icon>
            </v-flex>
            <v-flex xs3>
                <p class="text-xs-center mb-0 message primary--text">
                    К сожалению, раздел находится в разработке
                </p>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        name: 'InDevelopmentPage',
    };
</script>

<style scoped lang="stylus">
  .message {
    font-size 2rem
  }

</style>
