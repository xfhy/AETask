package com.bailan.aetask.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bailan.aetask.util.log

/**
 * @author : xfhy
 * Create time : 2022/1/17 17:10
 * Description : 放鞭炮
 */
class FirecrackersView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : AppCompatImageView
    (context, attrs, defStyle) {

    var mAnimatorEndListener: (() -> Unit)? = null
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBottomRectHeight = 0
    private var mHeight = 0
    private var mWidth = 0

    init {
        mPaint.color = Color.WHITE
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, (mHeight - mBottomRectHeight).toFloat(), mWidth.toFloat(), mHeight.toFloat(), mPaint)
        log("onDraw ${tag}")
    }

    /*fun startAnimate() {
        val valueAnimator = ValueAnimator.ofInt(mHeight, 0).setDuration(1000)
        valueAnimator.addUpdateListener { animation ->
            mBottomRectHeight = mHeight - (animation.animatedValue as Int)
            invalidate()
        }
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                mAnimatorEndListener?.invoke()
            }
        })
        valueAnimator.start()
    }*/

    fun reduceHeight() {
        if (mBottomRectHeight > mHeight) {
            mAnimatorEndListener?.invoke()
            return
        }
        mBottomRectHeight += 10

        log("mBottomRectHeight=$mBottomRectHeight tag=$tag")
    }

}