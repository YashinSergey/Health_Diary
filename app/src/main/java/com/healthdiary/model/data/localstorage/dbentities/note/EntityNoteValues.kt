package com.healthdiary.model.data.localstorage.dbentities.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "note_values")
class EntityNoteValues (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "note_id") val noteID : Int,
    @ColumnInfo(name = "indicator_value_id")val indicatorValueId : Int,
    @ColumnInfo(name = "value")val value : Float
)