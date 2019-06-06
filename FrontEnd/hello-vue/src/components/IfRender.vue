<template>
    <div>
        <h1> v-if </h1>
        <p>v-if指令用于条件性的渲染一块内容</p>
        <h3 v-if="awesome">Vue is awesome!</h3>
        <h3 v-else>Oh no 😢</h3>

        <div v-if="type === 'A'">
        A
        </div>
        <div v-else-if="type === 'B'">
        B
        </div>
        <div v-else-if="type === 'C'">
        C
        </div>
        <div v-else>
        Not A/B/C
        </div>

        <h2> 用 key 管理可复用的元素 </h2>
        <template v-if="loginType === 'username'">
            <label>Username</label>
            <input key="username" placeholder="Enter your username">
        </template>
        <template v-else>
            <label>Email</label>
            <input key="email" placeholder="Enter your email address">
        </template>
        <Button @click="toggleLoginType">Toggle login type</Button>

        <h2> v-show 与 v-if 不同的是 v-show 的元素会始终被渲染并保留在 DOM 中，v-show 只是在切换元素的 CSS 的 display 属性</h2> 
        <q> v-show 不支持 template 元素，也不支持 v-else</q>
        <h1 v-show="ok">Hello!</h1>
    </div>
</template>
<script>
export default {
    data() {
        return {
            ok:true,
            awesome: true,
            type:'A',
            loginType: 'username'
        }
    },
    methods:{
        toggleLoginType() {
            return this.loginType = this.loginType === 'username' ? 'email' : 'username'
        }
    }
}
</script>
<style scoped>
p{
    color: green
}
</style>
