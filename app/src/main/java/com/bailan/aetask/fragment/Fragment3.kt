package com.bailan.aetask.fragment

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import kotlinx.android.synthetic.main.fragment_three.*
import kotlinx.coroutines.delay

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景3: 先在中间展示一个福字,然后倒过来,然后开始顺时针旋转
 */
class Fragment3 : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_three

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
            delay(200)
            ivCenter.animate().setDuration(100).rotationBy(180f).start()
            delay(600)
            val animator = ObjectAnimator.ofFloat(ivCenter, "rotation", 0f, 180f, 360f)
            animator.duration = 300
            animator.interpolator = LinearInterpolator()
            animator.repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.RESTART
            animator.start()
            delay(1000)
            animator.cancel()
            animateComplete()
        }
    }
}