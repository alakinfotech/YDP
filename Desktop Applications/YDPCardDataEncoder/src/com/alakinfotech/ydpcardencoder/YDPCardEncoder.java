
/*
 * Copyright (c) to Alak Info Tech  Inc. All Rights Reserved.
 * 
 * YDPCardEncoder is used to encode user data into encode format
 */


package com.alakinfotech.ydpcardencoder;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.sound.sampled.UnsupportedAudioFileException;
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
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;


/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 03 May 2013
 *
 */


public class YDPCardEncoder extends JFrame {

	/* YDPCardEncoder Class is used to create frame and user interface elements are implemented */
	
	
	private static final long serialVersionUID = 1L;
	private JTextField inputtxtfld, outputtxtfld;
	JPanel contentPane;
	JButton inputbtn, outputbtn, encodebtn;
	JLabel inputtxtlbl, outputtxtlbl;
	JFileChooser chooser;
	JProgressBar progressBar;
	
	boolean isEncodingProgress,fname;
	File f,f1;

	String resultStr = "";
	byte[] encryptedBytesTest;
	String encryptedStringTest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YDPCardEncoder frame = new YDPCardEncoder();
					frame.setTitle("YDPCardDataEncoder"); // Sets title of frame
					// changing default icon of jframe to custoum image
					BufferedImage image = null;// Setting image icon
					try {
						File imageFile = new File(
								"D:/AIT_PROJECTS/YDP_MOBILE/Desktop Applications/YDPCardDataEncoder/app_icon.png");// Image icon path
						image = ImageIO.read(imageFile);// Reading Image icon
					} catch (IOException e) {
						e.printStackTrace();
					}
					frame.setIconImage(image);// Attaching image icon to frame
					frame.setResizable(false);// resizing frame is disabled
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

	public YDPCardEncoder() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 729, 452);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//Title label
		JLabel titlelbl = new JLabel("YDP Card Data Encoder");
		titlelbl.setForeground(new Color(51, 102, 153));
		titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
		titlelbl.setIcon(new ImageIcon(
				"D:\\AIT_PROJECTS\\YDP_MOBILE\\Desktop Applications\\YDPCardDataEncoder\\app_icon.png"));//title icon
		titlelbl.setBackground(new Color(51, 102, 153));
		titlelbl.setBounds(12, 13, 652, 38);
		contentPane.add(titlelbl);
		//Input text label
		inputtxtlbl = new JLabel("WorkBook Input File:");
		inputtxtlbl.setBounds(23, 105, 129, 16);
		contentPane.add(inputtxtlbl);
		//Input text field
		inputtxtfld = new JTextField();
		inputtxtfld.setBounds(164, 102, 406, 22);
		contentPane.add(inputtxtfld);
		inputtxtfld.setEditable(false);
		inputtxtfld.setColumns(10);
		//Input button
		inputbtn = new JButton("Open");
		inputbtn.setForeground(Color.WHITE);
		inputbtn.setBackground(new Color(51, 102, 153));
		inputbtn.setBounds(582, 101, 97, 22);
		contentPane.add(inputbtn);
		inputbtn.addActionListener(new ActionListener() {
			// Action for browse button
			public void actionPerformed(ActionEvent event) {
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("xls files","xls");
				fileopen.setAcceptAllFileFilterUsed(false);
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(contentPane, "Open file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					inputtxtfld.setText(fileopen.getSelectedFile()
							.getAbsolutePath());
					String inputpath = inputtxtfld.getText();
					String fileName = new File(inputpath).getName();//getting input filename
					String outputfile = "Sample_Output";//sets output filename
					String temp = "";	
					int k=0;// k variable for representing filename with number
					
					/* Block is used to implement file name with number extension */
					for(;;){
						temp = k!=0?outputfile+"("+k+")":outputfile;
						String newpath1 = inputpath.replace(fileName, temp+".xls");
						f = new File(newpath1);
						if(f.exists()){
							k++;
						}else{
							outputtxtfld.setText(newpath1);
							break;
						}
					}
					
				}

			}

		});
		//Output text label
		outputtxtlbl = new JLabel("WorkBook Output File:");
		outputtxtlbl.setBounds(23, 195, 129, 16);
		contentPane.add(outputtxtlbl);
		//Output text field
		outputtxtfld = new JTextField();
		outputtxtfld.setBounds(168, 192, 406, 22);
		contentPane.add(outputtxtfld);
		outputtxtfld.setColumns(10);
		outputtxtfld.setEditable(false);
		//Output browse button
		outputbtn = new JButton("Save");
		outputbtn.setForeground(Color.WHITE);
		outputbtn.setBackground(new Color(51, 102, 153));
		outputbtn.setBounds(582, 191, 97, 22);
		contentPane.add(outputbtn);
		outputbtn.addActionListener(new ActionListener() {
			// Action for browse button
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("xls files",
						"xls");
				fileopen.setAcceptAllFileFilterUsed(false);
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(contentPane, "Save file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					outputtxtfld.setText(fileopen.getSelectedFile()
							.getAbsolutePath());

				}

			}
		});
		//Encoding button to encode excel data
		encodebtn = new JButton("Start Encoding");
		encodebtn.setForeground(Color.WHITE);
		encodebtn.setBackground(new Color(51, 102, 153));
		encodebtn.setBounds(293, 285, 124, 22);
		contentPane.add(encodebtn);
		//Progress bar declaration 
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(20, 361, 663, 25);
		contentPane.add(progressBar);
		progressBar.setVisible(false);
		encodebtn.addActionListener(new ActionListener() {
			// Encode button implementation
			public void actionPerformed(ActionEvent event) {
				
				// Disabling  all user interface elements 
				inputtxtfld.setEnabled(isEncodingProgress);
				outputtxtfld.setEnabled(isEncodingProgress);
				inputtxtlbl.setEnabled(isEncodingProgress);
				outputtxtlbl.setEnabled(isEncodingProgress);
				inputbtn.setEnabled(isEncodingProgress);
				outputbtn.setEnabled(isEncodingProgress);

				if (isEncodingProgress == false) {
					String inputpath, outpath;
					/* creating object for reading class to read input and to encode */
					Reading test = new Reading();
					inputpath = inputtxtfld.getText();
					test.setInputFile(inputtxtfld.getText());
					outpath = outputtxtfld.getText();
					test.setOutputFile(outputtxtfld.getText());
					if (inputpath.isEmpty() && outpath.isEmpty()) {
						// Message to user to fill input and output path
						JOptionPane.showMessageDialog(contentPane,
								" Input and Output file path should not empty",
								" Warning", JOptionPane.WARNING_MESSAGE);
						enableAll();
					} else {
						encodebtn.setText("Stop Encoding");

						try {
							progressBar.setVisible(true);
							test.read();	
							progressBar.setValue(100);

						} catch (IOException | UnsupportedAudioFileException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//Message to user that encoding process is completed
						JOptionPane.showMessageDialog(contentPane,
								"Check output in destination folder");
						enableAll();
						inputtxtfld.setText("");
						outputtxtfld.setText("");
					}
				} else {
					encodebtn.setText("Start Encoding");
					progressBar.setVisible(false);
				}
				isEncodingProgress = !isEncodingProgress;

			}

		});
		

	}
	
	/* This method to enable user interface elements */
	public void enableAll(){
		encodebtn.setText("Start Encoding");

		isEncodingProgress = true;
		inputtxtfld.setEnabled(isEncodingProgress);
		outputtxtfld.setEnabled(isEncodingProgress);
		inputtxtlbl.setEnabled(isEncodingProgress);
		outputtxtlbl.setEnabled(isEncodingProgress);
		inputbtn.setEnabled(isEncodingProgress);
		outputbtn.setEnabled(isEncodingProgress);
		progressBar.setVisible(false);
	}

}
