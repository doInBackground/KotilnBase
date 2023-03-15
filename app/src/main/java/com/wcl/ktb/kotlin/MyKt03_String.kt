package com.wcl.ktb.kotlin

// TODO: 字符串相关.
/**
 *  (1)字符串截取.
 *  (2)字符串分割.
 *  (3)字符串替换.
 *  (4)字符串遍历.
 */
fun main() {
    //(1)字符串截取.
    val msg = "ABC is a good man"
    val index = msg.indexOf('i')
    println(msg.substring(0, index))
    println(msg.substring(0 until index))//Kotlin基本用此方式.
    //(2)字符串分割.
    val list = msg.split(" ")
    println("分割后的list里面的元素有:$list")
    val (v1, v2, v3, v4, v5) = list//类似于C++解构,Kotlin也有.
    println("解构后的只读变量值: $v1 $v2 $v3 $v4 $v5")
    //(3)字符串替换.
    val sourcePwd = "JOIHAENZGKLAHEIOHFCANEKBGJAIEGIUAB"
    val newPwd = sourcePwd.replace(Regex("[ABC]")) {//传入正则.
        when (it.value) {//"it.value"代表每个匹配的值.
            "A" -> "1"
            "B" -> "2"
            "C" -> "3"
            else -> it.value
        }
    }
    println("替换后的字符串:$newPwd")//已经将其中的"A"/"B"/"C"替换成了"1"/"2"/"3".
    //(4)字符串遍历.
    sourcePwd.forEach {//方法原型:forEach(action: (Char) -> Unit): Unit
        println("单个字符:$it")//it表示字符串中的每一个字符.
    }
    //(5)内容空判断.
    //isEmpty() = (str == null || str.length == 0)
    //isBlank() = (str == null || str.length == 0 || str.trim().length == 0) //更严格.
    val str = " "
    println("isEmpty:${str.isEmpty()}  isBlank:${str.isBlank()}") //结果:false true.
}