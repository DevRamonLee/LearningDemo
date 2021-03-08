<template>
    <div>
        <h2>基础用法</h2>
        <div>
            <input v-model="message" placeholder="edit me">
            <p>Message is: {{ message }}</p>
        </div>
        <div>
            <span>Multiline message is:</span>
            <p style="white-space: pre-line;">{{ message }}</p>
            <br>
            <textarea v-model="multiMessage" placeholder="add multiple lines"></textarea>
        </div>
        <div>
            <input type="checkbox" id="checkbox" v-model="checked">
            <label for="checkbox">{{ checked }}</label>
        </div>
        <p> 多个复选框，绑定到一个数组 </p>
        <div>
            <input type="checkbox" id="jack" value="Jack" v-model="checkedNames">
            <label for="jack">Jack</label>
            <input type="checkbox" id="john" value="John" v-model="checkedNames">
            <label for="john">John</label>
            <input type="checkbox" id="mike" value="Mike" v-model="checkedNames">
            <label for="mike">Mike</label>
            <br>
            <span>Checked names: {{ checkedNames }}</span>
        </div>
        <p> 单选按钮 </p>
        <div>
            <input type="radio" id="one" value="One" v-model="picked">
            <label for="one">One</label>
            <br>
            <input type="radio" id="two" value="Two" v-model="picked">
            <label for="two">Two</label>
            <br>
            <span>Picked: {{ picked }}</span>
        </div>
        <p> 单选下拉框 </p>
        <div>
            <select v-model="selected">
                <option disabled value="">请选择</option>
                <option>A</option>
                <option>B</option>
                <option>C</option>
            </select>
            <span>Selected: {{ selected }}</span>
        </div>
        <p> 多选下拉框，绑定到数组</p>
        <div id="example-6">
            <select v-model="selectedArr" multiple style="width: 50px;">
                <option>A</option>
                <option>B</option>
                <option>C</option>
            </select>
            <br>
            <span>Selected: {{ selectedArr }}</span>
        </div>
        <p> 使用 v-for 动态渲染选项</p>
        <select v-model="selectedOptions">
            <option v-for="option in options" :key="option.text" v-bind:value="option.value">
                {{ option.text }}
            </option>
        </select>
        <span>Selected: {{ selectedOptions }}</span>

        <h2> 值绑定 </h2>
        <div>
            <p> 设置复选框值绑定，不绑定默认是 true 和 false </p>
            <input
                type="checkbox"
                v-model="toggle"
                true-value="yes"
                false-value="no">
            <span> checkbox : {{toggle}}</span>
            <p> 单选按钮</p>
            <input type="radio" v-model="pick" v-bind:value="a">
            <span>{{pick}}</span>
            <p>选择框的选项值绑定</p>
            <select v-model="selectedValue">
                <!-- 内联对象字面量 -->
                <option v-bind:value="{ number: 123 }">123</option>
            </select>
            <span>Selected value {{selectedValue}}</span>
        </div>
        <h2> v-model 修饰符 </h2>
        <div>
            <p> .lazy 修饰符 </p>
            <!-- 在“change”时而非“input”时更新 -->
            <input v-model.lazy="msg" ><br/>
            <span>v-model.lazy 在 change 时更新而非 input 时更新： {{msg}}</span>
        </div>
        <div>
            <p> .number 修饰符,将用户输入的值转为数值类型 </p>
            <input v-model.number="age" type="number">
        </div>
         <div>
            <p> .trim 修饰符,过滤用户输入的首尾空白字符 </p>
            <input v-model.trim="msg">
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            message: '',
            multiMessage: '',
            checked: false,
            checkedNames: [],
            picked: '',
            selected:'',
            selectedArr:[],
            options: [
                { text: 'One', value: 'A' },
                { text: 'Two', value: 'B' },
                { text: 'Three', value: 'C' }
            ],
            selectedOptions:'A',
            toggle: '',
            pick:false,
            a:'selected',
            selectedValue:'',
            msg:'',
            age:1
        }
    }
}
</script>
<style>

</style>
