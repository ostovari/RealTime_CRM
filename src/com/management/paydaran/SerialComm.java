package com.management.paydaran;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.swing.JOptionPane;


public class SerialComm implements SerialPortEventListener {
SerialPort serialPort;
    /** The port we're normally going to use. */
private static final String PORT_NAMES[] = {                  "/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyUSB0", // Linux
        "COM1", // Windows1
        "COM2", // Windows2
        "COM3", // Windows3
        "COM4", // Windows4
        "COM5", // Windows5
        "COM6", // Windows6
        "COM7", // Windows7
        "COM8", // Windows8
        "COM9", // Windows9
};
private InputStream input;
private OutputStream output;
private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 19200;
Database db = new Database();

public void initialize() {
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

    //First, Find an instance of serial port as set in PORT_NAMES.
    while (portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if (currPortId.getName().equals(portName)) {
                portId = currPortId;
                break;
            }
        }
    }
    if (portId == null) {
        //System.out.println("Could not find COM port.");
        JOptionPane.showMessageDialog(null, "پورت سریال یافت نشد");
        return;
    }

    try {
        serialPort = (SerialPort) portId.open(this.getClass().getName(),
                TIME_OUT);
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        
        serialPort.enableReceiveTimeout(50);//disableReceiveTimeout();
        serialPort.enableReceiveThreshold(4);
        
        // open the streams
        input = serialPort.getInputStream();
        output = serialPort.getOutputStream();

        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
    } catch (Exception e) {
        System.err.println(e.toString());
    }
}


public synchronized void close() {
    if (serialPort != null) {
        serialPort.removeEventListener();
        serialPort.close();
    }
}
public synchronized void sendData(byte [] data) {
        try {
	output.write(data);
        output.flush();
    } catch (IOException e) {
	e.printStackTrace();
	}
    
}

public synchronized void serialEvent(SerialPortEvent oEvent) {
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
        try {
            byte[] buffer = new byte[28];
            byte[] header = new byte[4];
            if (input.read(header)!=  -1) {
                if(header[0]== 126){
                    //System.out.println("7E:‌"+header[0]);
                    //System.out.println("id:‌"+ header[1]);
                    //System.out.println("type:‌"+ header[2]);
                    //System.out.println("L:‌"+ header[3]);
                    serialPort.enableReceiveThreshold((int)header[3]);
                    if(input.read(buffer) != -1){
 
                        Writetodb ser = new Writetodb(buffer, header);
                        Thread t2 = new Thread(ser);
                        t2.start();                       
                       
              }
                    serialPort.enableReceiveThreshold(4);
            }else{
                    System.out.println("out of format...!");
                }
          }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
    // Ignore all the other eventTypes, but you should consider the other ones.
}

    public static class Writetodb implements Runnable{

            Database db = new Database();
            byte[] toWrite = new byte[28];
            byte[] header = new byte[4];
            
            public Writetodb ( byte [] buff, byte [] head )
        {
            this.toWrite = buff;
            this.header = head;
        }
            
        @Override
        public void run() {
            if(header[2] == 7){
                            switch (header[1]){
                                case 1:
                                    Manage.id[0]++;
                                    //System.out.println("id 1 recieved");
                                    break;
                                case 2:
                                    Manage.id[1]++;
                                    break;
                                case 3:
                                    Manage.id[2]++;
                                    break;
                                case 4:
                                    Manage.id[3]++;
                                    break;
                                case 5:
                                    Manage.id[4]++;
                                    break;
                                default :
                                    break;
                            }
                    
                }else if(header[2] == 10){
                    db.openDb();
                    String query = "INSERT INTO PAYDARAN.TABLE_ANSWER (Q_ID, ANSWER, A_KIND) VALUES ("+(int)toWrite[1]+", "+(int)toWrite[0]+", 10)"; 
                    if(db.insertDb(query)){
			System.out.println("added...!");		
                    }else{
			System.out.println("ooooops...!");
                    }
                    db.closeDb();
                }else if(header[2] == 9){
                    String query = "INSERT INTO PAYDARAN.TABLE_ANSWER(Q_ID, S_ID, ANSWER, A_KIND) VALUES";
                    for(int i = 1 ; i < header[3] ; i +=2){
                        if(i == header[3] - 2){
                            query += " ("+(int)toWrite[i]+", "+(int)toWrite[0]+", "+(int)toWrite[i+1]+", 9)";
                            System.out.println(toWrite[i]+": "+toWrite[i+1]);
                        }
                        else{
                            query += " ("+(int)toWrite[i]+", "+(int)toWrite[0]+", "+(int)toWrite[i+1]+", 9),";
                            System.out.println(toWrite[i]+": "+toWrite[i+1]);
                        }
                    }
                    System.out.println(query);
                    db.openDb();
                        if(db.insertDb(query)){
                            System.out.println("added...!");
			}else{
                            System.out.println("oooooops...!");
			}
                        db.closeDb();
                }else if(header[2] == 4){
                    for(int i = 0 ; i < 5 ; i++){
                        //int L = toWrite[3] + 4;
                        db.openDb();
                        String query = "INSERT INTO PAYDARAN.TABLE_ANSWER(Q_ID, ANSWER, A_KIND) VALUES ("+(i+15)+", "+(int)toWrite[i]+", 4)"; 
                        if(db.insertDb(query)){
                            System.out.println("added...!");		
			}else{
                            System.out.println("oooooops...!");
			}
                        db.closeDb();  
                        }
                            //break;
                }     
                }
        }        
}
