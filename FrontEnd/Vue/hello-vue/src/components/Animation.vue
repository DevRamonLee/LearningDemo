<template>
    <div>
        <p> 单元素、组件的过渡</p>    
        <button v-on:click="show = !show"> Toggle </button>
        <transition name="fade">
            <p v-if="show">hello</p>
        </transition>
        <button v-on:click="show2 = !show2">Toggle2</button>
        <transition name="slide-fade">
            <p v-if="show2">slide fade</p>
        </transition>
        <button @click="show3 = !show3">Toggle show</button>
        <transition name="bounce">
            <p v-if="show3">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris facilisis enim libero, at lacinia diam fermentum id. Pellentesque habitant morbi tristique senectus et netus.</p>
        </transition>

        <p> 自定义过渡类名</p>
         <button @click="show4 = !show4">
        Toggle render

        <p> JavaScript 钩子函数</p>
        <transition
            v-on:before-enter="beforeEnter"
            v-on:enter="enter"
            v-on:after-enter="afterEnter"
            v-on:enter-cancelled="enterCancelled"

            v-on:before-leave="beforeLeave"
            v-on:leave="leave"
            v-on:after-leave="afterLeave"
            v-on:leave-cancelled="leaveCancelled"
            >
            <!-- ... -->
        </transition>
    </button>
        <transition
            name="custom-classes-transition"
            v-on:before-enter="beforeEnter"
            enter-active-class="animated tada"
            leave-active-class="animated bounceOutRight">
            <p v-if="show4">hello</p>
        </transition>
    </div>
</template>

<script>
export default {
    data() {
        return {
            show: true,
            show2: true,
            show3: true,
            show4: true
        }
    },
    methods: {
        beforeEnter: function() {
            console.log('beforeEnter')
        }
    }
}
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity .5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}

/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all .3s ease;
}
.slide-fade-leave-active {
  transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to
/* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(50px);
  opacity: 0;
}

.bounce-enter-active {
  animation: bounce-in 3s;
}
.bounce-leave-active {
  animation: bounce-in .5s reverse;
}
@keyframes bounce-in {
  0% {
    transform: scale(0);
  }
  50% {
    transform: scale(1.9);
  }
  100% {
    transform: scale(1);
  }
}
</style>
