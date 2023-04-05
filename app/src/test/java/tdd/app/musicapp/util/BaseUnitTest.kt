package tdd.app.musicapp.util

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

open class BaseUnitTest {

    @Suppress("OPT_IN_USAGE")
    @get:Rule
    var coroutineTestRule = MainCoroutineScopeRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
}
