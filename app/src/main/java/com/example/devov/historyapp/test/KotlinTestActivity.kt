package com.example.devov.historyapp.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.devov.historyapp.Model.HistoryData
import com.example.devov.historyapp.R
import com.example.devov.historyapp.test.internalPackage.InternalClass
import kotlinx.android.synthetic.main.activity_t3.*

/**
 * Created by devov on 2017/6/2.
 */
class KotlinTestActivity : AppCompatActivity() {

    var name: String ="name"

        get() = field.toUpperCase()

        set(value) {

            field = value

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t3);
        val test=Test(0,0)
        test.random=5
        Log.i("aaa","Test.random is ${test.random}")
//        Log.i("aaa","*****************");
//        Log.i("aaa","=================${fun1(Test(0,0))}");
//        Log.i("aaa","/////////////////");
//        var t1=Test(0,0);
//        var t2=Test(0,0);
//        var t3=t2;
//        var t4=Test(0,1)
//        Log.i("aaa","t1==t2  ${t1==t2}")
//        Log.i("aaa","t1===t2  ${t1===t2}")
//        Log.i("aaa","t2==t3  ${t2==t3}")
//        Log.i("aaa","t2===t3  ${t2===t3}")
//        Log.i("aaa","t1==t4  ${t1==t4}")


//        var t1=Test("1","1",1)
//        t1.a='S'
//        t1.b='S'
//        Log.i("aaa","a:${t1.a}   b:${t1.b}")
//        t1.c='b'
//        Log.i("aaa","c:${t1.c}")
//        t1.c='J'
//        Log.i("aaa","c:${t1.c}")
//        t1.c='j'
//        Log.i("aaa","c:${t1.c}")
//        t1.c='B'
//        Log.i("aaa","c:${t1.c}")
//        var t2=Test(5,10);
//        var t3=Test(0,0);
//        var t4=t2;
//        Log.i("aaa","t2==t3:${t2==t3}     t2===t3:${t2===t3}")
//        Log.i("aaa","t2==t4:${t2==t4}     t2===t4:${t2===t4}")
//        when{
//            t2.i1 in 1..10->Log.i("aaa","when 1")
//            t2.i1 in 1..3->Log.i("aaa","when 2")
//            t2.i2 in 5..20->Log.i("aaa","when 3")
//        }
        Test.num=10;
//        t.random=5;
        Log.i("aaa","Test.returnStr():  ${Test.returnStr()}");
        var u:String="aaaa";
        u+"aaa"
        (btn1 as TextView).text="aaa"
        btn1.setOnClickListener (fun (v: View){})
        btn1.setOnClickListener {

            if(it is TextView)
                  it.text="hello world"
//              else return
          }
        btn1.setOnClickListener(
                          fun(v: View) {
                              if (v is TextView)
                                  v.text = "hello world"
                              else return
                          }
        )

//        hello2 { i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22 ->
//            println("$i0, $i1, $i2, $i3, $i4, $i5, $i6, $i7, $i8, $i9, $i10, $i11, $i12, $i13, $i14, $i15, $i16, $i17, $i18, $i19, $i20, $i21, $i22,")
//        }
//        Test(0,0).fun5(4)
//        var closureField=Test(0,0).closureFunc()
//        closureField()
//        closureField()
//        closureField()
//        closureField()
//        closureField()
////        //全是true
//        Log.i("aaa","Test is Any:${t1 is Any}")
//        Log.i("aaa","Test is Object:${t1 is Object}")
        Log.i("aaa","Java Class is Any:${HistoryData() is Any}")
        Log.i("aaa","Java Class is Any:${HistoryData() is Object}")
        Log.i("aaa","Object is Any:${Object() is Any}")
        Log.i("aaa","Any is Object:${Any() is Object}")
////        val t=InternalClass()
//        t.str="Hello"
//        transmitFunc(t)
//        Log.i("aaa","onCreate: ${t.str}")
//        Log.i("aaa","Extensions:  ${5.add1()}")
        var tClass=TClass();
        Log.i("aaa","hashCode:${(tClass.infos[2] as Any).hashCode()}");
    }
    fun defaultParamsFunc(param0:Int=1,param1:String="SS",param2:Int){}
    fun transmitFunc(t:InternalClass){
        t.str="World"
        Log.i("aaa","transmitFunc: ${t.str}")
    }
    var t=Test("a","a","a",1)
        var u:String="SS".apply {
//            var a=it.fun2();
            Log.i("aaa","let method");
            ""
        }
    var function={i:Int,k:Char->i+1}
    fun fun1(t:Test):String{
//        val str=with(t){
//              returnStr();
//            "Aa"
//            return "aaa";
//        }
//         t.apply {
//            Log.i("aaa","t.apply")
//             return "aaaa"
//        }
//        t.let {
//            return "aaa"
//    }
        t.run {
            return toString()
        }
//        t.run { return "Aaaa" }

//        return str
//        stringTest("Aaa")
    }
    fun fun4():String{
//        fun func(i:Int):String=(i+1).toString()

//        Test1().fun3("Hello", ::func)
          inlineFunc('a'){
            i->
            val char=i.toChar()
              return char.toString()
        }
//        Log.i("aaa","finish")
    }
//会报错   lambda表达式参数最多22个
//    fun hello2(action: (Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int) -> Unit) {
//        action(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22)
//    }
public inline fun<T> inlineFunc(char:Char, action:(Int)->T):T= action(char.toInt())


}

fun Int.add1()=this+1
val <T> List<T>.lastIndex: Int
    get() = size-1

