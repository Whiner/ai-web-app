<template>
    <v-container fluid mt-5>
        <v-layout justify-center>
            <v-flex xs6>
                <SignQuestions v-if="sign && !answer" :sign="sign.name" @answer="onAnswer" />
                <AnimalAnswer
                        v-else-if="answer"
                        :answer="answer"
                        @refresh="onRefresh"
                        @edit="onEdit"
                />
                <AnimalForm
                        v-else-if="editingAnimal && signs"
                        :checked-signs="editingAnimal.signs"
                        :signs="signs"
                        @dismiss="onDismiss"
                />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
  import SignQuestions from '../components/animals/SignQuestions.vue';
  import { clear, getAllSigns, getAnswer, nextQuestion } from '../client/animals-client';
  import AnimalForm from '../components/animals/AnimalForm.vue';
  import compareById from '../utils/compareUtil';
  import AnimalAnswer from '../components/animals/AnimalAnswer.vue';

  export default {
        name: 'Animals',
        components: {
            AnimalAnswer,
            AnimalForm,
            SignQuestions,
        },
        data() {
            return {
                sign: null,
                answer: null,
                signs: null,
                editingAnimal: null,
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
                        console.log('следующий вопрос');
                    } else {
                        response = await getAnswer();
                        if (response.id) {
                            this.answer = response;
                            console.log('ответ');
                        } else {
                            this.editingAnimal = response;
                            this.answer = null;
                            this.sign = null;
                            console.log('добавление');
                        }
                    }
                } catch (e) {
                    console.log(e);
                }
            },
            async onRefresh() {
                await clear();
                this.sign = await nextQuestion(null);
                this.answer = null;
            },
            onEdit() {

            },
            onDismiss() {
                this.editingAnimal = null;
                this.onRefresh();
            },
        },
    };
</script>

<style scoped>

</style>
