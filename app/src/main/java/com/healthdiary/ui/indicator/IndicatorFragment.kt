package com.healthdiary.ui.indicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthdiary.R
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_indicator.*


class IndicatorFragment(indicatorId: Int) : Fragment() {

    private val layoutRes: Int = R.layout.fragment_indicator
    private val model: IndicatorViewModel =
        IndicatorViewModel(indicatorId, LocalDataSource)
    private val parametersMap = HashMap<Int, View>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model.loadNotes()
        return inflater.inflate(layoutRes, container, false)
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
            indicator.parameters?.let { parametersList ->
                if (parametersList.isEmpty()) {
                    return
                }
                parametersList.forEach {
                    parametersMap[it.id] = addSpinnerView(it)
                }
            }
            points?.let { updateChart(it) }
        }
        initChart()
    }

    private fun addSpinnerView(indicatorParameter: IndicatorParameter): View {

        val matchParent = LinearLayout.LayoutParams.MATCH_PARENT
        val wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT

        val parameterView = LinearLayout(context)
        parameterView.layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
        parameterView.orientation = LinearLayout.HORIZONTAL

        val title = TextView(context)
        title.layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
        title.text = indicatorParameter.title

        val spinner = Spinner(context)
        spinner.layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
        val adapter =
            context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    indicatorParameter.values
                )
            }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        parameterView.addView(title)
        parameterView.addView(spinner)

        parameters.addView(parameterView)

        return spinner
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