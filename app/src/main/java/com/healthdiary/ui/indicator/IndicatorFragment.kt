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
import com.healthdiary.model.entities.ParameterValues
import com.healthdiary.ui.indicator.adapters.IndicatorRVAdapter
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_indicator.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class IndicatorFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Default

    private val model: IndicatorViewModel by viewModel()
    private val fragmentArgs: IndicatorFragmentArgs by navArgs()
    private val parametersMap = HashMap<Int, Spinner>()
    private lateinit var  currentIndicator : Indicator

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
        model.loadIndicatorInfo(indicatorId)
        model.loadNotes(indicatorId)
        model.indicatorViewState.observe(viewLifecycleOwner, Observer {
            initView(it)
        })
        model.chartViewState.observe(viewLifecycleOwner, Observer {
            updateChart(it)
            Timber.d("Size DP for chart ${it.size}")
        })
        ti_current_measure.setEndIconOnClickListener {
            if (current_measure.text.isNullOrEmpty()) {
                return@setEndIconOnClickListener
            }
            val parametersList: MutableList<IndicatorParameter> = ArrayList<IndicatorParameter>()
            parametersMap.forEach {
                parametersList.add(IndicatorParameter(id = it.key, title = "", values = listOf(ParameterValues(id = null, value = it.value.selectedItem.toString()))))
            }
            launch {
                if (model.saveNote(
                        currentIndicator, listOf(current_measure.text.toString().toFloat()),
                        parametersList
                    )
                ) {
                    current_measure.text!!.clear()
                }
            }
        }
    }

    private fun initView(indicator: Indicator?) {
        Timber.d("Start initView")
        indicator?.let {
            currentIndicator = it
            indicator_title.text = indicator.title

            indicator.parameters?.let { parametersList ->
                if (parametersList.isEmpty()) {
                    Timber.d("Indicator.parameters is empty")
                    return
                }
                parametersList.forEach {param ->
                    for(value in param.values){
                        Timber.d("Value contains ${value.value}")
                    }
                    param.id.let { parametersMap[id] = addSpinnerView(param) }
                }
            } ?: Timber.d("Indicator.parameters is null")
            initAdapter(indicator.parameters)
        }
//        initChart()
        Timber.d("Over initView")
    }

    private fun addSpinnerView(indicatorParameter: IndicatorParameter): Spinner {
        Timber.d("Spinner start init")
        val adapter =
            context?.let {
                val values = mutableListOf<String>()
                    indicatorParameter.values.forEach{
                        Timber.d("Spinner add indicatorParameter.values.${it.value}")
                        values.add(it.value)
                    }
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    values
                )
            }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        return spinner
    }

    private fun initChart() {
        Timber.d("Start initChart")
        chart.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity)
        chart.gridLabelRenderer.numHorizontalLabels = 3
        chart.gridLabelRenderer.numVerticalLabels = 4
        chart.gridLabelRenderer.setHumanRounding(false)
        Timber.d("Over initChart")
    }

    private fun updateChart(points: Array<DataPoint>) {
        Timber.d("Start update chart")
        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(points)
        chart.removeAllSeries()
        chart.addSeries(series)
        if (points.isEmpty()) return

        chart.viewport.setMinX(points[0].x)
        chart.viewport.setMaxX(points[points.size - 1].x)
        chart.viewport.isXAxisBoundsManual = true
        Timber.d("Over update chart")

    }

    private fun initAdapter(parameters: List<IndicatorParameter>?) {
        Timber.d("Start  initadapter")
        val adapter = IndicatorRVAdapter(parameters)
        model.rvViewState.observe(viewLifecycleOwner, Observer {
            adapter.itemsList = it
            adapter.notifyDataSetChanged()
        })
        initRecycler(adapter)
        Timber.d("Over  initadapter")
    }

    private fun initRecycler(adapter: IndicatorRVAdapter) {
        Timber.d("Start  initRecycler")
        rv_indicator.layoutManager = LinearLayoutManager(this.context)
        rv_indicator.adapter = adapter
        Timber.d("Over  initRecycler")

    }
}