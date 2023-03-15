package com.wcl.ktb.kotlin

// TODO: 函数相关.
/**
 *  (1)函数调用.
 *  (2)匿名函数(lambda):
 *  (3)参数含lambda的函数调用.
 *  (4)单参lambda,匿名内部参数可用"it"代替.
 */
fun main() {
    //(1)函数调用.
    sum(1, 1) //默认调用.
    sum(b = 1, a = 1) //指定参数调用,可乱序.
    sum(b = 1) //a已有默认值,故可以不传值.
    //(2)匿名函数(lambda):
    val lambda: (String, Int, Double) -> String = { v1: String, v2: Int, v3: Double -> "v1=$v1,v2=$v2,v3=$v3" } //默认会进行类型推断,故lambda后的类型声明": (String, Int, Double) -> String"可省略.
    println(lambda("我", 123, 3.14))
    //(3)参数含lambda的函数调用.
    loginAPI("wcl", "123456", { v1: String, v2: Int -> println("登录结果:$v1 结果码:$v2") }) //可理解调用.
    loginAPI("wcl", "123456") { v1: String, v2: Int -> println("登录结果:$v1 结果码:$v2") } //函数是[单lambda参数]时,可用匿名方式简化调用.
    loginAPI("wcl", "123456", ::callBackMethod) //"::"可以实例化函数引用,变成具名函数(匿名函数的反义).
    loginAPI("wcl", "123456", getLambda())
    //(4)单参lambda,匿名内部参数可用"it"代替.
    test("wcl", "123456") { it.length } //lambda只有一个参数时,内部可用it代替该参数.
}

//(1)方法:计算两个整数的和.
fun sum(a: Int = 1, b: Int): Unit { //返回值": Unit"等价于Java的void,在Kotlin中可以省略.
    println("sum of $a and $b is ${a + b}")
}

//(3)参数含lambda的方法调用:
//此函数有使用lambda作为参数,需要声明成内联(inline).
//如果此函数,不使用内联,在调用端,会生成多个对象来完成lambda的调用(像java接口回调一样,会造成性能损耗).
//如果此函数,使用内联,相当于C++ #define 宏定义 宏替换,会把代码替换到调用处.调用处没有任何函数开辟对象开辟的损耗.
//小结: 如果函数参数有lambda,尽量用inline关键字,这样内部会做优化,减少函数开辟对象开辟的损耗.
inline fun loginAPI(username: String, password: String, callBack: (String, Int) -> Unit) {
    if (username.isEmpty() && password.isBlank())
        if (username == "wcl" && password == "123456") {
            callBack("登录成功", 200)
        } else {
            callBack("登录失败", 404)
        }
}

//方法:
fun callBackMethod(msg: String, code: Int) {
    println("登录结果:$msg 结果码:$code")
}

//方法:返回值是一个lambda函数.
fun getLambda(): (String, Int) -> Unit {
    return { v1: String, v2: Int -> println("登录结果:$v1 结果码:$v2") }
}

//(4)单参lambda参数声明可省略.
inline fun test(username: String, password: String, callBack: (String) -> Int) {
    val code = if ("wcl" == username && password == "123456") {
        callBack("本人")
    } else {
        callBack("非本人")
    }
    println("结果字数:$code")
}