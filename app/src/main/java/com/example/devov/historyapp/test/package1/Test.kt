package com.example.devov.historyapp.test.package1

import android.util.Log
import com.example.devov.historyapp.kotlin.NewsPOJO
import com.example.devov.historyapp.test.internalPackage.InternalClass
import kotlin.properties.Delegates

/**
 * Created by devov on 2017/6/2.
 */
open class Test(  i1:Int,var i2:Int) : SuperTest(), AA, BB {
    companion object{
        @JvmField
        public var num=5
        @JvmStatic
        fun returnStr()=NewsPOJO(null,null).result?.stat;
        var a:Char='a'

    }
    var int= arrayOf(0,0,0,"sss")
    var o=Object()
    var any=Any()
     var random=10
    set(value){
        Log.i("aaa","input value $value")
        field=value+55
    }
    var string:String? = null
    inner class innerClass{
        val o=this@Test.o
    }
    override fun fun2(): String {
        super.internalField
        Log.i("aaa","fun2");
        return 0.toString();
    }
    fun fun3(){
        num++
        Log.i("aaa","fun3");
        Test.fun4()

    }
    fun closureFunc():()->Unit{
        var count:Int=0
        return fun(){
            count++
            Log.i("aaa","closure function:"+count)
        }
    }
    var func=fun(x1:Int,x2:Int)=(x1>x2) isTrue x1 or x2

    fun func()=5;
    val dd={ii:Int,oo:String->
        (ii+1).toString()+oo
    }

     constructor(  str1:String,str2:String,i:Int) : this(i,i) {
            Log.i("aaa","3 params");
    }
    constructor(str1:String,str2:String,str3:String,i:Int):this(str1,str2,i){
        Log.i("aaa","4 params")
    }
    init {
        var x:Int=0;
        Log.i("aaa","init  $i1")
        when (x) {
            in 1..10 -> print("x is in the range")
            !in 10..20 -> print("x is outside the range")

//            is View ->print("x is outside the range")
            else -> print("none of the above") }

    }

    override var str="";

     var b:Char='a'
        get()=field+1
        set(value){
            field=value;
        }
    var c by Delegates.vetoable('A'){
        d,old,new->
        new >='G'
    }
    fun fun5(  i: Test1){
        Test1()
        for (a in 0..100 step 5){}
//        var r=i+1
        str=inner?.inner2?.inner4.toString()
        Log.i("aaa","fun5: $str")
        "aa" to "Aa"
        var internalClass=InternalClass();
        internalClass.str
    }

    override fun fun1(): Int {
        return super.fun1()
    }
}
interface  AA{
    var str:String;
    fun fun1():Int=1;
}
interface BB{
    fun fun2(): String{
        var a=10;
        var aa=object : AA {
            override var str: String="AAa"
        }
        return (a+aa.fun1()).toString()+ Test(0, 0).internalField
    }
}
open class SuperTest{
    private var privateField="private_field"
    internal var internalField="internal_field"
    protected var inner: InnerClass?=null
     fun SuperTest(){}
      class InnerClass{
        var inner2: Inner2Class? = null
        var inner3: Inner3Class?=null
    }
    class Inner2Class{
        var inner4: inner4Class?=null
        fun f(){
        }
    }
    class Inner3Class
    class inner4Class{var str:String?="Hello"}
}
class Condition<T>(val isTrue:Boolean,val value:T)
infix fun<T> Condition<T>.or(otherValue:T)=if(isTrue)value else otherValue
infix fun<T> Boolean.isTrue(value:T)= Condition(this, value)