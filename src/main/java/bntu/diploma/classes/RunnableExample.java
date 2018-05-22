package bntu.diploma.classes;

public class RunnableExample implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 150000; i++) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println("hello "+i);
        }
    }
}
