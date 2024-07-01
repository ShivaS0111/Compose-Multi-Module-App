package com.example.test_movie_app

import com.invia.data.datasource.network.datasource.MoviesDataSourceImpl
import com.invia.domain.datasource.network.datasource.MoviesDataSource
import com.invia.domain.model.ShowsResponseItem
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityTest {

    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        dataSource = MoviesDataSourceImpl(mockk())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testSomeUI(): Unit = runBlocking {
        val job = async (Dispatchers.Main) {
            dataSource.getTvShows()
        }

        val data = job.await()
        println(data.message)
        assertNotNull( data.message )
    }

    @Test
    fun testTakingALongTime() = runTest(timeout = 30.seconds) {
        val result = withContext(Dispatchers.Default) {
            delay(5.seconds) // this delay is not in the test dispatcher and will not be skipped
            3
        }
        assertEquals(3, result)
    }


    private lateinit var dataSource: MoviesDataSource

    private  val data: List<ShowsResponseItem> by lazy {
        arrayListOf(
            ShowsResponseItem(id = 1, name = "Movie 1", image = null, language = null),
            ShowsResponseItem(id = 2, name = "Movie 2", image = null, language = null),
            ShowsResponseItem(id = 3, name = "Movie 3", image = null, language = null)
        )
    }
}