package com.healthdiary.model.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import com.healthdiary.model.entities.User
import java.util.*

@Dao
interface DaoModel {
    @Query("SELECT * FROM EntityIndicator")
    fun getIndicatorsList()  : List<Indicator>

    @Query("SELECT * FROM EntityIndicator WHERE id == :indicatorId")
    fun getIndicatorById(indicatorId : Int) : Indicator

    @Query("SELECT * FROM EntityNote WHERE indicator == :indicatorId")
    fun getNotesListByIndicatorId(indicatorId : Int) : List<Note>

    @Query("SELECT * FROM EntityNote WHERE date == :date")
    fun getNotesByDate(date : Date) : Note

    @Query("SELECT * FROM EntityNote WHERE indicator == :indicator ORDER BY date DESC LIMIT 1")
    fun getCurrentIndicatorsValue(indicator : Indicator) : Float

    @Query("SELECT TOP (1) FROM EntityUser")
    fun getProfileInfo() : User

    @Insert()
    fun saveNote() : Unit


}