package com.bailan.aetask.util

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream


fun Fragment.toast(str: String) {
    Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Any?.logString(tag: String = "xfhy_tag") {
    Log.d(tag, this.toString())
}

fun log(tag: String = "xfhy_tag", msg: String?) {
    Log.d(tag, msg ?: "null")
}

fun log(msg: String?) {
    Log.d("xfhy_tag", msg ?: "null")
}
