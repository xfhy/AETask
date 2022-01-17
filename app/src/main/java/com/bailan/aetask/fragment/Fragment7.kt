package com.bailan.aetask.fragment

import androidx.lifecycle.lifecycleScope
import com.bailan.aetask.R

/**
 * @author : xfhy
 * Create time : 2022/1/17 12:04
 * Description :
 */
class Fragment7 : BaseFragment() {

    override fun getLayoutResId() = R.layout.fragment_seven

    override fun startAnimate() {
        lifecycleScope.launchWhenResumed {
        }
    }
}