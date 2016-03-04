package tianbi.producer;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.io.FileUtils;

public class Producer {

	//private String connectionIP = "tcp://10.250.10.140:61616";
	private String connectionIP = "tcp://localhost:61616";
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;    
    private int id;

    public Producer(int id) {
    	this.id = id;
    }
    
    public void tryConnecting() {
    	try {    
	    	factory = new ActiveMQConnectionFactory(connectionIP);
    		//factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
    		connection = factory.createConnection();
	        connection.start();            
	        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	        destination = session.createQueue("SAMPLEQUEUE");
	        producer = session.createProducer(destination);
    	} catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() throws IOException {
        try {          
            TextMessage message = session.createTextMessage();
            message.setText("haha");
            //message.setText(readTXT());
            producer.send(message);
            //System.out.println("Sent: from producer"+ id);            
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public void cleanUp() {
    	try {
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
    }

    public int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
    public String readTXT() throws IOException {  
    	File jarPath=new File(Producer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
    	String fileContents = FileUtils.readFileToString(new File(propertiesPath+"\\sample.txt"));   	
		return fileContents;    	
    }
    

}
