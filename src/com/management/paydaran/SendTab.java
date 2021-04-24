package com.management.paydaran;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class SendTab
extends JPanel
{
	public static JTextField	s_snumberTextField;
	private JTextField		s_snumber2TextField;
	private JTextField		s_hourTextField;
	private JTextField		s_minutTextField;
	private JTextField		btw_minutTextField;
	private JTextField		btw_secondTextField;
        private JTextField		client_idTextField;
	private JComboBox		s_kindComboBox; 
	String str[] = new String [] {" ","سوال فوری" , "سناریو"};
	private JButton sendSBtn;
        private JButton sendRSMSBtn;
	JPanel addeqpanel;
        JPanel scadulPanle;
	DefaultListModel qitem;
        DefaultListModel nitem;
	Vector<String> q_id;
        Vector<String> lang;
	Vector<Integer> sid;
	Vector<String> choices;
        Vector<String> SQ_ID;
        Vector<Integer> Lang_ID;
	Database db = new Database();
	JList qnumList;
	SerialComm conn ;
	DefaultListModel question;
        ListSelectionListener listener;
        JPanel tablepane;
        private JTable table;
        DefaultTableModel tablemodel;
        JPanel middlepanel2;
        private JTable table2;
        DefaultTableModel tablemodel2;
        JButton refresher;

	public SendTab(SerialComm sc )
	{
		conn = sc;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		
		JPanel slct = new JPanel();
		slct.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		slct.add(new JLabel("نوع پیام"));
		slct.add(Box.createHorizontalGlue());
		s_kindComboBox = new JComboBox(str);
		slct.add(s_kindComboBox);
		slct.add(Box.createHorizontalGlue());
		slct.add(Box.createHorizontalGlue());

		slct.add(Box.createHorizontalGlue());
		slct.add(Box.createHorizontalGlue());

		JButton applyButton = new JButton("تایید");
		applyButton.addActionListener(new refreshSQList());
		slct.add(applyButton);
		topPanel.add(slct);
		this.add(topPanel, BorderLayout.CENTER);
		               		
            refresher = new JButton();
            refresher.addActionListener(new refreshSQList());
            refresher.setVisible(false);
            this.add(refresher, BorderLayout.CENTER);
            
            JPanel middlepanel = new JPanel();
                middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);    
                tablepane = new JPanel();
		tablepane.setLayout(new BoxLayout(tablepane, BoxLayout.Y_AXIS));
            Object[] columnNames = {"متن سوال", "تعداد گزینه ها"};
            Object[][] data = null;
 
            tablemodel = new DefaultTableModel(data, columnNames);
            q_id = new Vector<String>();
            lang = new Vector<String>(); 
            table = new JTable(tablemodel) {
            private static final long serialVersionUID = 1L;
 
            
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
            }
                      
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        table.getColumn("متن سوال").setCellRenderer( rightRenderer );
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getColumn("تعداد گزینه ها").setCellRenderer( centerRenderer );
            
        Dimension d = table.getPreferredSize();
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(
             new Dimension(d.width*4, table.getRowHeight()*7+1));
        scrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tablepane.add(scrollPane);
        tablepane.setVisible(false);
        middlepanel.add(tablepane);
        this.add(middlepanel, BorderLayout.CENTER);
                                                                              
            middlepanel2 = new JPanel();
                middlepanel2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);    
            JPanel tablepane2 = new JPanel();
		tablepane2.setLayout(new BoxLayout(tablepane2, BoxLayout.Y_AXIS));
            Object[] columnNames2 = {"نام سناریو", "تعداد سوال"};
            Object[][] data2 = null;
                    
            tablemodel2 = new DefaultTableModel(data2, columnNames2);
            sid = new Vector<Integer>();
            Lang_ID = new Vector<Integer>();
            SQ_ID = new Vector<String>();
            choices = new Vector<String>();
            table2 = new JTable(tablemodel2) {
            private static final long serialVersionUID = 1L;

           
            @Override
            public boolean isCellEditable(int row, int column) {
            return false;
            }
                      
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        listener = new slistListener();
        table2.getSelectionModel().addListSelectionListener(listener);
        
        DefaultTableCellRenderer rightRenderer2 = new DefaultTableCellRenderer();
        rightRenderer2.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        table2.getColumn("نام سناریو").setCellRenderer( rightRenderer2 );
        DefaultTableCellRenderer centerRenderer2 = new DefaultTableCellRenderer();
        centerRenderer2.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table2.getColumn("تعداد سوال").setCellRenderer( centerRenderer2 );
        
        Dimension d2 = table2.getPreferredSize();
        table2.setPreferredScrollableViewportSize(table2.getPreferredSize());
        JScrollPane scrollPane2 = new JScrollPane(table2);
        scrollPane2.setPreferredSize(
             new Dimension(d2.width*2, 170)); //table2.getRowHeight()*7+1
        scrollPane2.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tablepane2.add(scrollPane2);
        middlepanel2.add(tablepane2);
        addeqpanel = new JPanel();
	    addeqpanel.setLayout(new BoxLayout(addeqpanel, BoxLayout.Y_AXIS));
	    question = new DefaultListModel();
	    JList addedQ = new JList(question);
            addedQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            JScrollPane scroll5 = new JScrollPane(new JLabel("سوالات موجود در سناریو"));
                        scroll5.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    addeqpanel.add(scroll5);
            JScrollPane qscroll12 = new JScrollPane(addedQ);
            qscroll12.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            qscroll12.setPreferredSize(new Dimension(350, 150));
	    addeqpanel.add(qscroll12);
	    addeqpanel.setVisible(false);
	    middlepanel2.add(addeqpanel);
	    middlepanel2.setVisible(false);
	    this.add(middlepanel2, BorderLayout.CENTER);              
            
		scadulPanle = new JPanel();
		scadulPanle.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		
		JPanel sNumber = new JPanel();
		sNumber.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		sNumber.add(new JLabel("زمان یندی ارسال سناریو:"));
		scadulPanle.add(sNumber);
		
		JPanel tValue = new JPanel();
		tValue.setLayout(new BoxLayout(tValue, BoxLayout.Y_AXIS));
		
		JPanel h = new JPanel();
		h.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		h.add(new JLabel("ساعت"));
		h.add(Box.createHorizontalGlue());
		s_hourTextField = new JTextField(3);
		h.add(s_hourTextField);
		h.add(Box.createHorizontalGlue());
		h.add(Box.createHorizontalGlue());
		h.add(new JLabel("فاصله زمانی بین سوالات دقیقه"));
		h.add(Box.createHorizontalGlue());
		btw_minutTextField = new JTextField(3);
		h.add(btw_minutTextField);
		tValue.add(h);
		
		JPanel i = new JPanel();
		i.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		i.add(new JLabel("دقیقه"));
		i.add(Box.createHorizontalGlue());
		s_minutTextField = new JTextField(3);
		i.add(s_minutTextField);
		i.add(Box.createHorizontalGlue());
		i.add(Box.createHorizontalGlue());
		i.add(new JLabel("فاصله زمانی بین سوالات ثانیه"));
		i.add(Box.createHorizontalGlue());
		btw_secondTextField = new JTextField(3);
		i.add(btw_secondTextField);
		tValue.add(i);
		
		scadulPanle.add(tValue);
                scadulPanle.setVisible(false);
		this.add(scadulPanle, BorderLayout.CENTER);
		
                JPanel idpanel = new JPanel();
		idpanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		idpanel.add(new JLabel("شناسه گیرنده"));
		idpanel.add(Box.createHorizontalGlue());
		client_idTextField = new JTextField(3);
		idpanel.add(client_idTextField);
                this.add(idpanel, BorderLayout.CENTER);
                
		JPanel btnPanel = new JPanel();
		btnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		sendSBtn = new JButton("ارسال سناریو");
		sendSBtn.addActionListener(new sendsenario());
                sendSBtn.setVisible(false);
                
		btnPanel.add(sendSBtn);
		btnPanel.add(Box.createHorizontalGlue());
		btnPanel.add(Box.createHorizontalGlue());
		sendRSMSBtn = new JButton("ارسال پیامک فوری");
		sendRSMSBtn.addActionListener(new sendSMS());
                sendRSMSBtn.setVisible(false);
		btnPanel.add(sendRSMSBtn);
		this.add(btnPanel, BorderLayout.CENTER);

	
	}
        public class slistListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            addeqpanel.setVisible(true);
            question.clear();
            choices.clear();
            SQ_ID.clear();
            String senar = null;
            if(table2.getSelectedRow() != -1)
                senar = table2.getValueAt(table2.getSelectedRow(), 0).toString();
            db.openDb();
            ResultSet rs3 = db.createQuery("SELECT Q_ID, Question, Choices" +
		" FROM PAYDARAN.TABLE_QUES" +
		" WHERE Q_id IN (SELECT Q_id" +
		" FROM PAYDARAN.TABLE_SENARIO_Q" +
		" WHERE S_name = '"+senar+"')");
		try {
		while(rs3.next()){
                    SQ_ID.add(rs3.getString("Q_ID"));
                    question.addElement(rs3.getString("Question"));
                    choices.add(rs3.getString("Choices"));  
		}
		} catch (SQLException w) {
                    w.printStackTrace();
					}
            db.closeDb();
        }
 
    
        }
        
        public class refreshSQList implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            refresher.setVisible(true);
            if(s_kindComboBox.getSelectedIndex() == 1){
                refresher.setText("به روز رسانی سوالات");
                while(tablemodel.getRowCount() != 0)
                    tablemodel.removeRow(0);
                q_id.clear();
                lang.clear();
                db.openDb();
                ResultSet rs = db.createQuery("SELECT Q_id, Question, Choices, LANGUAGE FROM Table_Ques");
                                try {
                                    while(rs.next()){
                                        tablemodel.addRow(new Object[]{rs.getString("Question"), rs.getString("Choices")});
                                        q_id.add(rs.getString("Q_id"));
                                        lang.add(rs.getString("LANGUAGE"));
                                    }
                                } catch (SQLException e5) {
                                    e5.printStackTrace();
                                    }
                                db.closeDb();
				tablepane.setVisible(true);
				middlepanel2.setVisible(false);
                                sendRSMSBtn.setVisible(true);
                                sendSBtn.setVisible(false);
                                scadulPanle.setVisible(false);
            }else if(s_kindComboBox.getSelectedIndex() == 2){
                refresher.setText("به روز رسانی سناریو ها");
                                    table2.getSelectionModel().clearSelection();
                                    table2.getSelectionModel().removeListSelectionListener(listener);
                                    table2.setEnabled(false);
                                    while(tablemodel2.getRowCount() != 0)
                                        tablemodel2.removeRow(0);
                                    sid.clear();
                                    Lang_ID.clear();
                                    db.openDb();
                                    ResultSet rs2 = db.createQuery("SELECT PAYDARAN.TABLE_SENARIO.S_name ,PAYDARAN.TABLE_SENARIO.S_id,PAYDARAN.TABLE_SENARIO.LANG, p.Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO LEFT JOIN" +
					" (" +
					" SELECT PAYDARAN.TABLE_SENARIO_Q.S_name , COUNT(Q_id) as Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO_Q" +
					" Group By PAYDARAN.TABLE_SENARIO_Q.S_name " +
					") as p" +
					" on PAYDARAN.TABLE_SENARIO.S_name = p.S_name");
                                    try {
                                        while(rs2.next()){
                                            tablemodel2.addRow(new Object[]{rs2.getString("S_name"), rs2.getInt("Qnumber")});
                                            sid.add(rs2.getInt("S_id"));
                                            Lang_ID.add(rs2.getInt("LANG"));
                                        }
                                    } catch (SQLException e6) {
                                        e6.printStackTrace();
                                        }
                                    db.closeDb();
                                    
					tablepane.setVisible(false);
					middlepanel2.setVisible(true);
                                        sendRSMSBtn.setVisible(false);
                                        sendSBtn.setVisible(true);
                                        scadulPanle.setVisible(true);
                                        table2.setEnabled(true);
                                        table2.getSelectionModel().addListSelectionListener(listener);
                                        
				}else if(s_kindComboBox.getSelectedIndex() == 0){
                                 
                                }
        }
 
    
        }
        
	
	InputVerifier inputverifire = new InputVerifier() {
		
		@Override
		public boolean verify(JComponent input) {
			final JTextComponent source = (JTextComponent) input;
			String text = source.getText();
			 if ((text.length() == 0)){
				 return true;
			 }
			 try {
				Integer.parseInt(text);
                                return true;
			} catch (Exception e) {
                            JOptionPane.showMessageDialog(source, "لطفا یک عدد وارد کنید", "خطا",
			              JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
	};
	
	public static byte[] hexStringToByteArray(String s) {
	    byte[] b = new byte[s.length() / 2];
	    for (int i = 0; i < b.length; i++) {
	      int index = i * 2;
	      int v = Integer.parseInt(s.substring(index, index + 2), 16);
	      b[i] = (byte) v;
	    }
	    return b;
	  }
public class sendsenario implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
				if(table2.getSelectedRow() == -1)
					JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
                                else if(client_idTextField.getText().isEmpty()){
                                     JOptionPane.showMessageDialog(null, "لطفا شناسه گیرنده را وارد نمایید");   
                                }
                                    else{
                                    Manage.flag2 = false;
                                    String ID = client_idTextField.getText().toString();
                                    try {
						Thread.sleep(80);
					} catch (InterruptedException ey){
						// TODO Auto-generated catch block
						ey.printStackTrace();
					}
                                        //send prepacket
					int sendSid = sid.get(table2.getSelectedRow());
					byte[] notice = new byte[8];
					notice[0] = hexStringToByteArray("7E") [0];
					notice[1] = (byte)Integer.parseInt(ID);//"1".getBytes()[0];
					notice[2] =  hexStringToByteArray("0E") [0];
					notice[3] = Byte.parseByte("4");
					notice[4] = (byte)(sendSid);
					btw_minutTextField.setInputVerifier(inputverifire);
					btw_secondTextField.setInputVerifier(inputverifire);
					try {
						notice[5] = Byte.parseByte(btw_minutTextField.getText());
					} catch (Exception e2) {
						notice[5] = Byte.parseByte("0");
					}
					try {
						notice[6] = Byte.parseByte(btw_secondTextField.getText());
					} catch (Exception e3) {
						notice[6] = Byte.parseByte("0");
					}
                                        notice[7] = Byte.parseByte(Lang_ID.get(table2.getSelectedRow())+"");
					conn.sendData(notice);
					//System.out.println(new String(notice, 0, 7));
					try {
						Thread.sleep(80);
					} catch (InterruptedException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                                        
                                        //send choices kind
                                        int qNumber = Integer.parseInt(table2.getValueAt(table2.getSelectedRow(), 1).toString());
                                        
					//send senario question
					for(int i = 0; i < qNumber; i++){
						byte [] currentSQ = null;
                                        try {
                                            currentSQ = question.elementAt(i).toString().getBytes("UTF-8");
                                        } catch (UnsupportedEncodingException ex) {
                                            Logger.getLogger(SendTab.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                                int packnum = 0;
                                                if(currentSQ.length%24 == 0)
                                                    packnum = (currentSQ.length/24);
                                                else
                                                    packnum = (currentSQ.length/24)+1;
						Vector<Byte> sQSec = new Vector<Byte>();
						sQSec.add(0 , hexStringToByteArray("7E") [0]);
						sQSec.add(1, (byte)Integer.parseInt(ID));
						sQSec.add(2, Byte.parseByte("8"));
						sQSec.add(3, (byte)(currentSQ.length+(4*packnum)));//bug:age y soal toolesh bishtar az 28 byte boood?!!!
						sQSec.add(4, (byte)(i+1));
						sQSec.add(5, Byte.parseByte(choices.elementAt(i)));
						sQSec.add(6, (byte)(qNumber));
						int q = 0 , j = 0;
						for(int f = 0; f< packnum ; f++){ //bug(fixed ;)) : age tool soal kam bashe in asan ejra nemishe!!!!!!!!!
							sQSec.add(7, (byte)(f+1));
							for(j = 8 ; j<32 && q<currentSQ.length; j++){
								sQSec.add(j, currentSQ[q]);
								q++;
							}
												
							sQSec.trimToSize();
							byte [] section = new byte[sQSec.size()];
							for(int w = 0 ; w< sQSec.size() ; w++){
								section[w] = sQSec.elementAt(w);
							}
							conn.sendData(section);
							//System.out.println(new String(section, 0, sQSec.size()));
							//clean vector for next proc
							for(int k = sQSec.size() - 1 ; k > 6 ; k--){
								sQSec.removeElementAt(k);
							}
							try {
								Thread.sleep(80);
							} catch (InterruptedException e4) {
								// TODO Auto-generated catch block
								e4.printStackTrace();
							}
						}
					}
                                        
                                        //send Q_ID
                                        Vector<Byte> sQ_idSec = new Vector<Byte>();
						sQ_idSec.add(0 , hexStringToByteArray("7E") [0]);
						sQ_idSec.add(1, (byte)Integer.parseInt(ID));
						sQ_idSec.add(2, hexStringToByteArray("0B") [0]);
						sQ_idSec.add(3, (byte)(qNumber));
                                                int packnum = 0;
                                                if(qNumber%28 == 0)
                                                    packnum = (qNumber/28);
                                                else
                                                    packnum = (qNumber/28)+1;
						int q = 0 , j = 0;
						for(int f = 0; f< packnum ; f++){ //bug(fixed ;)) : age tool soal kam bashe in asan ejra nemishe!!!!!!!!!
							for(j = 4 ; j<32 && q<qNumber; j++){
								sQ_idSec.add(j, (byte)Integer.parseInt(SQ_ID.elementAt(q)));
								q++;
							}			
							
							sQ_idSec.trimToSize();
							byte [] section2 = new byte[sQ_idSec.size()];
							for(int w = 0 ; w< sQ_idSec.size() ; w++){
								section2[w] = sQ_idSec.elementAt(w);
							}
							conn.sendData(section2);
							//System.out.println(new String(section2, 0, sQ_idSec.size()));
							//clean vector for next proc
							for(int k = sQ_idSec.size() - 1 ; k > 6 ; k--){
								sQ_idSec.removeElementAt(k);
							}
							try {
								Thread.sleep(80);
							} catch (InterruptedException e5) {
								// TODO Auto-generated catch block
								e5.printStackTrace();
							}
						}
                                        Manage.flag2 = true;
				}
			
            }
    
        }
public class sendSMS implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
				if(table.getSelectedRow() == -1){
					JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
				}else if(client_idTextField.getText().isEmpty()){
                                     JOptionPane.showMessageDialog(null, "لطفا شناسه گیرنده را وارد نمایید");   
                                }                               
				else{ 
                                    Manage.flag2 = false;
                                    String ID = client_idTextField.getText().toString();
                                    try {
						Thread.sleep(80);
					} catch (InterruptedException ex){
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
					String sendQ = table.getValueAt(table.getSelectedRow(), 0).toString();
					String senQnum = table.getValueAt(table.getSelectedRow(), 1).toString();
					byte [] data = null;
                                    try {
                                        data = sendQ.getBytes("UTF-8");
                                    } catch (UnsupportedEncodingException ex) {
                                        Logger.getLogger(SendTab.class.getName()).log(Level.SEVERE, null, ex);
                                    }
					Vector<Byte> sec = new Vector<Byte>();
					sec.add(0, hexStringToByteArray("7E") [0]);
					sec.add(1, (byte)Integer.parseInt(ID));
					sec.add(2, Byte.parseByte("5"));
					int packnum = 0;
                                        if(data.length%27 == 0)
                                            packnum = data.length/27;
                                        else 
                                            packnum = (data.length/27)+1;
                                        sec.add(3, (byte)(data.length+packnum));
					int q = 0 , j = 0;
					for(int i = 0; i< packnum ; i++){ //bug : age tool soal kam bashe in asan ejra nemishe!!!!!!!!!
						sec.add(4, (byte)(i+1));
						for(j = 5 ; j<32 && q<data.length; j++){
							sec.add(j, data[q]);
							q++;
						}
									
						sec.trimToSize();
						byte [] section = new byte [sec.size()];
						for(int w = 0 ; w< sec.size() ; w++){
							section[w] = sec.elementAt(w);
						}
						conn.sendData(section);
						//System.out.println(new String(section, 0, sec.size()));
						//clean vector for next package
						for(int k = sec.size() - 1 ; k > 3 ; k--){
							sec.removeElementAt(k);
						}
						try {
							Thread.sleep(90);
						} catch (InterruptedException esms) {							
							esms.printStackTrace();
						}
						
					}
					Vector<Byte> qidandchoice = new Vector<Byte>();
					qidandchoice.add(0, hexStringToByteArray("7E") [0]);
					qidandchoice.add(1, (byte)Integer.parseInt(ID));
					qidandchoice.add(2, Byte.parseByte("10"));
					qidandchoice.add(3, Byte.parseByte("3"));
					qidandchoice.add(4, (byte)Integer.parseInt(q_id.elementAt(table.getSelectedRow())));
					qidandchoice.add(5, Byte.parseByte(senQnum));
                                        qidandchoice.add(6, Byte.parseByte(lang.elementAt(table.getSelectedRow())));
					qidandchoice.trimToSize();
					byte [] qnum = new byte[qidandchoice.size()];
					for(int ei = 0 ; ei< qidandchoice.size() ; ei++){
						qnum[ei] = qidandchoice.elementAt(ei);
					}
					//System.out.println(new String(qnum, 0, qidandchoice.size()));
					 conn.sendData(qnum);
                                        try {
							Thread.sleep(90);
						} catch (InterruptedException eu) {							
							eu.printStackTrace();
						}				 
                                        Manage.flag2 = true;
                             }
			
        }
    
        }
   } 