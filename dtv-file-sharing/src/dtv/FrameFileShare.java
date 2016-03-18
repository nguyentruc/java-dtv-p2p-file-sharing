package dtv;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class FrameFileShare extends JFrame {

	private JPanel contentPane;
	private JFrame frameShare;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	private String getFileName;
	private String key;
	private String trackerNumber;
	private String trackerAddress;
	public FrameFileShare() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JButton btnAddFile = new JButton("AddFile");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAddFile, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnAddFile, 83, SpringLayout.WEST, contentPane);
		JLabel lblStatus = new JLabel("");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblStatus, 57, SpringLayout.SOUTH, btnAddFile);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblStatus, 105, SpringLayout.WEST, contentPane);
		//Select Source and create File Torrent	
		btnAddFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//fileChooser.showOpenDialog(contentPane);
				JFileChooser fileChooser = new JFileChooser();
				int value=fileChooser.showOpenDialog(btnAddFile);
				if(value ==JFileChooser.APPROVE_OPTION){			
				File fileChoose = fileChooser.getSelectedFile();
				getFileName=fileChoose.getName();
				/*getUrl=fileChoose.getAbsolutePath();
				InetAddress myHost;
				try {
					myHost = InetAddress.getLocalHost();
					 getIP=myHost.getHostAddress();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				Hashcode hashFile=new Hashcode();
				key=hashFile.hashCode(fileChoose);
				trackerNumber="1";
				trackerAddress="VDT";
				lblStatus.setText("File Selected : " + fileChoose.getName() +  fileChoose.getAbsolutePath());
				}
				else{
					lblStatus.setText("Open command cancelled by user." );           
		        }
				
	         }    
				//create torrent file
				//create TorFileMess
				//send to thread
				
		});
		JButton btnCreateFile = new JButton("Create");
		btnCreateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.showOpenDialog(contentPane);
				int value=fileChooser.showSaveDialog(btnCreateFile);
				if(value ==JFileChooser.APPROVE_OPTION){
					File addressChoose = fileChooser.getSelectedFile();
					String url=addressChoose.getAbsolutePath();
					String FileName=addressChoose.getName();
					// Write File
					try {
						FileOutputStream fos = new FileOutputStream(url,true);
						PrintWriter fileTorrent = new PrintWriter(fos);
						
					//	 fileTorrent.println(getFileName);
					//	 fileTorrent.println(getUrl);
			        //   fileTorrent.println(getIP);
			        //   fileTorrent.println(port);
						 fileTorrent.println(FileName);
			             fileTorrent.println(key);
			             fileTorrent.println(trackerNumber);
			             fileTorrent.println(trackerAddress);
			             fileTorrent.flush();
			             fileTorrent.close();	
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}    
				}
				else{
					lblStatus.setText("Save command cancelled by user." );           
		        }
				
			}
		});
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCreateFile, 0, SpringLayout.NORTH, btnAddFile);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCreateFile, 31, SpringLayout.EAST, btnAddFile);
		contentPane.add(btnAddFile);
		contentPane.add(lblStatus);
		contentPane.add(btnCreateFile);
		setVisible(true);
	}
}
 class Hashcode {
	public String hashCode(File file){
		MessageDigest md;
		byte[] digest = null;
		try {
			md = MessageDigest.getInstance("MD5");
			InputStream is;
			try {
				is = new FileInputStream(file);
				DigestInputStream dis = new DigestInputStream(is, md);
				digest = md.digest();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertByteArrayToHexString(digest);
	}
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
}
