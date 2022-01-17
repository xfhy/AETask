package com.bailan.aetask

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bailan.aetask.fragment.Fragment2
import com.blankj.utilcode.util.BarUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * 原作者视频: https://www.bilibili.com/video/BV1xL4y1E7nT
 *
 * 思路:
 * 1. 搞个Manager,管理Task
 * 2. 每个Task内部负责展示自己的Fragment,Task完成之后继续下一个Task的执行
 * 3. 责任链模式
 *
 * 开始之前:
 * 1. 搞到原作音频文件,当做背景音乐
 * 2. 下载必剪和投稿工具
 *
 * 动画:
 * 场景1: 3个福,先展示一个在中间,再依次展示右边和左边. 左右两边的去掉,中间的福放大
 * 场景2: 2个福字,中间有一点重叠
 * 场景3: 先在中间展示一个福字,然后倒过来,然后开始顺时针旋转
 * 场景4: 左右2个福字,分别旋转,开始时间不同
 * 场景5: 鞭炮随机出现在屏幕中,高度之间减小到0
 * 场景6: 出现一个绘制的福字,从左到右,然后从右到左且旋转,然后又从右边慢慢移动到左边,移动到中间时逐渐放大,在中间变成本来的大小,从小变大.移动到左上角,然后移动到右下角
 * 场景7: 又开始:鞭炮随机出现在屏幕中,高度之间减小到0,时间稍微久点
 * 场景8: 屏幕左边随机出现一些福字,从左到右且旋转;随机出现右边也随机出现一些福字,从右到左且旋转;
 * 有一个小一些的福字,从左到右,没有旋转;其次,从屏幕右边出现2行文字:过年啦,从右往左慢慢移动;2行文字要移动到一半的时候,屏幕中间出现一个逐渐变大的福字,变大速度很慢;
 * 与此同时,屏幕中间出现2行文字,第一行:欢欢喜喜;第二行:过大年.这2行文字挨个变大,然后缩小为本身的大小,变大的同时透明度逐渐降低,变小之后透明度又变成1了.
 * 等2行文字都完成动画时,中间的福字变小,然后消失
 *
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }

    private fun initUI() {
        BarUtils.setNavBarVisibility(this, false)
        BarUtils.setStatusBarVisibility(this, false)
        btnStart.setOnClickListener {
            btnStart.visibility = View.GONE
            test()
        }
    }

    private fun test() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fl_container, Fragment1())
        fragmentTransaction.replace(R.id.fl_container, Fragment2())
        fragmentTransaction.commit()
    }
}
/*
* 设置字体
* AssetManager mgr=getAssets();//得到AssetManager

Typeface tf=Typeface.createFromAsset(mgr, "fonts/ttf.ttf");//根据路径得到Typeface

tv=findViewById(R.id.textview);

tv.setTypeface(tf);//设置字体
* */