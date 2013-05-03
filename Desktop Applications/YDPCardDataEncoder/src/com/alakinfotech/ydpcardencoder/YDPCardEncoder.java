package com.alakinfotech.ydpcardencoder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

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
	private JTextField inputtxtfld;
	private JTextField outputtxtfld;


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
	public YDPCardEncoder() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 466);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titlelbl = new JLabel("Your Doctor Program");
		titlelbl.setForeground(new Color(51, 102, 153));
		titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
		titlelbl.setIcon(new ImageIcon("D:\\AIT_PROJECTS\\YDP_MOBILE\\Desktop Applications\\YDPCardDataEncoder\\app_icon.png"));
		titlelbl.setBackground(new Color(51, 102, 153));
		titlelbl.setBounds(12, 13, 652, 38);
		contentPane.add(titlelbl);
		
		JLabel inputtxtlbl = new JLabel("WorkBook Input File:");
		inputtxtlbl.setBounds(23, 98, 129, 16);
		contentPane.add(inputtxtlbl);
		
		inputtxtfld = new JTextField();
		inputtxtfld.setBounds(166, 95, 360, 22);
		contentPane.add(inputtxtfld);
		inputtxtfld.setColumns(10);
		
		JButton inputbtn = new JButton("Browse..");
		inputbtn.setForeground(Color.WHITE);
		inputbtn.setBackground(new Color(51, 102, 153));
		inputbtn.setBounds(538, 94, 97, 25);
		contentPane.add(inputbtn);
		
		JLabel outputlbl = new JLabel("WorkBook Output File:");
		outputlbl.setBounds(23, 186, 129, 16);
		contentPane.add(outputlbl);
		
		outputtxtfld = new JTextField();
		outputtxtfld.setBounds(168, 183, 360, 22);
		contentPane.add(outputtxtfld);
		outputtxtfld.setColumns(10);
		
		JButton outputbtn = new JButton("Browse..");
		outputbtn.setForeground(Color.WHITE);
		outputbtn.setBackground(new Color(51, 102, 153));
		outputbtn.setBounds(538, 182, 97, 25);
		contentPane.add(outputbtn);
		
		JButton encodebtn = new JButton("Start Encoding");
		encodebtn.setForeground(Color.WHITE);
		encodebtn.setBackground(new Color(51, 102, 153));
		encodebtn.setBounds(258, 285, 186, 43);
		contentPane.add(encodebtn);
	}
}
