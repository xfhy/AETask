package com.bailan.aetask.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import com.bailan.aetask.util.log
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景1: 3个福,先展示一个在中间,再依次展示右边和左边. 左右两边的去掉,中间的福放大
 */
class Fragment1 : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_one

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
            delay(400)
            iv3.visibility = View.VISIBLE
            delay(400)
            iv1.visibility = View.VISIBLE
            delay(300)
            iv3.visibility = View.INVISIBLE
            iv1.visibility = View.INVISIBLE
            delay(300)
            iv2.animate().scaleX(3f).scaleY(3f).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    log("场景1 完成动画")
                    animateComplete()
                }
            }).setDuration(1500).start()
        }
    }
}