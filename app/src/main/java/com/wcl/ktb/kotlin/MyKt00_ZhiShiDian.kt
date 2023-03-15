package com.wcl.ktb.kotlin //可省略.

import kotlin.math.roundToInt

val mMyTestValue1 = ""
var mMyTestValue2 = 0

// TODO: 简单知识点.
/**
 *  Kotlin代码转Java代码查看: "Tools" -> "Kotlin" -> "Show Kotlin Bytecode" -> "Decompile".
 */
fun main() {
    //(1)"=="和"==="的区别.
    val name1 = "Derry"
    val name2 = "Derry"//String比较特殊,字符串常量池中如果已存在该值,则两个引用指向同一个对象.
    println("${name1.equals(name2)} 等价于 ${name1 == name2}")//java中的"equals()"等价于kotlin中的"==",都属于值内容的比较.
    println("${name1 == name2}")//kotlin中的"===",属于引用的比较.
    //(2)类型转换.
    val number: Int = "666".toInt()
    val number2: Int = "666.6".toInt()//报错:类型转换异常.
    val number3: Int? = "666.6".toIntOrNull()//数字类型的安全转换:解决转换异常崩溃问题,转换失败返回null.
    println("$number $number2 ${number3 ?: "值为null"}")
    println(65.898788.toInt())//结果:65.
    println(65.898788.roundToInt())//结果:66.(roundToInt():是Double的四舍五入方法)
    val format: String = "%.3f".format(65.898788)//结果:"65.898".(保留小数点后几位的方法)


}

class MyTestClass { //MyTestClass类与MyKt00_ZhiShiDianKt类同级.
    class MyTestClass2 {} //MyTestClass2类为MyTestClass的静态内部类.
    inner class MyTestClass3 {} //MyTestClass3类为MyTestClass的非静态内部类.
}

fun myTestFunction() {}