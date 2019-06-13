import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import ListRender from '@/components/ListRender'
import TodoList from "@/components/widgets/TodoList"
import TemplateGrammar from "@/components/TemplateGrammar"
import ComputeAndWatch from "@/components/ComputeAndWatch"
import ClassAndStyle from "@/components/ClassAndStyle"
import IfRender from "@/components/IfRender"
import EventHandle from "@/components/EventHandle"
import FormBinding from "@/components/FormBinding"
import ComponentBasic from "@/components/ComponentBasic"
import ComponentRegister from "@/components/ComponentRegister"
import Prop from "@/components/Prop"
import CustomEvent from "@/components/CustomEvent"
import Slot from "@/components/Slot"
import Dynamic from "@/components/Dynamic"
import BoundarySituation from "@/components/BoundarySituation"

Vue.use(Router)

Vue.component(TodoList.name, TodoList);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/list/render',
      name: 'ListRender',
      component: ListRender
    },
    {
      path: '/template/grammar',
      name: 'TemplateGrammar',
      component: TemplateGrammar
    },
    {
      path: '/compute/watch',
      name: 'ComputeAndWatch',
      component: ComputeAndWatch
    },
    {
      path: '/class/style',
      name: 'ClassAndStyle',
      component: ClassAndStyle
    },
    {
      path: '/if/render',
      name: 'IfRender',
      component: IfRender
    },
    {
      path: '/event/handle',
      name: 'EventHandle',
      component: EventHandle
    },
    {
      path: '/form/binding',
      name:'FormBinding',
      component:FormBinding
    },
    {
      path:'/component/basic',
      name: 'ComponentBasic',
      component: ComponentBasic
    },
    {
      path:'/component/register',
      name:'ComponentRegister',
      component:ComponentRegister
    },
    {
      path:'/prop',
      name:'Prop',
      component:Prop
    },
    {
      path:'/custom/event',
      name:'CustomEvent',
      component:CustomEvent
    },
    {
      path:'/slot',
      name:'Slot',
      component:Slot
    },
    {
      path:'/dynamic',
      name:'Dynamic',
      component:Dynamic
    },
    {
      path:'/boundary/situation',
      name:'BoundarySituation',
      component:BoundarySituation
    }
  ]
})
