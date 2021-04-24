package com.management.paydaran;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javafx.scene.control.ComboBox;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class AccessControlTab extends JPanel{
    DefaultTableModel model;
    Vector<Object> editmodel;
    Database db = new Database();
	private JTable table;
        private JTextField              semat;
        private JTextField              username;
        private JTextField              password;
        private JCheckBox               Q_Bqnk_Access;
        private JCheckBox               S_Bqnk_Access;
        private JCheckBox               Diagram_Access;
                JPanel                  addpnl;
                JPanel                  addpnl2;
                JScrollPane             addpnlscrollPane;
        final JRadioButton              neewAccess;
	final JRadioButton              editAccess;
	final JRadioButton              removeAccess;
	
	public AccessControlTab (){
		super();
 
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel basepanel = new JPanel();
		basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
				
		JPanel radioBPanel = new JPanel();
		radioBPanel.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		neewAccess = new JRadioButton("تعیین دسترسی جدبد",true);
		editAccess = new JRadioButton("ویرایش دسترسی");
		removeAccess = new JRadioButton("حذف دسترسی");
		
		neewAccess.setMnemonic(KeyEvent.VK_C);
		editAccess.setMnemonic(KeyEvent.VK_M);
		removeAccess.setMnemonic(KeyEvent.VK_P);

		neewAccess.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {         
		    addpnlscrollPane.setVisible(true);
	         }           
	      });

		editAccess.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {             
	           addpnlscrollPane.setVisible(true);
	         }           
	      });

		removeAccess.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent e) {             
		    addpnlscrollPane.setVisible(false);
	         }           
	      });

	      //Group the radio buttons.
	      ButtonGroup group = new ButtonGroup();
	      group.add(neewAccess);
	      group.add(editAccess);
	      group.add(removeAccess);

	      radioBPanel.add(neewAccess);
	      radioBPanel.add(editAccess);
	      radioBPanel.add(removeAccess); 

	    basepanel.add(radioBPanel);
	    
            JPanel middlepanel = new JPanel();
            middlepanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    JPanel showPanel = new JPanel();
            showPanel.setLayout(new BoxLayout(showPanel, BoxLayout.Y_AXIS));
            Object[] columnNames = {"سمت", "نام کاربری", "رمز عبور", "بانک سوالات", "بانک سناریو", "نمودارها"};
            Object[][] data = null;
            model = new DefaultTableModel(data, columnNames);
            editmodel = new Vector<>();
            db.openDb();
            ResultSet rs = db.createQuery("SELECT TITLE, USERNAME, PASSWORD, Q_BANK, S_BANK, DIAGRAM FROM PAYDARAN.TABLE_ACCESSCONTROL "
                    + "WHERE TITLE != 'admin'");
            try {
		while(rs.next()){
                    //Retrieve by column name
                    model.addRow(new Object[]{rs.getString("TITLE"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getBoolean("Q_BANK"), rs.getBoolean("S_BANK"), rs.getBoolean("DIAGRAM")}); 
		}
            } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
            }
            db.closeDb();
            table = new JTable(model) {
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
                    case 3:
                        return Boolean.class;
                    default:
                        return Boolean.class;
                }
            }
        };
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {       
                if(editAccess.isSelected() && table.isEnabled()){
                semat.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                username.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                password.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                Q_Bqnk_Access.setSelected((boolean)table.getValueAt(table.getSelectedRow(), 3));
                S_Bqnk_Access.setSelected((boolean)table.getValueAt(table.getSelectedRow(), 4));
                Diagram_Access.setSelected((boolean)table.getValueAt(table.getSelectedRow(), 5));
                }
            }
        });
        Dimension d = table.getPreferredSize();
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(
            new Dimension(d.width,table.getRowHeight()*5+1));
        scrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        showPanel.add(scrollPane);
        
        addpnl = new JPanel();
        addpnl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        semat = new JTextField(6);
        username = new JTextField(6);
        password = new JTextField(6);
        Q_Bqnk_Access = new JCheckBox();
        S_Bqnk_Access = new JCheckBox();
        Diagram_Access = new JCheckBox();
        addpnl.add(semat);      
        addpnl.add(username);
        addpnl.add(Box.createHorizontalGlue());
        addpnl.add(password);
        addpnl.add(Box.createHorizontalStrut(16));
        addpnl.add(Q_Bqnk_Access); 
        addpnl.add(Box.createHorizontalStrut(43));
        addpnl.add(S_Bqnk_Access);
        addpnl.add(Box.createHorizontalStrut(44));
        addpnl.add(Diagram_Access);
        addpnlscrollPane = new JScrollPane(addpnl);
        addpnlscrollPane.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        showPanel.add(addpnlscrollPane);
        middlepanel.add(showPanel);
	basepanel.add(middlepanel);
		
		JPanel fnlBtnPanel = new JPanel();
		fnlBtnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		JButton fnlAksept = new JButton("تایید نهایی");
		fnlBtnPanel.add(fnlAksept);
		fnlAksept.addActionListener(new DeployOrder());
		fnlBtnPanel.add(Box.createHorizontalGlue());
		fnlBtnPanel.add(Box.createHorizontalGlue());
		JButton fnlcancelBtn = new JButton("انصراف");
		fnlBtnPanel.add(fnlcancelBtn);
		basepanel.add(fnlBtnPanel);

            this.add(basepanel);
                
            addpnl2 = new JPanel();
            addpnl2.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            addpnl2.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));                
            this.add(addpnl2, BorderLayout.CENTER);
                
                if(PassWordDialog.isadmin){
                    basepanel.setVisible(true);
                    addpnl2.setVisible(false);
                }else{
                    basepanel.setVisible(false);
                    addpnl2.setVisible(true);
                }
	}

    private class DeployOrder implements ActionListener {

        public DeployOrder() {
            
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if(neewAccess.isSelected()){                
                if(!(username.getText().isEmpty()) && !(password.getText().isEmpty()) && !(semat.getText().isEmpty())){
                    db.openDb();
                    String query = "INSERT INTO TABLE_ACCESSCONTROL (TITLE, USERNAME, PASSWORD, Q_BANK, S_BANK, DIAGRAM) VALUES"
                        + " ('"+semat.getText().toString()+"', '"+username.getText().toString()+"', '"+password.getText().toString()
                        +"', "+Q_Bqnk_Access.isSelected()+", "+S_Bqnk_Access.isSelected()+", "+Diagram_Access.isSelected()+")"; 
                    if(db.insertDb(query)){
                        System.out.println("added...!");
                        model.addRow(new Object[]{semat.getText().toString(), username.getText().toString(),
                        password.getText().toString(), Q_Bqnk_Access.isSelected(), S_Bqnk_Access.isSelected(), Diagram_Access.isSelected()});
                        username.setText("");
                        password.setText("");
                        semat.setText("");
                        Q_Bqnk_Access.setSelected(false);
                        S_Bqnk_Access.setSelected(false);
                        Diagram_Access.setSelected(false);
                    }else{
                        System.out.println("oooops...!");
							}
                    db.closeDb();
                }else 
                    JOptionPane.showMessageDialog(null, "لطفا نام کاربری و رمز عبور و سمت را وارد نمایید");
            }else if(editAccess.isSelected()){
                int row = table.getSelectedRow();
                if(!table.getValueAt(table.getSelectedRow(), 0).toString().equals(semat.getText().toString()) || 
                    !table.getValueAt(table.getSelectedRow(), 1).toString().equals(username.getText().toString()) || 
                    !table.getValueAt(table.getSelectedRow(), 2).toString().equals(password.getText().toString()) ||
                    !table.getValueAt(table.getSelectedRow(), 3).toString().equals(Q_Bqnk_Access.toString()) || 
                    !table.getValueAt(table.getSelectedRow(), 4).toString().equals(S_Bqnk_Access.toString()) ||
                    !table.getValueAt(table.getSelectedRow(), 5).toString().equals(Diagram_Access.toString())){
                     db.openDb();
                    String query = "UPDATE PAYDARAN.TABLE_ACCESSCONTROL"
                            + " SET USERNAME = '"+username.getText().toString()+"' , PASSWORD = '"+password.getText().toString()
                        +"', Q_BANK = "+Q_Bqnk_Access.isSelected()+" , S_BANK = "+S_Bqnk_Access.isSelected()+
                            " , DIAGRAM = "+Diagram_Access.isSelected()
                            + " WHERE CAST(USERNAME AS VARCHAR(128)) = '"+table.getValueAt(table.getSelectedRow(), 1).toString()+"'";
                    if(db.updateDb(query)){
			System.out.println("added...!");
                        table.setEnabled(false);
                        model.removeRow(row);
                        model.insertRow(row , new Object[]{semat.getText().toString(), username.getText().toString(),
                        password.getText().toString(), Q_Bqnk_Access.isSelected(), S_Bqnk_Access.isSelected(), Diagram_Access.isSelected()});
                        table.setEnabled(true);
                    }else{
			System.out.println("oooops...!");
			}
                    db.closeDb();
                }
            }else if(removeAccess.isSelected()){               
                if(table.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(null, "لطفا یک ردیف را انتخاب نمایید.");
                }else{
                    String username = model.getValueAt(table.getSelectedRow(), 1).toString();
                    String password = model.getValueAt(table.getSelectedRow(), 2).toString();
                    db.openDb();
                    String query = "DELETE FROM PAYDARAN.TABLE_ACCESSCONTROL WHERE USERNAME = '"+username+"' AND PASSWORD = '"+password+"'"; 
                    if(db.removefromDb(query)){
			System.out.println("removed...!");
                        model.removeRow(table.getSelectedRow());
                    }else{
			System.out.println("oooops...!");
                    }
                    db.closeDb();
                }         
            }
        }
    }
} 



