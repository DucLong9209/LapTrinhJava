package bai1;

    public class ThreadExample {
        public static void main(String[] args) {
            bai1.Counter counter = new bai1.Counter();

            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            });

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Giá trị cuối cùng của counter: " + counter.getCount());
        }
    }

