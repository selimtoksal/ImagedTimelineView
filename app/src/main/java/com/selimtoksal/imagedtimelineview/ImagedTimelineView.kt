package com.selimtoksal.imagedtimelineview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ImagedTimelineView
constructor(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val paint: Paint = Paint()
    private var lineColor: Int? = null
    @DrawableRes var image: Drawable? = null
    private var headerText: String? = null
    private var descriptionText: String? = null
    private var imagePadding: Int? = null
    private var isLastItem: Boolean = false

    private lateinit var imageView: ImageView

    init {
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        setWillNotDraw(false)
        paint.isAntiAlias = true
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ImagedTimelineView)
            initAttrs(typedArray)
            typedArray.recycle()
        }
        initViews()
    }

    private fun initAttrs(typedArray: TypedArray) {
        this.lineColor = Color.parseColor(typedArray.getString(R.styleable.ImagedTimelineView_line_color))
        val imageDrawableId = typedArray.getResourceId(R.styleable.ImagedTimelineView_image, -1)
        this.image = ContextCompat.getDrawable(context, imageDrawableId)
        this.headerText = typedArray.getString(R.styleable.ImagedTimelineView_header_text)
        this.descriptionText = typedArray.getString(R.styleable.ImagedTimelineView_description_text)
        this.imagePadding = typedArray.getInt(R.styleable.ImagedTimelineView_image_padding, 0)
        this.isLastItem = typedArray.getBoolean(R.styleable.ImagedTimelineView_is_last_item,false)
    }

    private fun initViews(){
        this.orientation = LinearLayout.HORIZONTAL
        this.gravity = Gravity.CENTER_VERTICAL


        imageView = ImageView(context)
        imageView.setImageDrawable(image)
        this.addView(imageView)

        val textLayout = LinearLayout(context)
        textLayout.orientation = LinearLayout.VERTICAL
        this.addView(textLayout)

        val headerTextView = TextView(context)
        headerTextView.text = this.headerText
        textLayout.addView(headerTextView)

        val descriptionTextView = TextView(context)
        descriptionTextView.text = this.descriptionText
        textLayout.addView(descriptionTextView)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(!isLastItem) {
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            val stopY = this.height
            val startX = this.imageView.width / 2
            val startY = this.imageView.height
            canvas.drawLine(startX.toFloat(), 0F, startX.toFloat(), stopY.toFloat(), p)
        }
    }

}