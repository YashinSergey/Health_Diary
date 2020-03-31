package com.healthdiary.ui.indicator

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.healthdiary.R
import com.healthdiary.model.data.localstorage.LocalDataSource
import com.healthdiary.model.entities.Indicator
import com.healthdiary.ui.MainActivity
import com.healthdiary.ui.viewmodel.IndicatorViewModel
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.fragment_entity.*
import timber.log.Timber


class IndicatorFragment : Fragment() {
    companion object {
        private val EXTRA_INDICATOR = IndicatorFragment::class.java.name + "extra.indicator"
        fun start(activity: MainActivity, indicatorId: Int? = null) =
            Intent(activity, IndicatorFragment::class.java).run {
                val fragment = IndicatorFragment()
                indicatorId?.let {
                    val bundle = Bundle()
                    bundle.putInt(EXTRA_INDICATOR, indicatorId)
                    fragment.arguments = bundle
                }
                activity.displayFragment(fragment)
            }
    }

    private val layoutRes: Int = R.layout.fragment_entity
    private val model: IndicatorViewModel = IndicatorViewModel(arguments?.getInt("IndicatorId"),LocalDataSource)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val indicatorId = arguments?.getInt("IndicatorId")
        model.loadNotes(indicatorId)
        Timber.d("Fragment get int from Bundle: $indicatorId")
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val indicatorId: Int? = this.arguments?.getInt(EXTRA_INDICATOR)
        indicatorId?.let {
            model.loadNotes(indicatorId)
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
        initChart()
    }

    private fun initChart() {
        // set date label formatter
        chart.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(activity);
        chart.gridLabelRenderer.numHorizontalLabels = 3; // only 4 because of the space

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
        chart.gridLabelRenderer.setHumanRounding(false);
    }

    private fun updateChart(points: Array<DataPoint>) {
        val series: LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>(points)
        chart.addSeries(series)
        // set manual x bounds to have nice steps
        if (points.isEmpty()) return

        chart.viewport.setMinX(points[0].x);
        chart.viewport.setMaxX(points[points.size - 1].x);
        chart.viewport.isXAxisBoundsManual = true;
    }


}