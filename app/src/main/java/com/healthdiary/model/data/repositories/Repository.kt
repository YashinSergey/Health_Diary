package com.healthdiary.model.data.repositories

import com.healthdiary.model.entities.Indicator
import com.healthdiary.model.entities.Note
import java.util.*

interface Repository {
    //region Note
    fun getLastNoteByIndicatorId(indicatorId: Int) : Note
    fun getNoteByNoteId(noteId : Int)
    fun getNotesByDate(date: Date) : List<Note>
    fun getNotesByIndicatorId(indicatorId: Int) : List<Note>
    fun saveNote(note : Note)
    //endregion

    //region NoteValues
    fun getyNoteValuesByNoteId(noteId : Int) : List<Float>
    fun saveNoteValueByNoteId(value : Float)
    fun getIndicatorValueIdByNoteId(noteId : Int)
    //endregion

    //regiom NoteParameters
    fun getNoteParametersByNoteId(noteId :  Int)
    fun saveNoteParametersByNoteId()
    //endregion

    //region Indicator
    fun getIndicatorById(id: Int?) : Indicator?
    fun getIndicatorList() : List<Indicator>

    //endregion


}