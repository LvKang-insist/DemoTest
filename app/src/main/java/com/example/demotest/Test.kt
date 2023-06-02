package com.example.demotest

/**
 * @author 345 QQ:1831712732
 * @name Test
 * @package com.example.demotest
 * @time 2022/03/02 18:45
 * @description
 */

sealed class Test(val s: String)


sealed class T1(val b: String) : Test(b)