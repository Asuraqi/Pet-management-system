package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.CommodityCategoryManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.util.BaseException;

public class FrmStaffManager_ChangePwd extends JDialog implements ActionListener {
	private BeanStaff Staff=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelOldPwd = new JLabel("旧密码：");
	private JLabel labelNewPwd = new JLabel("新密码：");
	
	private JTextField edtOldPwd = new JTextField(20);
	private JTextField edtNewPwd = new JTextField(20);
	public FrmStaffManager_ChangePwd(JDialog f, String s, boolean b,BeanStaff sf) {
		super(f, s, b);
		this.Staff=sf;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelOldPwd);
		
		workPane.add(edtOldPwd);
		workPane.add(labelNewPwd);
		workPane.add(edtNewPwd);
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.Staff=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String oldpwd=this.edtOldPwd.getText();
			String newpwd=this.edtNewPwd.getText();
			int staffid=this.Staff.getStaff_id();
			try {
				(new StaffManager()).changeStaffPwd(staffid, oldpwd, newpwd);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.Staff=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanStaff getStaff() {
		return Staff;
	}
	
}
