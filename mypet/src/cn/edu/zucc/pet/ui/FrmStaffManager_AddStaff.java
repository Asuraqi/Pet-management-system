package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.util.BaseException;

public class FrmStaffManager_AddStaff extends JDialog implements ActionListener {
	private BeanStaff staff=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelStaffid = new JLabel("账号：");
	private JLabel labelStaffname = new JLabel("姓名：");
	private JLabel labelStaffrank = new JLabel("等级：");
	private JLabel labelStaffpwd = new JLabel("密码：");
	private JTextField edtStaffid = new JTextField(20);
	private JTextField edtStaffname = new JTextField(20);
	private JTextField edtStaffpwd = new JTextField(20);
	private JComboBox cmbStaffrank = new JComboBox(new String[] { "管理员", "职员"});
	public FrmStaffManager_AddStaff(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelStaffid);
		workPane.add(edtStaffid);
		workPane.add(labelStaffname);
		workPane.add(edtStaffname);
		workPane.add(labelStaffpwd);
		workPane.add(edtStaffpwd);
		workPane.add(labelStaffrank);
		workPane.add(cmbStaffrank);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 250);
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
			if(this.cmbStaffrank.getSelectedIndex()<0){
				JOptionPane.showMessageDialog(null,  "请选择账号类别","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String staffid=this.edtStaffid.getText();
			int staff_id= Integer.parseInt(staffid);
			String staffname=this.edtStaffname.getText();
			String staffpwd=this.edtStaffpwd.getText();
			String staffrank=this.cmbStaffrank.getSelectedItem().toString();

			staff=new BeanStaff();
			staff.setStaff_id(staff_id);
			staff.setStaff_name(staffname);
			staff.setStaff_pwd(staffpwd);
			staff.setStaff_rank(staffrank);
			try {
				(new StaffManager()).createStaff(staff);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.staff=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanStaff getStaff() {
		return staff;
	}
	
}
