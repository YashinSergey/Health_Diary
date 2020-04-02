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

    @Query("SELECT * FROM EntityNote WHERE indicatorId == :indicatorId")
    fun getNotesListByIndicatorId(indicatorId : Int) : List<Note>

    @Query("SELECT * FROM EntityNote WHERE date == :date")
    fun getNotesByDate(date : Date) : Note

    @Query("SELECT ")
    fun getCurrentIndicatorsValue(indicatorId : Int) : Float

    @Query("SELECT TOP (1) FROM EntityUser")
    fun getProfileInfo() : User

    @Insert()
    fun saveNote() : Unit


}