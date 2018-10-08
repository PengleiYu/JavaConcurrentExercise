class YieldTest {
    companion object {
        val yieldRunnable = Runnable {
            for (i in 0..10) {
                Thread.currentThread().print(i)
                /*使用了yield后，各线程打印会均匀很多*/
                Thread.yield()
            }
        }
    }
}

fun main(args: Array<String>) {
    (0..3).map { Thread(YieldTest.yieldRunnable) }.forEach { it.start() }
//    Thread(YieldTest.yieldRunnable).start()
//    Thread(YieldTest.yieldRunnable).start()
//    Thread(YieldTest.yieldRunnable).start()
//    Thread(YieldTest.yieldRunnable).start()
}