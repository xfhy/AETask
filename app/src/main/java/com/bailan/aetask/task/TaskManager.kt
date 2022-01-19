package com.bailan.aetask.task

import androidx.fragment.app.FragmentActivity
import com.bailan.aetask.R
import com.bailan.aetask.fragment.BaseFragment

/**
 * @author : xfhy
 * Create time : 2022/1/17 11:56
 * Description :
 */
class TaskManager(private val activity: FragmentActivity, private val containerId: Int, private val fragmentList: List<BaseFragment>) {

    /**
     * 开始摆烂
     */
    fun startRotten() {
        show(0)
    }

    private fun show(index: Int) {
        if (index >= fragmentList.size) {
            return
        }
        val fragment = fragmentList[index]
        fragment.completeListener = {
            show(index + 1)
        }
        val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_container, fragment)
        fragmentTransaction.commit()
    }

}