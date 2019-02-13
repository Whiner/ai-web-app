<template>
    <v-dialog v-model="dialog" persistent max-width="400">
        <v-card>
            <v-card-title class="headline" />
            <v-card-text />
            <v-card-actions>
                <v-btn color="green darken-1" flat @click="dialog = false">
                    Disagree
                </v-btn>
                <v-btn color="red darken-1" flat @click="dialog = false">
                    Agree
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    export default {
        name: 'AddModalDialog',
        date() {
            return {
                name,
            };
        },
        props: {
            show: {
                type: Boolean,
                default: false,
            },
        },
        methods: {
            cancel() {
                this.$emit('cancel');
            },

            apply() {
                this.$emit('apply');
            },
        },
    };
</script>

<style scoped>

</style>
