package com.bailan.aetask.fragment

import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R
import kotlinx.coroutines.delay

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description : 场景2: 2个福字,中间有一点重叠
 */
class Fragment2 : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_two

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
            delay(500)
            animateComplete()
        }
    }
}