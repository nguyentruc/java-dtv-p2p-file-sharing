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
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
public class FrameFileShare extends JFrame {

	private JPanel contentPane;
	private JFrame frameShare;
	File fileChoose;
	File addressChoose;
	private String getFileName;
	private String key;
	private int trackerNumber;
	private String trackerAddress;
	private long size;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public FrameFileShare(BlockingQueue<TorFileMess> torMessQ) {
		////////////////////////////////////////////////////////////////////////////
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		JButton btnAddFile = new JButton("AddFile");
		JButton btnCreateFile = new JButton("Create");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnAddFile, 0, SpringLayout.NORTH, btnCreateFile);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnAddFile, -25, SpringLayout.WEST, btnCreateFile);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCreateFile, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCreateFile, 181, SpringLayout.WEST, contentPane);
		JLabel lblStatus = new JLabel("");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblStatus, 27, SpringLayout.SOUTH, btnCreateFile);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblStatus, 156, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblStatus, -143, SpringLayout.EAST, contentPane);
		JLabel lblAddressTracker = new JLabel("Address Tracker");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAddressTracker, 25, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblAddressTracker, -274, SpringLayout.EAST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAddressTracker, -77, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblAddressTracker, -45, SpringLayout.SOUTH, contentPane);
		JTextArea textAddressTracker = new JTextArea();
		sl_contentPane.putConstraint(SpringLayout.NORTH, textAddressTracker, 158, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblStatus, -113, SpringLayout.NORTH, textAddressTracker);
		sl_contentPane.putConstraint(SpringLayout.WEST, textAddressTracker, 41, SpringLayout.EAST, lblAddressTracker);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, textAddressTracker, -27, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, textAddressTracker, -63, SpringLayout.EAST, contentPane);
		
		/////////////////////////////////////////////////////////////////////////////////////////////
		//Button Add File to choose select soure include file -> button Create to create file torrent
		btnAddFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//fileChooser.showOpenDialog(contentPane);
				JFileChooser fileChooser = new JFileChooser();
				int value=fileChooser.showOpenDialog(btnAddFile);
				if(value ==JFileChooser.APPROVE_OPTION){			
				fileChoose = fileChooser.getSelectedFile();
				getFileName=fileChoose.getName();
				//hash content of file torrent
				Hashcode hashFile=new Hashcode(); 
				key=hashFile.hashCode(fileChoose);
				size=fileChoose.length();
				lblStatus.setText("Select Source: "+fileChoose.getAbsolutePath());
				}
				else{
					lblStatus.setText("Open command cancelled by user." );           
		        }
				
	         }    
		});
		//Create FileTorrent
		//JtextArea in order to edit addressTracker
		textAddressTracker.setLineWrap(true);//Sets the line-wrapping policy of the text area.
		textAddressTracker.append("DVT");
		btnCreateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.showOpenDialog(contentPane);
				int value=fileChooser.showSaveDialog(btnCreateFile);
				if(value ==JFileChooser.APPROVE_OPTION){
					addressChoose = fileChooser.getSelectedFile();
					String url=addressChoose.getAbsolutePath();
					String FileName=addressChoose.getName();
					//Get content (trackerAddress) and line (trackerNumber) of JtextArea 
					trackerAddress=textAddressTracker.getText();
					trackerNumber=textAddressTracker.getLineCount();
					// Write FileTorrent
					try {
						FileOutputStream fos = new FileOutputStream(url,true);
						PrintWriter fileTorrent = new PrintWriter(fos);
						 fileTorrent.println(FileName);
			             fileTorrent.println(key);
			             fileTorrent.print(trackerNumber);
			             fileTorrent.println();
			             fileTorrent.println(trackerAddress);
			             fileTorrent.println(size);
			             fileTorrent.flush();
			             fileTorrent.close();          
			             TorFileMess torMess = new TorFileMess(0,addressChoose,fileChoose.getAbsolutePath(),key);
			             torMessQ.put(torMess);
					} catch (FileNotFoundException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}    
				}
				else{
					lblStatus.setText("Save command cancelled by user." );           
		        }
				
			}
		});
		///////////////////////////////////////////////////////////////////////////////////////////
		contentPane.add(btnAddFile);
		contentPane.add(lblStatus);
		contentPane.add(btnCreateFile);
		contentPane.add(lblAddressTracker);
		contentPane.add(textAddressTracker);
		setVisible(true);
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////
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
