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
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
