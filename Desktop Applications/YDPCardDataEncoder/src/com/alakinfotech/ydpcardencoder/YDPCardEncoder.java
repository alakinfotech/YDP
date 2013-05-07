package com.alakinfotech.ydpcardencoder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;

/** copy rights to Alakinfotech
 * Date:03/05/2013 
 * @author Srikanth
 * YDPCardEncoder is used to encode 
 * user data into encode format
 */


public class YDPCardEncoder extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputtxtfld ,outputtxtfld;
	JButton inputbtn,outputbtn,encodebtn;
	JLabel inputtxtlbl,outputtxtlbl;
	JFileChooser chooser;
	JProgressBar progressBar;
	String defaultFileName = "del.txt";
	boolean isEncodingProgress;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YDPCardEncoder frame = new YDPCardEncoder();
					frame.setTitle("YDPCardDataEncoder"); //Sets title of frame
					// changing default icon of jframe to custoum image
					BufferedImage image = null;//Setting image icon
					try {
						File imageFile = new File("D:/AIT_PROJECTS/YDP_MOBILE/Desktop Applications/YDPCardDataEncoder/app_icon.png");//Image icon path
						image = ImageIO.read(imageFile);//Reading Image icon
					} catch (IOException e) {
						e.printStackTrace();
					}
					frame.setIconImage(image);//Attaching image icon to frame
					frame.setResizable(false);//resizing frame is disabled
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public YDPCardEncoder() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 452);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titlelbl = new JLabel("YDP Card Data Encoder");
		titlelbl.setForeground(new Color(51, 102, 153));
		titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
		titlelbl.setIcon(new ImageIcon("D:\\AIT_PROJECTS\\YDP_MOBILE\\Desktop Applications\\YDPCardDataEncoder\\app_icon.png"));
		titlelbl.setBackground(new Color(51, 102, 153));
		titlelbl.setBounds(12, 13, 652, 38);
		contentPane.add(titlelbl);
		
		inputtxtlbl = new JLabel("WorkBook Input File:");
		inputtxtlbl.setBounds(23, 105, 129, 16);
		contentPane.add(inputtxtlbl);
		
		inputtxtfld = new JTextField();
		inputtxtfld.setBounds(164, 102, 406, 22);
		contentPane.add(inputtxtfld);
		inputtxtfld.setEditable(false);
		inputtxtfld.setColumns(10);
		
		inputbtn = new JButton("Browse..");
		inputbtn.setForeground(Color.WHITE);
		inputbtn.setBackground(new Color(51, 102, 153));
		inputbtn.setBounds(582, 101, 97, 25);
		contentPane.add(inputbtn);
		inputbtn.addActionListener(new ActionListener() {
			// Action for browse button
            public void actionPerformed(ActionEvent event) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("xls files", "xls");
                fileopen.setAcceptAllFileFilterUsed(false);
                fileopen.addChoosableFileFilter(filter);

               int ret = fileopen.showDialog(contentPane, "Open file");

                if (ret == JFileChooser.APPROVE_OPTION) {
                	inputtxtfld.setText(fileopen.getSelectedFile().getAbsolutePath());
                    
                }

            }
        });
		
		
		outputtxtlbl = new JLabel("WorkBook Output File:");
		outputtxtlbl.setBounds(23, 195, 129, 16);
		contentPane.add(outputtxtlbl);
		
		outputtxtfld = new JTextField();
		outputtxtfld.setBounds(168, 192, 402, 22);
		contentPane.add(outputtxtfld);
		outputtxtfld.setColumns(10);
		outputtxtfld.setEditable(false);
		
		outputbtn = new JButton("Browse..");
		outputbtn.setForeground(Color.WHITE);
		outputbtn.setBackground(new Color(51, 102, 153));
		outputbtn.setBounds(582, 191, 97, 25);
		contentPane.add(outputbtn);
		outputbtn.addActionListener(new ActionListener() {
			// Action for browse button
			public void actionPerformed(ActionEvent e) {  
					JFileChooser fileopen = new JFileChooser();
	                FileFilter filter = new FileNameExtensionFilter("xls files", "xls");
	                fileopen.setAcceptAllFileFilterUsed(false);
	                fileopen.addChoosableFileFilter(filter);
	               

	               int ret = fileopen.showDialog(contentPane, "Save file");

	                if (ret == JFileChooser.APPROVE_OPTION) {
	                	outputtxtfld.setText(fileopen.getSelectedFile().getAbsolutePath());
	                    
	                }

	            }
	        });
		encodebtn = new JButton("Start Encoding");
		encodebtn.setForeground(Color.WHITE);
		encodebtn.setBackground(new Color(51, 102, 153));
		encodebtn.setBounds(262, 285, 186, 43);
		contentPane.add(encodebtn);
		
		
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(20, 361, 663, 25);
		contentPane.add(progressBar);
		progressBar.setVisible(false);
		encodebtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {

				

				
				inputtxtfld.setEnabled(isEncodingProgress);
				outputtxtfld.setEnabled(isEncodingProgress);
				inputtxtlbl.setEnabled(isEncodingProgress);
				outputtxtlbl.setEnabled(isEncodingProgress);
				inputbtn.setEnabled(isEncodingProgress);
				outputbtn.setEnabled(isEncodingProgress);
				
				if(isEncodingProgress==false)
				{
					
					encodebtn.setText("Stop Encoding");
					progressBar.setVisible(true);
				}
				else
				{
					encodebtn.setText("Start Encoding");
					progressBar.setVisible(false);
				}
				isEncodingProgress = !isEncodingProgress;
				
				
				
			}
			
			});
		
			}
}
		
	
	


