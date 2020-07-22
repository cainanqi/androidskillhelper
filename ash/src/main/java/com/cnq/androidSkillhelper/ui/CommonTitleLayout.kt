package com.cnq.androidSkillhelper.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.cnq.androidSkillhelper.R
import com.cnq.androidSkillhelper.ui.extend.dp2px
import com.cnq.androidSkillhelper.ui.extend.dp2sp
import com.cnq.androidSkillhelper.ui.extend.px2sp
import com.cnq.androidSkillhelper.ui.extend.sp2px

/**
 * Author by ${HeXinGen}, Date on 2018/10/17.
 * 通用标题布局
 */
class CommonTitleLayout : RelativeLayout {
    private var back_iv: ImageView? = null
    private var title_tv: TextView? = null
    private var mTextView: TextView? = null
    private var mTextView2: TextView? = null
    var rightTextColor=Color.parseColor("#333333")
    var titleStr=""
    var backResource=0

    constructor(context: Context?) : super(context) {
        addChild()
    }

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        val ta=context?.obtainStyledAttributes(attrs,R.styleable.CommonTitleLayout)
        titleStr= ta?.getString(R.styleable.CommonTitleLayout_ash_title) ?: ""
        backResource= ta?.getResourceId(R.styleable.CommonTitleLayout_ash_background,R.drawable.layer_list_common_title_layout_bg)!!
        ta.recycle()
        addChild()
        setValue()
    }

    private fun setValue() {
        setBackgroundResource(backResource)
        title_tv?.text=titleStr
    }



    private fun addChild() {
        back_iv = createBack()
        title_tv = createTitle()
        addView(back_iv)
        addView(title_tv)
    }

    private fun createTitle(): TextView {
        val textView = TextView(context)
        textView.setTextColor(Color.parseColor("#25252d"))
        textView.textSize =16.0f.dp2sp()
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_IN_PARENT,
            TRUE
        )
        textView.layoutParams = layoutParams
        return textView
    }

    private fun createBack(): ImageView {
        val imageView = ImageView(context)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_VERTICAL,
            TRUE
        )
        layoutParams.leftMargin = 10.dp2px()
        imageView.layoutParams = layoutParams
        imageView.setImageResource(R.drawable.ic_return)
        imageView.setPadding(
            0.dp2px(),
            5.dp2px(),
            5.dp2px(),
            5.dp2px()

        )
        imageView.setOnClickListener { v: View? ->
            val context = context
            if (context is Activity && isBack) {
                context.finish()
            }
        }
        return imageView
    }

    var isBack = true
    fun setBackView(): View? {
        isBack = false
        return back_iv
    }

    /**
     * 添加标题栏，右边文字
     * @param content
     * @param onClickListener
     */
    fun addRightContent(
        content: String?,
        onClickListener: OnClickListener?
    ) {
        mTextView = TextView(context)
        mTextView!!.setTextColor(rightTextColor)
        mTextView!!.textSize = 15.sp2px()
        mTextView!!.text = content
        mTextView!!.id = Int.MAX_VALUE - 1000
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_VERTICAL,
            TRUE
        )
        layoutParams.addRule(
            ALIGN_PARENT_RIGHT,
            TRUE
        )
        layoutParams.rightMargin = 15.dp2px()
        mTextView!!.setOnClickListener(onClickListener)
        addView(mTextView, layoutParams)
    }

    /**
     * 添加标题栏，右边文字
     * @param content
     * @param onClickListener
     */
    fun addRightContent(
        color: Int,
        content: String?,
        onClickListener: OnClickListener?
    ) {
        mTextView = TextView(context)
        mTextView!!.setTextColor(rightTextColor)
        mTextView!!.textSize = 15.sp2px()
        mTextView!!.text = content
        mTextView!!.setTextColor(color)
        mTextView!!.id = Int.MAX_VALUE - 1000
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_VERTICAL,
            TRUE
        )
        layoutParams.addRule(
            ALIGN_PARENT_RIGHT,
            TRUE
        )
        layoutParams.rightMargin = 15.dp2px()
        mTextView!!.setOnClickListener(onClickListener)
        addView(mTextView, layoutParams)
    }

    /**
     * 添加标题栏，右边图片
     * @param image
     * @param onClickListener
     */
    fun addRightContent(
        image: Int,
        onClickListener: OnClickListener?
    ) {
        val width: Int = 30.dp2px()
        val height = width
        val imageView = ImageView(context)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_VERTICAL,
            TRUE
        )
        layoutParams.addRule(
            ALIGN_PARENT_RIGHT,
            TRUE
        )
        layoutParams.rightMargin = 15.dp2px()
        imageView.setImageResource(image)
        imageView.setPadding(
            5.dp2px(),
            5.dp2px(),
            5.dp2px(),
            5.dp2px()
        )
        imageView.setOnClickListener(onClickListener)
        addView(imageView, layoutParams)
    }

    fun addRightContent(
        image: Int,
        onClickListener: OnClickListener?,
        color: Int
    ) {
        val width: Int =30.dp2px()
        val height = width
        val imageView = ImageView(context)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_VERTICAL,
            TRUE
        )
        layoutParams.addRule(
            ALIGN_PARENT_RIGHT,
            TRUE
        )
        layoutParams.rightMargin = 15.dp2px()
        imageView.setImageResource(image)
        imageView.setPadding(
            5.dp2px(),
            5.dp2px(),
            5.dp2px(),
            5.dp2px()
        )
        imageView.setOnClickListener(onClickListener)
        imageView.setColorFilter(color)
        addView(imageView, layoutParams)
    }

    /**
     * 添加标题栏，右边图片
     * @param content
     * @param onClickListener
     */
    fun addRightText(
        content: String?,
        onClickListener: OnClickListener?
    ) {
        mTextView2 = TextView(context)
        mTextView2!!.setTextColor(rightTextColor)
        mTextView2!!.textSize = 15.sp2px()
        mTextView2!!.text = content
        val a = mTextView!!.measuredWidth
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.addRule(
            CENTER_VERTICAL,
            TRUE
        )
        layoutParams.addRule(LEFT_OF, mTextView!!.id)
        layoutParams.rightMargin = 15.dp2px()
        mTextView2!!.setOnClickListener(onClickListener)
        addView(mTextView2, layoutParams)
    }

    fun removeRightText() {
        if (mTextView != null) {
            removeView(mTextView)
        }
    }

    /**
     * 设置标题信息
     * @param title
     */
    fun setTitle(title: String?) {
        title_tv!!.text = title
    }
}