package com.management.paydaran;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.*;
import javax.sound.sampled.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SBankTab extends JPanel implements ActionListener, ItemListener,
						  ChangeListener, PropertyChangeListener {
    static DefaultListModel question;
    Database db = new Database();

    public SBankTab() {
		
	super();
        
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
             JPanel basepanel = new JPanel();
            basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
            
            question = new DefaultListModel();
            db.openDb();
            ResultSet rs2 = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES");
            try {
		while(rs2.next()){
			//Retrieve by column name
			question.addElement(rs2.getString("Question"));
				  }
            } catch (SQLException e) {
				// TODO Auto-generated catch block
            e.printStackTrace();			}
             db.closeDb();            
            JTabbedPane subTabPane = new JTabbedPane();
            subTabPane.setComponentOrientation(getComponentOrientation()
		.RIGHT_TO_LEFT);
            JComponent	component;
            component = new EmployeeSB();
            subTabPane.add(component, "کارکنان");		
            component = new CustomerSB();
            subTabPane.add(component, "مشتریان");
            basepanel.add(subTabPane);
            this.add(basepanel, BorderLayout.CENTER);
            JPanel hamel = new JPanel();
            hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
            hamel.setAlignmentX(CENTER_ALIGNMENT);
            this.add(hamel, BorderLayout.CENTER);
            if(PassWordDialog.accessS || PassWordDialog.isadmin){
                basepanel.setVisible(true);
                hamel.setVisible(false);
            }else{
            basepanel.setVisible(false);
            hamel.setVisible(true);
        }
	}
    
    public void itemStateChanged(ItemEvent e) {}

    public void stateChanged(ChangeEvent e) {}
    
    private void enableButtons() {}
	


    public void propertyChange(PropertyChangeEvent e) {}

    private void startLevelMeterThread() {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
public class EmployeeSB extends JPanel{
		
	JPanel befshowpanel;
	JPanel addeqpanel;
	JPanel allqpanel;
	JPanel editSPanel;
	JTextField senarioTxt;
	JList allQ;
	JList addedQ;
	DefaultListModel model;
	JButton addQ;
	JPanel addSpanel;
	JButton removeQ;
        final JRadioButton persian;
        final JRadioButton english;
        final JRadioButton arabic;
        JPanel tablepane;
        JScrollPane editpnlscrollPane;
        private JTable table;
        DefaultTableModel tablemodel;
        JButton fnlAksept;       
		
		public EmployeeSB (){
			super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
                        
			JPanel radioBPanel = new JPanel();
			radioBPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			final JRadioButton neewSenario = new JRadioButton("تعریف سناریو جدید",true);
			final JRadioButton add2Senario = new JRadioButton("افزودن به سناریو");
			final JRadioButton removeFSenario = new JRadioButton("حذف از سناریو");
			final JRadioButton editSenario = new JRadioButton("ویرایش سناریو");
			
			neewSenario.setMnemonic(KeyEvent.VK_C);
			add2Senario.setMnemonic(KeyEvent.VK_M);
			removeFSenario.setMnemonic(KeyEvent.VK_P);
			 editSenario.setMnemonic(KeyEvent.VK_Q);

			neewSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	befshowpanel.setVisible(true);
		        	tablepane.setVisible(false);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				addeqpanel.setVisible(false);
				removeQ.setVisible(false);
				editSPanel.setVisible(false);
                                fnlAksept.setVisible(true);
		         }           
		      });

			add2Senario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
		        	tablepane.setVisible(true);
				removeQ.setVisible(false);
				editSPanel.setVisible(false);
                                fnlAksept.setVisible(false);
                                if(table.getSelectedRow() !=-1){
                                    allqpanel.setVisible(true);
                                    addQ.setVisible(true);
                                    addeqpanel.setVisible(true);
                                }
		         }           
		      });

			removeFSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {  
		        	befshowpanel.setVisible(false);
		        	tablepane.setVisible(true);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				editSPanel.setVisible(false);
                                fnlAksept.setVisible(false);
                                if(table.getSelectedRow() !=-1){
                                    addeqpanel.setVisible(true);
                                    removeQ.setVisible(true);
                                }
		         }           
		      });
			
			editSenario.addItemListener(new ItemListener() {
		        public void itemStateChanged(ItemEvent e) {             
                                befshowpanel.setVisible(false);
		        	tablepane.setVisible(true);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				addeqpanel.setVisible(false);
				removeQ.setVisible(false);
				editSPanel.setVisible(true);
                                fnlAksept.setVisible(false);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(neewSenario);
		      group.add(add2Senario);
		      group.add(removeFSenario);
		      group.add(editSenario);

		      radioBPanel.add(neewSenario);
		      radioBPanel.add(add2Senario);
		      radioBPanel.add(removeFSenario); 
		      radioBPanel.add(editSenario);
		    slct.add(radioBPanel, BorderLayout.EAST);
		    basepanel.add(slct);
		    
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel2 = new JPanel();
                    radioBPanel2.setLayout(new BoxLayout(radioBPanel2, BoxLayout.Y_AXIS));
                    persian = new JRadioButton("فارسی",true);
                    english = new JRadioButton("انگلیسی");
                    arabic = new JRadioButton("عربی");
		
                    persian.setMnemonic(KeyEvent.VK_C);
                    english.setMnemonic(KeyEvent.VK_M);
                    arabic.setMnemonic(KeyEvent.VK_P);
                    
		persian.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
		           //do something
	         }           
	      });

		english.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
	           //do something
	         }           
	      });

		arabic.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
		           //do something
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group2 = new ButtonGroup();
	      group2.add(persian);
	      group2.add(english);
	      group2.add(arabic);

	      radioBPanel2.add(persian);
	      radioBPanel2.add(english);
	      radioBPanel2.add(arabic); 
	    befshowpanel.add(radioBPanel2, BorderLayout.NORTH);  
		   JPanel senarioNam = new JPanel();
		    senarioNam.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    senarioNam.add(new JLabel("نام سناریو"), new BorderLayout());
		    senarioTxt = new JTextField(15);
		    senarioNam.add(senarioTxt);
                    befshowpanel.add(senarioNam);
		    basepanel.add(befshowpanel);                                      	                   
                    JPanel middlepanel = new JPanel();
                middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);    
                tablepane = new JPanel();
		tablepane.setLayout(new BoxLayout(tablepane, BoxLayout.Y_AXIS));
            Object[] columnNames = {"نام سناریو", "تعداد سوال"};
            Object[][] data = null;
            tablemodel = new DefaultTableModel(data, columnNames);
            db.openDb();
            ResultSet rs12 = db.createQuery("SELECT PAYDARAN.TABLE_SENARIO.S_name , p.Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO LEFT JOIN" +
					" (" +
					" SELECT PAYDARAN.TABLE_SENARIO_Q.S_name , COUNT(Q_id) as Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO_Q" +
					" Group By PAYDARAN.TABLE_SENARIO_Q.S_name " +
					") as p" +
					" on PAYDARAN.TABLE_SENARIO.S_name = p.S_name" +
                                        " WHERE ANSWERER = 'کارکنان'");
            try {
		while(rs12.next()){
                    //Retrieve by column name
                    tablemodel.addRow(new Object[]{rs12.getString("S_name"), rs12.getInt("Qnumber")+""}); 
		}
            } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
            db.closeDb();
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
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) { 
                if(add2Senario.isSelected()){
                    allqpanel.setVisible(true);
                    addQ.setVisible(true);
                    addeqpanel.setVisible(true);
                    model.clear();
                    String senar = table.getValueAt(table.getSelectedRow(), 0).toString();
                    db.openDb();//CAST(S_name AS VARCHAR(128)) =
                    ResultSet rs3 = db.createQuery("SELECT Question" +
				" FROM PAYDARAN.TABLE_QUES" +
				" WHERE Q_id IN (SELECT Q_id" +
				" FROM PAYDARAN.TABLE_SENARIO_Q" +
				" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senar+"')");
			try {
                            while(rs3.next()){
                                //Retrieve by column name
				model.addElement(rs3.getString("Question"));
                            }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
                            db.closeDb();
			}else if(removeFSenario.isSelected()){
                            removeQ.setVisible(true);
                            addeqpanel.setVisible(true);
                            model.clear();
                            String senar = table.getValueAt(table.getSelectedRow(), 0).toString();
                            db.openDb();
                            ResultSet rs3 = db.createQuery("SELECT Question" +
				" FROM PAYDARAN.TABLE_QUES" +
				" WHERE Q_id IN (SELECT Q_id" +
				" FROM PAYDARAN.TABLE_SENARIO_Q" +
				" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senar+"')");
						try {
							while(rs3.next()){
							     //Retrieve by column name
								model.addElement(rs3.getString("Question"));
							  }
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						db.closeDb();
					} else if(editSenario.isSelected()){
						
					} 
            }
        });
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        table.getColumn("نام سناریو").setCellRenderer( rightRenderer );
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getColumn("تعداد سوال").setCellRenderer( centerRenderer );
        
        Dimension d = table.getPreferredSize();
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(
             new Dimension(d.width*4, table.getRowHeight()*7+1));
        scrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tablepane.add(scrollPane);
        tablepane.setVisible(false);
        middlepanel.add(tablepane);
	basepanel.add(middlepanel);
                    
		    
		    addSpanel = new JPanel();
		    addSpanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);   
		    allqpanel = new JPanel();
		    allqpanel.setLayout(new BoxLayout(allqpanel, BoxLayout.Y_AXIS));
                    JScrollPane scroll3 = new JScrollPane(new JLabel("سوالات موجود"));
                        scroll3.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    allqpanel.add(scroll3);
		    allQ = new JList(question);
                    allQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JScrollPane qscroll = new JScrollPane(allQ);
                    qscroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    qscroll.setPreferredSize(new Dimension(400, 200));     
		    allqpanel.add(qscroll);
		    allqpanel.setVisible(false);
		    addSpanel.add(allqpanel);
		    
		    addQ = new JButton("اضافه شود به سناریو >>");
		    addQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(allQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = allQ.getSelectedValue().toString();
						String sentemp = table.getValueAt(table.getSelectedRow(), 0).toString();
						int qID = 0;
                                                int sID = 0;
                                                
						String query1 = "SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(32672)) = '"+questemp+"'";
						db.openDb();						
						ResultSet rs3 = db.createQuery(query1);
						try {
							while(rs3.next()){
							     //Retrieve by column name
								qID = rs3.getInt("Q_id");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                                                String querysid = "SELECT S_ID FROM PAYDARAN.TABLE_Senario"
                                                        + " WHERE CAST(S_NAME AS VARCHAR(32672)) = '"+sentemp+"'";
                                                ResultSet rs12 = db.createQuery(querysid);
						try {
							while(rs12.next()){
							     //Retrieve by column name
								sID = rs12.getInt("S_ID");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                                                
                                                
						String query2 = "INSERT INTO PAYDARAN.TABLE_SENARIO_Q VALUES ('"+sentemp+"',"+qID+", "+sID+")"; 
						if(db.insertDb(query2)){
							model.addElement(questemp);
                                                        int newqnum = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString())+1;
                                                        tablemodel.setValueAt(newqnum+"", table.getSelectedRow(), 1);
						}else{
                                                        JOptionPane.showMessageDialog(null, "سوال مورد نظر در سناریو موجود است");
						}
						db.closeDb();
					}
					
				}
			});
		    addQ.setVisible(false);
		    addSpanel.add(addQ);
		    
		    addeqpanel = new JPanel();
		    addeqpanel.setLayout(new BoxLayout(addeqpanel, BoxLayout.Y_AXIS));
		    model = new DefaultListModel();
		    addedQ = new JList(model);
                    addedQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JScrollPane scroll4 = new JScrollPane(new JLabel("سوالات اضافه شده"));
                        scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    addeqpanel.add(scroll4);
		    JScrollPane qscroll2 = new JScrollPane(addedQ);
                    qscroll2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    qscroll2.setPreferredSize(new Dimension(300, 200));     
		    addeqpanel.add(qscroll2);
		    addeqpanel.setVisible(false);
		    addSpanel.add(addeqpanel);
		    
		    removeQ = new JButton("حذف");
		    removeQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(addedQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = addedQ.getSelectedValue().toString();
						String sentemp = table.getValueAt(table.getSelectedRow(), 0).toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(32672)) = '"+sentemp+"' AND Q_id in " +
								"(SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(32672)) = '"+questemp+"' )";
						db.openDb();						
						if(db.removefromDb(query)){
							//System.out.println("removed...!");
							model.removeElement(questemp);
                                                        int newqnum = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString())-1;
                                                        tablemodel.setValueAt(newqnum+"", table.getSelectedRow(), 1);
						}
						else
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
						db.closeDb();
					}
					
				}
			});
		    removeQ.setVisible(false);
		    basepanel.add(addSpanel);
                    basepanel.add(removeQ);
		    
		    editSPanel = new JPanel();
		    editSPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    JButton editNamS = new JButton("تغییر نام سناریو");
		    editNamS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(table.getSelectedRow() == -1){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String oldS = table.getValueAt(table.getSelectedRow(), 0).toString();
						String newS = null;
                                                newS = JOptionPane.showInputDialog(editNamS, "تغییر نام سناریو", oldS);
                                                if(!newS.isEmpty() && !oldS.equals(newS)){
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_SENARIO "
									  +"SET S_name = '"+newS +"' "
									  +"WHERE CAST(S_name AS VARCHAR(32672)) = '"+oldS+"' AND ANSWERER = 'کارکنان'"; 
						if(db.updateDb(query)){
							//System.out.println("updated...!");
                                                        tablemodel.setValueAt(newS, table.getSelectedRow(), 0);
						}else{
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
						}
                                                String query2 = "UPDATE PAYDARAN.TABLE_SENARIO_Q "
									  +"SET S_name = '"+newS +"' "
									  +"WHERE CAST(S_name AS VARCHAR(32672)) = '"+oldS+"'"; 
						if(db.updateDb(query2)){
							//System.out.println("updated...!");
						}else{
							//System.out.println("oooops...!");
						}
						db.closeDb();
                                            }
					}
				}
			});
		    editSPanel.add(editNamS);
		    JButton removeS = new JButton("حذف سناریو");
		    removeS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(table.getSelectedRow() == -1){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String senarioname = table.getValueAt(table.getSelectedRow(), 0).toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO" +
								" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senarioname+"' AND ANSWERER = 'کارکنان'";
						db.openDb();						
						if(db.removefromDb(query)){
                                                    JOptionPane.showMessageDialog(null, "حذف شد");
							//System.out.println("removed...!");
                                                        tablemodel.removeRow(table.getSelectedRow());
						}
						else
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
                                                String query2 = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senarioname+"'";
						if(db.removefromDb(query2)){
							//System.out.println("removed...!");
						}
						else
							//System.out.println("oooops. try again!! ");
						db.closeDb();
					}
				}
			});
		    editSPanel.add(removeS);
		    editSPanel.setVisible(false);
		    basepanel.add(editSPanel);
		    
			JPanel fnlBtnPanel = new JPanel();
			fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			fnlAksept = new JButton("تایید");
			fnlAksept.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String temp = senarioTxt.getText();
				if(befshowpanel.isVisible() && neewSenario.isSelected() && !temp.isEmpty()){
					db.openDb();
                                        int lang = 0;
                                         if(persian.isSelected()){
                                            lang = 0;
                                        }else if(english.isSelected()){
                                            lang = 1;
                                        }else if(arabic.isSelected()){
                                            lang = 2;
                                        }
					String query = "INSERT INTO PAYDARAN.TABLE_SENARIO (S_NAME, ANSWERER, LANG) VALUES ('"+temp+"','کارکنان', "+lang+")"; 
					if(db.insertDb(query)){
                                                senarioTxt.setText("");
                                                tablemodel.addRow(new Object[]{temp, "0"});
                                                JOptionPane.showMessageDialog(null, "سناریو جدید ایجاد شد");
                                                
					}else{
						JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
					}
					db.closeDb();
				}
					
				}
			});
			fnlBtnPanel.add(fnlAksept);
			basepanel.add(fnlBtnPanel);
			
			
			this.add(basepanel, BorderLayout.CENTER);
		}
	}

public class CustomerSB extends JPanel{
	JPanel befshowpanel;
	JPanel addeqpanel;
	JPanel allqpanel;
	JPanel editSPanel;
	JTextField senarioTxt;
	JList allQ;
	JList addedQ;
	DefaultListModel model;
	Database db = new Database();
	JButton addQ;
	JPanel addSpanel;
	JButton removeQ;
        final JRadioButton persian;
        final JRadioButton english;
        final JRadioButton arabic;
        JPanel tablepane;
        JScrollPane editpnlscrollPane;
        private JTable table;
        DefaultTableModel tablemodel;
        JButton fnlAksept;
	
	public CustomerSB (){
		super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			
			JPanel radioBPanel = new JPanel();
			radioBPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			final JRadioButton neewSenario = new JRadioButton("تعریف سناریو جدید",true);
			final JRadioButton add2Senario = new JRadioButton("افزودن به سناریو");
			final JRadioButton removeFSenario = new JRadioButton("حذف از سناریو");
			final JRadioButton editSenario = new JRadioButton("ویرایش سناریو");
			
			neewSenario.setMnemonic(KeyEvent.VK_C);
			add2Senario.setMnemonic(KeyEvent.VK_M);
			removeFSenario.setMnemonic(KeyEvent.VK_P);
			editSenario.setMnemonic(KeyEvent.VK_Q);

			neewSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	befshowpanel.setVisible(true);
		        	tablepane.setVisible(false);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
                                addeqpanel.setVisible(false);
				removeQ.setVisible(false);
                            editSPanel.setVisible(false);
                            fnlAksept.setVisible(true);
		         }           
		      });

			add2Senario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
		        	tablepane.setVisible(true);
				removeQ.setVisible(false);
				editSPanel.setVisible(false);
                                fnlAksept.setVisible(false);
                                if(table.getSelectedRow() !=-1){
                                    allqpanel.setVisible(true);
                                    addQ.setVisible(true);
                                    addeqpanel.setVisible(true);
                                }
		         }           
		      });

			removeFSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {  
		        	 befshowpanel.setVisible(false);
		        	tablepane.setVisible(true);
		        	allqpanel.setVisible(false);
				addQ.setVisible(false);
				editSPanel.setVisible(false);
                                fnlAksept.setVisible(false);
                                if(table.getSelectedRow() !=-1){
                                    addeqpanel.setVisible(true);
                                    removeQ.setVisible(true);
                                }
		         }           
		      });
			
			editSenario.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	 befshowpanel.setVisible(false);
		        	 tablepane.setVisible(true);
		        	 allqpanel.setVisible(false);
						addQ.setVisible(false);
						addeqpanel.setVisible(false);
						removeQ.setVisible(false);
						editSPanel.setVisible(true);
                                                fnlAksept.setVisible(false);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(neewSenario);
		      group.add(add2Senario);
		      group.add(removeFSenario);
		      group.add(editSenario);

		      radioBPanel.add(neewSenario);
		      radioBPanel.add(add2Senario);
		      radioBPanel.add(removeFSenario); 
		      radioBPanel.add(editSenario);
		    slct.add(radioBPanel, BorderLayout.EAST);
		    basepanel.add(slct);
		    
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel2 = new JPanel();
                    radioBPanel2.setLayout(new BoxLayout(radioBPanel2, BoxLayout.Y_AXIS));
                    persian = new JRadioButton("فارسی",true);
                    english = new JRadioButton("انگلیسی");
                    arabic = new JRadioButton("عربی");
		
                    persian.setMnemonic(KeyEvent.VK_C);
                    english.setMnemonic(KeyEvent.VK_M);
                    arabic.setMnemonic(KeyEvent.VK_P);
                    
		persian.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
		           //do something
	         }           
	      });

		english.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
	           //do something
	         }           
	      });

		arabic.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {             
		           //do something
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group2 = new ButtonGroup();
	      group2.add(persian);
	      group2.add(english);
	      group2.add(arabic);

	      radioBPanel2.add(persian);
	      radioBPanel2.add(english);
	      radioBPanel2.add(arabic); 
	    befshowpanel.add(radioBPanel2, BorderLayout.NORTH);
                    
		   JPanel senarioNam = new JPanel();
		    senarioNam.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    senarioNam.add(new JLabel("نام سناریو"), new BorderLayout());
		    senarioTxt = new JTextField(15);
		    senarioNam.add(senarioTxt);
                    befshowpanel.add(senarioNam);
		    basepanel.add(befshowpanel);		    		    
                    
                    JPanel middlepanel = new JPanel();
                middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);    
                tablepane = new JPanel();
		tablepane.setLayout(new BoxLayout(tablepane, BoxLayout.Y_AXIS));
            Object[] columnNames = {"نام سناریو", "تعداد سوال"};
            Object[][] data = null;

            tablemodel = new DefaultTableModel(data, columnNames);
            db.openDb();
            ResultSet rs12 = db.createQuery("SELECT PAYDARAN.TABLE_SENARIO.S_name , p.Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO LEFT JOIN" +
					" (" +
					" SELECT PAYDARAN.TABLE_SENARIO_Q.S_name , COUNT(Q_id) as Qnumber" +
					" FROM PAYDARAN.TABLE_SENARIO_Q" +
					" Group By PAYDARAN.TABLE_SENARIO_Q.S_name " +
					") as p" +
					" on PAYDARAN.TABLE_SENARIO.S_name = p.S_name" + 
                                        " WHERE ANSWERER = 'مشتریان'");
            try {
		while(rs12.next()){
                    //Retrieve by column name
                    tablemodel.addRow(new Object[]{rs12.getString("S_name"), rs12.getInt("Qnumber")+""}); 
		}
            } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
            db.closeDb();
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
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) { 
                if(add2Senario.isSelected()){
                    allqpanel.setVisible(true);
                    addQ.setVisible(true);
                    addeqpanel.setVisible(true);
                    model.clear();
                    String senar = table.getValueAt(table.getSelectedRow(), 0).toString();
                    db.openDb();//CAST(S_name AS VARCHAR(128)) =
                    ResultSet rs3 = db.createQuery("SELECT Question" +
				" FROM PAYDARAN.TABLE_QUES" +
				" WHERE Q_id IN (SELECT Q_id" +
				" FROM PAYDARAN.TABLE_SENARIO_Q" +
				" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senar+"')");
			try {
                            while(rs3.next()){
                                //Retrieve by column name
				model.addElement(rs3.getString("Question"));
                            }
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
                            db.closeDb();
			}else if(removeFSenario.isSelected()){
                            removeQ.setVisible(true);
                            addeqpanel.setVisible(true);
                            model.clear();
                            String senar = table.getValueAt(table.getSelectedRow(), 0).toString();
                            db.openDb();
                            ResultSet rs3 = db.createQuery("SELECT Question" +
				" FROM PAYDARAN.TABLE_QUES" +
				" WHERE Q_id IN (SELECT Q_id" +
				" FROM PAYDARAN.TABLE_SENARIO_Q" +
				" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senar+"')");
						try {
							while(rs3.next()){
							     //Retrieve by column name
								model.addElement(rs3.getString("Question"));
							  }
						} catch (SQLException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						db.closeDb();
					} else if(editSenario.isSelected()){
						
					} 
            }
        });
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
        table.getColumn("نام سناریو").setCellRenderer( rightRenderer );
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getColumn("تعداد سوال").setCellRenderer( centerRenderer );
        
        Dimension d = table.getPreferredSize();
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(
             new Dimension(d.width*4, table.getRowHeight()*7+1));
        scrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tablepane.add(scrollPane);
        tablepane.setVisible(false);
        middlepanel.add(tablepane);
	basepanel.add(middlepanel);
                    
                    
                    
		    addSpanel = new JPanel();
		    addSpanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    allqpanel = new JPanel();
		    allqpanel.setLayout(new BoxLayout(allqpanel, BoxLayout.Y_AXIS));
                    JScrollPane scroll3 = new JScrollPane(new JLabel("سوالات موجود"));
                        scroll3.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    allqpanel.add(scroll3);
		    allQ = new JList(question);
                    allQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JScrollPane qscroll = new JScrollPane(allQ);
                    qscroll.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    qscroll.setPreferredSize(new Dimension(400, 200));     
		    allqpanel.add(qscroll);
		    allqpanel.setVisible(false);
		    addSpanel.add(allqpanel);
		    
		    addQ = new JButton("اضافه شود به سناریو >>");
		    addQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(allQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = allQ.getSelectedValue().toString();
						String sentemp = table.getValueAt(table.getSelectedRow(), 0).toString();
						int qID = 0;
                                                int sID = 0;
                                                
						String query1 = "SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(32672)) = '"+questemp+"'";
						db.openDb();						
						ResultSet rs3 = db.createQuery(query1);
						try {
							while(rs3.next()){
							     //Retrieve by column name
								qID = rs3.getInt("Q_id");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                                                String querysid = "SELECT S_ID FROM PAYDARAN.TABLE_Senario"
                                                        + " WHERE CAST(S_NAME AS VARCHAR(32672)) = '"+sentemp+"' AND ANSWERER = 'مشتریان'";
                                                ResultSet rs12 = db.createQuery(querysid);
						try {
							while(rs12.next()){
							     //Retrieve by column name
								sID = rs12.getInt("S_ID");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                                                
                                                
						String query2 = "INSERT INTO PAYDARAN.TABLE_SENARIO_Q VALUES ('"+sentemp+"',"+qID+", "+sID+")"; 
						if(db.insertDb(query2)){
							 //System.out.println("added...!");
							model.addElement(questemp);
                                                        int newqnum = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString())+1;
                                                        tablemodel.setValueAt(newqnum+"", table.getSelectedRow(), 1);
						}else{
							//System.out.println("oooops. try again!!");
                                                        JOptionPane.showMessageDialog(null, "سوال مورد نظر در سناریو موجود است");
						}
						db.closeDb();
					}
					
				}
			});
		    addQ.setVisible(false);
		    addSpanel.add(addQ);
		    
		    addeqpanel = new JPanel();
		    addeqpanel.setLayout(new BoxLayout(addeqpanel, BoxLayout.Y_AXIS));
		    model = new DefaultListModel();
		    addedQ = new JList(model);
                    addedQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JScrollPane scroll4 = new JScrollPane(new JLabel("سوالات اضافه شده"));
                        scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		    addeqpanel.add(scroll4);
                    JScrollPane qscroll2 = new JScrollPane(addedQ);
                    qscroll2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    qscroll2.setPreferredSize(new Dimension(300, 200));     
		    addeqpanel.add(qscroll2);
		    addeqpanel.setVisible(false);
		    addSpanel.add(addeqpanel);
		    
		    removeQ = new JButton("حذف");
		    removeQ.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(addedQ.isSelectionEmpty())
						JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
					else{
						String questemp = addedQ.getSelectedValue().toString();
						String sentemp = table.getValueAt(table.getSelectedRow(), 0).toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(32672)) = '"+sentemp+"' AND Q_id in " +
								"(SELECT Q_id FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(32672)) = '"+questemp+"' )";
						db.openDb();						
						if(db.removefromDb(query)){
							//System.out.println("removed...!");
							model.removeElement(questemp);
                                                        int newqnum = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 1).toString())-1;
                                                        tablemodel.setValueAt(newqnum+"", table.getSelectedRow(), 1);
						}
						else
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
						db.closeDb();
					}
					
				}
			});
		    removeQ.setVisible(false);
		    //addSpanel.add(removeQ);
		    basepanel.add(addSpanel);
                    basepanel.add(removeQ);
		    
		    editSPanel = new JPanel();
		    editSPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    JButton editNamS = new JButton("تغییر نام سناریو");
		    editNamS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(table.getSelectedRow() == -1){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String oldS = table.getValueAt(table.getSelectedRow(), 0).toString();
						String newS = JOptionPane.showInputDialog(null, "تغییر نام سناریو", oldS);
						db.openDb();
						String query = "UPDATE PAYDARAN.TABLE_SENARIO "
									  +"SET S_name = '"+newS +"' "
									  +"WHERE CAST(S_name AS VARCHAR(32672)) = '"+oldS+"' AND ANSWERER = 'مشتریان'"; 
						if(db.updateDb(query)){
							//System.out.println("updated...!");
                                                        tablemodel.setValueAt(newS, table.getSelectedRow(), 0);
						}else{
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
						}
                                                String query2 = "UPDATE PAYDARAN.TABLE_SENARIO_Q "
									  +"SET S_name = '"+newS +"' "
									  +"WHERE CAST(S_name AS VARCHAR(32672)) = '"+oldS+"'"; 
						if(db.updateDb(query2)){
							//System.out.println("updated...!");
						}else{
							//System.out.println("oooops...!");
						}
						db.closeDb();
					}
				}
			});
		    editSPanel.add(editNamS);
		    JButton removeS = new JButton("حذف سناریو");
		    removeS.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(table.getSelectedRow() == -1){
						JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید");
					}else{
						String senarioname = table.getValueAt(table.getSelectedRow(), 0).toString();
						String query = "DELETE FROM PAYDARAN.TABLE_SENARIO" +
								" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senarioname+"' AND ANSWERER = 'مشتریان'";
						db.openDb();						
						if(db.removefromDb(query)){
							//System.out.println("removed...!");
                                                        tablemodel.removeRow(table.getSelectedRow());
						}
						else
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
                                                String query2 = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q" +
								" WHERE CAST(S_name AS VARCHAR(32672)) = '"+senarioname+"'";
						if(db.removefromDb(query2)){
							//System.out.println("removed...!");
						}
						else
							//System.out.println("oooops. try again!! ");
						db.closeDb();
					}
				}
			});
		    editSPanel.add(removeS);
		    editSPanel.setVisible(false);
		    basepanel.add(editSPanel);
		    
			JPanel fnlBtnPanel = new JPanel();
			fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			fnlAksept = new JButton("تایید");
			fnlAksept.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String temp = senarioTxt.getText();
				if(befshowpanel.isVisible() && neewSenario.isSelected() && !temp.isEmpty()){
					db.openDb();
                                        int lang = 0;
                                         if(persian.isSelected()){
                                            lang = 0;
                                        }else if(english.isSelected()){
                                            lang = 1;
                                        }else if(arabic.isSelected()){
                                            lang = 2;
                                        }
					String query = "INSERT INTO PAYDARAN.TABLE_SENARIO (S_NAME, ANSWERER, LANG) VALUES ('"+temp+"','مشتریان', "+lang+")"; 
					if(db.insertDb(query)){
						//System.out.println("added...!");
                                                senarioTxt.setText("");
                                                tablemodel.addRow(new Object[]{temp, "0"});
                                                JOptionPane.showMessageDialog(null, "سناریو جدید ایجاد شد");
                                                
					}else{
						JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
					}
					db.closeDb();
				}
					
					
				}
			});
			fnlBtnPanel.add(fnlAksept);
			basepanel.add(fnlBtnPanel);
			
			
			this.add(basepanel, BorderLayout.CENTER);
		}
	
    }
}
/*** SBankTab.java ***/