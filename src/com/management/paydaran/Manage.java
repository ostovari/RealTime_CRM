package com.management.paydaran;

import static com.management.paydaran.Manage.server;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import org.apache.derby.drda.NetworkServerControl;

public class Manage {
	
        SerialComm conn ;
        Thread t;
        public static boolean flag2 = true;
        static boolean [] flag = new boolean[2];
        static int turn, p = 0;
        static int [] id = new int [5];
        static NetworkServerControl server;
        
	public Manage(SerialComm serconn)
	{       
                conn = serconn;
		JFrame frame = new JFrame();
		frame.setTitle("سیستم ارزیابی عملکرد");
		frame.setSize(new Dimension(900, 680));
		WindowAdapter	windowAdapter = new WindowAdapter()
			{
			public void windowClosing(WindowEvent we)
				{
                                    try {
                                        server.shutdown();
                                    } catch (Exception ex) {
                                        Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
                                    }
					System.exit(0);
				}
			};
		frame.addWindowListener(windowAdapter);
                
		JComponent tabbedPane = new ManagePane(conn);
		frame.getContentPane().add(tabbedPane);

                
                
		//Validate frames that have preset sizes
		//Pack frames that have useful preferred size info, e.g. from their layout

		//Center the window
		Dimension	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension	frameSize = frame.getSize();
		if (frameSize.height > screenSize.height)
		{
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width)
		{
			frameSize.width = screenSize.width;
		}
                
            
                
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		frame.setVisible(true);
                
                java.util.Timer timer1 = new java.util.Timer();
                java.util.Timer timer = new java.util.Timer();
                timer1.schedule(new Allowsend(conn),0 ,50);
                timer.schedule(new healthCycle(conn), 0, 30000);
       
	}
        
        public static byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte) v;
	    }
	    return b;
	  }
        
         public static class Allowsend extends TimerTask{
        SerialComm conn ;
        
        public Allowsend (SerialComm sconn)
        {
            super();
            conn = sconn;
        }
     
        @Override
        public void run() {          
            while(true){
                for(int e = 1 ; e < 6 ; e ++){
                    byte[] permission = new byte[5];
                    permission[0] = hexStringToByteArray("7E") [0];
                    permission[1] = Byte.parseByte(e+"");
                    permission[2] = Byte.parseByte("2");
                    permission[3] = Byte.parseByte("1");
                    permission[4] = Byte.parseByte("1"); 
                    flag[0] = true;
                    turn = 1;
                    while(flag[1] && turn ==1){ try{Thread.sleep(51);}catch(Exception w){}}
                    while(!flag2){
                        try{Thread.sleep(600);}catch(Exception q){}
                    }
                     conn.sendData(permission);            
                    //System.out.println("waiting for client "+1);
                    //conn.recievedata();
                    try {
                    Thread.sleep(50);
                    } catch (InterruptedException ex) {
                    Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    flag[0] = false;
                    }
                }
        }
 }
         public static class healthCycle extends TimerTask{
        SerialComm conn;
        public healthCycle (SerialComm sconn)
        {
            super();
            conn = sconn;
        }
        
        @Override
        public void run() {
            id[0] = 0;id[1] = 0;id[2] = 0;id[3] = 0;id[4] = 0;
            flag[1] = true;
            turn = 0;
            while(flag[0] && turn == 0){try{Thread.sleep(51);}catch(Exception z){}}
            for(p = 0 ; p < 20 ; p++){    
                for(int e = 1 ; e < 6 ; e ++){
                    byte[] healthcyc = new byte[5];
                    healthcyc[0] = hexStringToByteArray("7E") [0];
                    healthcyc[1] = Byte.parseByte(e+"");
                    healthcyc[2] = Byte.parseByte("7");
                    healthcyc[3] = Byte.parseByte("1");
                    healthcyc[4] = Byte.parseByte("0");
                    while(!flag2){
                        try{Thread.sleep(300);}catch(Exception w){}
                    }
                    conn.sendData(healthcyc);
                    //System.out.println("waiting for client "+1);
                    try {
                    Thread.sleep(70);
                    } catch (InterruptedException ex) {
                    Logger.getLogger(SerialComm.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                } 
            }
            flag[1] = false;  
        }
        
     
 }
        
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SerialComm conn = new SerialComm();
            try {
			conn.initialize();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}          
               try {
                server=new NetworkServerControl(InetAddress.getLocalHost(),1527);
                server.start (null);              
            } catch (Exception ex) {
                Logger.getLogger(Manage.class.getName()).log(Level.SEVERE, null, ex);
            }
                PassWordDialog passDialog = new PassWordDialog(conn);
                passDialog.setVisible(true);

	}

}

class PassWordDialog extends JDialog {

    private final JLabel jlblUsername = new JLabel("نام کاربری");
    private final JLabel jlblPassword = new JLabel("رمز عبور");

    private final JTextField jtfUsername = new JTextField(15);
    private final JPasswordField jpfPassword = new JPasswordField();

    private final JButton jbtOk = new JButton("ورود");
    private final JButton jbtCancel = new JButton("لغو");

    private final JLabel jlblStatus = new JLabel(" ");
    Database db = new Database();
    SerialComm conn ;
    static boolean accessD , accessS, accessQ, isadmin;

    public PassWordDialog(SerialComm conn2) {
        this(null, true);
        conn = conn2;
    }

    public PassWordDialog(final JFrame parent, boolean modal) {
        super(parent, modal);
        
        this.setTitle("ورود به سیستم");
        
        JPanel p3 = new JPanel(new GridLayout(2, 1));
        p3.add(jtfUsername);
        p3.add(jpfPassword);

        JPanel p4 = new JPanel(new GridLayout(2, 1));
        p4.add(jlblUsername);
        p4.add(jlblPassword);

        JPanel p1 = new JPanel();
        p1.add(p3);
        p1.add(p4);

        JPanel p2 = new JPanel();
        p2.add(jbtOk);
        p2.add(jbtCancel);

        JPanel p5 = new JPanel(new BorderLayout());
        p5.add(p2, BorderLayout.CENTER);
        p5.add(jlblStatus, BorderLayout.NORTH);
        jlblStatus.setForeground(Color.RED);
        jlblStatus.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(p1, BorderLayout.CENTER);
        add(p5, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {  
            @Override
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });


        jbtOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.openDb();
                String title;
                ResultSet rs = db.createQuery("SELECT TITLE, USERNAME, PASSWORD, Q_BANK, S_BANK, DIAGRAM FROM PAYDARAN.TABLE_ACCESSCONTROL "
                    + "WHERE USERNAME = '"+jtfUsername.getText().toString()+"' AND PASSWORD = '"+jpfPassword.getText().toString()+"'");
                try {
		if(rs.next()){
                    title = rs . getString("TITLE");
                    accessQ = rs.getBoolean("Q_BANK");
                    accessS = rs.getBoolean("S_BANK");
                    accessD = rs.getBoolean("DIAGRAM");
                    //parent.setVisible(true);
                    setVisible(false);
                    if("admin".equals(title))
                        isadmin = true;
                    try {
			new Manage(conn);
                    } catch (Throwable t) {
			System.err.println("Exception occurred in main():");
			t.printStackTrace();
			System.exit(1);
                    }
		}else {
                    jlblStatus.setText("نام کاربری یا رمز عبور اشتباه است");
                }
            } catch (SQLException ea) {
		// TODO Auto-generated catch block
		ea.printStackTrace();
            }
                db.closeDb();
            }
        });
        jbtCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}