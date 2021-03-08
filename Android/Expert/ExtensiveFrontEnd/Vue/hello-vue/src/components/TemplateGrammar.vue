<template>
    <div>
        <p> # 文本插值</p>
        <span v-once>Message: {{ msg }}</span>
        <p># 原始 HTML, 使用 v-html 显示真正的 html</p>
        <p>Using mustaches: {{ rawHtml }}</p>
        <p>Using v-html directive: <span v-html="rawHtml"></span></p>
        <p># 特性控制</p>
        <button v-bind:disabled="isButtonDisabled">Disabled Button</button>
        <p># 模板中使用 javascript 表达式，注意只能使用 单个表达式，类似 var a = 1 这种是不支持的。注意：模板表达式被放在沙盒中，只能访问
            全局变量的一个白名单，如 Math 和 Date，不应该在模板表达式中试图访问用户定义的全局变量。
        </p>
        {{number + 1 + new Date()}}
        <p>指令 v-if </p>
        <span v-if="seen"> v-if 测试，可以看见 </span>
        <p> # 参数绑定</p>
        <a v-bind:href="url" target="_blank">{{urlMessage}}</a>
        <Button class="change-link" v-on:click="goGoogle">改变链接</Button>
        <p> # 动态参数</p>
        <a v-bind:[attributeName]="url"> 动态参数 </a>
        <Button v-on:[eventName]="doSomething"> 动态绑定事件 </Button>

        <p> # 修饰符. .prevent 阻止默认事件，如阻止跳转</p>
        <a href="https://www.baidu.com"  @click.prevent="goBaidu">去百度</a>

        <p># 缩写： v-bind:href 缩写为 :href  ---- v-on:click 缩写为 @click</p>
    </div>
</template>

<script>
export default {
    data(){
        return {
            msg: '文本插值',
            rawHtml: '<span style="color:red">This should be red.</span>',
            isButtonDisabled: true,
            number: 1,
            seen: true,
            url: 'http://www.baidu.com',
            urlMessage: '百度一下、你就知道！',
            attributeName: 'href',
            eventName: 'click'
        }
    },
    methods: {
        goGoogle (e) {
            console.log(e.target.className)
            if(this.url == 'http://www.baidu.com'){
                this.url = 'http://wwww.google.com'
                this.urlMessage = 'google 一下才知道！'
            }else {
                this.url = 'http://www.baidu.com',
                this.urlMessage = '百度一下、你就知道！'
            }
        },
        doSomething() {
            alert("动态绑定事件")
        },
        goBaidu () {
            console.log("go baidu")
        }
    }
}
</script>
<style scoped>
p {
  color: green;
  font-size: 1.5em;
}
</style>
