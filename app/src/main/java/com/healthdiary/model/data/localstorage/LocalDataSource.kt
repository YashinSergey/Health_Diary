package com.healthdiary.model.data.localstorage

import android.annotation.SuppressLint
import com.healthdiary.model.data.repositories.Repository
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import java.text.SimpleDateFormat
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
        Indicator(1, "Height", "180", "cm", 123),
        Indicator(2, "Weight", "80", "kg", 123),
        Indicator(3, "Sleep", "8", "h", 123),
        Indicator(4, "Indicator 1","22", "custom", 123),
        Indicator(5, "Indicator 2","33", "custom", 123),
        Indicator(6, "Indicator 3", "12", "custom", 123),
        Indicator(7, "Indicator 4",  "68", "custom", 123)
    )

    val notes: MutableList<Note> = mutableListOf(
        Note(1, Date() , data, "ok"),
        Note(2, Date(), data, "everything bad, life is sucks")
    )
}