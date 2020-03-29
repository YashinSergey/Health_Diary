package com.healthdiary.model.data.localstorage

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object LocalDataSource : Repository {

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat("dd MMMM yyyy")

    private val indicators: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", "cm", 123),
        Indicator(2, "Weight", "kg", 123),
        Indicator(3, "Sleep", "h", 123),
        Indicator(4, "Indicator 1", "custom", 123),
        Indicator(5, "Indicator 2", "custom", 123),
        Indicator(6, "Indicator 3", "custom", 123),
        Indicator(7, "Indicator 4", "custom", 123)
    )

    private val notesForOneDay: MutableList<Note> = mutableListOf(
        Note(7, Date(), indicators[0], 68f, "custom"),
        Note(8, Date(), indicators[1], 70f, "custom"),
        Note(9, Date(), indicators[2], 69f, "custom")
    )

    private val notesOfIndicator: MutableList<Note> = mutableListOf(
        Note(1, Date(), indicators[1], 60f, "ok"),
        Note(2, Date(), indicators[1], 61f, "everything bad, life is sucks"),
        Note(2, Date(), indicators[1], 60f, "everything bad, life is sucks"),
        Note(2, Date(), indicators[1], 59f, "everything bad, life is sucks")
    )

    override fun getNotesByDate(date: Date): List<Note> {
        return notesForOneDay
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNotesByIndicatorId(id: Int?): List<Note> {
        return notesOfIndicator
    }

    override fun getIndicatorById(id: Int?): Indicator? {
        return indicators[1]
    }
}