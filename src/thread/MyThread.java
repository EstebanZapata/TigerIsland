package thread;

public class MyThread extends Thread {
    protected volatile boolean stop = false;

    public MyThread() {
        super();
    }


    public void stopThread() {
        this.stop = true;
    }
}
