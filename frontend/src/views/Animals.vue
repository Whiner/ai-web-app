<template>
    <v-container fluid mt-2>
        <v-layout justify-center>
            <v-flex xs6>
                <transition name="component-fade" mode="out-in">
                    <SignQuestions
                            v-if="sign && currentShowing === showingVariants.question"
                            :sign="sign.name"
                            @answer="onAnswer"
                    />
                    <AnimalAnswer
                            v-if="answer && currentShowing === showingVariants.answer"
                            :answer="answer"
                            @refresh="onRefresh"
                            @edit="onEdit"
                    />
                    <AnimalForm
                            v-if="editingAnimal && signs && currentShowing === showingVariants.form"
                            :editing-animal="editingAnimal"
                            :signs="signs"
                            @close="onClose"
                    />
                </transition>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import eventBus from '../eventBus';
    import SignQuestions from '../components/animals/SignQuestions.vue';
    import { clear, getAllSigns, getAnswer, nextQuestion } from '../client/animals-client';
    import AnimalForm from '../components/animals/AnimalForm.vue';
    import { compareById } from '../utils/compare-util';
    import AnimalAnswer from '../components/animals/AnimalAnswer.vue';
    //TODO: пройтись по всем компонентам и поправить запросы
    export default {
        name: 'Animals',
        components: {
            AnimalAnswer,
            AnimalForm,
            SignQuestions,
        },
        data() {
            return {
                showingVariants: {
                    question: 1,
                    answer: 2,
                    form: 3,
                },
                currentShowing: null,
                sign: null,
                answer: null,
                signs: null,
                editingAnimal: null,
            };
        },
        async beforeRouteEnter(to, from, next) {
            try {
                await clear();
                const sign = nextQuestion(null);
                const signs = getAllSigns();
                next(async (vm) => {
                    vm.sign = await sign;
                    vm.signs = (await signs).sort(compareById);
                    vm.currentShowing = vm.showingVariants.question;
                });
            } catch (e) {
                next(false);
                eventBus.$emit('message', e.response.data.message);
            }
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
                            this.answer = response;
                            this.currentShowing = this.showingVariants.answer;
                        } else {
                            this.editingAnimal = response;
                            this.answer = null;
                            this.sign = null;
                            this.currentShowing = this.showingVariants.form;
                        }
                    }
                } catch (e) {
                    eventBus.$emit('message', e.response.data.message);
                }
            },
            async onRefresh() {
                await clear();
                this.sign = await nextQuestion(null);
                this.answer = null;
                this.currentShowing = this.showingVariants.question;
            },
            onEdit() {
                this.editingAnimal = this.answer;
                this.currentShowing = this.showingVariants.form;
            },
            onClose() {
                this.editingAnimal = null;
                this.onRefresh();
            },
        },
    };
</script>

<style scoped>

</style>
