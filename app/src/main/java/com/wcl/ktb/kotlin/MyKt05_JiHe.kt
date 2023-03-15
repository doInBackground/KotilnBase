package com.wcl.ktb.kotlin

// TODO: 集合相关.
/**
 *  (1-1)List创建与元素获取.
 *  (1-2)可变List的增删,及其与不可变List相互转换.
 *  (1-3) "+=" "-=" (mutator的特性,也属于运算符重载).
 *  (1-4)List的遍历.
 *  (1-5)List配合解构语法,及过滤元素.
 *  (2-1)Set创建与元素获取.
 *  (2-2)可变Set集合.
 *  (2-3)List去重:转Set集合或用快捷函数"distinct()".
 *  (3-1)Map的创建.
 *  (3-2)Map值的读取.
 *  (3-3)Map的遍历.
 *  (3-4)可变Map.
 */
//@RequiresApi(Build.VERSION_CODES.N) //removeIf()需求.
fun main() {
    //(1-1)List创建与元素获取.
    val list: List<String> = listOf<String>("张三", "李四", "王麻子") //类型可自动推断,故listOf后的"<>"可省略.
    //普通取值方式: 内部会运算符重载,"[]"=="get()"
    println("${list[0]} ${list[1]} ${list[2]}") //可能会产生索引越界的崩溃.
    //Kotlin方式:
    //public inline fun <T> List<T>.getOrElse(index: Int, defaultValue: (Int) -> T): T {}
    println(list.getOrElse(999) { "$it 越界" }) //索引越界时,会取匿名函数的返回值作为getOrElse()的返回值.
    //public fun <T> List<T>.getOrNull(index: Int): T? {}
    println(list.getOrNull(999) ?: "越界") //索引越界时,会返回null. 再配合空合并操作符"?:"可更好处理.

    //(1-2)可变List的增删,及其与不可变List相互转换.
    val list2: MutableList<Int> = mutableListOf<Int>(1, 2, 3, 4, 5) //可变List才可以进行增删操作.
    list2.add(6) //添加元素.
    list2.remove(5) //删除指定元素.
    list2.removeAt(0) //删除指定索引处的元素.
//    list2.removeIf { //会遍历集合,"it"表示每一个元素. @RequiresApi(Build.VERSION_CODES.N)
//        it == 6 //该返回值为true则删除该元素.
//    }
    val list3: List<Int> = list2.toList() //可变集合->不可变集合.
    val list4: MutableList<Int> = list3.toMutableList() //不可变集合->可变集合.

    //(1-3) "+=" "-=" (mutator的特性,也属于运算符重载).
    val list5 = mutableListOf(1, 2, 3, 4, 5)
    list5 += 6 //public inline operator fun <T> MutableCollection<in T>.plusAssign(element: T) {//内部调用add()}
    list5 -= 5 //public inline operator fun <T> MutableCollection<in T>.minusAssign(element: T) {//内部调remove()}

    //(1-4)List的遍历.
    val list6 = listOf(1, 2, 3, 4, 5)
    //方式一:
    for (item in list6) {
        print("元素:$item ")
    }
    //方式二:
    list6.forEach { print("元素:$it ") }
    //方式三:
    list6.forEachIndexed { index, item -> print("下标:$index,元素:$item ") }

    //(1-5)List配合解构语法,及过滤元素.
    val list7 = listOf(1, 2, 3)
    val (v1, v2, v3) = list7 //解构. v1/v2/v3不可变.
    var (a1, a2, a3) = list7 //解构. a1/a2/a3可变.
    val (_, b2, b3) = list7 //过滤解构,内部不会创建变量接收第一个值,可用节约一点性能.

    //(2-1)Set创建与元素获取.
    val set: Set<String> = setOf<String>("张三", "李四", "王五", "赵六") //类型可自动推断,故setOf后的"<>"可省略.
    //普通取值方式:
    println("${set.elementAt(0)} ${set.elementAt(1)} ${set.elementAt(2)} ") //可能会产生索引越界的崩溃.
    //Kotlin方式:
    //public fun <T> Iterable<T>.elementAtOrElse(index: Int, defaultValue: (Int) -> T): T {}
    println(set.elementAtOrElse(999) { "$it 越界" }) //索引越界时,会取匿名函数的返回值作为elementAtOrElse()的返回值.
    println(set.elementAtOrNull(999) ?: "越界") //索引越界时,会返回null. 再配合空合并操作符"?:"可更好处理.

    //(2-2)可变Set集合.
    val set2: MutableSet<String> = mutableSetOf<String>("张三", "李四", "王五", "赵六")
    set2 += "赵日天" //运算符重载: public inline operator fun <T> MutableCollection<in T>.plusAssign(element: T) {//内部调用add()}
    set2 -= "赵日天" //运算符重载: public inline operator fun <T> MutableCollection<in T>.minusAssign(element: T) {//内部调remove()}
    set2.add("王诛仙")
    set2.remove("王诛仙")

    //(2-3)List去重:转Set集合或用快捷函数"distinct()".
    val list8: MutableList<String> = mutableListOf<String>("张三", "张三", "李四", "王五", "赵六")
    val set3: Set<String> = list8.toSet() //List转Set去重.
    val list9: List<String> = list8.toSet().toList() //List转Set再转List去重.
    val list10: List<String> = list8.distinct() //快捷函数去重,内部做了:toMutableSet().toList()和上行代码等价.

    //(3-1)Map的创建.
    val map1: Map<String, Double> = mapOf<String, Double>("Derry" to 534.4, "Kevin" to 454.5) //类型可自动推断,故mapOf后的"<>"可省略.
    val map2 = mapOf(Pair("Derry", 534.4), Pair("Kevin", 454.5)) //等价于上面的方式.

    //(3-2)Map值的读取.
    val map3 = mapOf("Derry" to 534.4, "Kevin" to 454.5)
    //方式一: 运算符"[]",等价于get(). (如果找不到,不会崩溃,会返回null)
    println("${map3["Derry"]} ${map3["Kevin"]} ${map3["XXX"]}")//对"[]"运算符进行了重载,实际上是调用: public operator fun get(key: K): V?
    //方式二: getOrDefault().
    val mapValue1: Double = map3.getOrDefault("Derry", 3.14)
    //方式三: getOrElse().
    val mapValue2: Double = map3.getOrElse("Derry") { 3.14 }
    //方式四: getValue(),与Java一样,找不到会崩溃,不推荐使用.
    val mapValue3: Double = map3.getValue("Derry")

    //(3-3)Map的遍历.
    val map4 = mapOf("Derry" to 534.4, "Kevin" to 454.5, "Leo" to 398.5)
    //方式一:
    map4.forEach {
        println("Key:${it.key} Value:${it.value}")//"it"表示每一个元素Map.Entry<K, V>
    }
    //方式二:
    map4.forEach { key, value -> println("Key:$key Value:$value") }
    //方式三:
    map4.forEach { (key, value) -> println("Key:$key Value:$value") }
    //方式四:
    for (item in map4) { //val item: Map.Entry<String, Double>
        println("Key:${item.key} Value:${item.value}")
    }

    //(3-4)可变Map.
    val map5: MutableMap<String, Int> = mutableMapOf<String, Int>("Derry" to 123, "Kevin" to 456, "Leo" to 789)
    //可变操作:
    map5 += "AAA" to 123
    map5 -= "AAA"
    map5["BBB"] = 123
    map5.put("CCC", 123) //put()和"[]"等价.
    //getOrPut():
    // 如果指定key不存在,则添加匿名函数返回值value到map再返回该value.
    // 如果指定key存在,则直接返回map中对应的value,匿名函数返回值value失效.
    val mapValue4: Int = map5.getOrPut("Derry") { 555 }
}