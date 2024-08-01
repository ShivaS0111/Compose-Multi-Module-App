package com.example.test_movie_app.noteParser

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/*
class JsonEditorParser : EditorAdapter {

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(RichTextSpan::class.java, RichTextSpanAdapter())
        .create()

    override fun encode(input: String): RichText {
        return gson.fromJson(input, object : TypeToken<RichText>() {}.type)
    }

    override fun decode(editorValue: RichText): String {
        return gson.toJson(editorValue)
    }
}*/
