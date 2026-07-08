package com.atf.moviedb.core.data

import com.atf.moviedb.core.state.AppState
import com.atf.moviedb.core.utils.ErrorMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

inline fun <ResultType, RequestType> offlineFirstResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
): Flow<AppState<ResultType>> = flow {

    emit(AppState.Loading)

    val localData = query().first()

    if (!shouldFetch(localData)) {
        emit(
            AppState.Success(
                data = localData,
                fromCache = true
            )
        )
        return@flow
    }

    emit(
        AppState.Refreshing(
            data = localData
        )
    )

    try {

        val response = fetch()

        withContext(Dispatchers.IO) {
            saveFetchResult(response)
        }

        query().collect {
            emit(
                AppState.Success(
                    data = it,
                    fromCache = false,
                    lastUpdated = System.currentTimeMillis()
                )
            )
        }

    } catch (e: Throwable) {

        emit(
            AppState.Error(
                ErrorMapper.map(e)
            )
        )

        emit(
            AppState.Offline(
                data = localData
            )
        )
    }

}.flowOn(Dispatchers.IO)