package com.atf.moviedb.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.ImageLoader
import coil.compose.AsyncImage
import org.koin.compose.koinInject

@Composable
fun MovieImage(
    poster:String?,
    modifier:Modifier = Modifier,
    cScale: ContentScale = ContentScale.Fit,
    contentDescription:String? = null,
    imageLoader:ImageLoader = koinInject()
){

    AsyncImage(
        model = poster,
        imageLoader = imageLoader,
        contentDescription = contentDescription,
        contentScale = cScale,
        modifier = modifier
    )
}