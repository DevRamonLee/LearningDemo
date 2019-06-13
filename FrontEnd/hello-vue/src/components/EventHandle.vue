<template>
    <div>
        <h2>监听事件</h2>
        <button v-on:click="counter += 1">Add 1</button>
        <p>The button above has been clicked {{ counter }} times.</p>

        <h2>事件处理方法</h2>
        <button v-on:click="greet">Greet</button>
        <button v-on:click="say('hi', $event)">内联直接调用 Say hi</button>

        <h2> 事件修饰符 </h2>
        <p>.stop 阻止事件继续传播,可阻止捕获事件也可以阻止冒泡事件</p>
        <p>.prevent  某些标签拥有自身的默认事件，如a[href="#"]，button[type="submit"] 这种标签在冒泡结束后会开始执行默认事件。注意默认事件虽然是冒泡后开始，但不会因为stop阻止事件传递而停止。</p>
        <p>.capture 代表捕获事件，从外部向内部传递的</p>
        <p>.self 只有当 event.target 是当前元素自身时触发函数（事件不是从内部元素触发的）</p>
        <p>.once 事件只会触发一次 </p>
        <p>.passive 每次事件产生，浏览器都会去查询一下是否有preventDefault阻止该次事件的默认动作。我们加上passive就是为了告诉浏览器，不用查询了，我们没用preventDefault阻止默认动作。可以极大的提高滑动效率</p>

        <h3> 1 .stop 阻止事件继续传播 </h3>
        <div v-on:click="dodo">
            <button v-on:click="doThis">阻止单击冒泡事件继续传播（未加.stop 修饰符）</button>
        </div>
        <div v-on:click="dodo">
            <button v-on:click.stop="doThis">阻止单击冒泡事件继续传播（加.stop 修饰符）</button>
        </div>

        <h3>2 .prevent 阻止组件的默认事件，注意观察捕获事件和冒泡事件的处理顺序</h3>
        <div @click="log('bubble'+1)" @click.capture="log(1)">
            <div @click="log('bubble'+2)" @click.capture="log(2)">
                <div @click="log('bubble'+3)" @click.capture="log(3)">
                    <a @click="log('bubble'+4)" @click.capture="log(4)" href="javascript:console.log('x')"> 点击这里</a>
                </div>
            </div>
        </div>

        <div @click="log('bubble'+1)" @click.capture="log(1)">
            <div @click="log('bubble'+2)" @click.capture="log(2)">
                <div @click="log('bubble'+3)" @click.capture.stop="log(3)">
                    <a @click="log('bubble'+4)" @click.capture="log(4)" href="javascript:console.log('x')"> 在捕获阶段加上了 .stop, 并没有阻止默认事件 输出 123x</a>
                </div>
            </div>
        </div>

        <div @click="log('bubble'+1)" @click.capture="log(1)">
            <div @click="log('bubble'+2)" @click.capture="log(2)">
                <div @click="log('bubble'+3)" @click.capture="log(3)">
                    <a @click.prevent="log('bubble'+4)" @click.capture="log(4)" href="javascript:console.log('x')">使用 .prevent 修饰符,阻止默认事件</a>
                </div>
            </div>
        </div>

        <h3> v-on:click.prevent.self（阻止所有点击） 与 v-on:click.self.prevent（只会阻止对元素自身的点击） 的区别</h3>
        <p> 点击 div 标签会输出 3 2 1 点击 a 标签会输出 2 1</p>
        <div>
            <div @click="log(1)">
                <a href="/#" @click="log(2)">a标签
                  <div @click="log(3)">div标签</div>
                </a>
            </div>
        </div>
        <p> 给 a 标签加上 v-on:click.prevent.self ,此时点击 a 标签会输出 2 1 ，点击 div 标签会输出 3 1  a标签没有冒泡也没有跳转，也就是阻止了所有点击 </p>
        <div>
            <div @click="log(1)">
                <a href="/#" @click.prevent.self="log(2)">a标签加上 .prevent.self
                  <div @click="log(3)">div标签</div>
                </a>
            </div>
        </div>

        <p>给 a 标签加上 v-on:click.self.prevent  点击 div标签会输出 3 1 跳转，此时 a标签没有输出 log，但是发生了跳转。点击 a 标签输出了log，但是没有跳转，所以就是只会阻止对自身元素的点击 </p>
        <div>
            <div @click="log(1)">
                <a href="/#" @click.self.prevent="log(2)">a标签加上 .self.prevent
                  <div @click="log(3)">div标签</div>
                </a>
            </div>
        </div>

        <h2> 按键修饰符 </h2>
        <div>
            <p>按键修饰符 v-on:keyup.enter ,在 按键 enter 按下时触发 </p>
            <input placeholder='请输入 enter 键' v-on:keyup.enter="submit">
            <input placeholder='请输入 page-down 键' v-on:keyup.page-down="onPageDown">
        </div>
        <h2> 按键码(按键码可能不被最新的浏览器支持，建议使用按键码别名) </h2>
        <div>
            <input placeholder= 'v-on:keyup.13= submit' v-on:keyup.13="submit">
        </div>
        
        <h2> 系统组合键 .ctrl .alt .shift .meta</h2>

        <div>
            <!-- Alt + C -->
            <input placeholder="alt + C " @keyup.alt.67="clear">
            <!-- Ctrl + Click -->
            <div @click.ctrl="doSomething"> Ctrl + Click Do something</div>
        </div>

        <h2>.exact 修饰符，精确控制系统的修饰符</h2>
        <div>
            <button @click.ctrl="onClick"> click.ctrl 即使 Alt 或 Shift 被一同按下时也会触发</button>
            <button @click.ctrl.exact="onCtrlClick"> click.ctrl.exact 有且只有 Ctrl 被按下的时候才触发</button>
        </div>

        <h2> 鼠标修饰符</h2>
        <div>
            <button @click.left="leftClick">Mouse left</button>
            <button @click.right="rightClick">Mouse right</button>
            <button @click.middle="middleClick">Mouse middle</button>
        </div>

    </div>
</template>
<script>
export default {
    data() {
        return {
            counter: 0,
            name: 'Vue.js'
        }
    },
    methods: {
        greet: function(event) {
            // this 在方法里指向当前vue 实例
            console.log(this)
            alert('Hello' + this.name + '!')
            // event 是原生 DOM 事件
            if(event) {
                alert(event.target.tagName)
            }
        },
        say: function(message, event) {
            // 访问原生事件对象
            if (event) event.preventDefault()
            alert(message)
        },
        doThis: function () {
            alert("noclick");
        },
        dodo: function () {
            alert("dodo");
        },
        log(msg) {
            console.log(msg)
        },
        submit(){
            alert('press enter')
        },
        onPageDown() {
            alert('press page-down')
        },
        clear() {
            alert('clear')
        },
        doSomething() {
            alert('Ctrl + Click do something')
        },
        onClick() {
            alert('onClick')
        },
        onCtrlClick(){
            alert('onCtrlClick')
        },
        leftClick() {
            alert('leftClick')
        },
        rightClick() {
            alert('rightClick')
        },
        middleClick() {
            alert('middleClick')
        }
    }
}
</script>
<style scoped>
ul{
  list-style-type: none;  
}
</style>
