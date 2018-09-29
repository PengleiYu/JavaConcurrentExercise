import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * 数据加载的抽象类
 */
abstract class AbstractDataRunnable(private val name: String, private val counter: CountDownLatch) : Runnable {
    override fun run() {
        try {
            println("$name 开始加载...")
            val l1 = System.currentTimeMillis()
            handle()
            val l2 = System.currentTimeMillis()
            println("$name 加载完成,花费时间: ${l2 - l1}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            counter.countDown()
        }
        afterCountDown()
    }

    private fun afterCountDown() {
        println("$name:CountDownLatch计数减一之后,继续加载其他数据...")
    }

    protected abstract fun handle()
}

/**
 * 数据加载实现类
 */
class ImplDataRunnable(name: String, counter: CountDownLatch, private val sleep: Long)
    : AbstractDataRunnable(name, counter) {
    override fun handle() {
        Thread.sleep(sleep)
    }
}

/**
 * 游戏启动主线程
 */
class StartGame(private val counter: CountDownLatch) : Runnable {
    override fun run() {
        try {
            println("开始加载基础数据...")
            val l1 = System.currentTimeMillis()
            counter.await()
            val l2 = System.currentTimeMillis()
            println("基础数据加载完成,总共花费时间: ${l2 - l1},可以开始游戏...")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun main(args: Array<String>) {
    val counter = CountDownLatch(4)
    Thread(StartGame(counter)).start()

    val random = Random()
    listOf("地图", "物品", "任务", "背景")
            .forEach {
                Thread(ImplDataRunnable(it, counter, random.nextInt(5) * 1000L)).start()
            }
}