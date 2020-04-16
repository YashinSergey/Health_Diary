package com.healthdiary.model.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicatorValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.EntityIndicator
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityParameterValues
import com.healthdiary.model.data.localstorage.dbentities.indicator.parameter.EntityIndicatorParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNote
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteParameters
import com.healthdiary.model.data.localstorage.dbentities.note.EntityNoteValues

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

    @Query("SELECT id FROM indicator_parameters WHERE indicator_id == :indicatorId")
    fun getIndicatorParametersID(indicatorId : Int) : Int

    @Query("SELECT id FROM indicator_values WHERE indicator_id == :indicatorId")
    fun getIdIndicatorValuesByIndicatorId(indicatorId : Int) : List<Int>

    @Insert()
    fun saveNote(noteEntry : EntityNote)

    @Insert()
    fun saveNoteValues(noteValuesEntry : EntityNoteValues)

    @Insert()
    fun saveNoteParameters(noteParameters: EntityNoteParameters)

    @Insert()
    fun saveIndicator(indicatorEntry : EntityIndicator)

    @Insert()
    fun saveIndicatorValue(indicatorValueEntry : EntityIndicatorValues)

    @Insert()
    fun saveIndicatorParameters(indicatorParameterEntry : EntityIndicatorParameters)

    @Insert()
    fun saveParameterValues(parameterValuesEntry : EntityParameterValues)




}