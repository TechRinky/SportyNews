package com.example.sportynews

import com.example.sportynews.domain.useCase.GetNewsHeadlinesUseCase
import com.example.sportynews.presentation.NewsViewModel
import com.example.sportynews.sealedclasses.NewsUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: NewsViewModel
    private lateinit var fakeRepository: FakeNewsRepository
    private lateinit var useCase: GetNewsHeadlinesUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeNewsRepository()
        useCase = GetNewsHeadlinesUseCase(fakeRepository)
        viewModel = NewsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `loadNextPage emits Success state`() = runTest {
        fakeRepository.shouldFail = false

        viewModel.loadNextPage()

        // Move time forward to ensure coroutines run
        advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is NewsUiState.Success)
    }

    @Test
    fun `loadNextPage emits Error state on failure`() = runTest {
        fakeRepository.shouldFail = true

        viewModel.loadNextPage()
        advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertTrue(state is NewsUiState.Error)
    }
}