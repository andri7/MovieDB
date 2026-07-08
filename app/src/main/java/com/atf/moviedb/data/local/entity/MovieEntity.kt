package com.atf.moviedb.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey
    val id:Int,

    val genreId:Int,

    val title:String,

    val overview:String,

    val poster:String?,

    val releaseDate:String?,

    val rating:Double

)