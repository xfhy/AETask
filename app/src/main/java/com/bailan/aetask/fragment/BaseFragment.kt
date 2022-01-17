package com.bailan.aetask.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * @author : xfhy
 * Create time : 2022/1/17 11:56
 * Description :
 */
abstract class BaseFragment : Fragment() {

    /**
     * 该fragment所对应的布局
     */
    protected lateinit var mRootView: View

    var completeListener: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //需要返回页面布局   所有子类需要返回view
        mRootView = inflater.inflate(getLayoutResId(), container, false)
        mRootView.setBackgroundColor(Color.WHITE)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAnimate()
    }

    fun animateComplete() {
        completeListener?.invoke()
    }

    /**
     * 设置布局的id
     *
     * @return 返回子类布局id
     */
    protected abstract fun getLayoutResId(): Int

    protected abstract fun startAnimate()

}