package dtv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTree;

public class UI  implements Runnable{

	private JFrame frame;
	protected BlockingQueue<DTVParams> torMessQ = null;
	private JTree tree;
	private JTable table;
	private JTable tableRequest;
	private JTable tableDownload;
	private JScrollPane scrollPaneTable1;
	private JScrollPane scrollTableRequest;
	private JScrollPane scrollTableDownload;
	private JScrollPane scrollPaneTree;
	private JButton btnAddTorrent;
	private JButton btnDelete;
	private JButton btnDownload;
	private JButton btnSearch;
	File fileSave; 
	
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public UI(BlockingQueue<DTVParams> q) {
		initialize();
		torMessQ = q;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 858, 455);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		btnAddTorrent = new JButton("ADD FILE");
		springLayout.putConstraint(SpringLayout.NORTH, btnAddTorrent, 14, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnAddTorrent, 28, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAddTorrent, -726, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnAddTorrent);
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Jtable FileShare ----------------------------
		scrollPaneTable1 = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneTable1, 70, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPaneTable1, 159, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneTable1, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneTable1, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollPaneTable1);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPaneTable1.setViewportView(table);
		springLayout.putConstraint(SpringLayout.SOUTH, table, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, table, 486, SpringLayout.WEST, frame.getContentPane());
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Jtable FileShare ----------------------------
		scrollTableRequest = new JScrollPane();
		springLayout.putConstraint(SpringLayout.SOUTH, btnAddTorrent, -29, SpringLayout.NORTH, scrollTableRequest);
		springLayout.putConstraint(SpringLayout.NORTH, scrollTableRequest, 70, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollTableRequest, 159, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollTableRequest, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollTableRequest, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollTableRequest);
		
		tableRequest = new JTable();
		tableRequest.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollTableRequest.setViewportView(tableRequest);
		springLayout.putConstraint(SpringLayout.SOUTH, tableRequest, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tableRequest, 486, SpringLayout.WEST, frame.getContentPane());
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Jtable FileShare ----------------------------
		scrollTableDownload = new JScrollPane();
		springLayout.putConstraint(SpringLayout.SOUTH, btnAddTorrent, -29, SpringLayout.NORTH, scrollTableDownload);
		springLayout.putConstraint(SpringLayout.NORTH, scrollTableDownload, 70, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollTableDownload, 159, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollTableDownload, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollTableDownload, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollTableDownload);
		
		tableDownload = new JTable();
		tableDownload.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollTableDownload.setViewportView(tableDownload);
		springLayout.putConstraint(SpringLayout.SOUTH, tableDownload, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tableDownload, 486, SpringLayout.WEST, frame.getContentPane());
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		btnDelete = new JButton("REMOVE");
		springLayout.putConstraint(SpringLayout.NORTH, btnDelete, -2, SpringLayout.NORTH, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 113, SpringLayout.EAST, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDelete, 0, SpringLayout.SOUTH, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.EAST, btnDelete, -525, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnDelete);
		
		btnDownload = new JButton("DOWNLOAD");
		springLayout.putConstraint(SpringLayout.NORTH, btnDownload, 0, SpringLayout.NORTH, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.WEST, btnDownload, 17, SpringLayout.EAST, btnDelete);
		frame.getContentPane().add(btnDownload);
		
		btnSearch = new JButton("SEARCH");
		springLayout.putConstraint(SpringLayout.NORTH, btnSearch, 0, SpringLayout.NORTH, btnDelete);
		springLayout.putConstraint(SpringLayout.WEST, btnSearch, 27, SpringLayout.EAST, btnDownload);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSearch, 0, SpringLayout.SOUTH, btnAddTorrent);
		frame.getContentPane().add(btnSearch);
		//Create TableModel of Table File Share 
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"STT", "FILE NAME", "SIZE", "PATH"
			}
		));
		//change JTable column width
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(1).setPreferredWidth(190);
		table.getColumnModel().getColumn(2).setPreferredWidth(0);
		table.getColumnModel().getColumn(3).setPreferredWidth(296);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		/////////////////////////////////////////////////////////
		tableRequest.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"STT", "FILE NAME", "SIZE", "IP ADDRESS","PORT"
				}
			));
			//change JTable column width
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			tableDownload.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			"STT", "FILE NAME", "SIZE", "STATUS","IP ADDRESS","PORT","TIME"
			}
			));
			//change JTable column width
			/////////////////////////////////////////////////////////////////
		scrollPaneTree = new JScrollPane();
		springLayout.putConstraint(SpringLayout.SOUTH, btnAddTorrent, -27, SpringLayout.NORTH, scrollPaneTree);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneTree, 0, SpringLayout.NORTH, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.WEST, scrollPaneTree, 14, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneTree, 0, SpringLayout.SOUTH, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneTree, -6, SpringLayout.WEST, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneTree, 0, SpringLayout.NORTH, scrollTableDownload);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneTree, 0, SpringLayout.SOUTH, scrollTableDownload);
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneTree, -6, SpringLayout.WEST, scrollTableDownload);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPaneTree, 0, SpringLayout.NORTH, scrollTableRequest);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPaneTree, 0, SpringLayout.SOUTH, scrollTableRequest);
		springLayout.putConstraint(SpringLayout.EAST, scrollPaneTree, -6, SpringLayout.WEST, scrollTableRequest);
		frame.getContentPane().add(scrollPaneTree);
			/////////////////////////////////////////////////////////
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				treeValueChanged(evt);
			}
		});
		tree.setModel(new DefaultTreeModel(
				new DefaultMutableTreeNode("Torrent") {
					{
						add(new DefaultMutableTreeNode("File Share"));
						add(new DefaultMutableTreeNode("Search"));
						add(new DefaultMutableTreeNode("Download"));
					}
				}
			));
		scrollPaneTree.setViewportView(tree);
		
		
		
		springLayout.putConstraint(SpringLayout.NORTH, tree, 0, SpringLayout.NORTH, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.WEST, tree, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tree, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tree, -6, SpringLayout.WEST, scrollPaneTable1);
		//////////////////////////////////////////////////////////////////////////////////////////////
		springLayout.putConstraint(SpringLayout.NORTH, tree, 0, SpringLayout.NORTH, scrollTableRequest);
		springLayout.putConstraint(SpringLayout.WEST, tree, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tree, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tree, -6, SpringLayout.WEST, scrollTableRequest);
		////////////////////////////////////////////////////////////////////////////////////////////////
		springLayout.putConstraint(SpringLayout.NORTH, tree, 0, SpringLayout.NORTH, scrollTableDownload);
		springLayout.putConstraint(SpringLayout.WEST, tree, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tree, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tree, -6, SpringLayout.WEST, scrollTableDownload);
		
		//Add data for Table FileShare
		DefaultTableModel model=(DefaultTableModel)table.getModel();
		btnAddTorrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileAddTorrent = new JFileChooser();
				int value=fileAddTorrent.showSaveDialog(btnAddTorrent);
				if(value ==JFileChooser.APPROVE_OPTION){
				File file = fileAddTorrent.getSelectedFile();
				String size=Long.toString(file.length())+" bytes";
				int numberRow=0;
				boolean flag = false;
				if(model.getRowCount()==0){
					model.addRow(new Object[]{model.getRowCount()+1,file.getName(),size,file.getAbsoluteFile()});
				}
				else{
					int count = model.getRowCount();
					while(numberRow<count){
						if(file.getName().equals((String)model.getValueAt(numberRow, 1))){
							flag = true;
							break;
						}
						numberRow=numberRow+1;
					}
					if(flag==false){
						model.addRow(new Object[]{model.getRowCount()+1,file.getName(),size,file.getAbsoluteFile()});
					} 
					}
				Hashcode hashFile=new Hashcode(); 
				DTVParams dtv_params = new DTVParams();
				dtv_params.setName(file.getName());
				dtv_params.setHashCode(hashFile.hashCode(file));
				dtv_params.setSize(file.length());
				dtv_params.setPathToFile(file.getAbsolutePath());
				dtv_params.setType(0);						//Share File
				}
			}
		}
		);
		//Remove a DataRow on Table
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        int del = table.getSelectedRows().length;
		        for(int i= 0; i<del;i++)
		        	model.removeRow(table.getSelectedRows()[0]);
		        for(int i=0;i<table.getRowCount();i++)
		        	model.setValueAt(i+1, i, 0);
			}
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//--------------------------------------------IMAGES TREE-------------------------------------------------------------------------
		//Change Icon for Jtree
				tree.setCellRenderer(new DefaultTreeCellRenderer() {
					private Icon downloadIcon = new ImageIcon(this.getClass().getResource("/download-icon.png"));
			        private Icon searchIcon =  new ImageIcon(this.getClass().getResource("/search-icon.png"));
			        private Icon uploadIcon =  new ImageIcon(this.getClass().getResource("/upload-icon.png"));
			        private Icon torrentIcon =  new ImageIcon(this.getClass().getResource("/torrent.png"));
				        @Override
				        public Component getTreeCellRendererComponent(JTree tree,
				                Object value, boolean selected, boolean expanded,
				                boolean isLeaf, int row, boolean focused) {
				            Component c = super.getTreeCellRendererComponent(tree, value,
				                    selected, expanded, isLeaf, row, focused);
				            if (isLeaf && value.toString().equals("Download"))
				                setIcon(downloadIcon);
				            else if (isLeaf && value.toString().equals("Search"))
				                setIcon(searchIcon);
				            else if (isLeaf && value.toString().equals("File Share"))
				                setIcon(uploadIcon);
				            else
				                 setIcon(torrentIcon);
				            return c;
				        }
				    });
	//////--------------------------------------------IMAGES BUTTON--------------------------------------------------
		btnDelete.setBorder(BorderFactory.createEmptyBorder());
		btnDelete.setContentAreaFilled(false);
		btnDelete.setIcon(new ImageIcon(this.getClass().getResource("remove.png")));
		btnDelete.setText("");
		btnDownload.setBorder(BorderFactory.createEmptyBorder());
		btnDownload.setContentAreaFilled(false);
		btnDownload.setIcon(new ImageIcon(this.getClass().getResource("up-down.png")));
		btnDownload.setText("");
		btnSearch.setBorder(BorderFactory.createEmptyBorder());
		btnSearch.setContentAreaFilled(false);
		btnSearch.setIcon(new ImageIcon(this.getClass().getResource("search.png")));
		btnSearch.setText("");
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	}
	
	//Change value for Jtree
	private void treeValueChanged(TreeSelectionEvent evt) {//GEN-FIRST:event_treeValueChanged
        // TODO add your handling code here:
       
        if(tree.getSelectionPath().getLastPathComponent().toString().equals("File Share"))
        {
        	scrollPaneTable1.setVisible(true);
        	scrollTableRequest.setVisible(false);
            scrollTableDownload.setVisible(false);
            btnDelete.setEnabled(true);
            btnAddTorrent.setEnabled(true);
            btnDownload.setEnabled(false);
            btnSearch.setEnabled(false);
          
        	/*
            if(status == 1)
                bttShare.setEnabled(true);
            else bttShare.setEnabled(false);
         */
               
        }
        else if(tree.getSelectionPath().getLastPathComponent().toString().equals("Search"))
        {
        	scrollPaneTable1.setVisible(false);
        	scrollTableRequest.setVisible(true);
            scrollTableDownload.setVisible(false);
            btnDelete.setEnabled(false);
            btnAddTorrent.setEnabled(false);
            btnDownload.setEnabled(true);
            btnSearch.setEnabled(true);                   
        }
        else  if(tree.getSelectionPath().getLastPathComponent().toString().equals("Download"))
        {
        	scrollPaneTable1.setVisible(false);
        	scrollTableRequest.setVisible(false);
            scrollTableDownload.setVisible(true);
            btnDelete.setEnabled(true);
            btnAddTorrent.setEnabled(false);
            btnDownload.setEnabled(false);
            btnSearch.setEnabled(false);
        }
    }//GEN-LAST:event_treeValueChanged
	// hash content of fileShare
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
		private  String convertByteArrayToHexString(byte[] arrayBytes) {
		    StringBuffer stringBuffer = new StringBuffer();
		    for (int i = 0; i < arrayBytes.length; i++) {
		        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
		                .substring(1));
		    }
		    return stringBuffer.toString();
		}
	}
	 @Override
		public void run() {
			// TODO Auto-generated method stub
			frame.setVisible(true);
		}

}
