<template>
    <v-container fluid mt-5>
        <v-layout justify-center>
            <v-flex xs6>
                <SignQuestions v-if="sign" :sign="sign.name" @answer="onAnswer" />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import SignQuestions from '../components/animals/SignQuestions.vue';
    import { clear, getAnswer, nextQuestion } from '../client/animals-client';

    export default {
        name: 'Animals',
        components: { SignQuestions },
        data() {
            return {
                sign: null,
                answer: null,
            };
        },
        async beforeRouteEnter(to, from, next) {
            await clear();
            const sign = nextQuestion(null);
            next(async (vm) => {
                vm.sign = await sign;
            });
        },
        methods: {
            async onAnswer(result) {
                const response = await nextQuestion(result);
                if (response) {
                    this.sign = response;
                } else {
                    try {
                        this.answer = await getAnswer();
                    } catch (e) {
                        console.log(e);
                    }
                }
            },
        },
    };
</script>

<style scoped>

</style>
