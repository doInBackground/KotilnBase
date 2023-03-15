package com.wcl.ktb.kotlin

import java.io.File

// TODO: 类的使用相关.
/**
 *  (1)继承与重载的"open"关键字.
 *  (2)类型的判断("is")及转换("as").
 *  (3)智能类型转换.
 *  (4)Any超类(相当于Java的Object类).
 *  (5)"object"对象声明:(定义单例类).
 *  (6)"object"对象表达式:(匿名实现类).
 *  (7)伴生对象("companion object")[类似于Java中static成员的使用].
 *  (8)内部类("inner")和嵌套类.
 *  (9)数据类("data").
 *  (10)数据类的"copy()"函数.
 *  (11)解构函数(类内部用"operator"声明以"component"开头的特定函数).
 *  (12)运算符重载(类内部用"operator"声明特定函数).
 *  (13)枚举(用"enum class"声明).
 *  (14)枚举类定义函数.
 *  (15)密封类("sealed class")[特殊的枚举类].
 */
fun main() {

    //(1)继承与重载的"open"关键字.
    val father: Father = Son("张三") //多态.
    father.println() //实际被调用的是子类的重写方法.

    //(2)类型的判断("is")及转换("as").
    val father2: Father = Son("张三")
    println("${father2 is Father} ${father2 is Son} ${father2 is File}") //通过"is"关键字判断是否为某类或其子类的实例化对象.
    (father2 as Son).println() //通过"as"来进行强转.
    (father2 as Father).println()

    //(3)智能类型转换.
    val father3: Father = Son("张三")
    father3.tellByFather() //可以调用.
//    father3.tellBySon() //报错:此时编译器认为是Father类型,只能调用tellByFather(),不能调用tellBySon().
    if (father3 is Son) {
        father3.tellByFather()
        father3.tellBySon() //通过"is"判断类型后的分支,就可以直接使用推断类型的专有方法.
    }
    (father3 as Son).tellBySon() //通过"as"来进行强转.
    father3.tellBySon() //调用成功! 通过"as"强转会智能类型转换,后面的代码视变量为强转后的类型.

    //(4)Any超类(相当于Java的Object类).
    println(MyObject().toString())

    //(5)"object"对象声明:(定义单例类).
    println(Singleton) //[饿汉式单例]背后代码: println(Singleton.INSTANCE) //即使多次输出,其对象地址值也是一样的.
    println(Singleton2.getInstanceAction()) //[懒汉式单例]
    println(Singleton3.instance) //[线程安全的懒汉式单例]

    //(6)"object"对象表达式:(匿名实现类).
    object : KtBase08_1() { //对象类型: <anonymous object : KtBase08_1>
        override fun add(msg: String) {
//            super.add(msg)
            println("我是匿名对象add:$msg")
        }
    }.add("张三") //会调用实现类的add().
    //<1>对Java的接口(如Runnable),用Kotlin[对象表达式]方式:
    object : Runnable {
        override fun run() {
            println("Java Runnable run() 1")
        }
    }.run()
    //<2>对Java的接口(如Runnable),用Java方式:
    Runnable { println("Java Runnable run() 2") }.run() //经测试,Java接口只有一个抽象方法的时候才能用这种方式,多个的时候还是只能用Kotlin的方式.
    //<3>对Kotlin的接口,用Kotlin[对象表达式]方式:
    object : IRunnableKT {
        override fun run() {
            println("Kotlin RunnableKT run() 1")
        }
    }.run()
    //<4>对Kotlin的接口,用Java方式:
//    RunnableKT{} //报错:Kotlin不能用此种方式.

    //(7)伴生对象(companion object):Kotlin中没有static,伴生对象内成员的使用类似于Java中static成员的使用.
    println(KtBase08_2.info) //背后代码: system.out.println(KtBase08_2.Companion.getInfo());//隐式调用公有静态伴生对象(Companion)的公有非静态方法(getInfo()).
    println(KtBase08_2.showInfo()) //背后代码: system.out.println(KtBase08_2.Companion.showInfo());//隐式调用公有静态伴生对象(Companion)的公有非静态方法(showInfo()).

    //(8)内部类("inner")和嵌套类.
    Body("OK").Heart().run() //调用[内部类]方法.
    Body.Kidney().run() //调用[嵌套类]方法.

    //(9)数据类(添加"data"声明).
    //数据类转成Java代码后自动会,生成:[set();get();构造函数;解构函数;copy();],重写:[toString();hasCode();equals();].
    println(BeanCommon("OK", 200, "正确")) //打印地址值.
    println(BeanData("OK", 200, "正确")) //打印数据内容.

    //(10)数据类的"copy()"函数.
    BeanDataForTestCopy().copy(msg = "OK", code = 200, data = "正确") //数据类(data)新生成的"copy()"函数的参数与主构造函数的参数一致,次构造函数的参数不在考虑范围内.

    //(11)解构函数(类内部用"operator"声明以"component"开头的特定函数). 格式:[operator fun component1() = par1].
    val (msg1, code1, data1) = BeanCommonForTestComponent("OK", 200, "正确") //普通类的解构函数需要在类内自定义.
    val (msg2, code2, data2) = BeanData("OK", 200, "正确") //数据类(data)的解构函数已默认隐式写出,故可直接解构.
    println("普通类解构后: $msg1 $code1 $data1  数据类解构后: $msg2 $code2 $data2")

    //(12)运算符重载(类内部用"operator"声明特定函数).
    val count: Int = AddClass(1, 1) + AddClass(2, 2) //对象的'+'运算符重载.
    println("两类相加的结果: $count")

    //(13)枚举(用"enum class"声明).
    println(Week.周一)
    println(Week.周一 is Week) //枚举值就是枚举类型.

    //(14)枚举类定义函数.
    println(Limbs.LEFT_HAND) //显示枚举.
    Limbs.LEFT_HAND.show() //枚举调用自身方法.
    Limbs.RIGHT_HAND.set(LimbsInfo("右手2", 99)) //更新枚举值.

    //(15)密封类(用"sealed class"声明): 特殊的枚举类.
    showExams(Exams.Fraction1)
    showExams(Exams.Fraction2)
    showExams(Exams.Fraction3)
    showExams(Exams.Fraction4(99))
    println(Exams.Fraction1 === Exams.Fraction1) //结果:true. 比较地址值,且是同一对象.
    println(Exams.Fraction4(99) === Exams.Fraction4(99)) //结果:false. 比较地址值,是两个不同对象.
}

// (1)继承与重载的"open"关键字.
// Kotlin的class默认是final修饰的,不能被继承.通过"open"关键字声明class可以被继承.
open class Father(private val fatherName: String) {
    open fun println() = println("父类,姓名:$fatherName") //Kotlin的方法默认是final修饰的,不能被重写.通过"open"关键字声明方法可以被重写.
    fun tellByFather() = println("父类方法说:你好")
}

// 子类继承父类.
class Son(private val sonName: String) : Father(sonName) {
    override fun println() { //通过"override"关键字声明是被重写的的方法.
//        super.println() //通过"super"可以调用父类的重写方法.
        println("子类,姓名:$sonName")
    }

    fun tellBySon() = println("子类方法说:你好")
}

/*
    (4)Any超类(相当于Java的Object类).
    在Kotlin中,所有的class都隐式继承了Any类,不写的话默认就有.
    Any类在Kotlin设计中,只提供标准看不到实现,实现在各个平台处理好了.
*/
class MyObject : Any() {}

// (5)"object"对象声明:(定义单例类).
// 对象声明:被"object"标识的类可以理解为单例模式.
// [饿汉式单例]
object Singleton {
    /**
     * 被object标识后,转Java代码后编译器内部做的事情如下(仅列出关键点):
     * public static final Singleton INSTANCE; //单例引用.
     * private Singleton(){} //私有化构造.
     * static{
     *      INSTANCE = new Singleton(); //实例化单例对象.
     *      System.out.println("Singleton init..."); //将init{}内代码移入static{}静态代码块中.
     * }
     */
    init {// 对于init{}在class和object中的处理不同:class中将init{}移入主构造函数中;object中将init{}移入static{}静态代码块中.
        println("Singleton init...")
    }
}

// [懒汉式单例]
class Singleton2 private constructor() {
    companion object { //类的伴生对象,只会初始化一次,其中的方法变量使用类似于Java的静态.
        private var instance: Singleton2? = null
            get() {
                if (field == null) {
                    field = Singleton2()
                }
                return field
            }

        //对外提供公有的获取单例实例的方法.
//        @Synchronized //添加此注解可变为线程安全的懒汉式单例.
        fun getInstanceAction(): Singleton2 = instance!! //用"!!"断言不为空,可以使该方法的返回值由可空类型变为非空类型(即"Singleton2?"转为"Singleton2").
    }
}

// [线程安全的懒汉式单例]
class Singleton3 private constructor() {
    companion object { //类的伴生对象,只会初始化一次,其中的方法变量使用类似于Java的静态.
        val instance: Singleton3 by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Singleton3() } //延迟初始化,使用的时候才会被调用.
    }
}

// (6)"object"对象表达式:(匿名实现类).
open class KtBase08_1 {
    open fun add(msg: String) = println("add:$msg")
}

// Kotlin接口.
interface IRunnableKT {
    fun run()
}

// (7)类的伴生对象(companion object):Kotlin中没有static,伴生对象内成员的使用类似于Java中static成员的使用.
class KtBase08_2 {
    //伴生对象: 即使KtBase08_2()实例化多次,伴生对象只初始化一次.
    companion object {
        val info = "Derry"
        fun showInfo() = println("showInfo:$info")
    }
    /* 伴生对象companion object {}背后的实现逻辑:
       public final class KtBase08_2 {
           @NotNull
           private static final String info = "Derry"; //(!)私有静态成员变量.
           @NotNull
           public static final KtBase08_2.Companion Companion = new KtBase08_2.Companion((DefaultConstructorMarker)null); //(!)公有静态伴生对象.
           @Metadata(...)
           public static final class Companion { //(!)伴生对象的类定义(默认静态内部类).
              @NotNull
              public final String getInfo() {//(!)公有非静态方法,对外提供私有静态成员变量.
                 return KtBase08_2.info;
              }
              public final void showInfo() { //(!)公有非静态方法.
                 String var1 = "showInfo:" + ((KtBase08_2.Companion)this).getInfo();
                 System.out.println(var1);
              }
              private Companion() { //(!)私有化构造.
              }
              // $FF: synthetic method
              public Companion(DefaultConstructorMarker $constructor_marker) {
                 this();
              }
           }
        }*/
}

// (8)内部类("inner")和嵌套类.
// [外部类]
class Body(val bodyInfo: String) {

    fun show() { //[外部类]可以访问[内部类]和[嵌套类].
        Heart().run()
        Kidney().run()
    }

    //[内部类]:内部类可以访问外部类成员.
    //Kotlin中有"inner"的[内部类],转成Java就是[非静态内部类],所以可以访问[外部类]成员.
    inner class Heart {
        fun run() = println("心脏访问身体信息:$bodyInfo")
    }

    //[嵌套类]:嵌套类不能访问外部类成员,除非加上"inner"声明为内部类.
    //Kotlin中无"inner"的[嵌套类],转成Java就是[静态内部类],所以不能访问[外部类]成员.
    class Kidney {
        //        fun run() = println("肾脏访问身体信息:$bodyInfo") //报错: [嵌套类]不能访问[外部类]成员.
        fun run() = println("肾脏访问身体信息:失败")
    }
}

// (9)数据类(添加"data"声明).
/*
    数据类(添加"data"声明) => 转成Java代码后自动会,生成:[set();get();构造函数;解构函数;copy();],重写:[toString();hasCode();equals();].
    使用条件: (1)JavaBean基本可以使用.
             (2)数据类的主构造函数必须至少有一个参数,且参数要用"var/val"修饰.
             (3)数据类不能使用"abstract","open","sealed","inner"等修饰.
             (4)有需要用到[解构函数;copy();toString();hasCode();equals();]这些丰富功能时也可以使用数据类.
 */
data class BeanData(var msg: String, var code: Int, var data: String)

// 普通类 => 转成Java代码后只会自动生成: set(); get(); 构造函数;
class BeanCommon(var msg: String, var code: Int, var data: String)

// (10)数据类的"copy()"函数.
// 含次构造的数据类.
data class BeanDataForTestCopy(var msg: String, var code: Int, var data: String) {
    constructor() : this("NO", 404, "错误") //空参次构造函数.
}

// (11)解构函数(类内部用"operator"声明以"component"开头的特定函数).
// 手写解构函数的普通类.
class BeanCommonForTestComponent(var msg: String, var code: Int, var data: String) {
    //解构函数: 普通类需要手写,格式:[operator fun component1() = par1]. //其中'1'的字符位置要从'1'开始递增1.
    operator fun component1() = msg
    operator fun component2() = code
    operator fun component3() = data
}

// (12)运算符重载(类内部用"operator"声明特定函数). (此处以'+'为例).
class AddClass(var i1: Int, var i2: Int) {

    //运算符重写: "plus"表示'+'运算符; "get"表示"[]"运算符; (其他运算符对应的单词可根据编译器提示查找或自行搜索)
    operator fun plus(addClass: AddClass): Int {
        return (i1 + addClass.i1) + (i2 + addClass.i2)
    }
}

// (13)枚举(用"enum class"声明).
enum class Week { 周一, 周二, 周三, 周四, 周五, 周六, 周日 }

// (14)枚举类定义函数.
// 如果枚举主构造含参,则定义枚举值时,后面括号内必须传参,且类型与数量必须与枚举主构造的参数一致.
enum class Limbs(private var mLimbsInfo: LimbsInfo) {
    LEFT_HAND(LimbsInfo("左手", 88)),
    RIGHT_HAND(LimbsInfo("右手", 88)),
    LEFT_FOOT(LimbsInfo("左脚", 140)),
    RIGHT_FOOT(LimbsInfo("右脚", 140)); //枚举类有拓展定义的函数时以';'结尾.

    fun show() = println("四肢信息:${mLimbsInfo.info} 长度:${mLimbsInfo.length}") //打印枚举信息.
    fun set(limbsInfo: LimbsInfo) { //更新枚举值.
        this.mLimbsInfo.info = limbsInfo.info
        this.mLimbsInfo.length = limbsInfo.length
        println("$this 更新后的数据是: ${this.mLimbsInfo}")
    }
}

// 数据类:四肢类.
data class LimbsInfo(var info: String, var length: Int)

// (15)密封类(用"sealed class"声明): 特殊的枚举类.
sealed class Exams() {
    object Fraction1 : Exams() //分数差 ("object"单例)
    object Fraction2 : Exams() //分数及格 ("object"单例)
    object Fraction3 : Exams() //分数良好 ("object"单例)
    class Fraction4(val grade: Int) : Exams() //分数优秀  ("class"非单例)
}

// 读取密封类对象的内容.
fun showExams(exams: Exams) {
    val msg: String = when (exams) {
        Exams.Fraction1 -> "分数差"
        Exams.Fraction2 -> "分数及格"
        Exams.Fraction3 -> "分数良好"
        is Exams.Fraction4 -> "分数良好,成绩为${exams.grade}"
//        else -> "" //代数数据类型:此处分类写全了故不用写"else",因为系统能推断出"枚举"/"密封类"的所有分类情况都列出了,没有其他情况了.
    }
    println(msg)
}
