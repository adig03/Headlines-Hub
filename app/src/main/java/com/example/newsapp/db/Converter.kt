package com.example.newsapp.db

import androidx.room.TypeConverters
import com.example.newsapp.util.Source

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name!!
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}
