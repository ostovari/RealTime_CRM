package com.management.paydaran;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.HorizontalDirection;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class QBankTab
extends JPanel
    implements ActionListener, ItemListener, PropertyChangeListener
{
    private JButton			m_connectButton;




	public QBankTab()
	{
		
		super();
	
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                JPanel basepanel = new JPanel();
		basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
                JTabbedPane subTabPane = new JTabbedPane();
                subTabPane.setComponentOrientation(getComponentOrientation()
			.RIGHT_TO_LEFT);
                JComponent	component;
                component = new EmployeeQB();
                subTabPane.add(component, "کارکنان");
		
                component = new CustomerQB();
                subTabPane.add(component, "مشتریان");
                basepanel.add(subTabPane, BorderLayout.CENTER);
                this.add(basepanel, BorderLayout.CENTER);
                JPanel hamel = new JPanel();
                hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
                hamel.setAlignmentX(CENTER_ALIGNMENT);
                this.add(hamel, BorderLayout.CENTER);
                if(PassWordDialog.accessQ || PassWordDialog.isadmin){
                        basepanel.setVisible(true);
                        hamel.setVisible(false);
                }else{
                    basepanel.setVisible(false);
                    hamel.setVisible(true);
                }
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public class EmployeeQB extends JPanel {
		
	    final JRadioButton addQues;
            final JRadioButton editQues;
            final JRadioButton removeQues;
            JTextField questionTxt;
            private JTextField editques;
            JComboBox chsNumber;
            JComboBox editchsNumber;
            JPanel befshowpanel;
            JPanel fnlBtnPanel;
            JPanel listPanel;
            JPanel editpanel;
            JPanel tablepane;
            JScrollPane editpnlscrollPane;
            Database db = new Database();
            private JTable table;
            DefaultTableModel tablemodel;
            JList queslist;
            JList numlist;
            JButton editQbtn;
            JButton editNbtn;
            final JRadioButton persian;
            final JRadioButton english;
            final JRadioButton arabic;
				
            public EmployeeQB (){
			super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			addQues = new JRadioButton("اضافه کردن سوال",true);
			editQues = new JRadioButton("ویرایش سوال");
			removeQues = new JRadioButton("حذف سوال");
			addQues.setMnemonic(KeyEvent.VK_C);
			editQues.setMnemonic(KeyEvent.VK_M);
			removeQues.setMnemonic(KeyEvent.VK_P);

			addQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
                            tablepane.setVisible(false);
                            befshowpanel.setVisible(true);
                            fnlBtnPanel.setVisible(true);
                            editpnlscrollPane.setVisible(false);
		         }           
		      });

			editQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
                            befshowpanel.setVisible(false);
                            tablepane.setVisible(true);
                            fnlBtnPanel.setVisible(true);
                            editpnlscrollPane.setVisible(true);
		         }           
		      });

			removeQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
                            befshowpanel.setVisible(false);
                            tablepane.setVisible(true);
                            fnlBtnPanel.setVisible(true);
                            editpnlscrollPane.setVisible(false);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(addQues);
		      group.add(editQues);
		      group.add(removeQues);

		      slct.add(addQues);
		      slct.add(editQues);
		      slct.add(removeQues); 		    
		      basepanel.add(slct);
		    
                      
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel = new JPanel();
                    radioBPanel.setLayout(new BoxLayout(radioBPanel, BoxLayout.Y_AXIS));
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
	         public void itemStateChanged(ItemEvent e){             
		           //do something
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group2 = new ButtonGroup();
	      group2.add(persian);
	      group2.add(english);
	      group2.add(arabic);

	      radioBPanel.add(persian);
	      radioBPanel.add(english);
	      radioBPanel.add(arabic); 
	    befshowpanel.add(radioBPanel, BorderLayout.NORTH);   
		    JPanel showPanel = new JPanel();
		    showPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		    JPanel Ques = new JPanel();
		    Ques.setLayout(new BoxLayout(Ques, BoxLayout.Y_AXIS));
		    Ques.add(new JLabel("متن سوال"), ComponentOrientation.RIGHT_TO_LEFT);
		    questionTxt = new JTextField(15);
		    Ques.add(questionTxt);
		    showPanel.add(Ques);
                    
		    JPanel chs = new JPanel();
		    chs.setLayout(new BoxLayout(chs, BoxLayout.Y_AXIS));
		    chs.add(new JLabel("تعداد گزینه"));
		    chsNumber = new JComboBox(new String [] {"2" , "3", "4", "5"});
		    chs.add(chsNumber);
		    showPanel.add(chs);                    
                    befshowpanel.add(showPanel);
                    basepanel.add(befshowpanel);			
		JPanel middlepanel = new JPanel();
                middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);    
                tablepane = new JPanel();
		tablepane.setLayout(new BoxLayout(tablepane, BoxLayout.Y_AXIS));
            Object[] columnNames = {"متن سوال", "تعداد گزینه ها"};
            Object[][] data = null;
            tablemodel = new DefaultTableModel(data, columnNames);
            db.openDb();
            ResultSet rs12 = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES"
                                + " WHERE CAST(ANSWERER AS VARCHAR(32672)) = 'کارکنان'");     //+ " WHERE ANSWERER = 'مشتریان'"
            try {
		while(rs12.next()){
                    //Retrieve by column name
                    tablemodel.addRow(new Object[]{rs12.getString("Question"), rs12.getString("Choices")}); 
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
                if(editQues.isSelected() && table.isEnabled()){
                editques.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                editchsNumber.setSelectedItem(table.getValueAt(table.getSelectedRow(), 1).toString());
                }
            }
        });
        
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
        
         editpanel = new JPanel();
         //new GridLayout(1, 6)
        editpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        editques = new JTextField(24);
        editchsNumber = new JComboBox(new String [] {" ", "2" , "3", "4", "5"});
        editpanel.add(editques);      
        editpanel.add(Box.createHorizontalStrut(150));
        editpanel.add(editchsNumber);
        editpnlscrollPane = new JScrollPane(editpanel);
        editpnlscrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tablepane.add(editpnlscrollPane);
        tablepane.setVisible(false);
        middlepanel.add(tablepane);
	basepanel.add(middlepanel);
                                            
            fnlBtnPanel = new JPanel();
			fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			JButton fnlAksept = new JButton("تایید");
			fnlAksept.addActionListener(new EOkListener());
			fnlBtnPanel.add(fnlAksept);
			basepanel.add(fnlBtnPanel);
			
			
			this.add(basepanel, BorderLayout.CENTER);
		}
            
            
            
            public class EOkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
		
					String temp = questionTxt.getText();
					if(befshowpanel.isVisible() && !temp.isEmpty()){
                                                db.openDb();
                                                int lang = 0;
                                                if(persian.isSelected()){
                                                    lang = 0;
                                                }else if(english.isSelected()){
                                                    lang = 1;
                                                }else if(arabic.isSelected()){
                                                    lang = 2;
                                                }
						String query = "INSERT INTO PAYDARAN.TABLE_QUES (QUESTION, ANSWERER, CHOICES, LANGUAGE)"
                                                        + " VALUES ('"+temp+"', 'کارکنان', "+chsNumber.getSelectedItem()+", "+lang+")"; 
						if(db.insertDb(query)){
							questionTxt.setText("");
                                                        tablemodel.addRow(new Object[]{temp, chsNumber.getSelectedItem().toString()});
                                                        SBankTab.question.addElement(temp);
                                                        JOptionPane.showMessageDialog(null, "سوال اضافه شد");                           
						}else{
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
						}
						db.closeDb();
					}else if(tablepane.isVisible() && editQues.isSelected()){
						
                                            if(table.getSelectedRow() == -1){
                                JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
                            }else{
                           String oldQ = table.getValueAt(table.getSelectedRow(), 0).toString();
                            String oldChsNum = table.getValueAt(table.getSelectedRow(), 1).toString();
                            String newQ = editques.getText();
                            String newChsNum = editchsNumber.getSelectedItem().toString();
                            int row = table.getSelectedRow();
                            if(!oldChsNum.equals(newChsNum)){
                                db.openDb();
                                String query = "UPDATE PAYDARAN.TABLE_QUES "
					+"SET Choices = "+newChsNum 
					+"WHERE CAST(Question AS VARCHAR(128)) = '"+oldQ+"' AND ANSWERER = 'کارکنان'"; 
                                if(db.updateDb(query)){
                                    table.setEnabled(false);
                                    tablemodel.removeRow(row);
                                    tablemodel.insertRow(row , new Object[]{oldQ, newChsNum});
                                    table.setEnabled(true);
                                    JOptionPane.showMessageDialog(null, "ویرایش انجام شد");
                                }else{
					JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
                                }
                                db.closeDb();
 
                                    }
                            if(!newQ.isEmpty() && !oldQ.equals(newQ)){ 
				db.openDb();
				String query = "UPDATE PAYDARAN.TABLE_QUES "
					+"SET Question = '"+newQ +"' "
					+"WHERE CAST(Question AS VARCHAR(32672)) = '"+oldQ+"' AND ANSWERER = 'کارکنان'"; 
				if(db.updateDb(query)){
                                    table.setEnabled(false);
                                    tablemodel.removeRow(row);
                                    tablemodel.insertRow(row , new Object[]{newQ, newChsNum});
                                    table.setEnabled(true);
                                    int place = SBankTab.question.indexOf(oldQ);
                                    SBankTab.question.removeElementAt(place);
                                    SBankTab.question.add(place, newQ);
                                    if(oldChsNum.equals(newChsNum))
                                        JOptionPane.showMessageDialog(null, "ویرایش انجام شد");
				}else{
                                    JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
				}
				db.closeDb();
				}             
                            
                            }
                                            
					}else if(tablepane.isVisible() && removeQues.isSelected()){
                                            if(table.getSelectedRow() == -1){
                                                JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
                                            }else{
						String temp2;
                                                int qid = 0 ;
						temp2 = table.getValueAt(table.getSelectedRow(), 0).toString();//CAST(Question AS VARCHAR(128)) = 'T1'
						db.openDb();
                                                ResultSet rsqid = db.createQuery("SELECT Q_ID FROM PAYDARAN.TABLE_QUES"
                                                                +" WHERE CAST(Question AS VARCHAR(32672)) = '"+temp2+"' AND ANSWERER = 'کارکنان'");     //+ " WHERE ANSWERER = 'مشتریان'"
                                                        try {
                                                            while(rsqid.next()){
                                                                //Retrieve by column name
                                                                qid = rsqid.getInt("Q_ID");
                                                            }
                                                        } catch (SQLException efu) {
                                                            // TODO Auto-generated catch block
                                                            efu.printStackTrace();
                                                            }  
						String query = "DELETE FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(32672)) = '"+temp2+"' AND ANSWERER = 'کارکنان'"; 
						if(db.removefromDb(query)){
                                                        tablemodel.removeRow(table.getSelectedRow());
                                                        SBankTab.question.removeElement(temp2);
                                                        JOptionPane.showMessageDialog(null, "حذف شد");
						}else{
							JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
						}
                                                String query2 = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q WHERE Q_ID = "+qid; 
						if(db.removefromDb(query2)){
							//System.out.println("removed CASCADING...!");         
						}else{
							//System.out.println("oooops...!");
						}
						db.closeDb();
                                            }
					}	
                }    
            }                                    
	}
			
public class CustomerQB extends JPanel{
	
        JPanel befshowpanel;
	JTextField questionTxt;
	JComboBox chsNumber;
	JPanel fnlBtnPanel;
        DefaultListModel model;
	DefaultListModel nitem;
	Database db = new Database();
	JList queslist;
	JList numlist;
	JButton editQbtn;
	JButton editNbtn;
        final JRadioButton persian;
        final JRadioButton english;
        final JRadioButton arabic;
        private JTextField editques;
        JComboBox editchsNumber;
        JPanel tablepane;
        JScrollPane editpnlscrollPane;
        private JTable table;
        DefaultTableModel tablemodel;
        JPanel editpanel;
        final JRadioButton addQues;
        final JRadioButton editQues;
        final JRadioButton removeQues;
		
		public CustomerQB (){
			super();
			setLayout(new BorderLayout());
			JPanel basepanel = new JPanel();
			basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
			
			JPanel slct = new JPanel();
			slct.setComponentOrientation(getComponentOrientation()
					.RIGHT_TO_LEFT);
			
			addQues = new JRadioButton("اضافه کردن سوال",true);
			editQues = new JRadioButton("ویرایش سوال");
			removeQues = new JRadioButton("حذف سوال");
			
			addQues.setMnemonic(KeyEvent.VK_C);
			editQues.setMnemonic(KeyEvent.VK_M);
			removeQues.setMnemonic(KeyEvent.VK_P);

			addQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {         
		        	tablepane.setVisible(false);
					befshowpanel.setVisible(true);
					fnlBtnPanel.setVisible(true);
                                        editpnlscrollPane.setVisible(false);
		         }           
		      });

			editQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
					tablepane.setVisible(true);
					fnlBtnPanel.setVisible(true);
                                        editpnlscrollPane.setVisible(true);
					//editQbtn.setVisible(true);
					//editNbtn.setVisible(true);
		         }           
		      });

			removeQues.addItemListener(new ItemListener() {
		         public void itemStateChanged(ItemEvent e) {             
		        	befshowpanel.setVisible(false);
					tablepane.setVisible(true);
					fnlBtnPanel.setVisible(true);
                                        editpnlscrollPane.setVisible(false);
		         }           
		      });

		      //Group the radio buttons.
		      ButtonGroup group = new ButtonGroup();
		      group.add(addQues);
		      group.add(editQues);
		      group.add(removeQues);

		      slct.add(addQues);
		      slct.add(editQues);
		      slct.add(removeQues); 
		      basepanel.add(slct);
		    
                    befshowpanel = new JPanel();
                    befshowpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                    JPanel radioBPanel = new JPanel();
                    radioBPanel.setLayout(new BoxLayout(radioBPanel, BoxLayout.Y_AXIS));
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

	      radioBPanel.add(persian);
	      radioBPanel.add(english);
	      radioBPanel.add(arabic); 
	    befshowpanel.add(radioBPanel, BorderLayout.NORTH);
                      
		     JPanel showPanel = new JPanel();
			    showPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
			    JPanel Ques = new JPanel();
			    Ques.setLayout(new BoxLayout(Ques, BoxLayout.Y_AXIS));
			    Ques.add(new JLabel("متن سوال"));
			    questionTxt = new JTextField(15);
			    Ques.add(questionTxt);
			    
			    showPanel.add(Ques);
			    JPanel chs = new JPanel();
			    chs.setLayout(new BoxLayout(chs, BoxLayout.Y_AXIS));
			    chs.add(new JLabel("تعداد گزینه"));
			    chsNumber = new JComboBox(new String [] {"2" , "3", "4", "5"});
			    chs.add(chsNumber);
			    showPanel.add(chs);
                            befshowpanel.add(showPanel);
				basepanel.add(befshowpanel);		
				
                                JPanel middlepanel = new JPanel();
                middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);    
                tablepane = new JPanel();
		tablepane.setLayout(new BoxLayout(tablepane, BoxLayout.Y_AXIS));
            Object[] columnNames = {"متن سوال", "تعداد گزینه ها"};
            Object[][] data = null;             
            tablemodel = new DefaultTableModel(data, columnNames);
            db.openDb();
            ResultSet rs12 = db.createQuery("SELECT Question, Choices FROM PAYDARAN.TABLE_QUES"
                                + " WHERE CAST(ANSWERER AS VARCHAR(32672)) = 'مشتریان'");     //+ " WHERE ANSWERER = 'مشتریان'"
            try {
		while(rs12.next()){
                    //Retrieve by column name
                    tablemodel.addRow(new Object[]{rs12.getString("Question"), rs12.getString("Choices")}); 
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
                if(editQues.isSelected() && table.isEnabled()){
                editques.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                editchsNumber.setSelectedItem(table.getValueAt(table.getSelectedRow(), 1).toString());
                }
            }
        });
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
        
        editpanel = new JPanel();
         //new GridLayout(1, 6)
        editpanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        editques = new JTextField(24);
        editchsNumber = new JComboBox(new String [] {" ", "2" , "3", "4", "5"});
        editpanel.add(editques);      
        editpanel.add(Box.createHorizontalStrut(150));
        editpanel.add(editchsNumber);
        editpnlscrollPane = new JScrollPane(editpanel);
        editpnlscrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        tablepane.add(editpnlscrollPane);
        tablepane.setVisible(false);
        middlepanel.add(tablepane);
	basepanel.add(middlepanel);                                                               
        fnlBtnPanel = new JPanel();
	fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
	JButton fnlAksept = new JButton("تایید");
	fnlAksept.addActionListener(new COkListener());
	fnlBtnPanel.add(fnlAksept);
	basepanel.add(fnlBtnPanel);								
	this.add(basepanel, BorderLayout.CENTER);
		}
                
                public class COkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
		String temp = questionTxt.getText();
						if(befshowpanel.isVisible() && !temp.isEmpty()){
							db.openDb();
                                                        int lang = 0;
                                                if(persian.isSelected()){
                                                    lang = 0;
                                                }else if(english.isSelected()){
                                                    lang = 1;
                                                }else if(arabic.isSelected()){
                                                    lang = 2;
                                                }
                                                        
							String query = "INSERT INTO PAYDARAN.TABLE_QUES (QUESTION, ANSWERER, CHOICES, LANGUAGE) VALUES ('"+temp+"','مشتریان',"+chsNumber.getSelectedItem()+", "+lang+")"; 
							if(db.insertDb(query)){
                                                                questionTxt.setText("");
                                                                tablemodel.addRow(new Object[]{temp, chsNumber.getSelectedItem().toString()}); 
                                                                SBankTab.question.addElement(temp);
                                                                JOptionPane.showMessageDialog(null, "سوال اضافه شد");      
							}else{
                                                            JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
							}
							db.closeDb();
						}else if(tablepane.isVisible() && editQues.isSelected()){
                                                    if(table.getSelectedRow() == -1){
                                                        JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
                                                    }else{
                                                    String oldQ = table.getValueAt(table.getSelectedRow(), 0).toString();
                                                    String oldChsNum = table.getValueAt(table.getSelectedRow(), 1).toString();
                                                    String newQ = editques.getText();
                                                    String newChsNum = editchsNumber.getSelectedItem().toString();
                                                    int row = table.getSelectedRow();
                                                    if(!oldChsNum.equals(newChsNum)){
                                                        db.openDb();
                                                        String query = "UPDATE PAYDARAN.TABLE_QUES "
								  +"SET Choices = "+newChsNum 
								  +"WHERE CAST(Question AS VARCHAR(128)) = '"+oldQ+"' AND ANSWERER = 'مشتریان'"; 
                                                        if(db.updateDb(query)){
                                                            table.setEnabled(false);
                                                            tablemodel.removeRow(row);
                                                            tablemodel.insertRow(row , new Object[]{oldQ, newChsNum});
                                                            table.setEnabled(true);
                                                            JOptionPane.showMessageDialog(null, "ویرایش انجام شد");
                                                        }else{
                                                            JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
                                                        }
                                                        db.closeDb();
 
                                                    }
                                                    if(!newQ.isEmpty() && !oldQ.equals(newQ)){ 
                                                    db.openDb();
                                                    String query = "UPDATE PAYDARAN.TABLE_QUES "
                                                        +"SET Question = '"+newQ +"' "
                                                        +"WHERE CAST(Question AS VARCHAR(32672)) = '"+oldQ+"' AND ANSWERER = 'مشتریان'"; 
                                                    if(db.updateDb(query)){
                                                        table.setEnabled(false);
                                                        tablemodel.removeRow(row);
                                                        tablemodel.insertRow(row , new Object[]{newQ, newChsNum});
                                                        table.setEnabled(true);
                                                        int place = SBankTab.question.indexOf(oldQ);
                                                        SBankTab.question.removeElementAt(place);
                                                        SBankTab.question.add(place, newQ);
                                                    if(oldChsNum.equals(newChsNum))
                                                        JOptionPane.showMessageDialog(null, "ویرایش انجام شد");
                                                    }else{
                                                        JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
                                                    }
                                                    db.closeDb();
                                                    }             
                            
                                                }	
						}else if(tablepane.isVisible() && removeQues.isSelected()){
                                                    if(table.getSelectedRow() == -1){
                                                        JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید");
                                                    }else {
							 String temp2;
                                                         int qid = 0;
							temp2 = table.getValueAt(table.getSelectedRow(), 0).toString();
							db.openDb();
                                                        ResultSet rsqid = db.createQuery("SELECT Q_ID FROM PAYDARAN.TABLE_QUES"
                                                                +" WHERE CAST(Question AS VARCHAR(32672)) = '"+temp2+"' AND ANSWERER = 'مشتریان'");     //+ " WHERE ANSWERER = 'مشتریان'"
                                                        try {
                                                            while(rsqid.next()){
                                                                //Retrieve by column name
                                                                qid = rsqid.getInt("Q_ID");
                                                            }
                                                        } catch (SQLException efu) {
                                                            // TODO Auto-generated catch block
                                                            efu.printStackTrace();
                                                            }
							String query = "DELETE FROM PAYDARAN.TABLE_QUES WHERE CAST(Question AS VARCHAR(32672)) = '"+temp2+"' AND ANSWERER = 'مشتریان'"; 
							if(db.removefromDb(query)){
                                                            tablemodel.removeRow(table.getSelectedRow());
                                                            SBankTab.question.removeElement(temp2);
                                                            JOptionPane.showMessageDialog(null, "حذف شد");
							}else{
								JOptionPane.showMessageDialog(null, "خطا لطفا دوباره تلاش کنید");
							}
                                                        String query2 = "DELETE FROM PAYDARAN.TABLE_SENARIO_Q WHERE Q_ID = "+qid; 
                                                        if(db.removefromDb(query2)){
                                                            //System.out.println("removed CASCADING...!");         
                                                        }else{
                                                            //System.out.println("oooops...!");
                                                        }
							db.closeDb();
                                                    }
						}	
                }
    
            }
	}
	
}

/*** QBankTab.java ***/