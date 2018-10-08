import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class ExecutorTest {
    class TaskWithResult(private val id: Int) : Callable<String> {
        companion object {
            private val random = Random()
        }
        override fun call(): String {
            val nextInt = random.nextInt(10)
            Thread.sleep(nextInt * 1000L)
            return "result of TaskWithResult $id, sleep $nextInt"
        }
    }
}

fun main(args: Array<String>) {
    val executorService = Executors.newFixedThreadPool(2)
    val results = mutableListOf<Future<String>>()
    for (i in 0..10) {
        results.add(executorService.submit(ExecutorTest.TaskWithResult(i)))
    }
    while (results.isNotEmpty()) {
        val iterator = results.iterator()
        while (iterator.hasNext()){
            val next = iterator.next()
            if (!next.isDone) continue
            println(next.get())
            iterator.remove()
        }
//        println("一轮结束")
    }
    executorService.shutdown()
}