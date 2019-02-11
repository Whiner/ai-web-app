<template>
    <span>{{ msg }}</span>
</template>

<script>
  import test from '../client/test';

  export default {
        name: 'Home',
        components: {
        },
        data() {
            return {
                msg: {
                    type: String,
                },
            };
        },
        async created() {
            this.msg = await test();
        },
    };
</script>
