<template>
    <v-navigation-drawer
            app
            permanent
            fixed
            floating
            dark
            width="240"
            :mini-variant.sync="mini"
    >
        <v-layout column
                  fill-height
                  justify-space-between
                  @mouseenter="mini = false"
                  @mouseleave="mini = true"
        >
            <v-flex xs1 />
            <v-flex xs4>
                <v-list>
                    <v-list-tile
                            v-for="item in items"
                            :key="item.title"
                            :to="item.route"
                            class="transition-off"
                    >
                        <v-list-tile-action>
                            <font-awesome-icon
                                    class="fa-lg transition-on"
                                    :icon="item.icon"
                            />
                        </v-list-tile-action>

                        <v-list-tile-content>
                            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-flex>
            <v-flex xs1>
                <v-list>
                    <v-list-tile :to="settings.route">
                        <v-list-tile-action>
                            <v-icon>{{ settings.icon }}</v-icon>
                        </v-list-tile-action>

                        <v-list-tile-content>
                            <v-list-tile-title>{{ settings.title }}</v-list-tile-title>
                        </v-list-tile-content>
                    </v-list-tile>
                </v-list>
            </v-flex>
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
                        icon: 'ambulance',
                        route: '/',
                    },
                    {
                        title: 'Угадать животное',
                        icon: 'cat',
                        route: '/animals',
                    },
                    {
                        title: 'Найти экстремум',
                        icon: 'chart-area',
                        route: '/extremum',
                    },
                ],
                settings: {
                    title: 'Настройки',
                    icon: 'settings',
                    route: '/settings',
                },
            };
        },
        methods: {

        },
    };
</script>

<style scoped lang="stylus">
  .transition-off {
    transition none
  }

  .transition-on {
    transition 0.5s
  }

</style>
