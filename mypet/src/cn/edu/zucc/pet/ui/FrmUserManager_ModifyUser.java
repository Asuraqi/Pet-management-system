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

public class FrmUserManager_ModifyUser extends JDialog implements ActionListener {
	private BeanUser user=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelId = new JLabel("用户编号：");
	private JLabel labelName = new JLabel("用户姓名：");
	private JLabel labelPhoneNumber = new JLabel("手机号码：");
	private JLabel labelWebmail = new JLabel("用户邮箱：");
	
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtPhoneNumber = new JTextField(20);
	private JTextField edtWebmail = new JTextField(20);

	public FrmUserManager_ModifyUser(JDialog f, String s, boolean b,BeanUser user) {
		super(f, s, b);
		this.user=user;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelId);
		this.edtId.setText(user.getUser_id()+"");
		this.edtId.setEnabled(false);
		workPane.add(edtId);
		workPane.add(labelName);
		this.edtName.setText(user.getUser_name());
		workPane.add(edtName);
		workPane.add(labelPhoneNumber);
		this.edtPhoneNumber.setText(user.getUser_phone_number()+"");
		workPane.add(edtPhoneNumber);
		workPane.add(labelWebmail);
		this.edtWebmail.setText(user.getUser_webmail());
		workPane.add(edtWebmail);
		
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(380, 200);
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
				FrmUserManager_ModifyUser.this.user=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.user=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			int id=Integer.parseInt(this.edtId.getText());
			String name=this.edtName.getText();
			String phone_number=this.edtPhoneNumber.getText();
			String webmail=this.edtWebmail.getText();
			BeanUser user=new BeanUser();
			user.setUser_id(id);
			user.setUser_name(name);
			user.setUser_phone_number(phone_number);
			user.setUser_webmail(webmail);
			try {
				(new UserManager()).modifyUser(user);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanUser getUser() {
		return user;
	}
	
}
