package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class FrmPetManager_AddPet extends JDialog implements ActionListener {
	private BeanPet pet=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("宠物编号：");
	private JLabel labelName = new JLabel("宠物昵称：");
	private JLabel labelcategory = new JLabel("宠物品种：");
	private JLabel labelUser_id = new JLabel("主人编号：");
	private JLabel labelPhoto = new JLabel("照片路径：");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtlcategory = new JTextField(20);
	private JTextField edtlUser_id = new JTextField(20);
	private JTextField edtPhoto = new JTextField(20);
	public FrmPetManager_AddPet(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		workPane.add(edtId);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelcategory);
		workPane.add(edtlcategory);
		workPane.add(labelUser_id);
		workPane.add(edtlUser_id);
		workPane.add(labelPhoto);
		this.edtPhoto.setText("C:/Users/Administrator/Desktop/.jpg");
		workPane.add(edtPhoto);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350, 350);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			Session session = HibernateUtil.getSession();
			try {
				String petname = this.edtName.getText();
				int petid = Integer.parseInt(this.edtId.getText());
				String petcategory = this.edtlcategory.getText();
				int userid = Integer.parseInt(this.edtlUser_id.getText());
				String pet_photo = this.edtPhoto.getText();
				File file = new File(pet_photo);
				InputStream input = new FileInputStream(file);
				Blob petphoto = Hibernate.getLobCreator(session).createBlob(input, input.available());
	
				BeanPet pet1=new BeanPet();
				
				pet1.setUser_id(userid);
				pet1.setPet_id(petid);
				pet1.setPet_category(petcategory);
				pet1.setPet_nickname(petname);
				pet1.setPet_photo(petphoto);
				(new PetManager()).createPet(pet1);
				this.setVisible(false);
			} catch (BaseException e1) {
				//this.pet1 = null;
				JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public BeanPet getPet() {
		return pet;
	}
	
}
