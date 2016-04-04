package dtv.peer;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;

import javax.swing.SpringLayout;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JTree;
import java.util.*;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.JMenuItem;
public class UI  implements Runnable{

	private JFrame frame;
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
	private JTextArea txtAddressTracker;
	private JComboBox selectTracker;
	File fileSave; 
	List<DTVParams> fileL;
	private String codeHash;
	private JButton btnNewButton;
	private JMenuItem mntmAboutDtv;
	private JMenuItem mntmExit;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
		
//		try {
//			List<DTVParams> fileL = fileListQ.take();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public String sizeToString(double size){
        String str_size;
        if(size > Math.pow(2,30))
                {
                    size = size/(Math.pow(2, 30));
                    long temp=Math.round(size*100);                    
                    size=(double)temp/100;
                    str_size = Double.toString(size);
                    str_size = str_size+" GB";
                }
                else if(size > Math.pow(2,20))
                {
                    size = size/(Math.pow(2, 20));
                    long temp=Math.round(size*100);                    
                    size=(double)temp/100;
                    str_size = Double.toString(size);
                    str_size = str_size+" MB";
                }
                else if(size > Math.pow(2,10))
                {
                    size = size/(Math.pow(2, 10));
                    long temp=Math.round(size*100);                    
                    size=(double)temp/100;
                    str_size = Double.toString(size);
                    str_size = str_size+" KB";
                }
                else 
                {
                    str_size = Double.toString(size);
                    str_size = str_size+" Byte";
                }
        return str_size; 
    }
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 884, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		frame.setTitle("DTV");
		//JMenu and MenuBar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setBackground(UIManager.getColor("PasswordField.inactiveBackground"));
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnHelps = new JMenu("Help");
		menuBar.add(mnHelps);
		
		mntmAboutDtv = new JMenuItem("About DTV");
		mnHelps.add(mntmAboutDtv);
		//------------------------------------------Define Jtable-------------------------------------------------------- 
		//Jtable File Share
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
		
		//Jtable Search 
		scrollTableRequest = new JScrollPane();
		springLayout.putConstraint(SpringLayout.NORTH, scrollTableRequest, 70, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollTableRequest, 159, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollTableRequest, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollTableRequest, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollTableRequest);
		
		tableRequest = new JTable();
		tableRequest.setColumnSelectionAllowed(true);
		tableRequest.setCellSelectionEnabled(true);
		tableRequest.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollTableRequest.setViewportView(tableRequest);
		springLayout.putConstraint(SpringLayout.SOUTH, tableRequest, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tableRequest, 486, SpringLayout.WEST, frame.getContentPane());
		
		//Jtable Download 
		scrollTableDownload = new JScrollPane();
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
		
		//------------------------------------------Define Button-------------------------------------------------------- 
		btnAddTorrent = new JButton("UPLOAD");
		btnAddTorrent.setForeground(Color.BLACK);
		btnAddTorrent.setFont(new Font("Tahoma", Font.PLAIN, 10));
		springLayout.putConstraint(SpringLayout.NORTH, btnAddTorrent, 12, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAddTorrent, -505, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnAddTorrent);
		
		btnDelete = new JButton("REMOVE");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 10));
		springLayout.putConstraint(SpringLayout.NORTH, btnDelete, 0, SpringLayout.NORTH, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.WEST, btnDelete, 6, SpringLayout.EAST, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDelete, -27, SpringLayout.NORTH, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.EAST, btnDelete, -403, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnDelete);
		
		btnDownload = new JButton("DOWNLOAD");
		btnDownload.setFont(new Font("Tahoma", Font.PLAIN, 10));	
		springLayout.putConstraint(SpringLayout.NORTH, btnDownload, 12, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, btnDownload, 6, SpringLayout.EAST, btnDelete);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDownload, -27, SpringLayout.NORTH, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.EAST, btnDownload, -298, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnDownload);
		
		btnSearch = new JButton("SEARCH");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 10));
		springLayout.putConstraint(SpringLayout.NORTH, btnSearch, 0, SpringLayout.NORTH, btnAddTorrent);
		springLayout.putConstraint(SpringLayout.SOUTH, btnSearch, -27, SpringLayout.NORTH, scrollPaneTable1);
		springLayout.putConstraint(SpringLayout.EAST, btnSearch, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(btnSearch);
	
		//------------------------------------------Define JTree-------------------------------------------------------- 
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
		
		tree = new JTree();
		tree.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent evt) {
				treeValueChanged(evt);
			}
		});
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
	
		DefaultComboBoxModel modelCombo=new DefaultComboBoxModel();	
		JFileChooser fileAddTorrent = new JFileChooser();
		fileAddTorrent.setFileFilter(new Filedoc());
		fileAddTorrent.setFileFilter(new Fileppt());
		fileAddTorrent.setFileFilter(new Filexls());
		fileAddTorrent.setFileFilter(new Filemp3());
		fileAddTorrent.setFileFilter(new Filemp4());
		fileAddTorrent.setFileFilter(new Filejpeg());
		fileAddTorrent.setFileFilter(new Filegif());
		fileAddTorrent.setFileFilter(new Fileflv());
		fileAddTorrent.setFileFilter(new Filehtml());
		fileAddTorrent.setFileFilter(new Filepsd());
		fileAddTorrent.setFileFilter(new Filejpg());
		fileAddTorrent.setFileFilter(new Filepdf());
		fileAddTorrent.setFileFilter(new Filetxt());
		fileAddTorrent.setFileFilter(new Fileavi());
		fileAddTorrent.setFileFilter(new Fileflv());
		fileAddTorrent.setFileFilter(new Filepng());
		fileAddTorrent.setFileFilter(new Filezip());
		fileAddTorrent.setFileFilter(new Filewinrar());
		fileAddTorrent.setFileFilter(fileAddTorrent.getAcceptAllFileFilter());
		fileAddTorrent.setFileView(new ImageFileView());
		//Create DataModel for Jtree
				tree.setModel(new DefaultTreeModel(
					new DefaultMutableTreeNode("DTV") {
						{
							add(new DefaultMutableTreeNode("File Share"));
							add(new DefaultMutableTreeNode("Search"));
							add(new DefaultMutableTreeNode("Download"));
						}
					}
				));
				scrollPaneTree.setViewportView(tree);
				//Create TableModel of Table File Share 
						table.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"NO.", "FILE NAME","SIZE","PATH","HASHCODE"
							}
						)
						);
						//Create TableModel of Table Search
						tableRequest.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
								"NO.", "FILE NAME", "SIZE","SEED"
							}));
						//Create TableModel of Table Download
							tableDownload.setModel(new DefaultTableModel(
							new Object[][] {
							},
							new String[] {
							"NO.", "FILE NAME", "SIZE","TIME"
							}));
				
		//----------------------------------------Action of Button-------------------------------------------
		//Button AddFile
		btnAddTorrent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				int value=fileAddTorrent.showSaveDialog(btnAddTorrent);
				if(value ==JFileChooser.APPROVE_OPTION){
				File file = fileAddTorrent.getSelectedFile();
				
				String size=sizeToString(file.length());
				String hashCode = "";
				try {
					hashCode = generateSHA512(new FileInputStream(file));
				} catch (FileNotFoundException e2) {
				}
				int numberRow=0;
				boolean flag = false;
				boolean flagLoad = false;
				if(model.getRowCount()==0){
					model.addRow(new Object[]{model.getRowCount()+1,file.getName(),size,file.getAbsolutePath(),hashCode});
				}
				else{
					int count = model.getRowCount();
					while(numberRow<count){
						if(file.getName().equals((String)model.getValueAt(numberRow, 1))){
							flag = true;
							flagLoad=true;
							break;
						}
						numberRow=numberRow+1;
					}
					if(flag==false){
						model.addRow(new Object[]{model.getRowCount()+1,file.getName(),size,file.getAbsoluteFile(),hashCode});
					} 
					}
				if(flagLoad==false){
				DTVParams dtv_params = new DTVParams();
				dtv_params.setName(file.getName());
				dtv_params.setHashCode(hashCode);
				dtv_params.setSize(file.length());
				dtv_params.setPathToFile(file.getAbsolutePath());
				//add tracker at dtv_params
				boolean flagAdd=false;
				String[] lines = txtAddressTracker.getText().split("\n");//read line by line of a text area
				int row=txtAddressTracker.getLineCount();
				int rowCombo=selectTracker.getItemCount();
				if(rowCombo==0){
					for(int i=0;i<row;i++){
						modelCombo.addElement(lines[i]);//add data in row for combobox
						dtv_params.addTracker(lines[i]);
					}
				}
				else {
					for(int i=0;i<row;i++){
						for(int j=0;j<rowCombo;j++){
							if(lines[i].equals((String)(selectTracker.getItemAt(j)))){
								flagAdd=true;
								dtv_params.addTracker(lines[i]);
							}
						}
						if(!flagAdd) {
							modelCombo.addElement(lines[i]);//add data in row for combobox
						}	
						flagAdd=false;
					}
				}
				selectTracker.setModel(modelCombo);
				dtv_params.setType(0);		//Share File
				try {
					DTV.UIToPeer.put(dtv_params);
				} catch (InterruptedException e1) {
				}
				}
				}
				tree.setSelectionRow(1);
			}
		}
		);
		//Button Search 
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel modelRequest=(DefaultTableModel)tableRequest.getModel();
				//delete data for tableSearch
				while(modelRequest.getRowCount()!=0)
			       {
					modelRequest.removeRow(0);
			       }
				DTVParams dtvParams1= new DTVParams();
				dtvParams1.addTracker((String)selectTracker.getSelectedItem());
				dtvParams1.setType(2);
				try {
					DTV.UIToPeer.put(dtvParams1);
					//List<DTVParams>
					fileL = DTV.fileList.take();	
					for (int i = 0; i < fileL.size(); i++)
					{
						DTVParams tParams = fileL.get(i);
						modelRequest.addRow(new Object[]{modelRequest.getRowCount()+1,tParams.getName(),
								sizeToString(tParams.getSize()),tParams.getType()});
					}
					
				} catch (InterruptedException e) {
				}
				tree.setSelectionRow(2);
				
			}
		});
		//Button Download
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel tableDowloadModel=(DefaultTableModel)tableDownload.getModel();
				DefaultTableModel tableRequestModel = (DefaultTableModel) tableRequest.getModel();
				JFileChooser fileChooseSave=new JFileChooser();			     
			    fileChooseSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    File temp = new File("c:/Users/" + System.getProperty("user.name") + "/Desktop/"
			    		+ (String) tableRequestModel.getValueAt(tableRequest.getSelectedRow(), 1));
			    fileChooseSave.setSelectedFile(temp);
			    fileChooseSave.showSaveDialog(btnDownload);
			    
				tree.setSelectionPath(tree.getPathForRow(2));//chon duong dan o Table Searh (JTree 2)
				for (int i = 0; i < fileL.size(); i++){
					if(fileL.get(i).getName().equals((String) tableRequestModel.getValueAt(tableRequest.getSelectedRow(), 1))){
						DTVParams dtvParamsDownload=fileL.get(i);
						dtvParamsDownload.setType(1);
						dtvParamsDownload.setPathToFile(fileChooseSave.getSelectedFile().getAbsolutePath());
						try {
							DTV.UIToPeer.put(dtvParamsDownload);
						} catch (InterruptedException e) {
						}
						break;
					}
				}
				
				 Date time = new Date();
		         int index = tableRequest.getSelectedRow();
		         
		         //Add Data for Jtable Download			     
		         tableDowloadModel.addRow(new Object[]{tableDowloadModel.getRowCount()+1, tableRequestModel.getValueAt(index, 1),
		        		 								tableRequestModel.getValueAt(index, 2), time });
		        tree.setSelectionRow(3);
			}
		});
		//Button Remove
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 DefaultTableModel model = (DefaultTableModel) table.getModel();
				 DefaultTableModel modelDownload=(DefaultTableModel) tableDownload.getModel();
				 if(tree.getSelectionPath().equals(tree.getPathForRow(1))){
					int del = table.getSelectedRows().length;
			        for(int i= 0; i<del;i++){
			        	DTVParams params=new DTVParams();
				        params.setType(3);
				        params.setHashCode((String)model.getValueAt(table.getSelectedRow(), 4));
				        try {
							DTV.UIToPeer.put(params);
						} catch (InterruptedException e) {
						}
			        	model.removeRow(table.getSelectedRows()[0]);
			        }
			        for(int i=0;i<table.getRowCount();i++)
			        	model.setValueAt(i+1, i, 0);
		        }
				 else if(tree.getSelectionPath().equals(tree.getPathForRow(3))){
						int del = tableDownload.getSelectedRows().length;
				        for(int i= 0; i<del;i++)
				        	modelDownload.removeRow(tableDownload.getSelectedRows()[0]);
				        for(int i=0;i<tableDownload.getRowCount();i++)
				        	modelDownload.setValueAt(i+1, i, 0);
					}
			}
			
		});
		//--------------------------------------------IMAGES TREE-------------------------------------------------------------------------
				tree.setCellRenderer(new DefaultTreeCellRenderer() {
					private final Icon downloadIcon = new ImageIcon(this.getClass().getResource("/dtv/picture/download-icon.png"));
			        private final Icon searchIcon =  new ImageIcon(this.getClass().getResource("/dtv/picture/search-icon.png"));
			        private final Icon uploadIcon =  new ImageIcon(this.getClass().getResource("/dtv/picture/upload-icon.png"));
			        private final Icon torrentIcon =  new ImageIcon(this.getClass().getResource("/dtv/picture/torrent.png"));
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
	    
		//----------------------------------------------Get Width for Jtable FileShare--------------------------------------------------
	         TableColumnModel tcmUpdate = table.getColumnModel();
	            TableColumn tcUpdate;
	            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	            tcUpdate= tcmUpdate.getColumn(0);
	            tcUpdate.setPreferredWidth(74);
	            
	            tcUpdate= tcmUpdate.getColumn(1);
	            tcUpdate.setPreferredWidth(301);
	           
	            tcUpdate= tcmUpdate.getColumn(2);
	            tcUpdate.setPreferredWidth(79);
	           
	            tcUpdate= tcmUpdate.getColumn(3);
	            tcUpdate.setPreferredWidth(400);
	            
	            tcUpdate= tcmUpdate.getColumn(4);
	            tcUpdate.setPreferredWidth(0);
	     //----------------------------------------------Get Width for Jtable Search--------------------------------------------------
				TableColumnModel tcmRequest = tableRequest.getColumnModel();
				TableColumn tcRequest;
				tableRequest.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tcRequest= tcmRequest.getColumn(1);
				tcRequest.setPreferredWidth(425);
				
				tcRequest= tcmRequest.getColumn(0);
				tcRequest.setPreferredWidth(74);
				
				tcRequest= tcmRequest.getColumn(2);
				tcRequest.setPreferredWidth(100);
				tcRequest= tcmRequest.getColumn(3);
				tcRequest.setPreferredWidth(100);
				
		//----------------------------------------------Get Width for Jtable FileShare--------------------------------------------------
				TableColumnModel tcmDownload = tableDownload.getColumnModel();
				TableColumn tcDownload;
				tableDownload.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
				tcDownload= tcmDownload.getColumn(1);
				tcDownload.setPreferredWidth(301);
				
				tcDownload= tcmDownload.getColumn(0);
				tcDownload.setPreferredWidth(74);
				
				tcDownload= tcmDownload.getColumn(2);
				tcDownload.setPreferredWidth(79);
				
				tcDownload= tcmDownload.getColumn(3);
				tcDownload.setPreferredWidth(300);
				
				//---------------------------------------Define TextArea and Combobox-------------------------------------------
				selectTracker = new JComboBox();
				springLayout.putConstraint(SpringLayout.WEST, btnSearch, 6, SpringLayout.EAST, selectTracker);
				springLayout.putConstraint(SpringLayout.NORTH, selectTracker, 0, SpringLayout.NORTH, btnAddTorrent);
				springLayout.putConstraint(SpringLayout.WEST, selectTracker, 6, SpringLayout.EAST, btnDownload);
				springLayout.putConstraint(SpringLayout.SOUTH, selectTracker, 43, SpringLayout.NORTH, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.EAST, selectTracker, -108, SpringLayout.EAST, frame.getContentPane());
				frame.getContentPane().add(selectTracker);
				
				txtAddressTracker = new JTextArea();
				springLayout.putConstraint(SpringLayout.NORTH, txtAddressTracker, 12, SpringLayout.NORTH, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.WEST, txtAddressTracker, 14, SpringLayout.WEST, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.SOUTH, txtAddressTracker, -6, SpringLayout.NORTH, scrollPaneTable1);
				springLayout.putConstraint(SpringLayout.EAST, txtAddressTracker, -675, SpringLayout.EAST, frame.getContentPane());
				frame.getContentPane().add(txtAddressTracker);		
				txtAddressTracker.setLineWrap(true);//Sets the line-wrapping policy of the text area.
				
				btnNewButton = new JButton("OK");
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
				springLayout.putConstraint(SpringLayout.WEST, btnAddTorrent, 6, SpringLayout.EAST, btnNewButton);
				springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 12, SpringLayout.NORTH, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 6, SpringLayout.EAST, txtAddressTracker);
				springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -27, SpringLayout.NORTH, scrollPaneTable1);
				springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -599, SpringLayout.EAST, frame.getContentPane());
				frame.getContentPane().add(btnNewButton);
				//Button OK
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						 btnSearch.setEnabled(true);
						String[] lines = txtAddressTracker.getText().split("\n");//read line by line of a text area
						int row=txtAddressTracker.getLineCount();
						int rowCombo=selectTracker.getItemCount();
						boolean flagAdd=false;
						if(rowCombo==0){
							for(int i=0;i<row;i++){
								modelCombo.addElement(lines[i]);//add data in row for combobox
							}
						}
						else {
							for(int i=0;i<row;i++){
								for(int j=0;j<rowCombo;j++){
									if(lines[i].equals((String)(selectTracker.getItemAt(j)))){
										flagAdd=true;
									}
								}
								if(!flagAdd) {
									modelCombo.addElement(lines[i]);//add data in row for combobox
								}	
								flagAdd=false;
							}
						}
						selectTracker.setModel(modelCombo);
						}
					}
				);	
				txtAddressTracker.append("192.168.43.53:1234");	
				btnSearch.setEnabled(false);
	}
	//Change value for Jtree
	private void treeValueChanged(TreeSelectionEvent evt) { //GEN-FIRST:event_treeValueChanged
            // TODO add your handling code here:
            switch (tree.getSelectionPath().getLastPathComponent().toString()) {
                case "File Share":
                    scrollPaneTable1.setVisible(true);
                    scrollTableRequest.setVisible(false);
                    scrollTableDownload.setVisible(false);
                    btnDelete.setEnabled(true);
                    btnAddTorrent.setEnabled(true);
                    btnDownload.setEnabled(false);
                    btnSearch.setEnabled(true);
                    break;
                case "Search":
                    scrollPaneTable1.setVisible(false);
                    scrollTableRequest.setVisible(true);
                    scrollTableDownload.setVisible(false);
                    btnDelete.setEnabled(false);
                    btnAddTorrent.setEnabled(false);
                    btnDownload.setEnabled(true);
                    btnSearch.setEnabled(true);
                    break;
                case "Download":
                    scrollPaneTable1.setVisible(false);
                    scrollTableRequest.setVisible(false);
                    scrollTableDownload.setVisible(true);
                    btnDelete.setEnabled(true);
                    btnAddTorrent.setEnabled(false);
                    btnDownload.setEnabled(false);
                    btnSearch.setEnabled(false);
                    break;
                default:
                    break;
            }
    }//GEN-LAST:event_treeValueChanged
	 @Override
		public void run() {
			// TODO Auto-generated method stub
			frame.setVisible(true);
			
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			while (true)
			{
				try {
					DTVParams revDtv = DTV.PeerToUI.take();
					
					if (revDtv.getType() == 0)
					{
						model.addRow(new Object[]{model.getRowCount()+1,revDtv.getName(),
								sizeToString(revDtv.getSize()),revDtv.getPathToFile(),
								revDtv.getHashCode()});
					}
				} catch (InterruptedException e) {
				}	
			}
		}
	 //Hash Code
	 public static String generateSHA512(FileInputStream inputStream){
		    if(inputStream==null){

		        return null;
		    }
		    MessageDigest md;
		    try {
		        md = MessageDigest.getInstance("SHA-512");
		        FileChannel channel = inputStream.getChannel();
		        ByteBuffer buff = ByteBuffer.allocate(1024*1024);
		        while(channel.read(buff) != -1)
		        {
		            buff.flip();
		            md.update(buff);
		            buff.clear();
		        }
		        byte[] hashValue = md.digest();
		        return convertByteArrayToHexString(hashValue);
		    }
		    catch (NoSuchAlgorithmException | IOException e) 
		    {
		        return null;
		    }
		    finally
		    {
		        try {
		            if(inputStream!=null)inputStream.close();
		        } catch (IOException e) {

		        }
		    } 
		}
	 
	 private static String convertByteArrayToHexString(byte[] arrayBytes) {
		    StringBuffer stringBuffer = new StringBuffer();
		    for (int i = 0; i < arrayBytes.length; i++) {
		        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
		                .substring(1));
		    }
		    return stringBuffer.toString();
		}
	 //Message Dialog
	 public static void showWarningTextBox(String messToShow, String title)
	 {
		JFrame frame = new JFrame();

    	JOptionPane.showMessageDialog(frame,
        messToShow,
        title,
        JOptionPane.ERROR_MESSAGE);
	 }
}

