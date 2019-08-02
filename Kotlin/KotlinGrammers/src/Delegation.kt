
// 委托：动态代理
interface Animal{
    fun bark()
}

class Dog :Animal {
    override fun bark() {
        println("Wang Wang")
    }
}

class Cat(animal: Animal) : Animal by animal {
}

fun main(args: Array<String>) {
    Cat(Dog()).bark()
}