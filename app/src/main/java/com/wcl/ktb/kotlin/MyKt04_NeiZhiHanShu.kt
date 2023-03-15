package com.wcl.ktb.kotlin


// TODO: 内置函数.
/**
 * (1)"apply()":        [this表示本身]                  [返回值是本身]
 * (2)"let()":          [it表示本身]                    [返回值是匿名函数返回值]
 * (3)"run()":          [this表示本身]                  [返回值是匿名函数返回值]
 * (4)"with()":         [this表示with()的首个参数]       [返回值是匿名函数返回值]     (与run()基本一样,只是不能通过"对象."的方式调用)
 * (5)"also()":         [it表示本身]                    [返回值是本身]
 * (6)"takeIf()":       [it表示本身]                    [返回值由匿名函数的Boolean返回值决定(true返回本身,false返回null)]
 * (7)"takeUnless()":   [it表示本身]                    [返回值由匿名函数的Boolean返回值决定(true返回null,false返回本身)]
 */
fun main() {
    //(1)内置函数:"apply()". [this表示本身] [返回值是本身]
    /* public inline fun <T> T.apply(block: T.() -> Unit): T {
            block()
            return this
     }*/
    val str0 = "Derry You Hao"
    val apply: String = str0.apply { //apply()的返回值是调用者本身,可用链式调用.
        //这里有"this"表示调用者本身. //匿名函数无参故这里没有"it"可用使用.
        println("apply()匿名函数内打印:$this")
        println("字符串长度:${length}") //匿名函数内使用"this"可省略,此处"length"等同于"this.length".
    }.apply {
        println("最后一个字符:${this[length - 1]}")
        println("全部转小写:${lowercase()}")
    }

    //(2)内置函数:"let()". [it表示本身] [返回值是匿名函数返回值]
    /* public inline fun <T, R> T.let(block: (T) -> R): R {
            return block(this)
    }*/
    val let: Int = listOf(4, 5, 8, 9, 3, 2, 7).let { //let()的返回值是匿名函数的返回值.
        // 这里有"it"表示调用者本身.
        it.first() + it.first()
    }

    //(3)内置函数:"run()". [this表示本身] [返回值是匿名函数返回值]
    /*public inline fun <T, R> T.run(block: T.() -> R): R {
            return block()
    }*/
    val str1 = "Derry is OK"
    val run: Boolean = str1.run { //run()的返回值是匿名函数的返回值.
        //这里有"this"表示调用者本身.
        isLong(this)
    }
    //[具名]方式调用run(): 允许传单参函数的实例进去,供匿名函数中的"this"以参数形式传入,得到返回值作为匿名函数返回值,即run()的返回值.
    val result: Unit = str1.run(::isLong) //this==str1
            .run(::showText) //this==isLong()返回的Boolean值.
            .run(::mapText) //this==showText()返回的String值.
            .run(::println) //this==mapText()返回的String值. //传入单参系统函数. 打印结果:"[字符串合格]"
    //同理:"apply()","let()","run()"都可以以具名方式调用.

    //(4)内置函数:"with()". [this表示with()的首个参数] [返回值是匿名函数返回值] (与run()基本一样,只是不能通过"对象."的方式调用)
    /*public inline fun <T, R> with(receiver: T, block: T.() -> R): R {
            return receiver.block()
    }*/
    val str2 = "李元霸"
    val with1: Boolean = with(str2) {//with()的返回值是匿名函数的返回值.
        //这里有"this"表示with()的首个参数.
        isLong(this)
    }
    //[具名]方式调用with():
    val with2: Boolean = with(str2, ::isLong)

    //(5)内置函数:"also()". [it表示本身] [返回值是本身]
    /*public inline fun <T> T.also(block: (T) -> Unit): T {
        block(this)
        return this
    }*/
    val str3 = "WCL"
    val also1: String = str3.also {//also()的返回值是调用者本身,可用链式调用.
        // 这里有"it"表示调用者本身.
        println("原始数据:$it")
    }
    //[具名]方式调用also():
    val also2: String = str3.also(::isLong)

    //(6)内置函数:"takeIf()". [it表示本身] [返回值由匿名函数的Boolean返回值决定(true返回本身,false返回null)]
    /*public inline fun <T> T.takeIf(predicate: (T) -> Boolean): T? {
            return if (predicate(this)) this else null
    }*/
    val str4 = "HelloWorld"
    val result2: String? = str4.takeIf {//takeIf()的返回值是调用者本身或者null,由匿名函数的Boolean返回值决定,true返回本身,false返回null.
        // 这里有"it"表示调用者本身.
        it.length > 5
    }
    val result3: String = result2 ?: "字符串为空"//takeIf()常配合空合并操作符"?:"一起使用.

    //(7)内置函数:"takeUnless()". [it表示本身] [返回值由匿名函数的Boolean返回值决定(true返回null,false返回本身)]
    /*public inline fun <T> T.takeUnless(predicate: (T) -> Boolean): T? {
            return if (!predicate(this)) this else null
    }*/
    val str5: String? = null
    val result4: String? = str5.takeUnless {//takeIf()的返回值是调用者本身或者null,由匿名函数的Boolean返回值决定,true返回null,false返回本身.
        // 这里有"it"表示调用者本身.
        it.isNullOrBlank()//blank指trim()后为""; empty指"";
    }
    val result5: String = result4 ?: "字符串为空"//takeUnless()常配合空合并操作符"?:"一起使用.
}

fun isLong(str: String) = str.length > 5 //简化函数,返回值类型自动推算故可省略.
fun showText(isLong: Boolean) = if (isLong) "字符串合格" else "字符串不合格"
fun mapText(getShow: String) = "[$getShow]"