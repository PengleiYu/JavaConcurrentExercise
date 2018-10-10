import java.util.concurrent.ThreadFactory

/**
 * 线程异常的捕获
 */
class ThreadExceptionTest {
    companion object {
        /**
         * 无法捕获线程异常
         */
        fun simpleTest() {
            val thread = Thread(ExceptionRunnable())
            try {
                thread.start()
            } catch (e: Exception) {
                println("catch ${e.message}")
            }
        }

        /**
         * 设置默认异常捕获器，优先使用指定的捕获器
         */
        fun defaultHandleTest() {
            Thread.setDefaultUncaughtExceptionHandler { t, e ->
                println("DefaultUncaughtExceptionHandler: ${e.message}")
            }
            Thread(ExceptionRunnable()).start()
            Factory().newThread(ExceptionRunnable()).start()
        }
    }

    class ExceptionRunnable : Runnable {
        override fun run() {
            throw RuntimeException("Hello")
        }
    }

    /**
     * 使用线程工厂为线程添加异常处理器
     */
    class Factory : ThreadFactory {
        override fun newThread(r: Runnable): Thread {
            return Thread(r).apply {
                setUncaughtExceptionHandler { t, e ->
                    println("ThreadFactory - UncaughtExceptionHandler: ${e.message}")
                }
            }
        }
    }
}

fun main(args: Array<String>) {
//    ThreadExceptionTest.Factory().newThread(ThreadExceptionTest.ExceptionRunnable()).start()
//    ThreadExceptionTest.simpleTest()
    ThreadExceptionTest.defaultHandleTest()
}