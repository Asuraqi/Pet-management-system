package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmPetManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加");
	private Button btnModify = new Button("修改");
	private Button btnDelete = new Button("删除");
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("查询");
	private Button btnView = new Button("显示宠物照片");
	private Object tblTitle[]={"宠物编号","主人编号","宠物品种","宠物昵称","宠物照片"};
	private Object tblData[][];
	List<BeanPet> pets=null;
	private static File file = null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			pets=(new PetManager()).searchPet(this.edtKeyword.getText());
			tblData =new Object[pets.size()][5];
			for(int i=0;i<pets.size();i++){
				tblData[i][0]=pets.get(i).getPet_id();
				tblData[i][1]=pets.get(i).getUser_id();
				tblData[i][2]=pets.get(i).getPet_category();
				tblData[i][3]=pets.get(i).getPet_nickname();
				tblData[i][4]=pets.get(i).getPet_photo();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmPetManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(btnDelete);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		toolBar.add(btnView);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
		this.btnView.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public static FileInputStream getByteImage(String infile) {
		FileInputStream inputImage = null;
		file = new File(infile);
		try {
			inputImage = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return inputImage;
	}
	public static void readBlob(FileInputStream inputStream, String path) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(path);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				fileOutputStream.write(buf, 0, len);
			}
			inputStream.close();
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmPetManager_AddPet dlg=new FrmPetManager_AddPet(this,"添加宠物",true);
			dlg.setVisible(true);
			this.reloadTable();
			
		}
		else if(e.getSource()==this.btnModify){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择宠物","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanPet pet=this.pets.get(i);
			
			FrmPetManager_ModifyPet dlg=new FrmPetManager_ModifyPet(this,"修改宠物",true,pet);
			dlg.setVisible(true);
			if(dlg.getPet()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择宠物","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanPet pet=this.pets.get(i);
			
			if(JOptionPane.showConfirmDialog(this,"确定删除"+pet.getPet_nickname()+"吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new PetManager()).deletePet(pet.getPet_id());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		else if(e.getSource()==this.btnView) {
			int i = this.dataTable.getSelectedRow();
			if (i < 0) {
				JOptionPane.showMessageDialog(null, "请选择宠物", "提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanPet pet = this.pets.get(i);
			Blob petimage = pet.getPet_photo();
			try {
				InputStream input = petimage.getBinaryStream();
		        File f = new File("C:/Users/Administrator/Desktop/"+pet.getPet_id()+".jpg");
		        OutputStream output = new FileOutputStream(f);
		        byte[] buff = new byte[input.available()];
		        input.read(buff);
		        output.write(buff);
		        input.close();
		        output.close();
		        this.setVisible(false);
		        JFrame jf = new JFrame();
		        JPanel panel = new JPanel();
		        JLabel image = new JLabel();
		        image.setIcon(new ImageIcon("C:/Users/Administrator/Desktop/"+pet.getPet_id()+".jpg"));
		        panel.add(image);
		        jf.setContentPane(panel);
		        jf.pack();
		        jf.setLocationRelativeTo(null);
		        jf.setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
	}
}
