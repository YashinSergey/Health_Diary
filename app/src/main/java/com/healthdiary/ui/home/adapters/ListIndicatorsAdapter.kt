package com.healthdiary.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.model.entities.Indicator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_item.*

class ListIndicatorsAdapter(private val elementList : ArrayList<Indicator>) : RecyclerView.Adapter<ListIndicatorsAdapter.ViewHolder>() {


    val holders : ArrayList<ListIndicatorsAdapter.ViewHolder> = ArrayList<ListIndicatorsAdapter.ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent,false)
        )
        holders.add(vh)

        return vh
    }

    override fun getItemCount(): Int = elementList.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(elementList[position])

    }



    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(entity : Indicator){
//            img_indicators_icon.setImageResource(entity.icon)
            txtv_indicators_title.text = entity.title
            val value = ": " + entity.values?.let { it[it.size-1].second }
            txtv_value.text = value                                                   // Если запихиваем в конец, конечно
            ll_item.setOnClickListener(View.OnClickListener {
                Toast.makeText(containerView.context, "Fraagment ${entity.title}", Toast.LENGTH_SHORT).show()
                //TODO вызов фрагмента
            })
        }


    }
}