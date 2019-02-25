<template>
    <v-container fluid mt-5>
        <v-layout justify-center>
            <v-flex xs6>
                <SignQuestions v-if="sign" :sign="sign.name" @answer="onAnswer" />
                <AnimalForm v-if="signs" :signs="signs" />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import SignQuestions from '../components/animals/SignQuestions.vue';
    import { clear, getAllSigns, getAnswer, nextQuestion } from '../client/animals-client';
    import AnimalForm from '../components/animals/AnimalForm.vue';
    import compareById from '../utils/compareUtil';

    export default {
        name: 'Animals',
        components: { AnimalForm, SignQuestions },
        data() {
            return {
                sign: null,
                answer: null,
                signs: null,
            };
        },
        async beforeRouteEnter(to, from, next) {
            await clear();
            const sign = nextQuestion(null);
            const signs = getAllSigns();
            next(async (vm) => {
                vm.sign = await sign;
                vm.signs = (await signs).sort(compareById);
            });
        },
        methods: {
            async onAnswer(result) {
                try {
                    let response = await nextQuestion(result);
                    if (response) {
                        this.sign = response;
                    } else {
                        response = await getAnswer();
                        if (response.id) {
                            console.log(`Это ${response.name}`);
                            this.answer = response;
                        } else {
                            this.signs = response.signs; // перенести это все в настройки вообще
                        }
                    }
                } catch (e) {
                    console.log(e);
                }
            },
        },
    };
</script>

<style scoped>

</style>
