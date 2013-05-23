
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

import org.apache.commons.io.FilenameUtils;


/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 03 May 2013
 *
 */


public class YDPCardEncoder extends JFrame  implements IYDPCardEncoder{

	/* YDPCardEncoder Class is used to create frame and user interface elements are implemented */
	
	
	private static final long serialVersionUID = 1L;//Program generated
	
	private JTextField inputTxtFld;//input text field
	private JTextField outputTxtFld;//output text field
	private JPanel contentPane;//Jpanel for adding ui elements
	private JButton inputBtn;//open file dialog button
	private JButton outputBtn;//save dialog button
	private JButton encodeBtn;//button for encoding
	static JProgressBar progressBar;// progress bar declaration
	private ExcelFileReading excelfilereadingobj;
	
	boolean isEncodingProgress;// for enabling/disabling UI elements
	private File newFile;//For crating new file
	static IYDPCardEncoder callback;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YDPCardEncoder frame = new YDPCardEncoder();
					callback = frame;
					frame.setTitle("YDP Card Data Encoder"); // Sets title of frame
					// changing default icon of jframe to custoum image
					BufferedImage image = null;// Setting image icon
					try {
						File imageFile = new File(
								"app_icon.png");// Image icon path
						image = ImageIO.read(imageFile);// Reading Image icon
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Image not Found"+ e);
					}
					frame.setIconImage(image);// Attaching image icon to frame
					frame.setResizable(false);// resizing frame is disabled
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
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
		
		//Title label implementation
		JLabel titleLbl = new JLabel("YDP Card Data Encoder");
		titleLbl.setForeground(new Color(51, 102, 153));
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setIcon(new ImageIcon(
				"app_icon.png"));//title icon
		titleLbl.setBackground(new Color(51, 102, 153));
		titleLbl.setBounds(12, 13, 652, 38);
		contentPane.add(titleLbl);
		
		//Input text label implementation
		JLabel inputTxtLbl = new JLabel("WorkBook Input File:");
		inputTxtLbl.setBounds(23, 105, 129, 16);
		contentPane.add(inputTxtLbl);
		
		//Input text field implementation
		inputTxtFld = new JTextField();
		inputTxtFld.setBounds(164, 102, 406, 22);
		inputTxtFld.setEditable(false);
		inputTxtFld.setColumns(10);
		contentPane.add(inputTxtFld);

		//Input button implementation
		inputBtn = new JButton("Open");
		inputBtn.setForeground(Color.WHITE);
		inputBtn.setBackground(new Color(51, 102, 153));
		inputBtn.setBounds(582, 101, 97, 22);
		contentPane.add(inputBtn);
		
		//Action for input (open dialog box) button
		inputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("xls files","xls");
				fileopen.setAcceptAllFileFilterUsed(false);
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(contentPane, "Open file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					inputTxtFld.setText(fileopen.getSelectedFile()
							.getAbsolutePath());
					String inputpath = inputTxtFld.getText();
					String fileName = new File(inputpath).getName();//getting input filename
					String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);//remove extension for file name
					String outputfile = fileNameWithOutExt + "_Output";//sets output filename
					String temp = "";	
					int k=0;// k variable for representing filename with number
					
					/* Block is used to implement file name with number extension */
					for(;;){
						temp = k!=0?outputfile+"("+k+")":outputfile;
						String newpath1 = inputpath.replace(fileName, temp+".xls");
						newFile = new File(newpath1);
						if(newFile.exists()){
							k++;
						}else{
							outputTxtFld.setText(newpath1);
							break;
						}
					}
					
				}

			}

		});
		//Output text label implementation
		JLabel outputTxtLbl = new JLabel("WorkBook Output File:");
		outputTxtLbl.setBounds(23, 195, 129, 16);
		contentPane.add(outputTxtLbl);
		
		//Output text field implementation
		outputTxtFld = new JTextField();
		outputTxtFld.setBounds(168, 192, 406, 22);
		outputTxtFld.setColumns(10);
		outputTxtFld.setEditable(false);
		contentPane.add(outputTxtFld);

		//Output browse button implementation
		outputBtn = new JButton("Save");
		outputBtn.setForeground(Color.WHITE);
		outputBtn.setBackground(new Color(51, 102, 153));
		outputBtn.setBounds(582, 191, 97, 22);
		contentPane.add(outputBtn);
		
		//Action for save file box 
		outputBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("xls files",
						"xls");
				fileopen.setAcceptAllFileFilterUsed(false);
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(contentPane, "Save file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					outputTxtFld.setText(fileopen.getSelectedFile()
							.getAbsolutePath());

				}

			}
		});
		
		//Progress bar implementation
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(20, 361, 663, 25);
		progressBar.setVisible(false);
		contentPane.add(progressBar);
		
		
		
		//Encoding button  implementation to encode excel data
		encodeBtn = new JButton("Start Encoding");
		encodeBtn.setForeground(Color.WHITE);
		encodeBtn.setBackground(new Color(51, 102, 153));
		encodeBtn.setBounds(293, 285, 124, 22);
		contentPane.add(encodeBtn);
		
		// Encode button implementation
		encodeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// Disabling  all user interface elements 
				inputTxtFld.setEnabled(isEncodingProgress);
				outputTxtFld.setEnabled(isEncodingProgress);
				inputBtn.setEnabled(isEncodingProgress);
				outputBtn.setEnabled(isEncodingProgress);

				if (isEncodingProgress == false) {
					String inputpath, outpath;
					/* creating object for reading class to read input and to encode */
					 excelfilereadingobj = new ExcelFileReading();
					 excelfilereadingobj.callback = callback;
					 
					 //passing secret key to ExcelFileReading class
					 excelfilereadingobj.setKeyForEncoding("Adi&Revanth");
					 
					 //passing input file to ExcelFileReading class
					 inputpath = inputTxtFld.getText();
					 excelfilereadingobj.setInputFile(inputTxtFld.getText());
					
					 //passing output file to ExcelFileReading class
					 outpath = outputTxtFld.getText();
					excelfilereadingobj.setOutputFile(outputTxtFld.getText());
					// checking input and output text fields
					if (inputpath.isEmpty() || outpath.isEmpty()) {
						// Message to user to fill input and output path
						JOptionPane.showMessageDialog(contentPane,
								" Input and Output file path should not empty",
								" Warning", JOptionPane.WARNING_MESSAGE);
						enableAll();//calling enable all function to enable UI elements
					} else {
						encodeBtn.setText("Stop Encoding");

						try {

							excelfilereadingobj.read();	

							
						
						} catch (IOException | UnsupportedAudioFileException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("IOEXCEPTION"+e);
							JOptionPane.showMessageDialog(contentPane,
									"IO EXCEPTION OCCUR"+ e);
						}
						
						
						enableAll();
						inputTxtFld.setText("");
						outputTxtFld.setText("");

					}
				} else {
					encodeBtn.setText("Start Encoding");
					progressBar.setVisible(false);
				}
				isEncodingProgress = !isEncodingProgress;

			}

		});
		

	}
	
	/* This method to enable user interface elements */
	public void enableAll(){
		encodeBtn.setText("Start Encoding");

		isEncodingProgress = true;
		inputTxtFld.setEnabled(isEncodingProgress);
		outputTxtFld.setEnabled(isEncodingProgress);
		inputBtn.setEnabled(isEncodingProgress);
		outputBtn.setEnabled(isEncodingProgress);
		progressBar.setVisible(false);
	}
	/*For updating progress bar value*/
	public  void progressupdate(int progress){
	    progressBar.setVisible(true);
	     progressBar.setValue(progress);
	}

}
