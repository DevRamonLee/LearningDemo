<template>
    <div>
        <h2> 访问根实例 $root  访问父组件 $parent </h2>
        <p> root 和parent 都能够实现访问父组件的属性的方法，两者的区别在于，
            如果存在多级子组件，通过$parent访问得到的是它最近一级的父组件，通过root访问得到的是根组件。</p>
            <root-obj ref='getRootObj'>xxxxx</root-obj>
        <h2> 访问子组件实例或子元素 </h2>
        <input ref="inputRef"/>
        <Button v-on:click="getRef">ref 测试 </Button>
        
    </div>
</template>
<script>
import Vue from 'vue'
Vue.component('root-obj', {
    data() {
        return {
                    msg: '我是子组件'
        }
    },
    template: `<div>
                    <button @click='getRoot'>子组件</button>
                    <child-component></child-component>
                </div>`,
    methods: {
        getRoot() {
            console.log(this.msg)
            console.log(this.$parent.msg)
            console.log(this.$root.msg)           
        }
    }
})
Vue.component('child-component', {
    data() {
        return {
                    msg: '我是子子组件'
        }
    },
    template: `<div>
                <button @click='getRoot'>子子组件</button>
                </div>`,
    methods: {
        getRoot() {
            console.log(this.msg)
            console.log(this.$parent.msg)
            console.log(this.$root.msg)            
        }
    }
})
export default {
    data() {
        return {
            msg:'我是父组件'
        }
    },
    methods:{
        getRootObj:function() {
            console.log(this.$refs.getRootObj.msg)
        },
        getRef:function() {
            console.log("getRef")
            this.$refs.inputRef.focus()
        }
    }
}
</script>
<style>

</style>
