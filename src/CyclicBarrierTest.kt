import java.util.*
import java.util.concurrent.CyclicBarrier

class CyclicBarrierTest {
    class StartGame(private val player: String,
                    private val barrier: CyclicBarrier) : Runnable {
        override fun run() {
            println("$player 进行匹配...")
            findOtherPlayer()
            println("$player 匹配完成...")
            barrier.await()

            println("$player 进行选择角色...")
            choiceRole()
            println("$player 角色选择完毕等待其他玩家...")
            barrier.await()

            println("$player 开始游戏,进行游戏加载...")
            loading()
            println("$player 游戏加载完毕等待其他玩家加载完成...")
            barrier.await()
        }

        private fun loading() {
            Thread.sleep(randomTime())
        }

        private fun choiceRole() {
            Thread.sleep(randomTime())
        }

        private fun findOtherPlayer() {
            Thread.sleep(randomTime())
        }

        private val random = Random()
        private fun randomTime(): Long = (random.nextInt(5) + 1) * 1000L
    }
}

fun main(args: Array<String>) {
    val barrier = CyclicBarrier(5)
    {
        println("阶段完成，等待2秒...")
        Thread.sleep(2000)
        println("进入下个阶段...")
    }
    (1..5).forEach {
        Thread(CyclicBarrierTest.StartGame("玩家$it", barrier)).start()
    }
//    System.`in`.read()
}
