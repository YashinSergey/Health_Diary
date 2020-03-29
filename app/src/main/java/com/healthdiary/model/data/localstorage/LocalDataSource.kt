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

    override fun getNoteByDate(date: Date): Note? {
        notes.forEach {
            if(dateFormat.format(it.date) == dateFormat.format(date))
                return it
        }
        return null
    }

    private val data: MutableList<Indicator> = mutableListOf(
        Indicator(1, "Height", 180.0, "cm", 123),
        Indicator(2, "Weight", 80.0, "kg", 123),
        Indicator(3, "Sleep", 8.0, "h", 123),
        Indicator(4, "Indicator 1", 22.0, "custom", 123),
        Indicator(5, "Indicator 2", 33.0, "custom", 123),
        Indicator(6, "Indicator 3", 12.0, "custom", 123),
        Indicator(7, "Indicator 4", 68.0, "custom", 123),
        Indicator(8, "Indicator 4", 70.0, "custom", 123),
        Indicator(9, "Indicator 4", 69.0, "custom", 123)
    )

    private val ind4Data: MutableList<Indicator> = mutableListOf(
        Indicator(7, "Indicator 4", 68.0, "custom", 123),
        Indicator(8, "Indicator 4", 70.0, "custom", 123),
        Indicator(9, "Indicator 4", 69.0, "custom", 123)
    )

    val notes: MutableList<Note> = mutableListOf(
        Note(1, Date() , data, "ok"),
        Note(2, Date(), data, "everything bad, life is sucks")
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getNotesByIndicatorId(id: Int?): List<Note> {
        return arrayListOf(
            Note(1, GregorianCalendar(2020, 3, 1).time, ind4Data, "ok"),
            Note(1, GregorianCalendar(2020, 3, 2).time, ind4Data, "ok"),
            Note(1, GregorianCalendar(2020, 3, 3).time, ind4Data, "ok")
        )
    }

    override fun getIndicatorById(id: Int?): Indicator? {
        return Indicator(9, "Indicator 4", 69.0, "custom", 123)
    }
}