package com.healthdiary.model.data.localstorage.dbentities.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_parameters")
class EntityNoteParameters (
    @PrimaryKey (autoGenerate = true) val id : Int? = null,
    @ColumnInfo(name = "note_id") val noteId : Int,
    @ColumnInfo(name = "parameter_id") val parameterId : Int,
    @ColumnInfo(name = "parameter_valueid") val parameterValueId : Int
)