package com.eziong.lottery

import android.util.Log

object MoneyCalculate {

    fun MoneyFormat(money:String):String{
        var m = money
        var ret = ""
        while(m.length>0){
            if(m.length>3) {
                ret = "," + m.substring(m.length - 3) + ret
                m = m.substring(0,m.length-3)
            }
            else {
                ret = m + ret
                m = ""
            }
        }
        return ret
    }
}