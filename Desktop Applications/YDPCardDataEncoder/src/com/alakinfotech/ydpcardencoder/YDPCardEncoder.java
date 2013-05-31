
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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.UIManager;



import org.apache.commons.io.FilenameUtils;


/**
 * 
 * @author Srikanth Gajula
 * @version 1.0 03 May 2013
 *
 */


public class YDPCardEncoder extends JFrame  implements IYDPCardEncoder{

	/* YDPCardEncoder Class is used to create frame and user interface elements are implemented */
	
	static IYDPCardEncoder callback;//interface reference
	static JProgressBar progressBar;// progress bar declaration
	private static final long serialVersionUID = 1L;//Auto generated
	
	private JTextField inputTxtFld;//input text field
	private JTextField outputTxtFld;//output text field
	private JPanel contentPane;//Jpanel for adding ui elements
	private JButton inputBtn;//open file dialog button
	private JButton outputBtn;//save dialog button
	private JButton encodeBtn;//button for encoding
	private JButton closeBtn;//for closing application
	private ExcelFileReading excelfilereadingobj;
	private File newFile;//For crating new file
	private String encodeKey = "Adi&Revanth";//Default key for encoding
	boolean isEncodingProgress;// for enabling/disabling UI elements
	

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YDPCardEncoder frame = new YDPCardEncoder();
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					callback = frame;
					frame.setTitle("YDP Card Data Encoder"); // Sets title of frame
					// changing default icon of jframe to custoum image
					BufferedImage image = null;// Setting image icon
					try {
						File imageFile = new File(
								"D:/ALAK INFO TECH/YDP/Desktop Applications/YDPCardDataEncoder/app_icon.png");// Image icon path
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
	 * With user defined elements
	 */
	
	public YDPCardEncoder() {
		//Auto generated
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 731, 454);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		excelfilereadingobj = new ExcelFileReading();
		
		//Title label implementation
		JLabel titleLbl = new JLabel("YDP Card Data Encoder");
		titleLbl.setForeground(new Color(51, 102, 153));
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setIcon(new ImageIcon(
				"D:/ALAK INFO TECH/YDP/Desktop Applications/YDPCardDataEncoder/app_icon.png"));//title icon
		titleLbl.setBackground(Color.WHITE);
		titleLbl.setBounds(30, 13, 652, 38);
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
				
				/* File chooser for chosing files and file filters*/
				JFileChooser fileopen = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("xls files","xls");
				fileopen.setAcceptAllFileFilterUsed(false);
				fileopen.addChoosableFileFilter(filter);

				int ret = fileopen.showDialog(contentPane, "Open file");

				if (ret == JFileChooser.APPROVE_OPTION) {
					inputTxtFld.setText(fileopen.getSelectedFile().getAbsolutePath());//Setting complete path of input file
//					// creating object for Excel file reading class
//					if(excelfilereadingobj==null){
//					excelfilereadingobj = new ExcelFileReading();
//					}
					excelfilereadingobj.setInputFile(inputTxtFld.getText());//Passing input file to reading class
					 String resultStr = excelfilereadingobj.testHeaderFormat();//calling test method to check weather header in correct format or not
					 	// Resultant of Test header format is correct than process continues
					 	if(resultStr=="Success"){
					
							encodeBtn.setEnabled(true);
							String inputpath = inputTxtFld.getText();
							File f = new File(inputpath);
							if(!f.exists()){
								messageAlert("Given file name does not exist");
								outputTxtFld.setText("");
							}
							else
							{
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
								}
								else{
								outputTxtFld.setText(newpath1);
								break;
								}
							 }
							}
	
						
						 }
						 else{
						 exceptionAlert(resultStr);
						 encodeBtn.setEnabled(false);
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
		outputTxtFld.setBounds(164, 192, 406, 22);
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
				JFileChooser filesave = new JFileChooser();
				FileFilter filter = new FileNameExtensionFilter("xls files","xls");
				filesave.setAcceptAllFileFilterUsed(false);
				filesave.addChoosableFileFilter(filter);
				int ret = filesave.showDialog(contentPane, "Save file");
				
			
			

				if (ret == JFileChooser.APPROVE_OPTION) {
					
						String filepath = filesave.getSelectedFile().getAbsolutePath();
						String ext = FilenameUtils.getExtension(filepath);
						String excel = ".xls";
				
						//checking weather extension is correct or not 
						if((ext.isEmpty())||(ext!=excel)){
							// Replace extension and always place '.xls' only
							filepath = FilenameUtils.removeExtension(filepath)+excel;
							File f = new File(filepath);
							 
							  if(f.exists()){
								  String fileNameExt = f.getName(); 
								  String fileName = FilenameUtils.removeExtension(fileNameExt);
								  String temp = "";	 
									int k=0;// k variable for representing filename with number
									
									/* Block is used to implement file name with number extension */
									for(;;){
										temp = k!=0?fileName+"("+k+")":fileName;
										String newpath1 = filepath.replace(fileNameExt, temp+excel);
										newFile = new File(newpath1);
										if(newFile.exists()){
											k++;
										}else{
											outputTxtFld.setText(newpath1);
											break;
										}
									}
							  }else{
								  outputTxtFld.setText(filepath);
							  }
						
						
					}
					


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
		encodeBtn = new JButton("Start ");
		encodeBtn.setForeground(Color.WHITE);
		encodeBtn.setBackground(new Color(51, 102, 153));
		encodeBtn.setBounds(265, 284, 97, 22);
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
//					/*If excel object is null then it allocates memory*/
//					if(excelfilereadingobj==null){
//					excelfilereadingobj = new ExcelFileReading();
//					}
					/* Creating instance for interface */
					  excelfilereadingobj.callback = callback;
					
					 /*Accessing properties file for setting secrect key to encode */
					 Properties prop = new Properties();
					 try {
			    		   //load a properties file
			    		prop.load(new FileInputStream("YDPCardDataEncoder.properties"));
			    		String fileKey = prop.getProperty("filekey");
			               //get the property value and print it out
			               
			                if(fileKey != null){
			                	if(!fileKey.isEmpty())
			                	{
			                		encodeKey = fileKey;
			                	}
			                }
			                
			           
			    		} catch (IOException ex) {
			    		ex.printStackTrace();
			    		System.out.println("Null pointer exception");
			    		}
					 
					
					
					 //passing secret key to ExcelFileReading class
					 excelfilereadingobj.setKeyForEncoding(encodeKey);
					 
					 //passing input file to ExcelFileReading class
					 inputpath = inputTxtFld.getText();
					 excelfilereadingobj.setOutputFile(inputTxtFld.getText());
					
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
						encodeBtn.setText("Stop");
						
					new Thread(new Runnable() {
							   public void run() {
			
					try {
							excelfilereadingobj.read();	
							
						    // this method is used to enable all UI elements
						    enableAll();
						    //Setting text fields empty
							inputTxtFld.setText("");
							outputTxtFld.setText("");

						
						} catch (IOException | UnsupportedAudioFileException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("IOEXCEPTION"+e);
							JOptionPane.showMessageDialog(contentPane,"IO EXCEPTION OCCUR"+"\n"+ "System cannot find th file specified" );
							enableAll();
						}
						
							   }
						}).start();
						
					}
				} else {
					excelfilereadingobj.isStopEncoding = true;
				    encodeBtn.setText("Start");
					progressBar.setVisible(false);
					if(excelfilereadingobj.progressVal != 100){
						messageAlert("Encoding process stopped");	
					}
					
				}
				isEncodingProgress = !isEncodingProgress;

			}

		});
		
		/*For closing entire application*/
		closeBtn = new JButton("Close");
		closeBtn.setForeground(Color.WHITE);
		closeBtn.setBackground(new Color(51, 102, 153));
		closeBtn.setBounds(394, 284, 97, 22);
		contentPane.add(closeBtn);
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

	}
	
	/* This method to enable user interface elements */
	public void enableAll(){
		encodeBtn.setText("Start");

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
	/*For showing alert message to user*/
	public void messageAlert(String message){
		JOptionPane.showMessageDialog(contentPane, message);
	}
	/*For showing  Exception alert to user*/
	@Override
	public void exceptionAlert(String exception) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(contentPane,exception,"Warning", JOptionPane.ERROR_MESSAGE);
	}
}
