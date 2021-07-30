package com.soares.task

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
abstract class BaseTest {
	protected lateinit var coroutineContextMock: CoroutineContext

	@get:Rule
	val rule: TestRule = InstantTaskExecutorRule()

	@get:Rule
	var coroutineTestRule = CoroutineTestRule()

	@Before
	open fun setup() {
		coroutineContextMock = coroutineTestRule.testDispatcher
	}
}