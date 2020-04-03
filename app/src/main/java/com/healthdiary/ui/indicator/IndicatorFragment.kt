package com.healthdiary.ui.indicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthdiary.R
import com.healthdiary.model.entities.Indicator
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_entity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndicatorFragment : Fragment() {

    private val layoutRes: Int = R.layout.fragment_entity
    private val model: IndicatorViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val indicatorId = arguments?.getInt("IndicatorId")
        model.loadNotes(indicatorId)
        model.viewState.observe(viewLifecycleOwner, Observer {
            initView(it.first, it.second)})
        return inflater.inflate(layoutRes, container, false)
    }

    private fun initView(indicator: Indicator?, points: Array<DataPoint>?) {
        indicator?.let {
            indicator_title.text = indicator.title
            points?.let {
                updateChart(it)
            }
        }
        initChart()
    }

    private fun initChart() {
        // set date label formatter
        chart.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        chart.gridLabelRenderer.numHorizontalLabels = 3 // only 4 because of the space

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        chart.gridLabelRenderer.setHumanRounding(false)
    }

    private fun updateChart(points: Array<DataPoint>) {
        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(points)
        chart.addSeries(series)
        // set manual x bounds to have nice steps
        if (points.isEmpty()) return

        chart.viewport.setMinX(points[0].x)
        chart.viewport.setMaxX(points[points.size - 1].x)
        chart.viewport.isXAxisBoundsManual = true
    }
}