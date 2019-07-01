package com.example.end.presentation.ui.products

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.end.R
import com.example.end.di.GlideApp

class ProductItemView : ConstraintLayout {

    lateinit var productImage: ImageView
    lateinit var title: TextView
    lateinit var price: TextView

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet? = null) {
        LayoutInflater.from(context).inflate(R.layout.item_product, this)
        productImage = findViewById(R.id.imageView_product)
        title = findViewById(R.id.textView_title)
        price = findViewById(R.id.textView_price)

        if (attrs != null) {
            val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProductItemView)
            attributes.getString(R.styleable.ProductItemView_textView_title)?.also { setTitle(it) }
            attributes.getString(R.styleable.ProductItemView_textView_price)?.also { setPrice(it) }
            attributes.getString(R.styleable.ProductItemView_imageView_product)
                ?.also { setImage(it) }
            attributes.recycle()
        }
    }

    fun setPrice(price: String) {
        this.price.text = price
    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setImageHeight(height: Int) {
        val params = productImage.layoutParams
        params.height = height
        productImage.layoutParams = params
    }

    fun setImage(imageUrl: String) {
        GlideApp.with(context)
            .load(imageUrl)
            .into(productImage)
    }

}