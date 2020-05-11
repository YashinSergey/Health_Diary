package com.healthdiary.ui.indicator.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.model.entities.Note
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.android.synthetic.main.notes_header.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class IndicatorRVAdapter(private val parameters: List<IndicatorParameter>?) :
    RecyclerView.Adapter<IndicatorRVAdapter.ViewHolder>() {
    private val typeHeader = 0
    private val typeItem = 1
    var itemsList: List<Note> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = R.layout.item_note
        if (viewType == typeHeader) {
            layout = R.layout.notes_header
        }
        return ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return typeHeader
        }
        return typeItem
    }

    override fun getItemCount(): Int = itemsList.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == typeHeader) {
            holder.bind(parameters)
        } else {
            val item = itemsList.let { it[it.size - position] }
            holder.bind(item)
        }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entity: Note?) {
            entity?.let { note ->
                value.text = String.format("%.1f %s", note.value, note.indicator.unit)
                time.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(entity.date)
                parameters.forEach {
                    parameters.removeView(it)
                }
                note.parameters?.forEach {
                    val view = createTextView(it.title, R.style.RVHistoryColumn)
                    parameters.addView(view)
                }
            }
        }

        fun bind(parameters: List<IndicatorParameter>?) {
            header_parameters.forEach {
                header_parameters.removeView(it)
            }
            parameters?.forEach {
                val view = createTextView(it.title, R.style.RVHistoryColumnTitle)
                header_parameters.addView(view)
            }
        }

        private fun createTextView(text: String, textStyle: Int): TextView {
            val wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT
            val view = TextView(containerView.context)
            view.layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent, 1F)
            view.text = text
            if (Build.VERSION.SDK_INT < 23) {
                view.setTextAppearance(containerView.context, textStyle)
            } else {
                view.setTextAppearance(textStyle)
            }
            return view
        }
    }
}