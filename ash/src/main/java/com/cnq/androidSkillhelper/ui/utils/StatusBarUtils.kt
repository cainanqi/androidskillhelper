package com.cnq.androidSkillhelper.ui.utils

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.cnq.androidSkillhelper.R


/**
 * ============================
 * Author: EdricCai
 * House Lannister of Casterly Rock
 * Hear me roar
 * Date  :2020/7/21
 * ============================
 **/
class StatusBarUtils {
    private var mActivity: Activity? = null

    //状态栏颜色
    private var mColor = -1

    //状态栏drawble
    private var mDrawable: Drawable? = null

    //是否是最外层布局是 DrawerLayout 的侧滑菜单
    private var mIsDrawerLayout = false

    //是否包含 ActionBar
    private var mIsActionBar = false

    //侧滑菜单页面的内容视图
    private var mContentResourseIdInDrawer = 0

    fun StatusBarUtils(activity: Activity?): StatusBarUtils? {
        mActivity = activity
        return this
    }

    fun with(activity: Activity?): StatusBarUtils? {
        return StatusBarUtils(activity)
    }

    fun getColor(): Int {
        return mColor
    }

    fun setColor(color: Int): StatusBarUtils? {
        mColor = color
        return this
    }

    fun getDrawable(): Drawable? {
        return mDrawable
    }

    fun setDrawable(drawable: Drawable?): StatusBarUtils? {
        mDrawable = drawable
        return this
    }

    fun isDrawerLayout(): Boolean {
        return mIsDrawerLayout
    }

    fun isActionBar(): Boolean {
        return mIsActionBar
    }

    fun setIsActionBar(actionBar: Boolean): StatusBarUtils? {
        mIsActionBar = actionBar
        return this
    }

    /**
     * 是否是最外层布局为 DrawerLayout 的侧滑菜单
     *
     * @param drawerLayout 是否最外层布局为 DrawerLayout
     * @param contentId    内容视图的 id
     * @return
     */
    fun setDrawerLayoutContentId(
        drawerLayout: Boolean,
        contentId: Int
    ): StatusBarUtils? {
        mIsDrawerLayout = drawerLayout
        mContentResourseIdInDrawer = contentId
        return this
    }

    fun init() {
        fullScreen(mActivity)
        if (mColor != -1) {
            //设置了状态栏颜色
            addStatusViewWithColor(mActivity, mColor)
        }
        if (mDrawable != null) {
            //设置了状态栏 drawble，例如渐变色
            addStatusViewWithDrawble(mActivity!!, mDrawable!!)
        }
        if (isDrawerLayout()) {
            //未设置 fitsSystemWindows 且是侧滑菜单，需要设置 fitsSystemWindows 以解决 4.4 上侧滑菜单上方白条问题
            fitsSystemWindows(mActivity)
        }
        if (isActionBar()) {
            //要增加内容视图的 paddingTop,否则内容被 ActionBar 遮盖
            val rootView: ViewGroup = mActivity!!.getWindow().getDecorView()
                .findViewById(R.id.content) as ViewGroup
            rootView.setPadding(
                0,
                getStatusBarHeight(mActivity) + getActionBarHeight(mActivity),
                0,
                0
            )
        }
    }

    /**
     * 去除 ActionBar 阴影
     */
    fun clearActionBarShadow(): StatusBarUtils? {
        if (Build.VERSION.SDK_INT >= 21) {
            val supportActionBar: ActionBar? =
                (mActivity as AppCompatActivity?)!!.supportActionBar
            if (supportActionBar != null) {
                supportActionBar.elevation = 0f
            }
        }
        return this
    }

    /**
     * 设置页面最外层布局 FitsSystemWindows 属性
     *
     * @param activity
     */
    private fun fitsSystemWindows(activity: Activity?) {
        val contentFrameLayout: ViewGroup = activity?.findViewById(R.id.content) as ViewGroup
        val parentView: View = contentFrameLayout.getChildAt(0)
        parentView.fitsSystemWindows = true
        //布局预留状态栏高度的 padding
        if (parentView is DrawerLayout) {
            val drawer = parentView
            //将主页面顶部延伸至status bar;虽默认为false,但经测试,DrawerLayout需显示设置
            drawer.clipToPadding = false
        }
    }

    /**
     * 利用反射获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight(activity: Activity?): Int {
        var result = 0
        //获取状态栏高度的资源id
        val resourceId: Int =
            activity!!.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        Log.e("getStatusBarHeight", result.toString() + "")
        return result
    }

    /**
     * 获得 ActionBar 的高度
     *
     * @param context
     * @return
     */
    fun getActionBarHeight(context: Context?): Int {
        var result = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            val tv = TypedValue()
            context!!.theme.resolveAttribute(R.attr.actionBarSize, tv, true)
            result = TypedValue.complexToDimensionPixelSize(
                tv.data,
                context.resources.displayMetrics
            )
        }
        return result
    }

    /**
     * 添加状态栏占位视图
     *
     * @param activity
     */
    private fun addStatusViewWithColor(activity: Activity?, color: Int) {
        if (isDrawerLayout()) {
            //要在内容布局增加状态栏，否则会盖在侧滑菜单上
            val rootView: ViewGroup = activity!!.findViewById(R.id.content) as ViewGroup
            //DrawerLayout 则需要在第一个子视图即内容试图中添加padding
            val parentView: View = rootView.getChildAt(0)
            val linearLayout = LinearLayout(activity)
            linearLayout.setOrientation(LinearLayout.VERTICAL)
            val statusBarView = View(activity)
            val lp: ViewGroup.LayoutParams = android.app.ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity)
            )
            statusBarView.setBackgroundColor(color)
            //添加占位状态栏到线性布局中
            linearLayout.addView(statusBarView, lp)
            //侧滑菜单
            val drawer = parentView as DrawerLayout
            //内容视图
            val content: View = activity.findViewById(mContentResourseIdInDrawer)
            //将内容视图从 DrawerLayout 中移除
            drawer.removeView(content)
            //添加内容视图
            linearLayout.addView(content, content.getLayoutParams())
            //将带有占位状态栏的新的内容视图设置给 DrawerLayout
            drawer.addView(linearLayout, 0)
        } else {
            //设置 paddingTop
            val rootView: ViewGroup = mActivity!!.window.decorView
                .findViewById(R.id.content) as ViewGroup
            rootView.setPadding(0, getStatusBarHeight(mActivity), 0, 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //直接设置状态栏颜色
                activity!!.window.statusBarColor = color
            } else {
                //增加占位状态栏
                val decorView: ViewGroup = mActivity!!.window.decorView as ViewGroup
                val statusBarView = View(activity)
                val lp: ViewGroup.LayoutParams = android.app.ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity)
                )
                statusBarView.setBackgroundColor(color)
                decorView.addView(statusBarView, lp)
            }
        }
    }

    /**
     * 添加状态栏占位视图
     *
     * @param activity
     */
    private fun addStatusViewWithDrawble(activity: Activity, drawable: Drawable) {
        //占位状态栏
        val statusBarView = View(activity)
        val lp: ViewGroup.LayoutParams = android.app.ActionBar.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getStatusBarHeight(activity)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            statusBarView.setBackground(drawable)
        } else {
            statusBarView.setBackgroundDrawable(drawable)
        }
        if (isDrawerLayout()) {
            //要在内容布局增加状态栏，否则会盖在侧滑菜单上
            val rootView: ViewGroup = activity.findViewById(R.id.content) as ViewGroup
            //DrawerLayout 则需要在第一个子视图即内容试图中添加padding
            val parentView: View = rootView.getChildAt(0)
            val linearLayout = LinearLayout(activity)
            linearLayout.setOrientation(LinearLayout.VERTICAL)
            //添加占位状态栏到线性布局中
            linearLayout.addView(statusBarView, lp)
            //侧滑菜单
            val drawer = parentView as DrawerLayout
            //内容视图
            val content: View = activity.findViewById(mContentResourseIdInDrawer)
            //将内容视图从 DrawerLayout 中移除
            drawer.removeView(content)
            //添加内容视图
            linearLayout.addView(content, content.getLayoutParams())
            //将带有占位状态栏的新的内容视图设置给 DrawerLayout
            drawer.addView(linearLayout, 0)
        } else {
            //增加占位状态栏，并增加状态栏高度的 paddingTop
            val decorView: ViewGroup = mActivity!!.window.decorView as ViewGroup
            decorView.addView(statusBarView, lp)
            //设置 paddingTop
            val rootView: ViewGroup = mActivity!!.window.decorView
                .findViewById(R.id.content) as ViewGroup
            rootView.setPadding(0, getStatusBarHeight(mActivity), 0, 0)
        }
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private fun fullScreen(activity: Activity?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val window: Window = activity!!.window
            val decorView: View = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option: Int = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            decorView.setSystemUiVisibility(option)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
            //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
        } else {
            val window: Window = activity!!.window
            val attributes: WindowManager.LayoutParams = window.attributes
            val flagTranslucentStatus: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            val flagTranslucentNavigation: Int =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            attributes.flags = attributes.flags or flagTranslucentStatus
            //                attributes.flags |= flagTranslucentNavigation;
            window.setAttributes(attributes)
        }
    }

    /**
     * 通过设置全屏，设置状态栏透明 导航栏黑色
     *
     * @param activity
     */
    fun setStatusTransparent(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.getWindow()
            val attributes: WindowManager.LayoutParams = window.getAttributes()
            val flagTranslucentStatus: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            val flagTranslucentNavigation: Int =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            //                attributes.flags |= flagTranslucentStatus;
            attributes.flags = attributes.flags or flagTranslucentNavigation
            window.setAttributes(attributes)
            window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            )
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(Color.TRANSPARENT)
            window.setNavigationBarColor(Color.TRANSPARENT)
        } else {
            val window: Window = activity.getWindow()
            val attributes: WindowManager.LayoutParams = window.getAttributes()
            val flagTranslucentStatus: Int = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            val flagTranslucentNavigation: Int =
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            attributes.flags = attributes.flags or flagTranslucentStatus
            attributes.flags = attributes.flags or flagTranslucentNavigation
            window.setAttributes(attributes)
        }
    }
}