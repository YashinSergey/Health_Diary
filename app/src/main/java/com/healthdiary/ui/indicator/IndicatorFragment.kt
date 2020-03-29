package com.healthdiary.ui.indicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthdiary.R
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.entities.Indicator
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_entity.*


class IndicatorFragment : Fragment() {
    companion object {
        private val EXTRA_INDICATOR = IndicatorFragment::class.java.name + "extra.indicator"
    }

    private val layoutRes: Int = R.layout.fragment_entity
    private val model: IndicatorViewModel = IndicatorViewModel(LocalDataSource)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noteId: Int? = this.arguments?.getInt(EXTRA_INDICATOR)
        noteId?.let {
            model.loadNotes(noteId)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.viewState.observe(viewLifecycleOwner, Observer {
            initView(it.first, it.second)
        })
    }

    private fun initView(indicator: Indicator?, points: Array<DataPoint>?) {
        indicator?.let {
            indicator_title.text = indicator.title
            points?.let {
                updateChart(it)
            }
        }
    }

    private fun updateChart(points: Array<DataPoint>?) {
        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(points)
        chart.addSeries(series)
    }


}