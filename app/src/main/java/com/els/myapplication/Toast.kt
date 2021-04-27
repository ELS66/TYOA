package com.els.myapplication

import android.widget.Toast

fun String.showToast(duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.context,this,duration).show()
}
fun Int.showToast(duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.context,this,duration).show()
}