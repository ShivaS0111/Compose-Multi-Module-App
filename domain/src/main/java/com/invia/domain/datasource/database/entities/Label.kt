package com.invia.domain.datasource.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "labels")
data class Label(
    @PrimaryKey(autoGenerate = true)
    val labelId: Long? = null,

    @SerializedName("label")
    var label: String,

    @SerializedName("color")
    var color: String? = null,

    @SerializedName("textColor")
    var textColor: String? = null,

    )