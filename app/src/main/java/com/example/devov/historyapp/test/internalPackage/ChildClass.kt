package com.example.devov.historyapp.test.internalPackage

/**
 * Created by devov on 2017/6/23.
 */
open class ChildClass() :InternalClass(){


    final override  fun func2() {
        super.func2()
    }
      fun func1(i:Int){}
}

interface MyInterface {
    val property: Int // abstract

    val propertyWithImplementation: String
        get() = "foo"

    fun foo() {
        print(property)
    }
}

class Child : MyInterface {
    override val property: Int = 29
}