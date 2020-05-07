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
import androidx.navigation.fragment.navArgs
import com.healthdiary.R
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_indicator.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class IndicatorFragment : Fragment() {


    private val model: IndicatorViewModel by viewModel()
    private val fragmentArgs: IndicatorFragmentArgs by navArgs()
    private val parametersMap = HashMap<Int, View>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_indicator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        consumeViewState(fragmentArgs.indicatorId)
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

        val values : MutableList<String> = mutableListOf()
        for(parameterValues in indicatorParameter.values){
            values.add(parameterValues.value)
        }
        val adapter  = context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    values
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

    private fun consumeViewState(indicatorId : Int){
        model.indicatorId = indicatorId
        model.launch {
            model.viewState.consumeEach {
                initView(it.first, it.second)
            }
        }
    }
}