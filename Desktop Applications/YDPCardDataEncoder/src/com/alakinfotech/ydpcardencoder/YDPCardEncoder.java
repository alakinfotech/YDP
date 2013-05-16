package com.alakinfotech.ydpcardencoder;

import java.awt.BorderLayout;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.UnsupportedAddressTypeException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JProgressBar;

import org.apache.commons.codec.binary.Base64;





import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;

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
	JPanel contentPane;
	private JTextField inputtxtfld ,outputtxtfld;
	private String inputFile;
	private String outputFile;
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;
	JButton inputbtn,outputbtn,encodebtn;
	JLabel inputtxtlbl,outputtxtlbl;
	JFileChooser chooser;
	JProgressBar progressBar;
	boolean isEncodingProgress;

	  String resultStr = "" ; 
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
		
		inputbtn = new JButton("Open");
		inputbtn.setForeground(Color.WHITE);
		inputbtn.setBackground(new Color(51, 102, 153));
		inputbtn.setBounds(582, 101, 97, 22);
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
                	String inputpath = inputtxtfld.getText();
                	String fileName = new File(inputpath).getName();
                	String outputfile = "Sample_Output.xls";
                	String newpath1=inputpath.replace(fileName, outputfile);
                	outputtxtfld.setText(newpath1);
                	
                	
                	
                }

            }
        });
		
		
		outputtxtlbl = new JLabel("WorkBook Output File:");
		outputtxtlbl.setBounds(23, 195, 129, 16);
		contentPane.add(outputtxtlbl);
		
		outputtxtfld = new JTextField();
		outputtxtfld.setBounds(168, 192, 406, 22);
		contentPane.add(outputtxtfld);
		outputtxtfld.setColumns(10);
		outputtxtfld.setEditable(false);
		
		outputbtn = new JButton("Save");
		outputbtn.setForeground(Color.WHITE);
		outputbtn.setBackground(new Color(51, 102, 153));
		outputbtn.setBounds(582, 191, 97, 22);
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
		encodebtn.setBounds(293, 285, 124, 22);
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
					String inputpath,outpath;
					
					Reading test = new Reading();
					inputpath = inputtxtfld.getText();
					System.out.println(inputpath);
					test.setInputFile(inputtxtfld.getText());
					outpath = outputtxtfld.getText();
					System.out.println(outpath);
					test.setOutputFile(outputtxtfld.getText());
					if(inputpath.isEmpty()&& outpath.isEmpty())
					{
						JOptionPane.showMessageDialog(contentPane," Input and Output file path should not empty", " Warning",JOptionPane.WARNING_MESSAGE);
						isEncodingProgress=true;
						inputtxtfld.setEnabled(isEncodingProgress);
						outputtxtfld.setEnabled(isEncodingProgress);
						inputtxtlbl.setEnabled(isEncodingProgress);
						outputtxtlbl.setEnabled(isEncodingProgress);
						inputbtn.setEnabled(isEncodingProgress);
						outputbtn.setEnabled(isEncodingProgress);
							
					}
					else{
						encodebtn.setText("Stop Encoding");
						
						for(int i=10;i<=100;i++)
						{
							progressBar.setVisible(true);
						progressBar.setValue(i);
							try {
								test.read();
							} catch (IOException | UnsupportedAudioFileException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							}
						 JOptionPane.showMessageDialog(contentPane,"Check output in destination folder");
					}
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
		
	
	


