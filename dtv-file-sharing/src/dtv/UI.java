package dtv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
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
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JMenu;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.Font;
public class UI  implements Runnable{

	private JFrame frame;
	protected BlockingQueue<DTVParams> torMessQ = null;
	BlockingQueue<List<DTVParams>> fileListQ = null;
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
	private String codeHash;
	private List<DTVParams> fileL;
	private JButton btnNewButton;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public UI(BlockingQueue<DTVParams> q, BlockingQueue<List<DTVParams>> fileList) {
		initialize();
		torMessQ = q;
		fileListQ = fileList;
		
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("E:\\Hoc tap\\MMT1\\Assignment\\Assignment\\test\\src\\client\\images\\icons\\torrent.png"));
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 884, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		frame.setTitle("Torrent");
		
		btnAddTorrent = new JButton("ADD FILE");
		btnAddTorrent.setFont(new Font("Tahoma", Font.PLAIN, 10));
		springLayout.putConstraint(SpringLayout.NORTH, btnAddTorrent, 12, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, btnAddTorrent, -505, SpringLayout.EAST, frame.getContentPane());
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
		//Create TableModel of Table File Share 
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"STT", "FILE NAME", "SIZE", "PATH","HASHCODE"
			}
		){
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }			
		);
		/////////////////////////////////////////////////////////
		tableRequest.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"STT", "FILE NAME", "SIZE", "IP ADDRESS","PORT"
				}
			)
		{
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
            		true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }		
			);
			//change JTable column width
			/////////////////////////////////////////////////////////
			/////////////////////////////////////////////////////////
			tableDownload.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			"STT", "FILE NAME", "SIZE", "STATUS","IP ADDRESS","PORT","TIME"
			})
			{
	            Class[] types = new Class [] {
	                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
	            };
	            boolean[] canEdit = new boolean [] {
	                true, false, false, false,false, false,true
	            };

	            public Class getColumnClass(int columnIndex) {
	                return types [columnIndex];
	            }

	            public boolean isCellEditable(int rowIndex, int columnIndex) {
	                return canEdit [columnIndex];
	            }
	        }			
					);
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
		tree.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tree.setShowsRootHandles(true);
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
		//////////////////////////////////////////////////
		btnAddTorrent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model=(DefaultTableModel)table.getModel();
				//hash code
				//////////
				int value=fileAddTorrent.showSaveDialog(btnAddTorrent);
				if(value ==JFileChooser.APPROVE_OPTION){
				File file = fileAddTorrent.getSelectedFile();
				String size=sizeToString(file.length());
				String hashCode = "";
				try {
					hashCode = generateSHA512(new FileInputStream(file));
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				int numberRow=0;
				boolean flag = false;
				boolean flagLoad = false;
				if(model.getRowCount()==0){
					model.addRow(new Object[]{model.getRowCount()+1,file.getName(),size,file.getAbsoluteFile(),hashCode});
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
				/////////////////////////////////////////
				dtv_params.setType(0);		//Share File
				try {
					torMessQ.put(dtv_params);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}
				}
				tree.setSelectionRow(1);
			}
		}
		);
		//Search 
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel modelRequest=(DefaultTableModel)tableRequest.getModel();
				//delete data for tableSearch
				while(modelRequest.getRowCount()!=0)
			       {
					modelRequest.removeRow(0);
			       }
				 tree.setSelectionRow(2);
				///////////////////////////////////
				DTVParams dtvParams1= new DTVParams();
				dtvParams1.addTracker((String)selectTracker.getSelectedItem());
				dtvParams1.setType(2);
				try {
					torMessQ.put(dtvParams1);
					//List<DTVParams>
					fileL = fileListQ.take();
					
					for (int i = 0; i < fileL.size(); i++)
					{
						DTVParams tParams = fileL.get(i);
						modelRequest.addRow(new Object[]{modelRequest.getRowCount()+1,tParams.getName(),sizeToString(tParams.getSize())});
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
		//Download a File
		btnDownload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel tableDowloadModel=(DefaultTableModel)tableDownload.getModel();
				DefaultTableModel tableRequestModel = (DefaultTableModel) tableRequest.getModel();
				DefaultTableModel model=(DefaultTableModel) table.getModel();
				 JFileChooser fileChooseSave=new JFileChooser();
			     
			     fileChooseSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
			     
			     //if(value==JFileChooser.APPROVE_OPTION){
			    	 File temp = new File("c:/Users/" + (String) tableRequestModel.getValueAt(tableRequest.getSelectedRow(), 1));
			    	 fileChooseSave.setSelectedFile(temp);//save file 
			    	fileChooseSave.showSaveDialog(btnDownload);
				tree.setSelectionPath(tree.getPathForRow(2));//chon duong dan o Table Searh (JTree 2)
				for (int i = 0; i < fileL.size(); i++){
					if(fileL.get(i).getName().equals((String) tableRequestModel.getValueAt(tableRequest.getSelectedRow(), 1))){
						DTVParams dtvParamsDownload=fileL.get(i);
						dtvParamsDownload.setType(1);
						 codeHash=dtvParamsDownload.getHashCode();
						dtvParamsDownload.setPathToFile(fileChooseSave.getSelectedFile().getAbsolutePath());
						try {
							torMessQ.put(dtvParamsDownload);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
				////////////////////////////////////////////
				 Date time = new Date();
		         int index = tableRequest.getSelectedRow();
		         ///////////////////////////////////////////
		         model.addRow(new Object[]{model.getRowCount()+1,tableRequestModel.getValueAt(index, 1),tableRequestModel.getValueAt(index, 2),fileChooseSave.getSelectedFile().getAbsolutePath(),codeHash});
				
			     //////////////////////////////////////////
			    
		         Object[] d = new Object[tableDownload.getColumnCount()];
		         String path = fileChooseSave.getSelectedFile().getAbsolutePath();
		         d[0] = tableDowloadModel.getRowCount()+1;            
		         d[1] = tableRequestModel.getValueAt(index, 1);
		         d[2] = tableRequestModel.getValueAt(index, 2);
		         d[3] = 0;
		         d[4] = tableRequestModel.getValueAt(index, 3);
		         d[5] = tableRequestModel.getValueAt(index, 4);
		         d[6] = time;        
		         int count = tableDownload.getRowCount();
		         tableDowloadModel.insertRow(count,d);
		         tree.setSelectionRow(3);
			}
		});
		//Remove a DataRow on Table
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 DefaultTableModel model = (DefaultTableModel) table.getModel();
				 DefaultTableModel modelDownload = (DefaultTableModel) tableDownload.getModel();
				//if(tree.getSelectionPath().equals(tree.getPathForRow(1))){
					int del = table.getSelectedRows().length;
			        for(int i= 0; i<del;i++){
			        	DTVParams params=new DTVParams();
				        params.setType(3);
				        params.setHashCode((String)model.getValueAt(table.getSelectedRow(), 4));
				        try {
							torMessQ.put(params);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	model.removeRow(table.getSelectedRows()[0]);
			        }
			        for(int i=0;i<table.getRowCount();i++)
			        	model.setValueAt(i+1, i, 0);
			        
				/*}
				else if(tree.getSelectionPath().equals(tree.getPathForRow(3))){
					int del = tableDownload.getSelectedRows().length;
			        for(int i= 0; i<del;i++)
			        	modelDownload.removeRow(tableDownload.getSelectedRows()[0]);
			        for(int i=0;i<tableDownload.getRowCount();i++)
			        	modelDownload.setValueAt(i+1, i, 0);
				}*/
		        
			}
		});
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//--------------------------------------------IMAGES TREE-------------------------------------------------------------------------
		//Change Icon for Jtree
				tree.setCellRenderer(new DefaultTreeCellRenderer() {
					private Icon downloadIcon = new ImageIcon(this.getClass().getResource("download-icon.png"));
			        private Icon searchIcon =  new ImageIcon(this.getClass().getResource("search-icon.png"));
			        private Icon uploadIcon =  new ImageIcon(this.getClass().getResource("upload-icon.png"));
			        private Icon torrentIcon =  new ImageIcon(this.getClass().getResource("torrent.png"));
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
	/*	btnDelete.setBorder(BorderFactory.createEmptyBorder());
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
		btnAddTorrent.setBorder(BorderFactory.createEmptyBorder());
		btnAddTorrent.setContentAreaFilled(false);
		btnAddTorrent.setIcon(new ImageIcon(this.getClass().getResource("up-file-share.png")));
		btnAddTorrent.setText("");*/
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////
	       
	    /////////////////////////////Image for Table////////////////////////////////////////
	            
	    //////////////////UDATABLE////////////////////////////////////////////////////
	            TableColumnModel tcmUpdate = table.getColumnModel();
	            TableColumn tcUpdate;
	            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	            tcUpdate= tcmUpdate.getColumn(1);
	            tcUpdate.setPreferredWidth(301);
	            tcUpdate.setHeaderRenderer(new TableCellRenderer(){

	                @Override
	                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
	                    JLabel label=new JLabel();
						label.setBorder(BorderFactory.createEmptyBorder());
						label.setIcon(new ImageIcon(this.getClass().getResource("file-name.png")));
						//label.setText("avc");
						//label.setBackground(Color.red);
						return label;
	                }
	                
	            });
	            tcUpdate= tcmUpdate.getColumn(0);
	            tcUpdate.setPreferredWidth(74);
	            tcUpdate.setHeaderRenderer(new TableCellRenderer(){

	                @Override
	                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
	                    JLabel label=new JLabel();
						label.setBorder(BorderFactory.createEmptyBorder());
						label.setIcon(new ImageIcon(this.getClass().getResource("stt.png")));
						return label;
	                }
	                
	            });
	            tcUpdate= tcmUpdate.getColumn(2);
	            tcUpdate.setPreferredWidth(79);
	            tcUpdate.setHeaderRenderer(new TableCellRenderer(){

	                @Override
	                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
	                    JLabel label=new JLabel();
						label.setBorder(BorderFactory.createEmptyBorder());
						label.setIcon(new ImageIcon(this.getClass().getResource("size.png")));
						return label;
	                }
	                
	            });
	            tcUpdate= tcmUpdate.getColumn(3);
	            tcUpdate.setPreferredWidth(300);
	            tcUpdate.setHeaderRenderer(new TableCellRenderer(){

	                @Override
	                public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
	                    JLabel label=new JLabel();
						label.setBorder(BorderFactory.createEmptyBorder());
						label.setIcon(new ImageIcon(this.getClass().getResource("path.png")));
						return label;	              
	                }         
	            });
				///////////////////////////////////////////////////Request/////////////////////
				TableColumnModel tcmRequest = tableRequest.getColumnModel();
				TableColumn tcRequest;
				tableRequest.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				tcRequest= tcmRequest.getColumn(1);
				tcRequest.setPreferredWidth(301);
				tcRequest.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("file-name.png")));
				return label;
				}
				
				});
				tcRequest= tcmRequest.getColumn(0);
				tcRequest.setPreferredWidth(74);
				tcRequest.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("stt.png")));
				return label;
				}
				
				});
				tcRequest= tcmRequest.getColumn(2);
				tcRequest.setPreferredWidth(79);
				tcRequest.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("size.png")));
				return label;
				}
				
				});
				tcRequest= tcmRequest.getColumn(3);
				tcRequest.setPreferredWidth(153);
				tcRequest.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("ip-address1.png")));
				return label;
				
				}
				
				});
				
				tcRequest= tcmRequest.getColumn(4);
				tcRequest.setPreferredWidth(152);
				tcRequest.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("port1.png")));
				return label;
			
				}
				});
				////////////////////////////////////////////////////////////////////////////////
				
				////////////////////////Download/////////////////////////////////////////////////
				TableColumnModel tcmDownload = tableDownload.getColumnModel();
				TableColumn tcDownload;
				tableDownload.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
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
				tcDownload= tcmDownload.getColumn(1);
				tcDownload.setPreferredWidth(301);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("file-name.png")));
				return label;
			
				}
				
				});
				tcDownload= tcmDownload.getColumn(0);
				tcDownload.setPreferredWidth(74);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("stt.png")));
				return label;
				
				}
				
				});
				tcDownload= tcmDownload.getColumn(2);
				tcDownload.setPreferredWidth(79);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("size.png")));
				return label;
				
				}
				
				});
				tcDownload= tcmDownload.getColumn(3);
				tcDownload.setPreferredWidth(151);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("status.png")));
				return label;
				
				}
				
				});
				tcDownload= tcmDownload.getColumn(4);
				tcDownload.setPreferredWidth(153);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("ip-address1.png")));
				return label;
				
				}
				
				});
				tcDownload= tcmDownload.getColumn(5);
				tcDownload.setPreferredWidth(153);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("port1.png")));
				return label;
				}
				
				});
				
				tcDownload= tcmDownload.getColumn(6);
				tcDownload.setPreferredWidth(120);
				tcDownload.setHeaderRenderer(new TableCellRenderer(){
				
				@Override
				public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
				JLabel label=new JLabel();
				label.setBorder(BorderFactory.createEmptyBorder());
				label.setIcon(new ImageIcon(this.getClass().getResource("time.png")));
				return label;
				}
				
				});
				////////////////////////////////////////////////////////////////////////////////
				//textArea txtAddressTracker
				txtAddressTracker.setLineWrap(true);//Sets the line-wrapping policy of the text area.
				
				btnNewButton = new JButton("OK");
				btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
				springLayout.putConstraint(SpringLayout.WEST, btnAddTorrent, 6, SpringLayout.EAST, btnNewButton);
				springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 12, SpringLayout.NORTH, frame.getContentPane());
				springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 6, SpringLayout.EAST, txtAddressTracker);
				springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -27, SpringLayout.NORTH, scrollPaneTable1);
				springLayout.putConstraint(SpringLayout.EAST, btnNewButton, -599, SpringLayout.EAST, frame.getContentPane());
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
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
				frame.getContentPane().add(btnNewButton);
				
				JMenuBar menuBar = new JMenuBar();
				menuBar.setForeground(Color.WHITE);
				menuBar.setBackground(UIManager.getColor("PasswordField.inactiveBackground"));
				frame.setJMenuBar(menuBar);
				
				JMenu mnFile = new JMenu("File");
				menuBar.add(mnFile);
				
				JMenu mnEdit = new JMenu("Edit");
				menuBar.add(mnEdit);
				
				JMenu mnOption = new JMenu("Option");
				menuBar.add(mnOption);
			
				txtAddressTracker.append("192.168.43.53:1234");

				
	            
	            
	            
	            
	            
	 
	            
	            
	            
	            
	            
	            
	            
	            
	////////////////////////////////////////////////////////////////////////	
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
            btnSearch.setEnabled(true);
               
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
            btnDelete.setEnabled(false);
            btnAddTorrent.setEnabled(false);
            btnDownload.setEnabled(false);
            btnSearch.setEnabled(false);
        }
    }//GEN-LAST:event_treeValueChanged
	// hash content of fileShare
	 
	 @Override
		public void run() {
			// TODO Auto-generated method stub
			frame.setVisible(true);
		}
	 
	 private static String generateSHA512(FileInputStream inputStream){
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
		    catch (NoSuchAlgorithmException e)
		    {
		        return null;
		    } 
		    catch (IOException e) 
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
	 
	 public static void showWarningTextBox(String messToShow, String title)
	 {
		JFrame frame = new JFrame("JOptionPane showMessageDialog example");

    	JOptionPane.showMessageDialog(frame,
        messToShow,
        title,
        JOptionPane.ERROR_MESSAGE);
	 }
}

