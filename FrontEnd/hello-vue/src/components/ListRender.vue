<template>
  <div id="listRender">
    <h2>列表渲染</h2>
    <p>1. v-for 支持第二个可选参数，代表索引 v-for="(item, index) in items"; v-for 块中支持访问父作用域的属性</p>
    <p>数组的变异方法会触发视图更新</p>
    <button @click="pushTest">变异方法 push():增加</button>
    <button @click="popTest">变异方法 pop()：取出最后一个</button>
    <button @click="shiftTest">变异方法 shift()：取出第一个</button>
    <button @click="unshiftTest">变异方法 unshift()：数组开头加入元素</button>
    <button @click="spliceTest">变异方法 splice()：删除元素或在指定位置添加元素</button>
    <button @click="sortTest">变异方法 sort()：对数组进行排序</button>
    <button @click="reverseTest">变异方法 reverse()：对数组进行反转</button>

    <p>数组的非变异方法不会触发视图的更新，会返回一个新数组</p>
    <button @click="filterTest">非变异方法 filter()：对数组进行过滤</button>
    <button @click="concatTest">非变异方法 concat(): 连接两个或多个数组</button>
    <button @click="sliceTest">非变异方法 slice(): 截取数组</button>

    <p>为了解决 vm.items[1] = 'x' // 不是响应性的 和 vm.items.length = 2 // 不是响应性的</p>
    <button @click="setTest">Vue.set 与 splice 设置长度</button>
    <ul>
      <li
        v-for="(item, index) in items"
        :key="index"
      >{{parentMessage}} - {{index}}: {{ item.message }}</li>
    </ul>
    <q>为了给 Vue 一个提示，以便它能跟踪每个节点的身份，从而重用和重新排序现有元素，你需要为每项提供一个唯一 key 属性</q>

    <p>2. 使用 v-for 来遍历一个对象的属性, 可以使用三个参数 (value, name, index)，分别代表 值、属性名、属性下标</p>
    <ul id="v-for-object" class="demo">
      <li v-for="(value, name, index) in object" :key="index">{{index}} : {{name}} : {{ value }}</li>
    </ul>
    <p>对于非根级别的响应式属性使用 Vue.set(object, propertyName, value) 动态添加属性</p>
    <button @click="addPropertity">给对象动态添加响应式属性</button>
    <button @click="addMultiPropertity">给对象动态添加多个响应式属性</button>

    <p>3. 显示一个经过过滤或者排序后的数组，不实际改变或重置原始数据，可以创建计算属性实现,原始数据为 numbers: [ 1, 2, 3, 4, 5 ]</p>
    <li v-for="(n,index) in evenNumbers" :key="index">{{ n }}</li>

    <p>在计算属性不适用的时候也可以在 for 循环中使用方法</p>
    <li v-for="(n, index) in even(numbers)" :key="index + 3">{{ n }}</li>

    <p>在 v-for 里使用整数</p>
    <!-- key 相同会有警告 -->
    <span v-for="n in 10" :key="n + 10">{{ n }}</span>

    <p>4. todo list 的例子</p>
    <todo-item></todo-item>
  </div>
</template>

<script>
import Vue from "vue";
export default {
  name: "ListRender",
  data() {
    return {
      parentMessage: "Parent",
      items: [{ message: "Foo" }, { message: "Bar" }],
      object: {
        title: "How to do lists in Vue",
        author: "Ramon Lee",
        publishedAt: "2019-06-05"
      },
      numbers: [1, 2, 3, 4, 5]
    };
  },
  methods: {
    pushTest() {
      this.items.push({ message: "push test" });
    },
    popTest() {
      // 取出最后一个元素
      this.items.pop();
    },
    shiftTest() {
      // 取出第一个元素
      this.items.shift();
    },
    unshiftTest() {
      // 在数组前拼接元素
      this.items.unshift({ message: "unshift test" });
    },
    spliceTest() {
      // 从 0 开始删除 1 个元素(第二个参数代表删除几个)并重新插入一个到 0 ，如果第二个参数为 0 代表不删除
      this.items.splice(0, 1, { message: "splice" });
    },
    sortTest() {
      this.items.sort(); // sort 不生效
    },
    reverseTest() {
      this.items.reverse();
    },
    filterTest() {
      this.items = this.items.filter(function(item) {
        return item.message.match(/Foo/);
      });
    },
    concatTest() {
      this.items = this.items.concat([
        { message: "concat 1" },
        { message: "concat 2" }
      ]);
    },
    sliceTest() {
      this.items = this.items.slice(0, 1);
    },
    setTest() {
      Vue.set(this.items, 0, {
        message: "vue.set(vm.items, indexOfItem, newValue)"
      });
      // this.items.splice(1, 1, {message: 'items.splice(1, 1, nueValue)'})
      this.items.splice(1);
    },
    addPropertity() {
      Vue.set(this.object, "action", "add propertity");
    },
    addMultiPropertity() {
      // Object.assign(target, ...sources)
      this.object = Object.assign({}, this.object, {
        age: 28,
        favoriteColor: "black"
      });
    },
    even(numbers) {
      return numbers.filter(function(number) {
        return number % 2 === 0;
      });
    }
  },
  computed: {
    evenNumbers: function() {
      return this.numbers.filter(function(number) {
        return number % 2 === 0;
      });
    }
  }
};
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}
/* li {
  display: inline-block;
  margin: 0 10px;
} */
p {
  color: green;
  font-size: 1.5em;
}
</style>
