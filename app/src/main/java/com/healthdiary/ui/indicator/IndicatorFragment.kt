package com.healthdiary.ui.indicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.healthdiary.R
import com.healthdiary.model.entities.Indicator
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_entity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndicatorFragment : Fragment() {

    private val model: IndicatorViewModel by viewModel()
    private val fragmentArgs: IndicatorFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val indicatorId = fragmentArgs.indicatorId
        model.loadNotes(indicatorId)
        model.viewState.observe(viewLifecycleOwner, Observer {
            initView(it.first, it.second)})
        return inflater.inflate(R.layout.fragment_entity, container, false)
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
        chart.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        chart.gridLabelRenderer.numHorizontalLabels = 3
        chart.gridLabelRenderer.setHumanRounding(false)
    }

    private fun updateChart(points: Array<DataPoint>) {
        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(points)
        chart.addSeries(series)
        if (points.isEmpty()) return

        chart.viewport.setMinX(points[0].x)
        chart.viewport.setMaxX(points[points.size - 1].x)
        chart.viewport.isXAxisBoundsManual = true
    }
}