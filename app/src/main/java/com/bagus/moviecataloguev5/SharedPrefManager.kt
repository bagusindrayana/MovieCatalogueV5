package com.bagus.moviecataloguev5

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(var context: Context) {

    var sp: SharedPreferences? = null
    var spEditor: SharedPreferences.Editor? = null

    init {
        sp = context.getSharedPreferences("MCV5", Context.MODE_PRIVATE)
        spEditor = sp!!.edit()
    }



    fun saveSPBoolean(keySP: String?, value: Boolean) {
        spEditor!!.putBoolean(keySP, value)
        spEditor!!.commit()
    }

    fun getSPBoolean(keySP: String?) : Boolean {
        return sp!!.getBoolean(keySP, false)
    }


}