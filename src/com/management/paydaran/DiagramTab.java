package com.management.paydaran;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;

public class DiagramTab
extends JPanel
{
	
        private JTextField		d_idTextField;
	private JComboBox		d_kindComboBox;
	private JTextArea recieveData;
        JLabel message;
        JLabel answerNum;
        JList holderList;
        JPanel detailPanel;
        JPanel fp;
        JPanel qp;
        JPanel sp;
        JPanel showPanel;
        JPanel topChartPanel;
        JPanel QinS;
        JPanel answererNumPanel;
        ChartPanel chartPanel;
        JFreeChart barChart;
        JList senarioQ;
        String recievedData;
        String str[] = new String [] {" ", "سوال ثابت","سوال فوری" , "سناریو"};
        Database db = new Database();
        int bad, avrg, good;
        String s1, s2, s3;
        double sat, unsat, avg;
        DefaultListModel model;
        DefaultListModel holdermodel;
        JScrollPane scroll6;
        
	public DiagramTab( )
	{
                JPanel basepanel = new JPanel();
                basepanel.setLayout(new BoxLayout(basepanel, BoxLayout.Y_AXIS));
                
		JPanel topPanel = new JPanel();
		JPanel gotInputPanle = new JPanel();
		gotInputPanle.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		
		JPanel slct = new JPanel();
		slct.setComponentOrientation(getComponentOrientation()
				.RIGHT_TO_LEFT);
		slct.add(new JLabel("نوع پیام"));
		slct.add(Box.createHorizontalGlue());
		d_kindComboBox = new JComboBox(str);         
                d_kindComboBox.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED){
                        switch (d_kindComboBox.getSelectedIndex())
                        {
                            case 0:                              
                                detailPanel.setVisible(false);
                            case 1 :
                                holdermodel.clear();
                                db.openDb();//CAST(S_name AS VARCHAR(128)) =
				ResultSet rs3 = db.createQuery("select QUESTION from PAYDARAN.TABLE_QUES");
				try {
                                    while(rs3.next()){
                                    //Retrieve by column name
                                        holdermodel.addElement(rs3.getString("QUESTION"));
                                    }
				} catch (SQLException ewe) {
					// TODO Auto-generated catch block
					ewe.printStackTrace();
				}
				db.closeDb();
                                detailPanel.setVisible(true);
                                break;
                            case 2 :
                                holdermodel.clear();
                                db.openDb();//CAST(S_name AS VARCHAR(128)) =
				ResultSet rs4 = db.createQuery("select QUESTION from PAYDARAN.TABLE_QUES");
				try {
                                    while(rs4.next()){
                                    //Retrieve by column name
                                        holdermodel.addElement(rs4.getString("QUESTION"));
                                    }
				} catch (SQLException ewe) {
					// TODO Auto-generated catch block
					ewe.printStackTrace();
				}
				db.closeDb();
                                detailPanel.setVisible(true);
                                break;
                            case 3 :
                                holdermodel.clear();
                                db.openDb();//CAST(S_name AS VARCHAR(128)) =
				ResultSet rs5 = db.createQuery("select S_NAME from PAYDARAN.TABLE_SENARIO");
				try {
                                    while(rs5.next()){
                                    //Retrieve by column name
                                        holdermodel.addElement(rs5.getString("S_NAME"));
                                    }
				} catch (SQLException ewe) {
					// TODO Auto-generated catch block
					ewe.printStackTrace();
				}
				db.closeDb();
                                detailPanel.setVisible(true);
                                break;
                            default :
                                break;
                        }
                    }
                }
            });
		slct.add(d_kindComboBox);
		slct.add(Box.createHorizontalGlue());
		slct.add(Box.createHorizontalGlue());
		gotInputPanle.add(slct);
		
		detailPanel = new JPanel();
		detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
                holdermodel = new DefaultListModel();
                holderList = new JList(holdermodel);
                holderList.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JScrollPane scrl = new JScrollPane(holderList);
                scrl.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                scrl.setPreferredSize(new Dimension(300, 150));
		detailPanel.add(scrl);
                detailPanel.setVisible(false);                
		
		gotInputPanle.add(detailPanel);
                gotInputPanle.add(Box.createHorizontalGlue());

		topPanel.add(gotInputPanle);
		basepanel.add(topPanel, BorderLayout.CENTER);
		
		showPanel = new JPanel();
                showPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                QinS = new JPanel();
		QinS.setLayout(new BoxLayout(QinS, BoxLayout.Y_AXIS));
		model = new DefaultListModel();
		senarioQ = new JList(model);
                senarioQ.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                JScrollPane scroll4 = new JScrollPane(new JLabel("سوالات موجود در سناریو"));
                scroll4.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		QinS.add(scroll4);
                JScrollPane qscroll23 = new JScrollPane(senarioQ);
                qscroll23.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                 qscroll23.setPreferredSize(new Dimension(230, 200));
		QinS.add(qscroll23);
		QinS.setVisible(false);
		showPanel.add(QinS);
                
                message = new JLabel("نموداری انتخاب نشده است.");
                showPanel.add(message);
                topChartPanel = new JPanel();
                 showPanel.add(topChartPanel);
                //showPanel.setVisible(false);
		basepanel.add(showPanel, BorderLayout.CENTER);
		
                answererNumPanel = new JPanel();
                answererNumPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                scroll6 = new JScrollPane();
                scroll6.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                answererNumPanel.add(new JLabel("تعداد شرکت کنندگان:"));
                answerNum = new JLabel();
                answererNumPanel.add(answerNum);
                //answererNumPanel.add(scroll6);
                answererNumPanel.setVisible(false);
                basepanel.add(answererNumPanel, BorderLayout.CENTER);
                
		JPanel btnPanel = new JPanel();
		btnPanel.setComponentOrientation(getComponentOrientation().RIGHT_TO_LEFT);
		JButton showdiagram = new JButton("نمایش نمودار");
                showdiagram.addActionListener(new clickedbtn());
		btnPanel.add(showdiagram);
		btnPanel.add(Box.createHorizontalGlue());
		btnPanel.add(Box.createHorizontalGlue());
		JButton refreshdiagram = new JButton("به روز رسانی نمودار");
                refreshdiagram.addActionListener(new clickedbtn());
		btnPanel.add(refreshdiagram);
		basepanel.add(btnPanel, BorderLayout.CENTER);
                this.add(basepanel, BorderLayout.CENTER);
                
                JPanel hamel = new JPanel();
                hamel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                hamel.add(new JLabel("اجازه دسترسی به این قسمت برای شما تعریف نشده است"));
                hamel.setAlignmentX(CENTER_ALIGNMENT);
                this.add(hamel, BorderLayout.CENTER);
               
                //Diagram
                if(PassWordDialog.accessD || PassWordDialog.isadmin){
                    basepanel.setVisible(true);
                hamel.setVisible(false);
        }else{
            basepanel.setVisible(false);
            hamel.setVisible(true);
            }
		
	}       

        private CategoryDataset createDataset(long [] chartvalue )
   {
      final String audi = "میزان رضایت";        
      final String verybad = "خیلی ناراضی";
      final String bad = "ناراضی";
      final String average = "متوسط";
      final String good = "راضی";        
      final String verygood = "خیلی راضی";
      final String yes = "بله";
      final String no = "خیر";
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset(); 
      if(chartvalue[0] == 1){
          int diagramkind = chartvalue.length;
          switch(diagramkind) {
              case 3:
                  dataset.addValue( chartvalue[1] , audi ,no);
                  dataset.addValue( chartvalue[2] , audi ,yes);
                  break;
              case 4:
                  dataset.addValue( chartvalue[1] , audi ,bad);
                  dataset.addValue( chartvalue[2] , audi ,average);
                  dataset.addValue( chartvalue[3] , audi ,good);
                  break;
              case 5:
                  dataset.addValue( chartvalue[1] , audi ,verybad);
                  dataset.addValue( chartvalue[2] , audi ,bad);
                  dataset.addValue( chartvalue[3] , audi ,good);
                  dataset.addValue( chartvalue[4] , audi ,verygood);
                  break;
              case 6:
                  dataset.addValue( chartvalue[1] , audi ,verybad);
                  dataset.addValue( chartvalue[2] , audi ,bad);
                  dataset.addValue( chartvalue[3] , audi ,average);
                  dataset.addValue( chartvalue[4] , audi ,good);
                  dataset.addValue( chartvalue[5] , audi ,verygood);
                  break;
              default :     
                  break;
          }
      }else{
          for(int i = 1 ; i < chartvalue.length ; i++){
            dataset.addValue( chartvalue[i] , audi , "سوال "+i );
      }
      }
           
      return dataset; 
   }
    
    private class clickedbtn implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(d_kindComboBox.getSelectedIndex()== 0){
                JOptionPane.showMessageDialog(null, "لطفا نوع پیام را انتخاب نمایید", "خطا",
			JOptionPane.ERROR_MESSAGE);
            }else if(d_kindComboBox.getSelectedIndex()== 1 && holderList.isSelectionEmpty()){
                JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید", "خطا",
			JOptionPane.ERROR_MESSAGE);
            }else if(d_kindComboBox.getSelectedIndex()== 2 && holderList.isSelectionEmpty()){
                JOptionPane.showMessageDialog(null, "لطفا یک سوال را انتخاب نمایید", "خطا",
			JOptionPane.ERROR_MESSAGE);
            }else if(d_kindComboBox.getSelectedIndex()== 3 && holderList.isSelectionEmpty()){
                JOptionPane.showMessageDialog(null, "لطفا یک سناریو را انتخاب نمایید", "خطا",
			JOptionPane.ERROR_MESSAGE);
            }else if(d_kindComboBox.getSelectedIndex()== 1){
                db.openDb();
                int choices = 0 , qID = 0;
                db.openDb();
                ResultSet rs1 = db.createQuery("SELECT PAYDARAN.TABLE_QUES.Q_ID , PAYDARAN.TABLE_QUES.CHOICES"
                    + " FROM PAYDARAN.TABLE_QUES"
                    + " WHERE CAST(PAYDARAN.TABLE_QUES.QUESTION AS VARCHAR(32672)) ="
                        + " '"+holderList.getSelectedValue().toString()+"'");
                try {
                    while(rs1.next()){
                        qID = rs1.getInt("Q_ID");
                        choices = rs1.getInt("CHOICES");                      
                        //System.out.println(choices);
                        }
                    } catch (SQLException exce) {
                        // TODO Auto-generated catch block
                        exce.printStackTrace();
                        }
                long [] chartValue3 = new long [choices+1];
                chartValue3[0] = 1 ; //its SMS
                for(int i = 1 ; i<=choices ; i++){
                ResultSet rs2 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                    + " FROM PAYDARAN.TABLE_ANSWER"
                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = "+i+" and PAYDARAN.TABLE_ANSWER.Q_ID = "+qID+" and PAYDARAN.TABLE_ANSWER.A_KIND = 4"
                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                try {
                    while(rs2.next()){
			//Retrieve by column name
                        chartValue3[i] = rs2.getInt("ANS");
                        //System.out.println(unsat);
                        }
                } catch (SQLException exce) {
                    // TODO Auto-generated catch block
                    exce.printStackTrace();
                    }
                }
                db.closeDb();
                //remove current chart
                topChartPanel.removeAll();
                topChartPanel.revalidate();                
                JFreeChart barChart = ChartFactory.createBarChart(
                "نمودار سوال ثابت",           
                "وضعیت",            
                "تعداد",            
                        createDataset(chartValue3),          
                        PlotOrientation.VERTICAL,           
                        true, true, false);
                        barChart.removeLegend();
                        chartPanel = new ChartPanel( barChart );        
                        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
                        topChartPanel.setLayout(new BorderLayout());
                        message.setVisible(false);
                        QinS.setVisible(false);
                        chartPanel.setVisible(true);
                        
                        topChartPanel.add(chartPanel);
                        topChartPanel.repaint();
                            
                        db.closeDb();
                    }else if(d_kindComboBox.getSelectedIndex()== 2 && !holderList.isSelectionEmpty()){
                        int choices = 0 , qID = 0;
                        db.openDb();
                             ResultSet rs1 = db.createQuery("SELECT PAYDARAN.TABLE_QUES.Q_ID , PAYDARAN.TABLE_QUES.CHOICES"
                                     + " FROM PAYDARAN.TABLE_QUES"
                                     + " WHERE CAST(PAYDARAN.TABLE_QUES.QUESTION AS VARCHAR(32672)) ="
                                     + " '"+holderList.getSelectedValue().toString()+"'");
                                try {
                                    while(rs1.next()){
                                        //Retrieve by column name
                                        qID = rs1.getInt("Q_ID");
                                        choices = rs1.getInt("CHOICES");               
                                        //System.out.println(choices);
                                      }
                                } catch (SQLException exce) {
                            	// TODO Auto-generated catch block
                            	exce.printStackTrace();
                            }
                                long [] chartValue2 = new long [choices+1];
                                chartValue2[0] = 1 ; //its SMS
                                for(int i = 1 ; i<=choices ; i++){
                                    ResultSet rs2 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.ANSWER = "+i+" and PAYDARAN.TABLE_ANSWER.Q_ID = "+qID+" and PAYDARAN.TABLE_ANSWER.A_KIND = 10"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID ");
                                try {
                                    while(rs2.next()){
				     //Retrieve by column name
                                        chartValue2[i] = rs2.getInt("ANS");
                                        //System.out.println(unsat);
                                    }
                                } catch (SQLException exce) {
                                    // TODO Auto-generated catch block
                                    exce.printStackTrace();
                                }
                                }
                                
                        db.closeDb();
                        //remove current chart
                        topChartPanel.removeAll();
                        topChartPanel.revalidate();                
                        JFreeChart barChart = ChartFactory.createBarChart(
                        "نمودار سوال فوری",           
                        "وضعیت",            
                        "تعداد",            
                        createDataset(chartValue2),          
                        PlotOrientation.VERTICAL,           
                        true, true, false);
                        barChart.removeLegend();
                        chartPanel = new ChartPanel( barChart );        
                        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );
                        topChartPanel.setLayout(new BorderLayout());
                        message.setVisible(false);
                        QinS.setVisible(false);
                        chartPanel.setVisible(true);
                        
                        topChartPanel.add(chartPanel);
                        topChartPanel.repaint();
                        
                    }else if(d_kindComboBox.getSelectedIndex()== 3 && !holderList.isSelectionEmpty()){     
                        answererNumPanel.setVisible(true);
                        model.clear();			
                        String sname = holderList.getSelectedValue().toString();
                        int sID = 0;
                        db.openDb();                        
			ResultSet srs2 = db.createQuery("SELECT Question" +
                                                       " FROM PAYDARAN.TABLE_QUES" +
                                                       " WHERE Q_id IN (SELECT Q_id" +
                                                       " FROM PAYDARAN.TABLE_SENARIO_Q" +
                                                       " WHERE S_NAME = '"+sname+"')");
			try {
                            while(srs2.next()){
				//Retrieve by column name
				model.addElement(srs2.getString("Question"));
				}
                               //System.out.println(model);
			} catch (SQLException esrs2) {
				// TODO Auto-generated catch block
				esrs2.printStackTrace();
			}
                        
                        String querysid = "SELECT S_ID FROM PAYDARAN.TABLE_Senario"
                                            + " WHERE CAST(S_NAME AS VARCHAR(128)) = '"+sname+"'";
                        ResultSet rs12 = db.createQuery(querysid);
			try {
				while(rs12.next()){
				//Retrieve by column name
					sID = rs12.getInt("S_ID");
				}
			} catch (SQLException epo) {
				// TODO Auto-generated catch block
				epo.printStackTrace();
			}
                        
                        int sSize = model.getSize();                        
                        long [] chartValue = new long [sSize+1]; 
                        chartValue[0] = 2; //its Senario
                        for(int i = 1 ; i <= sSize; i++){
                            int currQid = 0;
                            int answererNum = 0;
                            int choices = 0;
                            int maxans;
                            int sumOfAnswer = 0;
                            ResultSet srs3 = db.createQuery("SELECT Q_ID, CHOICES FROM PAYDARAN.TABLE_QUES"
                                + " WHERE CAST(QUESTION AS VARCHAR(32672)) = '"+model.get(i-1).toString()+"'");
                            try {
				while(srs3.next()){
				     //Retrieve by column name
					currQid = srs3.getInt("Q_ID");
                                        choices = srs3.getInt("CHOICES");
                                        //System.out.println(choices);
				  }
                            } catch (SQLException esrs3) {
				// TODO Auto-generated catch block
				esrs3.printStackTrace();
                            }
                            
                            ResultSet srs4 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , COUNT(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.Q_ID = "+currQid+" and PAYDARAN.TABLE_ANSWER.S_ID = "+sID+" and PAYDARAN.TABLE_ANSWER.ANSWER != 0"
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID");
                            try {
				while(srs4.next()){
				     //Retrieve by column name
					answererNum = srs4.getInt("ANS");
                                        //System.out.println(answererNum);
				  }
                            } catch (SQLException esrs4) {
				// TODO Auto-generated catch block
				esrs4.printStackTrace();
                            }
                            maxans = answererNum * choices;    
                            ResultSet srs5 = db.createQuery("SELECT PAYDARAN.TABLE_ANSWER.Q_ID , sum(ANSWER) as ANS"
                                    + " FROM PAYDARAN.TABLE_ANSWER"
                                    + " WHERE PAYDARAN.TABLE_ANSWER.Q_ID = "+currQid+" and ANSWER !=1 and PAYDARAN.TABLE_ANSWER.S_ID = "+sID
                                    + " Group By PAYDARAN.TABLE_ANSWER.Q_ID");
                            try {
				while(srs5.next()){
				     //Retrieve by column name
					sumOfAnswer = srs5.getInt("ANS");
                                        //System.out.println(sumOfAnswer);
                                        //System.out.println(maxans);
				  }
                            } catch (SQLException esrs5) {
				// TODO Auto-generated catch block
				esrs5.printStackTrace();
                            }
                            if(maxans == 0)
                                chartValue[i] = 0;
                            else
                                chartValue[i] = (sumOfAnswer*100/maxans) ;
                            answerNum.setText(answererNum+"");

                            currQid = 0;
                            answererNum = 0;
                            choices = 0;
                            maxans = 0;
                            sumOfAnswer = 0;
                            
                        }                        
                        db.closeDb();
                        //System.out.println(chartValue.length);
                        //remove current chart
                        topChartPanel.removeAll();
                        topChartPanel.revalidate();                
                        JFreeChart barChart = ChartFactory.createBarChart(
                        "نمودار سناریو",           
                        "وضعیت",            
                        "درصد",            
                        createDataset(chartValue),          
                        PlotOrientation.VERTICAL,           
                        true, true, false);
                        barChart.removeLegend();
                        chartPanel = new ChartPanel( barChart );        
                        chartPanel.setPreferredSize(new java.awt.Dimension( 620 , 267 ) );
                        topChartPanel.setLayout(new BorderLayout());                      
                        chartPanel.setVisible(true);
                        QinS.setVisible(true);
                        message.setVisible(false);
                        topChartPanel.add(chartPanel);
                        topChartPanel.repaint();   
                    }                
                
        }
        
    }
        
} 



/*** Diagram.java ***/