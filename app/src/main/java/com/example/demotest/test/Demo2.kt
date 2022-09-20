package com.example.demotest.test

import java.util.*

/**
 * @name Demo2
 * @package com.example.demotest.test
 * @author 345 QQ:1831712732
 * @time 2022/07/03 14:24
 * @description
 */
class MinStack {

    var stack = Stack<Int>()
    var stackSqrt = Stack<Int>()



    fun push(value: Int) {
        stack.push(value)
        if(stackSqrt.lastOrNull() == null){
            stackSqrt.push(value)
        }else{
            stack.push(kotlin.math.min(value,min()))
        }
    }

    fun pop() {
        stack.pop()
        stackSqrt.pop()
    }

    fun top(): Int? {
     return  stack.last()
    }

    fun min(): Int {
        return stackSqrt.last()
    }
}


fun main() {
    val minStack = MinStack()
    minStack.push(-2)
    minStack.push(0)
    minStack.push(-3)
    println(minStack.min())
    minStack.pop()
    println(minStack.top())
    println(minStack.min())
}
