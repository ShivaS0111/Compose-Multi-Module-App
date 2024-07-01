package com.invia.domain.datasource.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date


@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long? = null,

    @SerializedName("title")
    var title: String,

    @SerializedName("note")
    var note: String,

    @SerializedName("createdAt")
    val dateTime: Date? = Date()
)