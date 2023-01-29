package com.group.common.executor

import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * 描述:
 *
 *
 * author zys
 * create by 2021/5/24
 */
object DispatcherExecutor {

    /**
     * 当前设备可以使用的 CPU 核数
     */
    private val CPU_COUNT = Runtime.getRuntime().availableProcessors()

    /**
     * 线程池核心线程数，其数量在2 ~ 5这个区域内
     */
//    private val CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5))
    private val CORE_POOL_SIZE = when {
        CPU_COUNT - 1 > 5 -> 5
        CPU_COUNT - 1 < 2 -> 2
        else -> CPU_COUNT - 1
    }

    /**
     * 线程池线程数的最大值：这里指定为了核心线程数的大小
     */
    private val MAXIMUM_POOL_SIZE = CORE_POOL_SIZE

    /**
     * 线程池中空闲线程等待工作的超时时间，当线程池中
     * 线程数量大于corePoolSize（核心线程数量）或
     * 设置了allowCoreThreadTimeOut（是否允许空闲核心线程超时）时，
     * 线程会根据keepAliveTime的值进行活性检查，一旦超时便销毁线程。
     * 否则，线程会永远等待新的工作。
     */
    private const val KEEP_ALIVE_SECONDS = 5

    /**
     * 创建一个基于链表节点的阻塞队列
     */
    private val S_POOL_WORK_QUEUE: BlockingQueue<Runnable> = LinkedBlockingQueue()

    /**
     * 用于创建线程的线程工厂
     */
    private val S_THREAD_FACTORY = DefaultThreadFactory()

    /**
     * 线程池执行耗时任务时发生异常所需要做的拒绝执行处理
     * 注意：一般不会执行到这里
     */
    private val S_HANDLER =
        RejectedExecutionHandler { r, _ -> Executors.newCachedThreadPool().execute(r) }

    /**
     * CPU 密集型任务的线程池
     *
     *
     */
    val cPUExecutor: ThreadPoolExecutor by lazy {
        ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS.toLong(), TimeUnit.SECONDS,
            S_POOL_WORK_QUEUE, S_THREAD_FACTORY, S_HANDLER
        ).apply {
            // 设置是否允许空闲核心线程超时时，线程会根据keepAliveTime的值进行活性检查，
            // 一旦超时便销毁线程。否则，线程会永远等待新的工作。
            allowCoreThreadTimeOut(true)
        }
    }

    /**
     * IO 密集型任务的线程池
     * IO密集型任务线程池直接采用CachedThreadPool来实现，
     * 它最多可以分配Integer.MAX_VALUE个非核心线程用来执行任务
     */
    val iOExecutor: ExecutorService by lazy { Executors.newCachedThreadPool(S_THREAD_FACTORY) }

    /**
     * 实现一个默认的线程工厂
     */
    private class DefaultThreadFactory : ThreadFactory {
        private val group: ThreadGroup
        private val threadNumber = AtomicInteger(1)
        private val namePrefix: String
        override fun newThread(r: Runnable): Thread {
            // 每一个新创建的线程都会分配到线程组group当中
            val t = Thread(
                group, r, namePrefix + threadNumber.getAndIncrement(), 0
            )
            if (t.isDaemon) {
                // 非守护线程
                t.isDaemon = false
            }
            // 设置线程优先级
            if (t.priority != Thread.NORM_PRIORITY) {
                t.priority = Thread.NORM_PRIORITY
            }
            return t
        }

        companion object {
            private val POOL_NUMBER = AtomicInteger(1)
        }

        init {
            val s = System.getSecurityManager()
            group = if (s != null) s.threadGroup else Thread.currentThread().threadGroup!!
            namePrefix = "TaskDispatcherPool-${POOL_NUMBER.getAndIncrement()}-Thread-"
        }
    }
}