package com.management.paydaran;

import java.awt.BorderLayout;
import javafx.application.Platform;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ManagePane
extends JPanel
{
    private JLabel		m_statusLabel;
    SerialComm conn;
        
	public ManagePane(SerialComm sercomm)
	{
		super();
                 conn = sercomm;
		setLayout(new BorderLayout());
		JComponent tabbed = createTabbedPane();
		this.add(tabbed, BorderLayout.CENTER);
		m_statusLabel = new JLabel();
		 this.add(m_statusLabel, BorderLayout.SOUTH);

		setStatusLine(" ");
	}

	public JComponent createTabbedPane()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		JComponent	component;
		component = new QBankTab();
		tabbedPane.add(component, "بانک سوالات");

		component = new SBankTab();
		tabbedPane.add(component, "بانک سناریوها");
		
		component = new SendTab(conn);
		tabbedPane.add(component, "ارسال");

		component = new DiagramTab();
		tabbedPane.add(component, "نمودار");
		
		component = new UserPassTab();
		tabbedPane.add(component, "تغییر رمز عبور");
		
		component = new HealthChek(conn);
		tabbedPane.add(component, "چرخه سلامت");
		
		component = new AccessControlTab();
		tabbedPane.add(component, "تعیین سطح دسترسی");

		return tabbedPane;
	}


	public void setStatusLine(String strMessage)
	{
		m_statusLabel.setText(strMessage);
	}
}