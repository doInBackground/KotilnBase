/*
(1)"@file:JvmName()"注解.
文件名是"MyKt10_ZhuJie.kt"默认生成Java文件的类名是"MyKt10_ZhuJieKt.class",加上此声明可声明自定义类名.
*/
@file:JvmName("MYKT10")

package com.wcl.ktb.kotlin

// TODO: 注解相关.
/**
 * (1)"@file:JvmName()"注解.
 * (2)"@JvmField"注解.
 * (3)"@JvmStatic"注解.
 * (4)"@JvmOverloads"注解.
 */
class MineObject {
    /*
        (2)"@JvmField"注解.
        默认情况下val成员变量生成的Java代码如下:
        @NotNull
        private final String name = "WCL";

        @NotNull
        public final String getName() {
           return this.name;
        }
        Java端调用的时候,只能调用方法"getName()"去获取"name"变量的值.
        但加上"@JvmField"声明后,val成员变量生成的Java代码如下:
        public final String name = "WCL";
        "name"变量成了"public"属性,Java代码可以直接操作"name"变量.
     */
    @JvmField
    val name = "WCL"

    //伴生对象.
    companion object {
        /*
            (3)"@JvmStatic"注解.
            伴生对象中的变量和方法,Kotlin使用起来类似于Java的静态变量和静态方法,但其实不是,只是隐藏了对象调用而已.
            供Kotlin使用: MineObject.target
            供Java使用时,被隐藏的细节就显示出来了: MineObject.Companion.getTarget();
            如果想让Java端调用的时候也可以使用"MineObject.target",就需要用"@JvmStatic"注解声明.
            伴生对象中定义的方法亦是如此,可以使用"@JvmStatic"注解声明.
        */
        @JvmStatic
        val target = "黄石公园"
    }
}

/*
    (4)"@JvmOverloads"注解.
    含有默认参数的Kotlin方法,Java调用时需要传全参数.
    如果用"@JvmOverloads"声明函数的话,编译器会生成重载函数,Java端使用时也可以省略默认参数调用.
*/
@JvmOverloads
fun show(name: String, sex: Char = '男') {
    println("name:$name sex:$sex")
}