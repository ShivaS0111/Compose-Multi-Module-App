package com.example.test_movie_app.presentation.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_movie_app.presentation.viewModels.stateHolders.MovieStateHolder
import com.invia.domain.common.Result
import com.invia.domain.model.ShowsResponseItem
import com.invia.domain.useCases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MoviesViewModel @Inject constructor(val useCase: GetMoviesUseCase) : ViewModel() {

    private var _response = mutableStateOf(MovieStateHolder())

    val response: State<MovieStateHolder>
        get() = _response

    fun collectData()= response

    fun getAllTvShows() {
        viewModelScope.launch {
            /*  useCase().run {
                if(this.isSuccessful){
                    _response.value= this.body()!!
                }else{
                    Log.d("tag", "getAllTvShows Error: ${this.code()}")
                }
            }*/
            useCaseInvoke().collect {
                when (it) {
                    is Result.Loading -> {
                        _response.value = MovieStateHolder(isLoading = true)
                    }

                    is Result.Success<*> -> {
                        if ( it.data is List)
                            _response.value = MovieStateHolder(data = it.data )
                    }

                    is Result.Error -> {
                        _response.value = MovieStateHolder(error = it.message.toString())
                    }
                }

            }
        }
    }

    suspend fun useCaseInvoke():Flow<Result<List<ShowsResponseItem>>> = useCase.invoke()


    @OptIn(DelicateCoroutinesApi::class)
    fun onCoroutineTest() {
        val job = GlobalScope.launch(Dispatchers.Main) {
            println("onCoroutineTest:1 This is executed before the first delay")
            stallForTime1(1)
            println("onCoroutineTest:1 This is executed after the first delay")
        }
        GlobalScope.launch(Dispatchers.Main) {
            println("onCoroutineTest:2 This is executed at the start of the second coroutine")
            job.cancelAndJoin()
            println("onCoroutineTest:2 This is executed before the second delay")
            stallForTime1(2)
            println("onCoroutineTest:2 This is executed after the second delay")
        }
        println("onCoroutineTest: This is executed immediately")
    }
    private suspend fun stallForTime1(i: Int) {
        println("onCoroutineTest:$i This is executed in stallForTime")
        withContext(Dispatchers.Default) {
            delay(2000L)
            println("onCoroutineTest:$i This is executed in  last stmt stallForTime")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onCoroutineTest1(case :Int) {
        val job = GlobalScope.launch(Dispatchers.Main) {
            println("onCoroutineTest:1 This is executed before the first delay")
            stallForTime1(1)
            println("onCoroutineTest:1  This is executed after the first delay")
        }
        GlobalScope.launch(Dispatchers.Main) {
            println("onCoroutineTest:2 This is executed before the second delay")
            when (case){
                0->{}
                1->{job.join()}
                2->{ job.cancel()}
                3->{ job.cancelAndJoin()}
            }
            stallForTime1(2)
            println("onCoroutineTest:2 This is executed after the second delay")
        }
        println("onCoroutineTest: This is executed immediately")
    }

    suspend fun onCoroutine(){
       // viewModelScope.launch {
            val sourceFlow: Flow<Int> = flow {
                emit(1)
                emit(2)
                throw RuntimeException("Error occurred!")
                emit(3)
                emit(4)
            }

            val recoveredFlow: Flow<Int> = sourceFlow.catch { exception ->
                // Handle the exception gracefully
                println("Caught exception: ${exception.message}")
                emit(5) // Emit a recovery value
            }

            recoveredFlow.collect { value ->
                println("Received: $value")
            }
        //}
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun onCoroutine1() {
        viewModelScope.launch {
            val job = launch {
                val res= async {
                    for (i in 0..100) {
                        delay(100)
                        println("==>0:$i")
                    }
                }
                val res1= launch {
                    for (i in 0..100) {
                        delay(100)
                        if(i==20) res.await()
                        println("==>1:$i")
                    }
                }
            }

            val timeoutJob = launch(Dispatchers.Main) {
                try {
                    val result = withTimeoutOrNull(1000L) {
                        println("==>This is executed before the delay")
                        withContext(Dispatchers.Default) {
                            delay(3000L)
                        }
                        println("==>This is executed after the delay")
                    }
                } catch (e: TimeoutCancellationException) {
                    println("==>We got a timeout exception")
                } catch (e: Exception) {
                    println("==>We got a timeout exception: $e")
                }
                println("==>This is printed after the timeout")
            }
            /*launch {
                delay(1000)
                //job.cancelAndJoin()
                //timeoutJob.cancel()
            }*/
            val job1 = GlobalScope.launch(Dispatchers.Main) {
                try {
                    println("This is executed before the first delay")
                    delay(500)
                    println("This is executed after the first delay")
                } finally {
                    withContext(NonCancellable) {
                        println("This is executed before the finally block delay")
                        delay(500)
                        println("This is executed after the finally block delay")
                    }
                }
            }
            launch {
                println("This is executed before the first delay")
                stallForTime()
                println("This is executed after the first delay")
            }
            println("==>items: ${randomPercentages(10, 200).toList()}")
            randomPercentages(10, 200).collect{
                println("==>item: $it")
            }
            println("That's all folks!")
        }.invokeOnCompletion {
            println("invokeOnCompletion: $it")

        }
    }

    private suspend fun busyWait(ms: Int) {
        val start = Date().time.toLong()
        while ((Date().time.toLong() - start) < ms) {
// busy loop
        }
    }
    private suspend fun stallForTime() {
        withContext(Dispatchers.Default) {
            coroutineContext[Job]!!.cancel()
                println("This is executed before the busyWait(2000) call : ${coroutineContext[Job]}")
                busyWait(2000)
                println("This is executed after the busyWait(2000) call: ${coroutineContext[Job]}")

        }
    }

    private fun randomPercentages(count: Int, delayMs: Long) = flow {
        for (i in 0 until count) {
            delay(delayMs)
            emit(Random.nextInt(1,100))
        }
    }
}