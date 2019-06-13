// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'

Vue.config.productionTip = false


// 定义一个名为 button-counter 的新组件
Vue.component('button-counter', {
  //一个组件的 data 选项必须是一个函数，这样每个实例可以获得一份返回对象的独立拷贝
  data: function () {
    return {
      count: 0
    }
  },
  template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
})

// 通过 prop 向子组件传递数据, 通过 $emit 向父组件传递一个事件，第二个参数为值
Vue.component('blog-post', {
  props: ['post'],
  template: `
    <div class="blog-post">
      <h3>{{ post.title }}</h3>
      <button v-on:click="$emit('enlarge-text', 0.1)">
        Enlarge text
      </button>
      <div v-html="post.content"></div>
    </div>
  `
})

// 在自定义组件上使用 v-model
Vue.component('custom-input', {
  props: ['value'],
  template: `
    <input
      v-bind:value="value"
      v-on:input="$emit('input', $event.target.value)"
    >
  `
})

// 通过插槽分发内容
Vue.component('alert-box', {
  template: `
    <div class="demo-alert-box">
      <strong>Error!</strong>
      <slot></slot>
    </div>
  `
})

// 动态进行组件的切换
Vue.component('tab-home', { 
	template: '<div>Home component</div>' 
})
Vue.component('tab-posts', { 
	template: '<div>Posts component</div>' 
})
Vue.component('tab-archive', { 
	template: '<div>Archive component</div>' 
})

/* eslint-disable no-new */
new Vue({
  data(){
    return {
      msg:'我是根组件'
    }
  },
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
