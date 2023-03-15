package com.wcl.ktb.kotlin

// TODO: 空安全问题.
/**
 *  (1)安全调用(?或!!)操作符.
 *  (2)空合并(?:)操作符.
 *  (3)内置函数"let()"的安全调用.
 *  (4)先决条件函数.
 *  (5)Kotlin的互操作性和可空性.
 */
fun main() {
    //(1)安全调用(?或!!)操作符.
//    var name: String = null//报错:默认是不支持赋值null的,除非用"?"声明该类型.
    val name: String? = null
//    name.capitalize()//报错:name是可空类型,可能是null,不可调用,除非给出补救措施("?"或"!!").
    val capitalize1: String? = name?.capitalize()//name是可空类型,如果值为null,"?"后面代码不执行,就不会引发空指针.
    val capitalize2: String = name!!.capitalize()//"!!"表示断言,强制执行(同java一样),如果name为null则引发空指针异常.
    println("$capitalize1 + $capitalize2")

    //(2)空合并(?:)操作符.
    val name3: String? = null
    val result1: String = name3 ?: "是NULL" //如果name3为null,则返回"?:"后面.
    val result2: String = name3?.let { "[$it]" } ?: "是NULL" //let函数+空合并操作符的搭配使用场景.
    println("$result1 + $result2")

    //(3)内置函数"let()"的安全调用(常用).
    val name2: String? = null
    val let: String? = name2?.let {//如果name2为null,"?"后面不会执行,直接返回name2.
        //name2不为null时才能执行到这里,it表示非空的name2.
        if (it.isBlank()) {//isBlank()函数用来判断字符串是否没有内容,即""或" ".
            "Default"
        } else {
            "[$it]"
        }
    }
    println(let)

    //(4)先决条件函数(多用在对外提供库时,调用者传的参数不合法则直接抛异常).
    val name4: String? = "WCL"
    checkNotNull(name4)//参数为null则直接抛异常.
    requireNotNull(name4)//参数为null则直接抛异常.
    require(false)//参数为false则直接抛异常.

    //(5)Kotlin的互操作性和可空性.
    //错误案例: 因为Java可能返回null,造成空指针.
    println(MyJavaClass().testGetString().length)
    //正确案例(1):(不推荐,因为使用时很容易忘记用"?."的方式调用)
    val str1 = MyJavaClass().testGetString() //返回值类型"String!",跨平台传递的对象类型会以'!'结尾.
    println(str1?.length) //对象类型是后面带'!'的,调用函数或变量必须要用"?."的方式调用,防止异常崩溃.
    //正确案例(2):(推荐,声明了可空类型后,后面使用就要强制判断是否为空)
    val str2: String? = MyJavaClass().testGetString() //返回值类型"String!".
    println(str2?.length) //此处会强制要求非空判断,否则编译器报错.
}