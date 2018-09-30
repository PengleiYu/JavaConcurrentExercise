package test;

public class ThreadResource {
    private String threadName;
    private int threadId;

    public ThreadResource(String threadName, int threadId) {
        this.threadName = threadName;
        this.threadId = threadId;
    }

    public String getThreadName() { return threadName; }

    public void setThreadName(String threadName) { this.threadName = threadName; }

    public int getThreadId() { return threadId; }

    public void setThreadId(int threadId) { this.threadId = threadId; }
}
