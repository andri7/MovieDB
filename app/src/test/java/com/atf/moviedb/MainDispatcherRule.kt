package com.atf.moviedb

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainDispatcherRule(
    private val dispatcher: TestDispatcher =
        StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(
        description: Description
    ) {
        Dispatchers.setMain(
            dispatcher
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(
        description: Description
    ) {
        Dispatchers.resetMain()
    }
}