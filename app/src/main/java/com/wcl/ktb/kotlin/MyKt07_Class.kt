package com.wcl.ktb.kotlin

// TODO: 类相关.
/**
 * (1)定义类("class")与"field"关键字.
 * (2)主构造函数和次构造函数.
 * (3)构造函数中默认参数.
 * (4)延迟初始化("lateinit").
 * (5)惰性初始化("by lazy").
 * (6)初始化陷阱.
 */
fun main() {
    //(1)定义类("class")与"field"关键字.
    println(KtBase07().name) //背后隐式调用getName().
    KtBase07().name = "Kevin" //背后隐式调用setName().
    println(KtBase07().name) //背后隐式调用getName().
    println(KtBase07().info) //背后隐式调用getInfo().
    KtBase07().info = "Kevin" //背后隐式调用setInfo().
    println(KtBase07().info) //背后隐式调用getInfo().
    println(KtBase07().number) //背后隐式调用getNumber().
    println(KtBase07().number2) //背后隐式调用getNumber2().
    println(KtBase07().msg) //背后隐式调用getMsg().

    //(2)主构造函数和次构造函数.
    val ktbase07_2 = KtBase07_2("张三", '男', 28)
    ktbase07_2.show()

    //(3)构造函数中默认参数.
    KtBase07_3()//构造函数含默认参数:以匹配度高的构造函数优先,匹配度相同时编译器会懵逼报错.

    //(4)延迟初始化("lateinit").
    val ktbase07_4 = KtBase07_4() //实例化对象,此时对象中"lateinit"类型的成员变量还未初始化.
    ktbase07_4.requestResult("初始化")//手动初始化赋值"lateinit"类型变量.
    ktbase07_4.showResult()//展示"lateinit"类型变量.

    //(5)惰性初始化("by lazy").
    val ktbase07_5 = KtBase07_5() //实例化类时,未用"by lazy"声明的成员变量已经初始化赋值了.
//    Thread.sleep(5000) //线程休息5s,方便观察区别.
    println("使用成员变量: ${ktbase07_5.value1} ${ktbase07_5.value2}") //"by lazy"声明的成员变量"value2"此时才被首次使用,才会初始化赋值.

    //(6)初始化陷阱.
    val length: Int = KtBase07_6("Device").content.length //报错:初始化先后顺序错乱,导致"content"为空,调用其方法会空指针异常.
}

// (1)定义类("class")与"field"关键字.
class KtBase07 {
    /* var类型变量,默认情况: */
    var name = "Derry" //var变量,下面的get(),set()不写的话默认就有,是隐式代码.
        get() = field //Java隐式代码: public final String getName() {}
        set(value) { //Java隐式代码: public final void setName(@NotNull String value) {}
            field = value
        }

    /* var类型变量,重写情况: */
    var info = "wcl is success"
        get() = field.capitalize() //get()时把首字母修改成大写.
        set(value) {
            field = "[$value]" //set()时加包装.
        }

    /* val类型变量,默认情况: */
    val number: Int = 0 //val变量,下面的get()不写的话默认就有,是隐式代码.
        get() = field //val是只读的,只能有get(),不能有set().

    /* val类型变量,重写情况: */
    val number2: Int
        get() = (1..1000).shuffled().first() //从1-1000取随机数返回. 这里没有用到field,Java中甚至不会定义number2属性,只会有个getNumber2()的方法.

    var msg: String? = null
}

/*
    (2)主构造函数和次构造函数.
    主构造函数: 类名后直接跟"()"可以重写主构造.
    主构造函数中,不以"var/val"开头的为临时变量,init{}初始化块中和成员变量初始化时可以使用,但其他地方不能直接使用,需要接收成为成员变量才能使用(临时变量参数命名规范以"_"开头).
    次构造函数: 用"constructor"来定义次构造函数.次构造函数参数不能以"var/val"开头.
*/
class KtBase07_2(_name: String, val sex: Char, var age: Int) {

    val wcl = "WCL"
    var name = _name //成员变量初始化时,将临时变量为其赋值.
        get() = field //成员变量是公开的,其get()不允许私有化.
        private set(value) {
            field = value
        }

    //init{}不是Java的static{},相当于Java的{}构造代码块.实例化时才会执行.
    //init{}是初始化代码块,可以理解为主构造的函数体.
    //变成Java代码后,init{}中的代码与类成员变量初始化一样,会被插入主构造函数体中,先后顺序取决于在类中的先后位置关系
    //(比如此类实例化时,主构造中以"var/val"开头的类成员变量的初始化要最优先,再执行[val wcl = "WCL"]和[var name = _name],再执行init{}中的代码).
    init {
        println("主构造函数被调用了. $_name $sex $age")
    }

    //外部调用次构造时的执行流程:次构造会先调用主构造,再执行自己函数体内的代码.
    constructor(name: String) : this(name, '无', 0) {//次构造函数最后必须要用"this()"显式调用主构造,是为了更好的初始化设计,方便主构造统一管理.
        println("次构造函数被调用了. $name $sex $age")
    }

    fun show() {
//        println("姓名:$_name") //报错,临时变量无法直接使用,需要先接收成为变量.
        println("姓名:$name 性别:$sex 年龄:$age") //正确打印.
    }
}

// (3)构造函数中默认参数.
class KtBase07_3(_name: String = "张三") {
    //    constructor(_age: Int = 28) : this("李四") {} //此种情况时,空参实例化(即KtBase07_3())时会报错,编译器不知道该用此次构造还是主构造.
    constructor(_name: String = "张三", _sex: Char = '男', _age: Int = 28) : this(_name) {}
    constructor(_name: String = "张三", _sex: Char = '男', _info: String = "三好学生") : this(_name) {}
}

// (4)延迟初始化("lateinit").
class KtBase07_4 {
    //    var value: String //报错:编译器要求此处value必须初始化赋值.除非(1)添加"lateinit"关键字;(2)设为"abstract"变量;(3)将其移入构造函数;(4)实现其get();
    lateinit var value: String //"lateinit"表示延迟初始化,不能用在"val"变量上.

    fun requestResult(v: String) {
        value = v //为"lateinit"变量初始化赋值.
    }

    fun showResult() {
        //错误写法:
//        if (value == null) println() //如未初始化赋值该"lateinit"变量时,执行此行即崩溃.
//        println("value:$value") //如未初始化赋值该"lateinit"变量时,执行此行即崩溃.
        //正确写法: 检查是否已经初始化,然后再使用.
        if (::value.isInitialized) { //判断是否已经初始化.
            println("value初始化完成,值为:$value")
        } else {
            println("value未初始化")
        }
    }
}

// (5)惰性初始化("by lazy").
class KtBase07_5 {
    val value1 = getString() //普通方式(饿汉式):实例化类时立即为成员变量赋值getString()立即被调用.
    val value2 by lazy { getString() } //惰性初始化(懒汉式):实例化类时不为成员变量赋值getString()不会被调用,当该成员变量被使用时才初始化赋值getString()才会被调用.
    private fun getString() {
        println("will getString")
        "Data has get"
    }
}

// (6)初始化陷阱.
// 成员变量的赋值是按代码顺序来的,给"content"赋值时"info"还未初始化故"content"被赋值为了null,调用"content"的方法就会空指针异常.
// 解决办法:把"第2行"提到"第1行"前面.
class KtBase07_6(_info: String) {
    val content: String = getInfoMethod() //第1行.
    private val info = _info //第2行.
    private fun getInfoMethod() = info //第3行.
}