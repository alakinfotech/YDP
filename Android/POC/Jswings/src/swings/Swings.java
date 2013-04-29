package swings;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Swings extends JFrame {

	private JPanel contentPane;
	private JTextField sourcetxtFld;
	private JTextField desttxtFld;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Swings frame = new Swings();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	 public String readFile(File file) {

	        StringBuffer fileBuffer = null;
	        String fileString = null;
	        String line = null;

	        try {
	            FileReader in = new FileReader(file);
	            BufferedReader brd = new BufferedReader(in);
	            fileBuffer = new StringBuffer();

	            while ((line = brd.readLine()) != null) {
	                fileBuffer.append(line).append(
	                        System.getProperty("line.separator"));
	            }

	            in.close();
	            fileString = fileBuffer.toString();
	        } catch (IOException e) {
	            return null;
	        }
	        return fileString;
	    }

	/**
	 * Create the frame.
	 */
	public Swings() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 426);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Choose excel file to Encode :");
		lblNewLabel.setBounds(25, 116, 422, 16);
		contentPane.add(lblNewLabel);
		
		sourcetxtFld = new JTextField();
		sourcetxtFld.setBounds(155, 145, 285, 22);
		contentPane.add(sourcetxtFld);
		sourcetxtFld.setColumns(10);
		
		JButton browsefilebtn = new JButton("Browse File");
		browsefilebtn.setForeground(Color.WHITE);
		browsefilebtn.setBackground(new Color(51, 102, 153));
		browsefilebtn.setBounds(452, 145, 136, 22);
		contentPane.add(browsefilebtn);
        browsefilebtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("xls files", "xls");
                fileopen.addChoosableFileFilter(filter);

               int ret = fileopen.showDialog(contentPane, "Open file");

                if (ret == JFileChooser.APPROVE_OPTION) {
                   // File file = fileopen.getSelectedFile();
                  //  String text = readFile(file);
                    sourcetxtFld.setText(fileopen.getSelectedFile().getName());
                    sourcetxtFld.setEditable(false);
                }

            }
        });

		
		
		JLabel lblNewLabel_1 = new JLabel("Choose destination folder to save Encoded data :");
		lblNewLabel_1.setBounds(25, 193, 415, 22);
		contentPane.add(lblNewLabel_1);
		
		desttxtFld = new JTextField();
		desttxtFld.setBounds(155, 222, 285, 22);
		contentPane.add(desttxtFld);
		desttxtFld.setColumns(10);
		
		JButton browsefolderbtn = new JButton("Browse Folder");
		browsefolderbtn.setForeground(Color.WHITE);
		browsefolderbtn.setBackground(new Color(51, 102, 153));
		browsefolderbtn.setBounds(450, 221, 136, 22);
		contentPane.add(browsefolderbtn);
		 browsefolderbtn.addActionListener(new ActionListener() {

	            public void actionPerformed(ActionEvent event) {
	                JFileChooser fileopen = new JFileChooser();
	                fileopen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	                FileFilter filter = new FileNameExtensionFilter("xml files", "xls");
	                fileopen.addChoosableFileFilter(filter);

	               int ret = fileopen.showDialog(contentPane, "Open file");

	                if (ret == JFileChooser.SAVE_DIALOG) {
	                   // File file = fileopen.getSelectedFile();
	                  //  String text = readFile(file);
	                    desttxtFld.setText(fileopen.getSelectedFile().getName());
	                }

	            }
	        });

		
		JButton genrateencodebtn = new JButton("Generate encode date");
		genrateencodebtn.setForeground(Color.WHITE);
		genrateencodebtn.setBackground(new Color(51, 102, 153));
		genrateencodebtn.setBounds(212, 298, 170, 36);
		contentPane.add(genrateencodebtn);
		genrateencodebtn.addActionListener(new ActionListener() {

		public void actionPerformed(ActionEvent event) {
			 ProgressBar pbar = new ProgressBar();
			   pbar.setVisible(true); 
		}
		});
		JLabel lblYourDoctorProgram = new JLabel("Your Doctor Program");
		lblYourDoctorProgram.setForeground(new Color(51, 102, 153));
		lblYourDoctorProgram.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourDoctorProgram.setToolTipText("");
		lblYourDoctorProgram.setLabelFor(this);
		lblYourDoctorProgram.setIcon(new ImageIcon("D:\\AIT_PROJECTS\\YDP_MOBILE\\Documents\\YDP_Images\\app_icon.png"));
		lblYourDoctorProgram.setBackground(new Color(51, 102, 153));
		lblYourDoctorProgram.setBounds(25, 0, 585, 44);
		contentPane.add(lblYourDoctorProgram);
	}
}
