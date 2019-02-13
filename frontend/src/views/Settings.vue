<template>
    <v-container fluid>
        <v-layout row>
            <v-flex xs6 mr-3>
                <DiagnosisSettings />
            </v-flex>
            <v-flex xs6 ml-3>
                <SymptomsSettings />
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import DiagnosisSettings from '../components/settings/DiagnosisSettings.vue';
    import SymptomsSettings from '../components/settings/SymptomsSettings.vue';

    export default {
        name: 'Settings',
        components: { SymptomsSettings, DiagnosisSettings },
    };
</script>

<style scoped>

</style>
