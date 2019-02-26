<template>
    <v-card>
        <v-card-title>
            <p class="mb-0 ml-2 title">
                Добавить животное
            </p>
        </v-card-title>
        <v-divider />
        <v-card-text class="py-0 my-3">
            <v-container fluid py-0 px-3>
                <v-layout v-for="sign in showingSigns"
                          :key="sign.id"
                          row
                          mb-2
                >
                    <v-flex>
                        <v-checkbox
                                v-model="sign.checked"
                                :label="sign.name"
                                color="secondary"
                                :disabled="!sign.checked && currentChecked.length === 2"
                                hide-details
                        />
                    </v-flex>
                </v-layout>
                <v-layout row mt-3 ml-1>
                    <v-flex>
                        <v-text-field v-model="name" label="Название" />
                    </v-flex>
                </v-layout>
            </v-container>
        </v-card-text>
        <v-divider />
        <v-card-actions class="layout justify-center mt-2 pt-0">
            <v-flex xs4 mr-3>
                <v-btn flat
                       block
                       color="success"
                       @click="add"
                >
                    Добавить
                </v-btn>
            </v-flex>
            <v-flex xs4 ml-3>
                <v-btn flat
                       block
                       color="error"
                       @click="dismiss"
                >
                    Отменить
                </v-btn>
            </v-flex>
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        name: 'AnimalForm',
        props: {
            signs: {
                type: Array,
                default: null,
            },
            checkedSigns: {
                type: Array,
                default: null,
            },
        },
        data() {
            return {
                name: '',
            };
        },
        computed: {
            localSigns() {
                return this.signs.map(v => ({
                    id: v.id,
                    name: v.name,
                    checked: false,
                }));
            },
            currentChecked() {
                return this.localSigns.filter(v => v.checked);
            },
            showingSigns() {
                this.localSigns.forEach((v) => {
                    if (this.checkedSigns.find(value => v.id === value.id)) {
                        v.checked = true;
                    }
                });
                return this.localSigns;
            },

        },
        methods: {
            add() {
                // this.$emit('add', this.name, this.currentChecked);
                console.log('add');
            },
            dismiss() {
                this.$emit('dismiss');
            },
        },
    };
</script>

<style scoped lang="stylus">
  .title {
    font-size 1.3rem
  }
  .theme--light >>> .v-label {
    color var(--secondary-base)
  }
</style>
