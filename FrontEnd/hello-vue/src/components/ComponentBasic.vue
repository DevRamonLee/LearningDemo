<template>
    <div>
        <!-- 需要在 main.js 里注册这个组件 -->
        <button-counter></button-counter>
        <button-counter></button-counter>
        <button-counter></button-counter>
        <p> 通过 prop 向子组件传递数据 </p>
        <div>
            <blog-post title="My journey with Vue"></blog-post>
        </div>
        <p>使用 v-bind 来动态传递数据, $event 表示子组件传递给父组件的值。如果事件处理函数是一个方法，那么这个值会
            作为第一个参数传给函数。
        </p>
        <div>
            <div :style="{ fontSize: postFontSize + 'em' }">
                <blog-post
                    v-for="post in posts"
                    v-bind:key="post.id"
                    v-bind:post="post"
                    v-on:enlarge-text="postFontSize += $event"
                ></blog-post>
            </div>
        </div>
        <p> 在自定义组件使用 v-model </p>
        <custom-input v-model="searchText"></custom-input>
        <p>searchText: {{searchText}}</p>

        <p>上面的写法等价于下面的写法</p>
        <custom-input
            v-bind:value="searchText"
            v-on:input="searchText = $event"
        ></custom-input>

        <p> 通过插槽分发内容, 通过 slot 向一个组件传递内容</p>
        <alert-box>
            Something bad happened.
        </alert-box>

        <p>通过 is 特性动态切换组件，参数为已注册组件的名字</p>

        <button
            v-for="tab in tabs"
            v-bind:key="tab"
            v-bind:class="['tab-button', { active: currentTab === tab }]"
            v-on:click="currentTab = tab"
        >{{ tab }}</button>

        <component
            v-bind:is="currentTabComponent"
            class="tab"
        ></component>

        <p>解析 DOM 模板时通过使用 is 可以避免渲染错误，如 table 中只能出现 tr </p>
    </div>
</template>
<script>
export default {
    data(){
        return {
            posts: [
                { id: 1, title: 'My journey with Vue',content:'<p>It is a content</p>' },
                { id: 2, title: 'Blogging with Vue' ,content:'<p>It is a content</p>'},
                { id: 3, title: 'Why Vue is so fun' ,content:'<p>It is a content</p>'}
            ],
            postFontSize: 1,
            searchText:'',
            currentTab: 'Home',
            tabs: ['Home', 'Posts', 'Archive']
        }
    },
    computed: {
        currentTabComponent: function () {
            return 'tab-' + this.currentTab.toLowerCase()
        }
    }
}
</script>
<style>
.demo-alert-box{
    background-color: darksalmon;
}

.tab-button {
  padding: 6px 10px;
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
  border: 1px solid #ccc;
  cursor: pointer;
  background: #f0f0f0;
  margin-bottom: -1px;
  margin-right: -1px;
}
.tab-button:hover {
  background: #e0e0e0;
}
.tab-button.active {
  background: #e0e0e0;
}
.tab {
  border: 1px solid #ccc;
  padding: 10px;
}
</style>
