<template>
    <div>
        <h2> 自定义组件的 v-model </h2>
        <base-checkbox v-model="lovingVue"></base-checkbox>
        <p>lovingVue is : {{lovingVue}}</p>

        <p>将原生事件绑定到组件</p>
        <p> 我们可以使用 v-on 的 .native 修饰符在一个组件的根元素上直接监听一个原生事件
            但是当这个组件没有这个事件时，父级的 .native 监听器将静默失败
        </p>
        <!--<base-input v-on:focus.native="onFocus"></base-input>-->
        <base-input v-on:focus="onFocus" v-bind:placeholder='hint'></base-input><br/>
        <q>Vue 提供了一个 $listeners 属性，它是一个对象，里面包含了作用在这个组件上的所有监听器。</q>
        <h2> v2.3.0s .sync 修饰符（语法糖）</h2>
        <p> 当一个子组件改变了一个带 .sync 的 prop 值时，这个变化也会同步到父组件中所绑定的值</p>

        <span> 父组件 title 的值 {{title}} </span>
        <text-document
            v-bind:title="title"
            v-on:update:title="title = $event"
        ></text-document>
        <p> 使用 .sync 的简写写法</p>
        <text-document
            v-bind:title.sync="title"
        ></text-document>
    </div>
</template>
<script>
export default {
    data(){
        return {
            lovingVue: true,
            hint:'点击获取焦点，触发 onFocus 事件',
            title:'this is .sync example.'
        }
    },
    components:{
        'base-checkbox':{
            model: {
                prop: 'checked',
                event: 'change'
            },
            props: {
                checked: Boolean
            },
            template: `
                <input
                type="checkbox"
                v-bind:checked="checked"
                v-on:change="$emit('change', $event.target.checked)"
                >
            `
        },
        'base-input':{
            inheritAttrs: false,
            props: ['label', 'value','placeholder'],
            computed: {
                inputListeners: function () {
                var vm = this
                // `Object.assign` 将所有的对象合并为一个新对象
                return Object.assign({},
                    // 我们从父级添加所有的监听器
                    this.$listeners,
                    // 然后我们添加自定义监听器，
                    // 或覆写一些监听器的行为
                    {
                        // 这里确保组件配合 `v-model` 的工作
                        input: function (event) {
                            vm.$emit('input', event.target.value)
                        }
                    }
                )
                }
            },
            template:`
                <label>
                {{ label }}
                <input
                    v-bind="$attrs"
                    v-bind:value="value"
                    v-bind:placeholder="placeholder"
                    v-on="inputListeners"
                >
                </label>
            `
        },
        'text-document':{
            model: {
                prop: 'title',
            },
            props: ['title'],
            computed: {
                myListeners: function() {
                    var vm = this;
                    return Object.assign({},
                        // 我们从父级添加所有的监听器
                        this.$listeners,
                        // 然后我们添加自定义监听器，
                        // 或覆写一些监听器的行为
                        {
                            input:function(event) {
                                vm.$emit('update:title',event.target.value)
                            }
                        }
                    )
                }
            },
            template: `
                <div>
                    <input v-on="myListeners">
                </div>
            `
        }
    },methods:{
        onFocus() {
            alert('onFocus')
        }
    }
}
</script>
<style>

</style>
