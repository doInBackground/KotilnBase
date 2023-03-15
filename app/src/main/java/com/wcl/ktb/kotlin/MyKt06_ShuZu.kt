package com.wcl.ktb.kotlin

import java.io.File

// TODO: 数组相关.
/**
 * Kotlin中的各种数组类型,虽然是引用类型,但背后可以编译成Java基本数据类型.
 * IntArray         intArrayOf()
 * DoubleArray      doubleArrayOf()
 * LongArray        longArrayOf()
 * ShortArray       shortArrayOf()
 * ByteArray        byteArrayOf()
 * FloatArray       floatArrayOf()
 * BooleanArray     booleanArrayOf()
 * Array(对象数组)   arrayOf()
 */
fun main() {
    //(1)基本类型数组的创建和取值.
    val intArr: IntArray = intArrayOf(1, 2, 3, 4, 5)
    //取值,方式一:
    println("${intArr[0]} ${intArr[1]} ${intArr[2]} ${intArr[3]} ${intArr[4]}") //可能会索引越界异常.
    //取值,方式二:
    val item: Int = intArr.elementAt(0) //可能会索引越界异常.
    //取值,方式三:
    val item1: Int = intArr.elementAtOrElse(999) {//如果索引越界会返回一个默认值.
        println("越界:$it") //"it"表示越界索引.
        -1 //索引越界,返回默认值.
    }
    //取值,方式四:
    val value: Any = intArr.elementAtOrNull(999) ?: "越界"

    //(2)对象类型数组的创建.
    val objArr: Array<File> = arrayOf<File>(File("AAA"), File("BBB"), File("CCC"))//根据类型推断arrayOf后的"<>"可省略.
}