package com.healthdiary.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home_rv.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeRVAdapter(val repository: Repository, private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<HomeRVAdapter.ViewHolder>(), CoroutineScope {
    
    override val coroutineContext: CoroutineContext = Dispatchers.IO


    companion object{
        var itemsList: List<Indicator>? = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_home_rv,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = itemsList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsList?.let { it[position] }
        holder.bind(item)
        holder.itemView.setOnClickListener { item?.let { listener(it.id) } }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entity: Indicator?) {
            tv_indicator_title.text = entity?.title
            launch {
                repository.getLastValueByIndicatorId(entity?.id).consumeEach { entity ->
                    tv_indicators_value.text = "${entity?.let { it.value } ?: 0}"
                    }
                }
            }
        }
    }