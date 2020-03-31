package com.healthdiary.ui.home.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import com.healthdiary.ui.indicator.IndicatorFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_item_home_rv.*
import timber.log.Timber
import java.util.*
import kotlin.collections.*
import kotlin.random.Random

class HomeRVAdapter(val repository: Repository) :
    RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {

    interface OnClickListener

    var elementList: List<Indicator>? = ArrayList()
    val holders: ArrayList<ViewHolder> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_item_home_rv,
                parent,
                false
            )
        )
        holders.add(viewHolder)
        return viewHolder
    }

    override fun getItemCount(): Int = elementList?.let { it.size } ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(elementList?.let { it[position] })
        val indicatorId : Int = elementList?.let { it[position].id }!!
        holder.containerView.setOnClickListener { view ->
            val indicatorFragment = IndicatorFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        "IndicatorId",
                        indicatorId
                    )
                }
            }
            Timber.d("IndicatorID put: ${indicatorId}")
            val activity: AppCompatActivity = view.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.container, indicatorFragment).addToBackStack("HOME").commit()
        }

    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entity: Indicator?) {

//            img_icon.setImageResource(entity.icon)
            txtv_indicators_title.text = entity?.title
            val value : Float = repository.getNotesByIndicatorId(entity?.id)?.let { it[it.size-1].value }
            txtv_indicators_value.text = value.toString()
            btn_save.setOnClickListener { view ->
                val value = txtv_indicators_value.text.toString().toFloat()
                val note = Note(Random.nextInt(0, 100000), Date(), entity!!, value, "custom")
                repository.saveNote(note)
                Timber.d("Save new Note. List notesBase now have ${LocalDataSource.getBaseNote().size} elements")
            }
        }
    }
}