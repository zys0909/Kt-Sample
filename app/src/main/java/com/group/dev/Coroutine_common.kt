package com.group.dev

import kotlinx.coroutines.*
import kotlin.contracts.ExperimentalContracts
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * 描述:协程扩展
 *
 * @author zhaoys
 * create by 2019/7/2 0002 11:00
 */
/*
获取当前协程的 Job
 */
suspend inline fun kotlinx.coroutines.Job.Key.currentJob() = kotlin.coroutines.coroutineContext[kotlinx.coroutines.Job]

@ExperimentalContracts
internal fun <R> GlobalScope.start(block:  CoroutineScope.() -> R):R {
//    launch()
    return block(this)
}

internal fun Job.dispatcherMain(block: suspend CoroutineScope.() -> Unit): Job {
    GlobalScope.launch(context = Dispatchers.Main, block = block)
    return this
}

@SinceKotlin("1.3")
internal fun runSuspend(block: suspend () -> Unit) {
    val run = RunSuspend()
    block.startCoroutine(run)
    run.await()
}

/*
源于kotlin标准库
 */
private class RunSuspend : Continuation<Unit> {
    override val context: CoroutineContext
        get() = EmptyCoroutineContext

    private var result: Result<Unit>? = null

    override fun resumeWith(result: Result<Unit>) = synchronized(this) {
        this.result = result
        @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (this as Object).notifyAll()
    }

    fun await() = synchronized(this) {
        while (true) {
            when (val result = this.result) {
                null -> @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN") (this as Object).wait()
                else -> {
                    result.getOrThrow() // throw up failure
                    return
                }
            }
        }
    }
}