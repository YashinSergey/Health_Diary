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
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthdiary.R
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.IndicatorParameter
import com.healthdiary.ui.indicator.adapters.IndicatorRVAdapter
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_indicator.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class IndicatorFragment : Fragment() {

    private val model: IndicatorViewModel by viewModel()
    private val fragmentArgs: IndicatorFragmentArgs by navArgs()
    private val parametersMap = HashMap<Int, Spinner>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_indicator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val indicatorId = fragmentArgs.indicatorId
        tv_date.text = dateFormat.format(Date())
        ti_current_measure.setEndIconOnClickListener {
            if (current_measure.text.isNullOrEmpty()) {
                return@setEndIconOnClickListener
            }
            val parametersList: MutableList<Pair<Int, String>> = ArrayList<Pair<Int, String>>()
            parametersMap.forEach {
                parametersList.add(Pair(it.key, it.value.selectedItem.toString()))
            }
            if (model.saveNote(
                    indicatorId, listOf(current_measure.text.toString().toFloat()),
                    parametersList
                )
            ) {
                current_measure.text!!.clear()
            }
        }
        model.loadIndicatorInfo(indicatorId)
        model.loadNotes(indicatorId)
        model.indicatorViewState.observe(viewLifecycleOwner, Observer {
            initView(it)
        })
        model.chartViewState.observe(viewLifecycleOwner, Observer {
            updateChart(it)
        })
    }

    private fun initView(indicator: Indicator?) {
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
            initAdapter(indicator.parameters)
        }
        initChart()
    }

    private fun addSpinnerView(indicatorParameter: IndicatorParameter): Spinner {

        val matchParent = LinearLayout.LayoutParams.MATCH_PARENT
        val wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT

        val parameterView = LinearLayout(context)
        parameterView.layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
        parameterView.orientation = LinearLayout.HORIZONTAL
        val screenDensity = context?.resources?.displayMetrics?.density ?: 0f
        (parameterView.layoutParams as ViewGroup.MarginLayoutParams).topMargin =
            4 * screenDensity.toInt()

        val title = TextView(context)
        title.layoutParams = LinearLayout.LayoutParams(wrapContent, wrapContent)
        title.text = indicatorParameter.title

        val spinner = Spinner(context)
        spinner.layoutParams = LinearLayout.LayoutParams(matchParent, wrapContent)
        val adapter =
            context?.let {
                ArrayAdapter(
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
        chart.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        chart.gridLabelRenderer.numHorizontalLabels = 3
        chart.gridLabelRenderer.numVerticalLabels = 4
        chart.gridLabelRenderer.setHumanRounding(false)
    }

    private fun updateChart(points: Array<DataPoint>) {
        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(points)
        chart.removeAllSeries()
        chart.addSeries(series)
        if (points.isEmpty()) return

        chart.viewport.setMinX(points[0].x)
        chart.viewport.setMaxX(points[points.size - 1].x)
        chart.viewport.isXAxisBoundsManual = true
    }

    private fun initAdapter(parameters: List<IndicatorParameter>?) {
        val adapter = IndicatorRVAdapter(parameters)
        model.rvViewState.observe(viewLifecycleOwner, Observer {
            adapter.itemsList = it
            adapter.notifyDataSetChanged()
        })
        initRecycler(adapter)
    }

    private fun initRecycler(adapter: IndicatorRVAdapter) {
        rv_indicator.layoutManager = LinearLayoutManager(this.context)
        rv_indicator.adapter = adapter
    }
}