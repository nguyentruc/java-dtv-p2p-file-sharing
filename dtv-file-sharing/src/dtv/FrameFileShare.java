package dtv;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import XMLDom.FileTorrent;
import XMLDom.Hashcode;
import XMLDom.Marshal;
import XMLDom.Unmarshal;

import javax.swing.SpringLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class FrameFileShare extends JFrame {

	private JPanel contentPane;
	private JFrame frameShare;
	private final FileTorrent fileTorrent = new FileTorrent();
	/**
	 * Launch the application.
	 */
	
	     
	
	/**
	 * Create the frame.
	 */
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
		
		btnAddFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.showOpenDialog(contentPane);
				int value=fileChooser.showOpenDialog(btnAddFile);
				if(value ==JFileChooser.APPROVE_OPTION){
				
				//test
					int count=fileChooser.getSelectedFiles().length;
					String fileName[] = new String[count];
					for(int i=0;i<count;i++)
			           {
			                fileName[i] = fileChooser.getName(fileChooser.getSelectedFiles()[i]);
			                
			           }
				//			
				File fileChoose = fileChooser.getSelectedFile();
				lblStatus.setText("File Selected : " + fileChoose.getName() +  fileChoose.getAbsolutePath());
				fileTorrent.setTitle(fileChoose.getAbsolutePath());
				
				InetAddress myHost;
				try {
					myHost = InetAddress.getLocalHost();
					fileTorrent.setIP(myHost.getHostAddress());
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				fileTorrent.setPORT("6789");
				Hashcode hashFile=new Hashcode();
				String key=hashFile.hashCode(fileChoose);
				fileTorrent.setHashKey(key);
				fileTorrent.setTrackerAddress("VDT dep trai");
				}
				else{
					lblStatus.setText("Open command cancelled by user." );           
		        }
				
	         }    
				//create torrent file
				//create TorFileMess
				//send to thread
				
		});
		contentPane.add(btnAddFile);
		contentPane.add(lblStatus);
		
		JButton btnCreateFile = new JButton("Create");
		btnCreateFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.showOpenDialog(contentPane);
				int value=fileChooser.showSaveDialog(btnCreateFile);
				if(value ==JFileChooser.APPROVE_OPTION){
					File addressChoose = fileChooser.getSelectedFile();
					String url=addressChoose.getAbsolutePath();
					Marshal marshal=new Marshal();
					marshal.marshalXML(url, fileTorrent);
					Unmarshal unmarshal=new Unmarshal();
					FileTorrent filexml=unmarshal.unmarshalXML(new File (url));
					System.out.println(filexml.getTitle());
				}
				else{
					lblStatus.setText("Save command cancelled by user." );           
		        }
				
			}
		});
		//
		
		
		//
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnCreateFile, 0, SpringLayout.NORTH, btnAddFile);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnCreateFile, 31, SpringLayout.EAST, btnAddFile);
		contentPane.add(btnCreateFile);
		setVisible(true);
	}
}
