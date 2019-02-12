<template>
    <v-navigation-drawer
            permanent
            fixed
            floating
            dark
            :mini-variant.sync="mini"
            width="300"
    >
        <v-layout column
                  fill-height
                  justify-center
                  @mouseenter="mini = false"
                  @mouseleave="mini = true"
        >
            <v-list>
                <v-list-tile
                        v-for="item in items"
                        :key="item.title"
                >
                    <v-list-tile-action>
                        <font-awesome-icon :icon="item.icon" class="fa-lg" />
                    </v-list-tile-action>

                    <v-list-tile-content>
                        <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-layout>
    </v-navigation-drawer>
</template>

<script>
    export default {
        name: 'Sidebar',
        data() {
            return {
                mini: true,
                items: [
                    {
                        title: 'Узнать диагноз',
                        icon: 'ambulance', //тут вопрос
                    },
                    {
                        title: 'Угадать животное',
                        icon: 'cat',
                    },
                    {
                        title: 'Найти экстремум',
                        icon: 'chart-area',
                    },
                ],
            };
        },
    };
</script>

<style scoped>

</style>
