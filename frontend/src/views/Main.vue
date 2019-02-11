<template>
    <div>
        <Sidebar />
        <v-content>
            <transition name="component-fade" mode="out-in">
                <router-view />
            </transition>
            <v-snackbar
                    v-model="snackbar"
                    :timeout="timeout"
                    right
                    bottom
            >
                {{ text }}
                <v-btn
                        color="pink"
                        flat
                        @click="snackbar = false"
                >
                    Закрыть
                </v-btn>
            </v-snackbar>
        </v-content>
    </div>
</template>

<script>
    import Sidebar from '../components/Sidebar.vue';
    import eventBus from '../eventBus';

    export default {
        name: 'Main',
        components: { Sidebar },
        data() {
            return {
                text: '',
                snackbar: false,
                timeout: 3000,
            };
        },
        created() {
            eventBus.$on('message', (data) => {
                this.text = data;
                this.snackbar = true;
            });
        },
    };
</script>

<style lang="stylus">
  body {
    font-family 'Roboto', sans-serif
  }

  .component-fade-enter-active, .component-fade-leave-active {
    transition: opacity .3s ease;
  }
  .component-fade-enter, .component-fade-leave-to {
    opacity: 0;
  }
</style>
