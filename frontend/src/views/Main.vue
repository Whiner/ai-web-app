<template>
    <div>
        <Sidebar />
        <v-content>
            <transition name="component-fade" mode="out-in">
                <router-view />
            </transition>
        </v-content>
    </div>
</template>

<script>

    import Sidebar from '../components/Sidebar.vue';

    export default {
        name: 'Main',
        components: { Sidebar },
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
