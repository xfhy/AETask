package com.bailan.aetask.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import com.bailan.aetask.util.*
import kotlinx.android.synthetic.main.fragment_eight.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.random.Random

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景8: 屏幕左边随机出现一些福字,从左到右且旋转;右边也随机出现一些福字,从右到左且旋转;
 * 有一个小一些的福字,从左到右,没有旋转;其次,从屏幕右边出现2行文字:过年啦,从右往左慢慢移动;2行文字要移动到一半的时候,屏幕中间出现一个逐渐变大的福字,变大速度很慢;
 * 与此同时,屏幕中间出现2行文字,第一行:欢欢喜喜;第二行:过大年.这2行文字挨个变大,然后缩小为本身的大小,变大的同时透明度逐渐降低,变小之后透明度又变成1了.
 * 等2行文字都完成动画时,中间的福字变小,然后消失
 */
class Fragment8 : BaseFragment() {

    /**
     * 福字图片的宽高
     */
    private val blessingViewWidth by lazy {
        screenWidth * 0.25f
    }

    override fun getLayoutResId() = R.layout.fragment_eight

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
            val leftBlessings = createLeftBlessings()
            val rightBlessings = createRightBlessings()
            val leftDeferred = async {
                addViewToContainer(leftBlessings)
            }
            val rightDeferred = async {
                addViewToContainer(rightBlessings)
            }
            val leftViews = leftDeferred.await()
            val rightViews = rightDeferred.await()

            doLeftSmallAnimator()

            doRightTextAnimator()

            doBlessingsLTRAnimator(leftViews)
            doBlessingsRTLAnimator(rightViews)

            doCenterAnimator()

            doCenterTextAnimator()

        }
    }

    private fun doCenterTextAnimator() {
        val array = arrayListOf<TextView>(
            tvCenterText1, tvCenterText2, tvCenterText3, tvCenterText4, tvCenterText5, tvCenterText6,
            tvCenterText7
        )
        flow1.visibility = View.VISIBLE
        flow2.visibility = View.VISIBLE
        activity?.assets?.let { assetManager ->
            val typeface = Typeface.createFromAsset(assetManager, "华文行楷.ttf")
            array.forEach {
                it.typeface = typeface
            }
        }

        array.forEachIndexed { index, textView ->
            val scaleXAnimator = ObjectAnimator.ofFloat(textView, "scaleX", 1f, 3.2f, 1f)
            val scaleYAnimator = ObjectAnimator.ofFloat(textView, "scaleY", 1f, 3.2f, 1f)

            val animatorSet = AnimatorSet()
            animatorSet.duration = 3600
            animatorSet.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                }
            })
            animatorSet.play(scaleXAnimator).with(scaleYAnimator)
            animatorSet.startDelay = index * animatorSet.duration
            animatorSet.start()
        }

    }

    private fun doRightTextAnimator() {
        tvRightText.animate().translationX(-screenWidth.toFloat() - tvRightText.width).setInterpolator(LinearInterpolator())
            .setDuration(12000).start()
    }

    private fun doLeftSmallAnimator() {
        ivSmallBlessing.animate().translationX(screenWidth.toFloat() + ivSmallBlessing.width).setInterpolator(LinearInterpolator())
            .setDuration(12000).start()
    }

    private suspend fun doCenterAnimator() = suspendCancellableCoroutine<Unit?> { continuation ->

        val alphaAnimator = ObjectAnimator.ofFloat(ivCenterBlessing, "alpha", 0f, 1f)
        alphaAnimator.duration = 400

        val scaleXAnimator = ObjectAnimator.ofFloat(ivCenterBlessing, "scaleX", 1f, 2.2f)
        val scaleYAnimator = ObjectAnimator.ofFloat(ivCenterBlessing, "scaleY", 1f, 2.2f)

        scaleXAnimator.duration = 3600
        scaleYAnimator.duration = 3600

        val animatorSet = AnimatorSet()
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                continuation.activeResume(null)
            }
        })
        animatorSet.play(scaleXAnimator).with(scaleYAnimator).after(alphaAnimator)
        animatorSet.start()
    }

    private fun createLeftBlessings(): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()

        val rangeMaxX = screenWidth / 8
        val rangeMaxY = screenHeight - blessingViewWidth

        for (i in 0..2) {
            val x = createOneRandomNumber(rangeMaxX) - blessingViewWidth
            val y = createOneRandomNumber(rangeMaxY.toInt())
            list.add(Pair(x.toInt(), y))
        }

        return list
    }

    private fun createRightBlessings(): List<Pair<Int, Int>> {
        val list = mutableListOf<Pair<Int, Int>>()

        val rangeMaxY = screenHeight - blessingViewWidth

        for (i in 0..2) {
            val x = Random.nextInt(screenWidth - screenWidth / 8, screenWidth)
            val y = createOneRandomNumber(rangeMaxY.toInt())
            list.add(Pair(x, y))
        }

        return list
    }

    private suspend fun addViewToContainer(list: List<Pair<Int, Int>>): List<ImageView> {
        val viewList = mutableListOf<ImageView>()
        list.forEach { pair ->
            val imageView = ImageView(requireActivity())
            imageView.setImageResource(R.mipmap.blessing)
            val layoutParams = ConstraintLayout.LayoutParams(blessingViewWidth.toInt(), blessingViewWidth.toInt())
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.leftMargin = pair.first
            layoutParams.topMargin = pair.second

            delay(300)
            log("addView")
            viewList.add(imageView)
            clEight.addView(imageView, layoutParams)
        }
        return viewList
    }

    private suspend fun doBlessingsRTLAnimator(rightViews: List<ImageView>) = suspendCancellableCoroutine<Unit?> { continuation ->
        rightViews.forEachIndexed { index, imageView ->
            val animatorSet = AnimatorSet()
            val translationXAnimator = ObjectAnimator.ofFloat(imageView, "translationX", -screenWidth.toFloat())
            translationXAnimator.duration = (1500 + Random.nextInt(2000)).toLong()
            translationXAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    imageView.animate().setDuration(400).alpha(0f).start()
                    if (index == rightViews.size - 1) {
                        continuation.activeResume(null)
                    }
                }
            })
            val rotationAnimator = imageView.createRotationAnimator(350)
            animatorSet.play(translationXAnimator).with(rotationAnimator)
            animatorSet.startDelay = (index * 300 + Random.nextInt(100)).toLong()
            animatorSet.start()
        }
    }

    private suspend fun doBlessingsLTRAnimator(leftViews: List<ImageView>) = suspendCancellableCoroutine<Unit?> { continuation ->
        leftViews.forEachIndexed { index, imageView ->

            val animatorSet = AnimatorSet()
            val translationXAnimator = ObjectAnimator.ofFloat(imageView, "translationX", screenWidth.toFloat())
            translationXAnimator.duration = (1500 + Random.nextInt(500)).toLong()
            translationXAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    if (index == leftViews.size - 1) {
                        continuation.activeResume(null)
                    }
                }
            })
            val rotationAnimator = imageView.createRotationAnimator(350)
            animatorSet.play(translationXAnimator).with(rotationAnimator)
            animatorSet.startDelay = (index * 300 + Random.nextInt(100)).toLong()
            animatorSet.start()
        }
    }

}