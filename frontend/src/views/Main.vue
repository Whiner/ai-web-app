<template>
    <v-container fluid>
        <Sidebar />
        <v-content>
            <router-view />
        </v-content>
    </v-container>
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
