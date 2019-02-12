<template>
    <v-container fluid>
        <Sidebar />
        <router-view />
    </v-container>
</template>

<script>

    import Sidebar from '../components/Sidebar.vue';

    export default {
        name: 'Home',
        components: { Sidebar },
    };
</script>

<style lang="stylus">
  body {
    font-family: 'Roboto', sans-serif;
  }
</style>
