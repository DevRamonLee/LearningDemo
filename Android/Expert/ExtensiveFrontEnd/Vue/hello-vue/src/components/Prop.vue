<template>
    <div>
        <p> prop 的流向是单向的，只能从父级组件流向子组件，反过来则不行，我们不应该在子组件内部尝试
            改变 Prop，下面是两种常见的试图改变一个 Prop 的情形
        </p>
        <p>1. 这个 prop 用来传递一个初始值；这个子组件接下来希望将其作为一个本地的 prop 数据来使用。在这种情况下，最好定义一个本地的 data 属性并将这个 prop 用作其初始值：</p>
        <code>
            props: ['initialCounter'],
            data: function () {
            return {
                counter: this.initialCounter
            }
            }
        </code>
        <p> 2. 这个 prop 以一种原始的值传入且需要进行转换。在这种情况下，最好使用这个 prop 的值来定义一个计算属性：</p>
        <code>
            props: ['size'],
            computed: {
            normalizedSize: function () {
                return this.size.trim().toLowerCase()
            }
            }
        </code>
        <br/>
        <a href="https://cn.vuejs.org/v2/guide/components-props.html"> Prop 官方使用介绍</a><br/>
        <a href="https://www.jianshu.com/p/ce8ca875c337">vm.$attrs 【Vue 2.4.0新增inheritAttrs，attrs详解】</a>

        <p> inheritAttrs: false 只渲染从父组件传过来的你需要渲染的数据 </p>
        <mytest  :title="title" :massgae="massgae"></mytest>
    </div>
</template>
<script>
export default {
    data() {
        return {
            title:'title1111',
            massgae:'message111'
        }
    },
    components:{
    'mytest':{
      template:`<div>这是个h1标题{{title}}</div>`,
      props:['title'],
      inheritAttrs: false,
      data(){
        return{
          mag:'111'
        }
      },
      created:function(){
        console.log(this.$attrs)//注意这里
      }
    }
  }

}
</script>
<style>

</style>
