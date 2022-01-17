package com.bailan.aetask.fragment

import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import com.bailan.aetask.util.createRotationAnimator
import kotlinx.android.synthetic.main.fragment_four.*
import kotlinx.coroutines.delay

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景4: 左右2个福字,分别旋转,开始时间不同
 */
class Fragment4 : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_four

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
            val leftAnimator = ivLeft.createRotationAnimator()
            val rightAnimator = ivRight.createRotationAnimator()
            leftAnimator.start()
            delay(200)
            rightAnimator.start()
            delay(1000)
            leftAnimator.cancel()
            rightAnimator.cancel()
            animateComplete()
        }
    }
}