package com.wcl.ktb.kotlin

import com.wcl.ktb.kotlin.sum as and //重命名扩展.

// TODO: [接口] [抽象类] [泛型] [扩展] 相关.
/**
 *  (1)接口.
 *  (2)接口的默认实现.
 *  (3)抽象类.
 *  (4)泛型类.
 *  (5)泛型函数.
 *  (6)泛型限定.
 *  (7)泛型变换实战(仿RxJava).
 *  (8)动态参数(添加"vararg"修饰).
 *  (9)协变(泛型前加"out")和逆变(泛型前加"in")[类似于Java的泛型的上下边界].
 *  (10)泛型前加"reified"使得可以[对象 is 泛型](需要配合"inline"使用).
 *  (11)扩展函数.
 *  (12)泛型扩展函数.
 *  (13)泛型扩展函数实践.
 *  (14)"可空类型(即"?")"的扩展函数.
 *  (15)扩展属性.
 *  (16)中缀表达式(用"infix"关键字声明).
 *  (17)"as"重命名扩展(将引入的已有函数重命名).
 *  (18)"map()"变换函数.
 *  (19)"flatMap()"变换函数.
 *  (20)"filter()"过滤函数.
 *  (21)"zip()"合并函数.
 */
fun main() {
    //(1)接口(通过"interface"关键字声明).
    val mouse = Mouse()
    val keyBoard = KeyBoard()
    println("${mouse.insertUsb()}  ${keyBoard.insertUsb()}")

    //(2)接口的默认实现.
    object : IUsb2 {
        //因为接口默认实现了变量usbDevice,故实现类可不必强制实现它.
        override var usbVersion: String = ""
        override fun insertUsb(): String {
            return "IUsb2 $usbVersion $usbDevice"
        }
    }

    //(3)抽象类:用"abstract"定义(使用同Java基本一样).
    MyMainActivity().show()

    //(4)泛型类.
    KtBase09_1("ABC").show()

    //(5)泛型函数.
    function(123)

    //(6)泛型限定.
//    functionLimit(123) //报错: 因为泛型参数做了限定,只能传指定类型.
    functionLimit("123")

    //(7)泛型变换实战(仿RxJava).
    val map: String? = map(123, true) { //map()方法将[参数1]做一系列操作[参数3]后,得到[参数3]的返回值.
        "[$it]"
    }

    //(8)动态参数(添加"vararg"修饰).
    add(1, 2, 3, 4, 5)

    //(9)协变(泛型类型前添加"out"修饰符)和逆变(泛型类型前添加"in"修饰符).类似于Java的泛型的上下边界.
    /*  Java中 =>
        List<CharSequence> list = new ArrayList<String>(); //报错:父类泛型不能接收子类泛型.
        List<? extends CharSequence> list = new ArrayList<String>(); //正确写法.
        List<String> list = new ArrayList<CharSequence>(); //报错:子类泛型不能接收父类泛型.
        List<? super String> list = new ArrayList<CharSequence>(); //正确写法.
        Kotlin中 =>
        "out"就类似于[Java的上限("? extends T"):能取不能存(即只能get()),因为编译器只知道容器存的是T的子类,但不知道是哪种子类,存的话可能出现类型不匹配].
        "in"就类似于[Java的下限("? super T"):能存但取只能赋值给Object,因为编译器只知道容器存的是T的父类,但不知道是哪种父类,所以只能统一用Object来接收].
     */

    //(10)泛型前加"reified"关键字:可以使[对象]对[泛型]进行类型推断,需要配合"inline"使用.
    val fatherAndSon: Boolean = isFatherAndSon(Any(), "String")

    //(11)扩展函数(此处以拓展源生类"String"为例,该类的实例就可以调用新增的拓展函数).
    println("AAA".addExtAction(3)) //输出:"AAA@@@".
    "BBB".showStr() //字定义输出.

    //(12)泛型扩展函数(可供任意符合泛型类型的对象调用).
    123.show()
    true.show()
    "WCL".show()

    //(13)泛型扩展函数实践.
    val let: String = 123.let { it.toString() } //系统内置函数:"let()".
    val myLet: String = 123.myLet { it.toString() } //调用仿写的系统内置函数.
    val apply: Int = 123.apply { println("打印$this") } //系统内置函数:"apply()".
    val myApply: Int = 123.myApply { println("打印$this") } //调用仿写的系统内置函数.

    //(14)"可空类型(即"?")"的扩展函数.
    val str: String? = null
    str.showOrDefault("空值") //调用"可空类型"的扩展函数.

    //(15)扩展属性.
    val birthday: Int = "AAA".birthday //字定义的'扩展属性'.

    //(16)中缀表达式(用"infix"关键字声明).
    val map1: Map<Int, Int> = mapOf(0 to 10) //系统的"to"中缀表达式: public infix fun <A, B> A.to(that: B): Pair<A, B> = Pair(this, that)
    "ABC" gogogo 123 //自定义中缀表达式"gogogo()",原始写法:"ABC".gogogo(123).

    //(17)"as"重命名扩展(将引入的已有函数重命名).
    and(1, 1) //将"sum()"改名为"and()"使用.

    //(18)"map()"变换函数(作用:遍历旧集合,得到每次遍历的返回值,将返回值加入新集合,并且返回新集合).
    /*public inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
        return mapTo(ArrayList<R>(collectionSizeOrDefault(10)), transform)
    }*/
    val list1: List<String> = listOf("张三", "李四", "王五")
    val list2: List<Int> = list1.map { //把匿名函数返回值加入新的集合,并且返回新集合
        it.length //"it"表示原集合的每一个元素.
    }

    //(19)"flatMap()"变换函数(作用:遍历旧集合,每次遍历创建一个临时集合作为匿名函数的返回值,最后将所有临时集合的值归入一个新集合,并且返回新集合).
    /*public inline fun <T, R> Iterable<T>.flatMap(transform: (T) -> Iterable<R>): List<R> {
        return flatMapTo(ArrayList<R>(), transform)
    }*/
    val list3: List<String> = listOf("张三", "李四四", "王五五五")
    val list4: List<Int> = list3.flatMap {
        listOf(0, it.length) //"it"表示原集合的每一个元素.
    }
    println(list4) //输出:[0,2,0,3,0,4].

    //(20)"filter()"过滤函数(作用:遍历旧集合,根据元素是否符合条件为匿名函数返回Boolean值,返回true的元素将被加入新集合,最后返回新集合).
//    public inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
//        return filterTo(ArrayList<T>(), predicate)
//    }
    val list5: List<String> = listOf("张三", "李四四", "王五五五")
    val list6: List<String> = list5.filter {
        it.length == 3
    }
    println(list4) //输出:[李四四].

    //(21)"zip()"合并函数(作用:以键值对形式合并两个集合).
    /*public infix fun <T, R> Iterable<T>.zip(other: Iterable<R>): List<Pair<T, R>> {
        return zip(other) { t1, t2 -> t1 to t2 }
    }*/
    val list7: List<String> = listOf("张三", "李四", "王五")
    val list8: List<Int> = listOf(1, 2, 3)
    val zip: List<Pair<String, Int>> = list7.zip(list8) //数据长度不一样,按size小的合并.
    zip.forEach {
        println("合并后的集合,key:${it.first} value:${it.second}") //it == Pair<String, Int>
    }

}
// (1)接口(通过"interface"关键字声明).
/** 定义接口[IUsb].
 *  (1)接口里面所有成员和接口本身都是"public open"的,这是接口的特性.
 *  (2)接口不能有构造,主构造次构造都没有.
 *  (3)接口的[变量]和[方法]实现类都要重写.
 *  (4)实现的[变量]和[方法]全部都要用"override"修饰.
 *  本质:
 *  Java的interface中[变量]必须是不可变的静态常量,而Kotlin的interface中[变量]可以是"val/var",故转成Java代码时逻辑肯定时间接实现的.
 *  由Kotlin转成Java代码可见,Kotlin的interface中[变量]本质也是转成了它的"get()/set()"抽象函数.
 *  "val"类型只有抽象的"get()","var"类型有抽象的"get()&set()".
 */
interface IUsb {
    var usbVersion: String
    var usbDevice: String
    fun insertUsb(): String
}

// [鼠标类]实现[IUsb接口].
class Mouse(override var usbVersion: String = "USB 3.0", override var usbDevice: String = "鼠标") : IUsb {
    override fun insertUsb(): String = "Mouse $usbVersion $usbDevice"
}

// [键盘类]实现[IUsb接口].
class KeyBoard : IUsb {
    override var usbVersion: String = "USB 3.1"
    override var usbDevice: String = "键盘"
    override fun insertUsb(): String = "KeyBoard $usbVersion $usbDevice"
}

// (2)接口的默认实现.
interface IUsb2 {
    var usbVersion: String //"var"类型不能有默认实现.

    val usbDevice: String
        get() = (1..100).shuffled().last().toString() //"val"类型可默认实现"get()",子类即可不用强制实现该变量.

    fun insertUsb(): String
}

// 抽象类:用"abstract"定义(使用同Java基本一样).
abstract class BaseActivity {
    fun onCreate() {
        setContentView(getLayoutID())
        init()
    }

    private fun setContentView(layoutID: Int) = println("加载[$layoutID]布局xml文件")
    abstract fun getLayoutID(): Int  //抽象方法也要用"abstract"声明.
    abstract fun init()  //抽象方法也要用"abstract"声明.
}

// (3)抽象类:用"abstract"定义(使用同Java基本一样).
// 实现类:实现抽象类,必须要实现抽象类的所有抽象方法.
class MyMainActivity : BaseActivity() {
    override fun getLayoutID(): Int = 123 //实现抽象方法.
    override fun init() = println("具体的初始化实现..") //实现抽象方法.
    fun show() {
        super.onCreate()
    }
}

// (4)泛型类.
class KtBase09_1<T>(private val obj: T) {
    fun show() = println("万能输出器:$obj")
}

// (5)泛型函数.
fun <T> function(obj: T) {
    println("万能输出器:$obj")
}

// (6)泛型限定.
fun <T : CharSequence> functionLimit(str: T) {
    println("非万能输出器:$str") //因为限定了类型,所以参数str的类型只能是CharSequence及其子类.
}

// (7)泛型变换实战(仿RxJava).
inline fun <I, O> map(input: I, isMap: Boolean = true, action: (I) -> O): O? = if (isMap) action(input) else null

// (8)动态参数(添加"vararg"修饰).
fun add(vararg numberArr: Int): Int {
    val arr: IntArray = numberArr //用"vararg"修饰的Int类型其实就是IntArray数组类型.
    var count = 0
    for (i in arr) {
        count += i
    }
    return count
}

// (10)泛型前加"reified"关键字:可以使[对象]对[泛型]进行类型推断,需要配合"inline"使用.
inline fun <reified T> isFatherAndSon(any1: T, any2: Any) = any2 is T //方法作用:判断参数1与参数2是否父子关系.

// (11)扩展函数(此处以拓展源生类"String"为例,该类的实例就可以调用新增的拓展函数).
/*  [扩展函数]:
    为目标类定义扩展函数,目标类的对象就可以调用该函数.[格式:"fun 目标类.拓展函数()"].
    下面以为源生类"String"拓展函数为例,Java实现原理是在当前定义类中新增如下方法:
    public static final void showStr(@NotNull String $this$showStr) {
      Intrinsics.checkNotNullParameter($this$showStr, "$this$showStr");
      String var1 = '[' + $this$showStr + ']';
      System.out.println(var1);
    }
    注意:[1]我们不能定义两个一样的扩展函数(方法名和参数相同即为一样). [2]Kotlin内置的拓展函数,可以被我覆盖定义且优先使用我们的.
 */
fun String.showStr() = println("[$this]") //为Kotlin的"String"类定义扩展函数.
fun String.addExtAction(number: Int) = this + "@".repeat(number) //为Kotlin的"String"类定义扩展函数.

// (12)泛型扩展函数(可供任意符合泛型类型的对象调用).
fun <T> T.show() = println(if (this is String) "字符串长度是:$length" else "[$this]不是字符串")

// (13)泛型扩展函数,实践:仿写系统内置函数"let()".
private inline fun <I, O> I.myLet(lambda: (I) -> O): O = lambda(this)

// (13)泛型扩展函数,实践:仿写系统内置函数"apply()".
private inline fun <I> I.myApply(lambda: I.() -> Unit): I {
    this.lambda()
    return this
}

// (14)"可空类型(即"?")"的扩展函数:对字符串进行判断,非空打印本身,为空打印默认值.
fun String?.showOrDefault(default: String) = println(this ?: default)

//(15)扩展属性.
/*  [扩展属性]:
    为目标类定义扩展属性,目标类的对象就可以调用该属性.[格式:"val 目标类.拓展属性 : 属性类型 get()"].
    下面以为源生类"String"拓展属性为例,Java实现原理是在当前定义类中新增如下方法:
    public static final int getBirthday(@NotNull String $this$birthday) {
      Intrinsics.checkNotNullParameter($this$birthday, "$this$birthday");
      return 19950203;
   }
 */
val String.birthday: Int
    get() = 19950203

//(16)中缀表达式(用"infix"关键字声明).
/*
    自定义中缀表达式 + 扩展函数.
    [1]是对某类型或泛型的扩展函数. [2]只能传一个参数.
 */
private infix fun <F, L> F.gogogo(l: L): Unit {
    //逻辑代码...
    println("中缀表达式,值1:$this 值2:$l")
}