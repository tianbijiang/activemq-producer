package tianbi.producer;

import tianbi.producer.Producer;

public class Send {

	private static int incrementingProducerId = 0;
	
	public Send() {
		
	}
	
	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	    
    public static void thread(Runnable runnable, boolean daemon) {
        Thread myThread = new Thread(runnable);
        myThread.setDaemon(daemon);
        myThread.start();
    }
    
    public static class MyProducer implements Runnable {
        public void run() {
            try {
            	//incrementingProducerId++;
            	Producer producer = new Producer(1);
            	producer.tryConnecting();
            	for(int i=0; i<10000; i++) {
            		producer.sendMessage();
            	}
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void main(String[] args) {

		thread(new MyProducer(), false);

	}
}
