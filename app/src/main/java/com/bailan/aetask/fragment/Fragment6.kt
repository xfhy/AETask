package com.bailan.aetask.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.animation.LinearInterpolator
import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import com.bailan.aetask.util.activeResume
import com.bailan.aetask.util.createRotationAnimator
import com.bailan.aetask.util.screenHeight
import com.bailan.aetask.util.screenWidth
import kotlinx.android.synthetic.main.fragment_six.*
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景6: 出现一个绘制的福字,从左到右,然后从右到左且旋转,然后又从右边慢慢移动到左边,移动到中间时逐渐放大,在中间变成本来的大小,从小变大.移动到左上角,然后移动到右下角
 */
class Fragment6 : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_six

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {

            suspendCancellableCoroutine<Unit?> {
                ivLantern.animate().translationX(screenWidth.toFloat()).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        it.activeResume(null)
                    }
                }).setInterpolator(LinearInterpolator()).setDuration(1600).start()
            }

            val animatorSet = AnimatorSet()
            suspendCancellableCoroutine<Unit?> {
                val translationXAnimator = ObjectAnimator.ofFloat(ivLantern, "translationX", -screenWidth.toFloat())
                translationXAnimator.duration = 2000
                translationXAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        it.activeResume(null)
                    }
                })
                val rotationAnimator = ivLantern.createRotationAnimator(250)

                animatorSet.play(translationXAnimator).with(rotationAnimator)
                animatorSet.start()
            }
            animatorSet.cancel()

            suspendCancellableCoroutine<Unit?> {
                ivLantern.animate().setListener(null).translationX(screenWidth.toFloat()).setDuration(0).start()
                ivLantern.animate().translationX(screenWidth / 3f).setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        it.activeResume(null)
                    }
                }).setInterpolator(LinearInterpolator()).setDuration(1600).start()
            }

            suspendCancellableCoroutine<Unit?> {
                val scaleXAnimator = ObjectAnimator.ofFloat(ivLantern, "scaleX", 1f, 1.5f, 1f, 1.8f, 1f)
                val scaleYAnimator = ObjectAnimator.ofFloat(ivLantern, "scaleY", 1f, 1.5f, 1f, 1.8f, 1f)
                val animatorSet = AnimatorSet()
                animatorSet.interpolator = LinearInterpolator()
                animatorSet.duration = 3500
                animatorSet.play(scaleXAnimator).with(scaleYAnimator)
                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        it.activeResume(null)
                    }
                })
                animatorSet.start()
            }

            suspendCancellableCoroutine<Unit?> {
                val translationXAnimator = ObjectAnimator.ofFloat(
                    ivLantern, "translationX", screenWidth / 3f, -screenWidth / 3f,
                    screenWidth * 0.75f
                )
                val translationYAnimator = ObjectAnimator.ofFloat(
                    ivLantern, "translationY", 0f, -screenHeight.toFloat(),
                    screenHeight.toFloat() * 0.6f
                )
                val animatorSet = AnimatorSet()
                animatorSet.interpolator = LinearInterpolator()
                animatorSet.duration = 3500
                animatorSet.play(translationXAnimator).with(translationYAnimator)
                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        it.activeResume(null)
                    }
                })
                animatorSet.start()
            }

            animateComplete()

        }
    }
}