package learnConcurrency;


public class MyRunnable implements Runnable {
    private final long countUtil;
    MyRunnable(long countUtil) {
        this.countUtil = countUtil;
    }

    @Override
    public void run() {
        long sum = 0;
        for (long i = 0; i < countUtil; i++) {
            sum += 1;
        }
        // System.out.println(sum);
    }
}
