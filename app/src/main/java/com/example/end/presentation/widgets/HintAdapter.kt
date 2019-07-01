package com.example.end.presentation.widgets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.end.R
import timber.log.Timber

class HintAdapter(
    context: Context,
    resource: Int,
    private val mode: HintSpinner.HintMode,
    var spinnerItems: List<String>
) :
    ArrayAdapter<String>(
        context,
        resource,
        spinnerItems
    ) {

    var selectedPos: Int = spinnerItems.size
    var selectedPos2: Int = spinnerItems.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return hintSpinnerView(parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return if (mode == HintSpinner.HintMode.NORMAL) {
            normalSpinnerView(position, parent)
        } else {
            multiSpinnerView(position, parent)
        }
    }

    private fun normalSpinnerView(position: Int, parent: ViewGroup): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = layoutInflater.inflate(R.layout.spinner_list_item, parent, false)
        val textView = customView.findViewById(R.id.text_view_spinner) as TextView
        if (position != spinnerItems.size - 1 && position == selectedPos) {
            customView.setBackgroundColor(ContextCompat.getColor(context, R.color.highlightColor))
        }
        textView.text = getItem(position)
        return customView
    }

    private fun multiSpinnerView(position: Int, parent: ViewGroup): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = layoutInflater.inflate(R.layout.spinner_list_item, parent, false)
        val textView = customView.findViewById(R.id.text_view_spinner) as TextView
        if (position == selectedPos || position == selectedPos2) {
            customView.setBackgroundColor(ContextCompat.getColor(context, R.color.highlightColor))
        }
        val border = customView.findViewById(R.id.border) as View
        if (position == 1) {
            border.visibility = View.VISIBLE
        }
        textView.text = getItem(position)
        return customView
    }

    private fun hintSpinnerView(parent: ViewGroup): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val customView = layoutInflater.inflate(R.layout.spinner_list_item, parent, false)
        val textView = customView.findViewById(R.id.text_view_spinner) as TextView
        textView.text = spinnerItems.last()
        return customView
    }

    fun updateList(items: List<String>) {
        selectedPos = items.size
        this.clear()
        this.addAll(items)
        this.notifyDataSetInvalidated()
    }

    fun setSelected(pos: Int) {
        if (mode == HintSpinner.HintMode.NORMAL) {
            selectedPos = pos
        } else {
            Timber.i("Selecting position $pos")
            if (pos == 0 || pos == 1) {
                selectedPos = pos
            } else {
                selectedPos2 = pos
            }
        }
    }

    override fun getCount(): Int = spinnerItems.size - 1

    override fun getItem(position: Int): String = spinnerItems[position]

    override fun getItemId(position: Int): Long = position.toLong()

}