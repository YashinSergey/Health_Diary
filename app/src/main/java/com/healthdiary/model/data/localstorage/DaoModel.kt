package com.healthdiary.model.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote

@Dao
interface DaoModel {
    @Query("SELECT * FROM indicators")
    fun getIndicatorsList()  : List<EntityIndicator>

    @Query("SELECT * FROM indicators WHERE id == :indicatorId")
    fun getIndicatorById(indicatorId : Int) : EntityIndicator

    @Query("SELECT * FROM notes WHERE indicator_id == :indicatorId")
    fun getNotesListByIndicatorId(indicatorId : Int) : List<EntityNote>

    @Query("SELECT * FROM notes WHERE date == :date")
    fun getNotesByDate(date : Long) : List<EntityNote>

//    @Query("SELECT * FROM notes WHERE indicator_id == :indicator ORDER BY date DESC LIMIT 1")
//    fun getCurrentIndicatorsValue(indicator : Indicator) : Float

//    @Query("SELECT TOP (1) FROM EntityUser")
//    fun getProfileInfo() : EntityUser

    @Insert()
    fun saveNote(noteEntry : EntityNote)


}