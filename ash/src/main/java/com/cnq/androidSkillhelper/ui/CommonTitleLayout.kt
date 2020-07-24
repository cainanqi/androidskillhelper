package com.cnq.androidSkillhelper.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
class CommonTitleLayout : LinearLayout {
    private var leftImg: ImageView? = null
    private var rightImg: ImageView? = null
    private var leftText: TextView? = null
    private var rightText: TextView? = null
    private var titleText: TextView? = null

    private var ctlListener: CTLListener? = null
    private var rightTextColor = Color.parseColor("#333333")
    private var leftTextColor = Color.parseColor("#333333")
    private var leftResource = R.drawable.ic_return
    private var rightResource = R.drawable.ic_default_right

    private var titleStr = ""
    private var backResource = 0
    private var type = 3

    constructor(context: Context?) : super(context) {
        addChild()
    }

    @SuppressLint("CustomViewStyleable")
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.CommonTitleLayout)
        titleStr = ta?.getString(R.styleable.CommonTitleLayout_ash_title) ?: ""
        backResource = ta?.getResourceId(
            R.styleable.CommonTitleLayout_ash_background,
            R.drawable.layer_list_common_title_layout_bg
        )!!
        type = ta.getInt(R.styleable.CommonTitleLayout_ash_type, 3)
        ta.recycle()
        addChild()
        setValue()
    }

    private fun setValue() {
        setBackgroundResource(backResource)
    }


    private fun addChild() {
        when (type) {
            0 -> {
                //text_img
                createLeftText()
                createTitle()
                createRightImg()
            }
            1 -> {
                //text_text
                createLeftText()
                createTitle()
                createRightText()
            }
            2 -> {
                //img_img
                createLeftImg()
                createTitle()
                createRightImg()
            }
            3 -> {
                // img_text
                createLeftImg()
                createTitle()
                createRightText()
            }
        }

    }

    private fun createLeftImg() {
        leftImg = ImageView(context)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )

        layoutParams.leftMargin = 10.dp2px()
        leftImg?.layoutParams = layoutParams
        leftImg?.setImageResource(leftResource)
        leftImg?.setPadding(
            0.dp2px(),
            5.dp2px(),
            5.dp2px(),
            5.dp2px()

        )
        leftImg?.setOnClickListener {
            ctlListener?.onLeftListener()
        }
        addView(leftImg)
    }

    private fun createRightText() {
        rightText = TextView(context)
        rightText?.setTextColor(rightTextColor)
        rightText?.textSize = 14.0f.dp2sp()
        rightText?.text = context.getString(R.string.ash_sure)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.rightMargin = 10.dp2px()

        rightText?.layoutParams = layoutParams
        rightText?.setOnClickListener {
            ctlListener?.onRightListener()
        }

        addView(rightText)
    }

    private fun createRightImg() {
        rightImg = ImageView(context)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )

        layoutParams.rightMargin = 10.dp2px()
        rightImg?.layoutParams = layoutParams
        rightImg?.setImageResource(rightResource)
        rightImg?.setPadding(
            0.dp2px(),
            5.dp2px(),
            5.dp2px(),
            5.dp2px()

        )
        rightImg?.setOnClickListener { _: View? ->
            ctlListener?.onRightListener()
        }
        addView(rightImg)
    }

    private fun createLeftText() {
        leftText = TextView(context)
        leftText?.setTextColor(leftTextColor)
        leftText?.textSize = 14.0f.dp2sp()
        leftText?.text = context.getString(R.string.ash_cancel)
        val layoutParams =
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            )
        layoutParams.leftMargin = 10.dp2px()
        leftText?.layoutParams = layoutParams
        leftText?.setOnClickListener {
            ctlListener?.onLeftListener()
        }
        addView(leftText)
    }

    private fun createTitle() {
        titleText = TextView(context)
        titleText?.setTextColor(Color.parseColor("#25252d"))
        titleText?.textSize = 16.0f.dp2sp()
        titleText?.text = titleStr
        val layoutParams =
            LayoutParams(
                0,
                LayoutParams.WRAP_CONTENT, 1.0f
            )
        layoutParams.gravity = Gravity.CENTER
        titleText?.layoutParams = layoutParams
        titleText?.gravity = Gravity.CENTER
        addView(titleText)
    }


    /**
     * 设置标题信息
     * @param title
     */
    fun setTitle(title: String) {
        titleText?.text = title
    }

    fun setLeftText(text: String) {
        leftText?.text = text
    }

    fun setRightText(text: String) {
        rightText?.text = text
    }

    fun setLeftImg(resourceId: Int) {
        leftImg?.setImageResource(resourceId)
    }

    fun setRightImg(resourceId: Int) {
        rightImg?.setImageResource(resourceId)
    }

    interface CTLListener {
        fun onLeftListener()
        fun onRightListener()
    }
}
