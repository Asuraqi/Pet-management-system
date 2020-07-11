package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmPetManager_ModifyPet extends JDialog implements ActionListener {
	private BeanPet pet=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("宠物编号：");
	private JLabel labelName = new JLabel("宠物昵称：");
	private JLabel labelcategory = new JLabel("宠物品种：");
	private JLabel labelUser_id = new JLabel("主人编号：");
	private JLabel labelPhoto = new JLabel("宠物照片：");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtlcategory = new JTextField(20);
	private JTextField edtlUser_id = new JTextField(20);
	private JTextField edtPhoto = new JTextField(20);

	public FrmPetManager_ModifyPet(JDialog f, String s, boolean b,BeanPet pet) {
		super(f, s, b);
		this.pet=pet;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		this.edtId.setText(pet.getPet_id()+"");
		this.edtId.setEnabled(false);
		workPane.add(edtId);
		workPane.add(labelName);
		this.edtName.setText(pet.getPet_nickname());
		workPane.add(edtName);
		workPane.add(labelcategory);
		this.edtlcategory.setText(pet.getPet_category());
		workPane.add(edtlcategory);
		workPane.add(labelUser_id);
		this.edtlcategory.setText(pet.getUser_id()+"");
		workPane.add(edtlUser_id);
		workPane.add(labelPhoto);
		workPane.add(edtPhoto);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350,350);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmPetManager_ModifyPet.this.pet=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.pet=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			int id=Integer.parseInt(this.edtId.getText());
			String name=this.edtName.getText();
			int user_id=Integer.parseInt(this.edtlUser_id.getText());
			String category=this.edtlcategory.getText();
			String photo=this.edtPhoto.getText();
			BeanPet pet=new BeanPet();
			pet.setPet_id(id);
			pet.setUser_id(user_id);
			pet.setPet_nickname(name);
			pet.setPet_category(category);
			try {
				(new PetManager()).modifyPet(pet);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanPet getPet() {
		return pet;
	}
	
}
