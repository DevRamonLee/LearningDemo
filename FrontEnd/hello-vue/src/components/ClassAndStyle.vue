<template>
    <div>
        <h1> 绑定 HTML Class</h1>
        <h2># 对象语法：给 v-bind:class 传递一个对象，实现动态切换 class </h2>
        <div class="static" v-bind:class="{ active: isActive, 'text-danger': hasError }">
            你可以在对象中传入更多属性来动态切换多个 class。此外，v-bind:class 指令也可以与普通的 class 属性共存
        </div>
        <h2># 第二种写法，绑定的数据对象不必内联定义在模板里 </h2>
        <div v-bind:class="classObject">第二种写法，绑定的数据对象不必内联定义在模板里</div>
        <h2># 第三种写法，绑定一个返回对象的结算属性，这是常用且强大的模式 </h2>
        <div v-bind:class="classComputeObject">第三种写法，绑定一个返回对象的结算属性，这是常用且强大的模式</div>
        <h2># 数组语法，可以把一个数组传给 v-bind：class, 数组可以使用三元表达式 </h2>
        <div v-bind:class="[isActive ? activeClass : '', errorClass]">v-bind:class="[isActive ? activeClass : '', errorClass]"</div>
        <h2># 上面的三元表达式有些繁琐，所以在数组中可以使用对象语法</h2>
        <div v-bind:class="[{ active: isActive }, errorClass]">v-bind:class="[{ active: isActive }, errorClass]</div>

        <h2># 也可以在自定义组件上使用 v-bind:class 添加样式，这些类会被添加到根元素上，但是根元素已经存在的类不会被覆盖</h2>

        <h1> 绑定内联样式 </h1>
        <div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }">v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }</div>
        <div v-bind:style="styleObject">直接绑定一个内联对象 v-bind:style="styleObject"</div>
        <h2> 绑定数组对象， v-bind:style 会自动为属性添加浏览器前缀 </h2>
        <div v-bind:style="[baseStyles, overridingStyles]"> v-bind:style 的数组语法可以将多个样式对象应用到同一个元素上  v-bind:style="[baseStyles, overridingStyles]</div>
        
        <h2> vue 2.3.0+ 多重值，:style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"> 只会渲染最后一个被浏览器支持的值</h2>

        <div :style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }">:style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            isActive: true,
            hasError: true,
            classObject: {
                active: true,
                'text-danger': false
            },
            error: null,
            activeClass: 'active',
            errorClass: 'text-danger',
            activeColor: 'red',
            fontSize: 30,
            styleObject: {
                color: 'red',
                fontSize: '13px'
            }
        }
    },
    computed:{
        classComputeObject: function () {
            return {
                active: this.isActive && !this.error,
                'text-danger': this.error && this.error.type === 'fatal'
            }
        }
    }
}
</script>
<style scoped>
h2{
    color:green
}
.static {
    border: 2px solid red
}
.active{
    background-color: aqua
}
.text-danger{
    color: red
}
</style>
