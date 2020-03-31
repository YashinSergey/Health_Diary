package com.healthdiary.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_item_home_rv.*

class HomeRVAdapter(val repository: Repository) : RecyclerView.Adapter<HomeRVAdapter.ViewHolder>() {

    var itemsList: List<Indicator>? = ArrayList()
    var itemClickSubject = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item_home_rv, parent, false))

    override fun getItemCount(): Int = itemsList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemsList?.let { it[position] })
        val itemId : Int = itemsList?.let { it[position].id }!!
        holder.containerView.setOnClickListener { itemClickSubject.onNext(itemId) }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entity: Indicator?) {
            tv_indicator_title.text = entity?.title
            tv_indicators_value.text = String.format(
                "${repository.getNotesByIndicatorId(entity?.id).let { it[it.size-1].value }}")
        }
    }
}