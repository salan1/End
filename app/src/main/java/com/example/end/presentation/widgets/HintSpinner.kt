package com.example.end.presentation.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import timber.log.Timber

class HintSpinner : Spinner {

    private var hintAdapter: HintAdapter? = null
    var selectListener: ((pos: Int) -> Unit)? = null

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) : super(
        context,
        attrs,
        defStyleAttr,
        mode
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
        mode: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes, mode)

    init {
        setupSelectedListener()
    }

    fun setItems(items: List<String>, mode: HintMode, viewType: Boolean? = null) {
        if (hintAdapter == null) {
            hintAdapter = HintAdapter(context, this.id, mode, items)
            adapter = hintAdapter
            setSelection(items.size - 1)
        } else {
            hintAdapter?.also { adapter ->
                adapter.updateList(items)
                adapter.spinnerItems = items
                setSelection(items.size - 1)
            }
        }
        if (mode == HintMode.MULTI) {
            hintAdapter?.setSelected(0)
            if (viewType != null) {
                if (viewType) {
                    // Highlight grid
                    hintAdapter?.setSelected(3)
                } else {
                    // Highlight large
                    hintAdapter?.setSelected(2)
                }
            } else {
                hintAdapter?.setSelected(3)
            }
        } else {
            hintAdapter?.setSelected(0)
        }
    }

    private fun setupSelectedListener() {
        this.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Timber.i("Selected pos $position")
                if (position != hintAdapter?.count) {
                    hintAdapter?.setSelected(position)
                    selectListener?.invoke(position)
                }
            }
        }
    }

    enum class HintMode {
        NORMAL, MULTI
    }

}