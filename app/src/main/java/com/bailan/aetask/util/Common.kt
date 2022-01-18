package com.bailan.aetask.util

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ScreenUtils
import kotlinx.coroutines.CancellableContinuation
import kotlin.random.Random


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

@SuppressLint("Recycle")
fun View.createRotationAnimator(myDuration: Long = 300L) = ObjectAnimator.ofFloat(this, "rotation", 0f, 180f, 360f).apply {
    duration = myDuration
    interpolator = LinearInterpolator()
    repeatCount = ValueAnimator.INFINITE
    repeatMode = ValueAnimator.RESTART
}

fun createOneRandomNumber(rangeMax: Int) = Random.nextInt(rangeMax)

val screenHeight by lazy {
    ScreenUtils.getScreenHeight()
}

val screenWidth by lazy {
    ScreenUtils.getScreenWidth()
}

//只在active时才resume,避免重复resume问题
fun <T> CancellableContinuation<T>.activeResume(value: T) {
    if (isActive) {
        resumeWith(Result.success(value))
    }
}
