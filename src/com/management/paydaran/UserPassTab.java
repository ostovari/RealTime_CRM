package com.management.paydaran;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class UserPassTab
extends JPanel
implements ActionListener
{
	private JTextField		up_usernameTextField;
	private JTextField		up_oldpassTextField;
	private JTextField		up_newpassTextField;
        Database db;


	public UserPassTab()
	{
		//this.setLayout(new BorderLayout());
		db = new Database();
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		JPanel lablePanel = new JPanel();
		lablePanel.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		lablePanel.add(new JLabel("نام کاربری"));
		lablePanel.add(Box.createHorizontalGlue());
                lablePanel.add(Box.createHorizontalGlue());
                lablePanel.add(Box.createHorizontalGlue());
                lablePanel.add(Box.createHorizontalGlue());
                up_usernameTextField = new JTextField(10);
                lablePanel.add(up_usernameTextField);
                topPanel.add(lablePanel, BorderLayout.CENTER);
                
                JPanel panel2 = new JPanel();
                panel2.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		panel2.add(new JLabel("رمز عیور قبلی"));
		lablePanel.add(Box.createHorizontalGlue());
                up_oldpassTextField = new JTextField(10);
		panel2.add(up_oldpassTextField);
                topPanel.add(panel2, BorderLayout.CENTER);
                
                JPanel panel3 = new JPanel();
                panel3.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		panel3.add(new JLabel("رمز عیور جدید"));
		lablePanel.add(Box.createHorizontalGlue());
                up_newpassTextField = new JTextField(10);
		panel3.add(up_newpassTextField);
                topPanel.add(panel3, BorderLayout.CENTER);
                
                JPanel panel4 = new JPanel();
                panel4.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		JButton passChangebtn = new JButton("تغییر رمز");
                passChangebtn.addActionListener(this);
		panel4.add(passChangebtn);
		panel4.add(Box.createHorizontalGlue());
		JButton cancelbtn = new JButton("انصراف");
		panel4.add(cancelbtn);
		topPanel.add(panel4, BorderLayout.CENTER);
		
		this.add(topPanel, BorderLayout.CENTER);
		
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
            boolean status = false;
            db.openDb();
                ResultSet rs = db.createQuery("SELECT USERNAME, PASSWORD FROM PAYDARAN.TABLE_ACCESSCONTROL "
                    + "WHERE USERNAME = '"+up_usernameTextField.getText().toString()+"' AND PASSWORD = '"+up_oldpassTextField.getText().toString()+"'");
                try {
		if(rs.next()){
                    status = true;
		}else {
                    JOptionPane.showMessageDialog(null, "نام کاربری یا رمز عبور اشتباه است"); 
                    status = false;
                }
                } catch (SQLException ea) {
		// TODO Auto-generated catch block
		ea.printStackTrace();
                }
                db.closeDb();
                if(status){
                    db.openDb();
		String query = "UPDATE PAYDARAN.TABLE_ACCESSCONTROL "
                        + "SET PASSWORD = '"+up_newpassTextField.getText().toString()+"' "
                        + "WHERE USERNAME = '"+up_usernameTextField.getText().toString()+"'"; 
		if(db.updateDb(query)){
                    JOptionPane.showMessageDialog(null, "رمز عبور با موفقیت تغییر یافت");
                    System.out.println("updated...!");
		}else{
                    System.out.println("oooops...!");
		}
		db.closeDb();
                }
		
		
	}







} 



