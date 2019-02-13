<template>
    <div>
        <Sidebar />
        <transition name="component-fade" mode="out-in">
            <v-content>
                <router-view />
            </v-content>
        </transition>
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
</style>
