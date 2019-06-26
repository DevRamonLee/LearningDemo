<template>
    <div>
        <h2> # 计算属性</h2>
        <p>Original message: "{{ message }}"</p>
        <p>Computed reversed message: "{{ reversedMessage }}"</p>
        <q> 使用方法也可以达到这种效果，但是不同的是，计算属性会进行缓存，只要计算属性依赖的
            值没有发生改变，多次访问计算属性会立即返回之前的计算结果，而不需要重新计算。
        </q>

        <h2> # 计算属性 vs 侦听属性</h2>
        <q>计算属性适用于多个值决定一个值，侦听器适用于响应数据的变化，当数据变化时执行异步或者开销较大的操作</q>
        <p> {{ fullName }}</p>

        <h2>使用侦听器监听输入变化，执行异步操作例子</h2>
        <q>注意： 需要在 index.html 中引入第三方 js 库 lodash 和 axios</q>
        <p>
            Ask a yes/no question:
            <input v-model="question">
        </p>
        <p>{{ answer }}</p>
    </div>
    
</template>
<script>
export default {
    data() {
        return {
            message: 'Hello',
            firstName: 'Foo',
            lastName: 'Bar',
            // fullName: 'Foo Bar'
            question: '',
            answer: 'I cannot give you an answer until you ask a question!'
        }
    },
    computed: {
        // 计算属性的 getter
        reversedMessage: function () {
            // `this` 指向 vm 实例
            return this.message.split('').reverse().join('')
        },
        fullName:{ // 使用计算属性优于下面的侦听器
            // getter
            get: function () {
                return this.firstName + ' ' + this.lastName
            },
            // setter 
            //运行 vm.funName = 'Ramon Lee',setter 函数会被调用，firstName 和 lastName 会被更新
            set: function (newValue) {
                var names = newValue.split(' ')
                this.firstName = names[0]
                this.lastName = names[names.length - 1]
            }
        }
    },
    watch: {
        // firstName: function (val) {
        // this.fullName = val + ' ' + this.lastName
        // },
        // lastName: function (val) {
        // this.fullName = this.firstName + ' ' + val
        // }
        // 如果 `question` 发生改变，这个函数就会运行
        question: function (newQuestion, oldQuestion) {
            this.answer = 'Waiting for you to stop typing...'
            this.debouncedGetAnswer()
        }
    },
    created: function () {
        // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
        // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
        // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
        // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
        // 请参考：https://lodash.com/docs#debounce
        this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
    },
    methods: {
        getAnswer: function () {
        if (this.question.indexOf('?') === -1) {
            this.answer = 'Questions usually contain a question mark. ;-)'
            return
        }
        this.answer = 'Thinking...'
        var vm = this
        axios.get('https://yesno.wtf/api')
            .then(function (response) {
                vm.answer = _.capitalize(response.data.answer)
            })
            .catch(function (error) {
                vm.answer = 'Error! Could not reach the API. ' + error
            })
        }
    }
}
</script>
<style scoped>
h2{
    color: green
}
</style>
