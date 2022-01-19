package com.bailan.aetask.fragment

import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import com.bailan.aetask.util.createOneRandomNumber
import com.bailan.aetask.util.log
import com.bailan.aetask.util.screenHeight
import com.bailan.aetask.util.screenWidth
import com.bailan.aetask.view.FirecrackersView
import kotlinx.android.synthetic.main.fragment_five.*
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景5: 鞭炮随机出现在屏幕中,高度逐渐减小到0
 */
open class Fragment5 : BaseFragment() {

    open val viewCount
        get() = 15

    override fun getLayoutResId() = R.layout.fragment_five

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
            val whPair = getFirecrackersViewWH()
            val firecrackersCoordinate = createSomeFirecrackers(whPair)
            flFiveContainer.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            async {
                addViewToContainer(firecrackersCoordinate)
            }
            async {
                setOffFirecrackers(firecrackersCoordinate.size)
            }
        }
    }

    private fun getFirecrackersViewWH(): Pair<Int, Int> {
        val firecrackersView = FirecrackersView(requireActivity())
        firecrackersView.setImageResource(R.mipmap.firecrackers)
        val width: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val height: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        firecrackersView.measure(width, height)
        return Pair(firecrackersView.measuredWidth, firecrackersView.measuredHeight)
    }

    private fun createSomeFirecrackers(whPair: Pair<Int, Int>): List<Pair<Int, Int>> {
        val coordinateList = mutableListOf<Pair<Int, Int>>()

        val rangeMaxX = screenWidth - whPair.first
        val rangeMaxY = screenHeight - whPair.second

        for (i in 0..viewCount) {
            val x = createOneRandomNumber(rangeMaxX)
            val y = createOneRandomNumber(rangeMaxY)
            coordinateList.add(Pair(x, y))
        }

        return coordinateList
    }

    private suspend fun addViewToContainer(firecrackersCoordinate: List<Pair<Int, Int>>) {
        firecrackersCoordinate.forEach { pair ->
            val firecrackersView = FirecrackersView(requireActivity())
            firecrackersView.setImageResource(R.mipmap.firecrackers)
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.leftMargin = pair.first
            layoutParams.topMargin = pair.second

            delay(300)
            log("addView")
            flFiveContainer.addView(firecrackersView, layoutParams)
        }
    }


    private suspend fun setOffFirecrackers(size: Int) {
        var flag = true
        flFiveContainer.setWillNotDraw(false)

        while (flag) {
            val childCount = flFiveContainer.childCount
            for (i in 0 until childCount) {
                val firecrackersView = flFiveContainer.getChildAt(i) as FirecrackersView

                firecrackersView.setTag(i)

                if (i == size - 1 && firecrackersView.mAnimatorEndListener == null) {
                    firecrackersView.mAnimatorEndListener = {
                        flag = false
                    }
                }
                firecrackersView.reduceHeight()
            }
            delay(100)
            log("invalidate")
            flFiveContainer.invalidate()
        }
        log("结束")
        animateComplete()
    }

}