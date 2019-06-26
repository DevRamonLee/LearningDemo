<template>
    <div>
        <xxx></xxx>
        <a href="https://github.com/chrisvfritz/vue-enterprise-boilerplate/blob/master/src/components/_globals.js" target="_blank">通过 require.context 全局注册基础组件</a>
    </div>
</template>
<script>
// 局部注册 TodoList 组件
import TodoList from './widgets/TodoList'
export default {
    components:{
        'xxx':TodoList
    }
}
</script>
<style>

</style>
