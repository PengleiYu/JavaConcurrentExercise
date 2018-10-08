package threadLocal;

/**
 * ThreadLocal中的值其实是放在线程对象中的
 * get和set方法都是去线程中查找储存的值
 */
public class ThreadLocalTest {
    /**
     * 该静态对象被main线程和TestThread线程公用
     */
    private static ThreadLocal<ThreadResource> resourcePackage =
            ThreadLocal.withInitial(() -> new ThreadResource("initialThread", 0));

    private static class TestThread extends Thread {
        private static int threadId = 0;

        {
            synchronized (this){
                threadId++;
            }
        }

        @Override
        public void run() {
            resourcePackage.set(new ThreadResource("TestThread", threadId));
            System.out.println(
                    resourcePackage.get().getThreadName() + resourcePackage.get().getThreadId());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(
                resourcePackage.get().getThreadName() + resourcePackage.get().getThreadId());
        new TestThread().start();
        Thread.sleep(100);
        new TestThread().start();
        Thread.sleep(100);
        new TestThread().start();
        Thread.sleep(100);
        System.out.println(
                resourcePackage.get().getThreadName() + resourcePackage.get().getThreadId());
    }
}
