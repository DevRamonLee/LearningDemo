<template>
    <div>
        <h2> 具名插槽 </h2>
        <p> 使用 v-slot 指令向具名插槽提供内容，任何没有被包裹在带有 v-slot 的 template 中的内容都会被视为默认插槽的内容。</p>
        <slot-demo>
            <template v-slot:header>
                <h1>Here might be a page title</h1>
            </template>

            <p>A paragraph for the main content.</p>
            <p>And another one.</p>

            <template v-slot:footer>
                <p>Here's some contact info</p>
            </template>
        </slot-demo>
        <h2> 作用域插槽 </h2>
        <p> 父组件可以拿到子组件的数据，子组件可以在 slot 标签绑定属性值</p>
        <current-user>
            <template v-slot:default="slotProps">
                {{ slotProps.user.firstName }}
            </template>
        </current-user>

        <p> 默认插槽缩写语法，注意默认插槽缩写语法不能和具名插槽混用 </p>
        <current-user v-slot="slotProps">
            {{ slotProps.user.firstName }}
        </current-user>
        <p> 使用 ES 2015 解构插槽 Prop </p>
        <current-user v-slot="{ user }">
            {{ user.firstName }}
        </current-user>
        <p> prop 重命名 </p>
        <current-user v-slot="{ user: person }">
            {{ person.firstName }}
        </current-user>

        <h2> 具名插槽的缩写 </h2>
        <slot-demo>
            <template #header>
                <h1>Here might be a page title</h1>
            </template>

            <p>A paragraph for the main content.</p>
            <p>And another one.</p>

            <template #footer>
                <p>Here's some contact info</p>
            </template>
        </slot-demo>
        
    </div>
</template>
<script>
export default {
    components: {
        'slot-demo':{
             template: `
                <div class="container">
                <header>
                    <slot name="header"></slot>
                </header>
                <main>
                    <slot></slot>
                </main>
                <footer>
                    <slot name="footer"></slot>
                </footer>
                </div>
            `
        },
        'current-user':{
            data(){
                return {
                    user:{firstName:'Ramon', lastName:'Lee'}
                }
            },
            template:`
               <span>
                 <slot v-bind:user="user">{{ user.lastName }}</slot>
                </span> 
            `
        }
    }
}
</script>
<style>

</style>
