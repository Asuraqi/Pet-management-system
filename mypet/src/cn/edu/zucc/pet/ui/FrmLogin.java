package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.util.BaseException;

public class FrmLogin extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnLogin = new Button("µ«¬Ω");
	private Button btnCancel = new Button("ÕÀ≥ˆ");
	private JLabel labelUser = new JLabel("”√ªß£∫");
	private JLabel labelPwd = new JLabel("√‹¬Î£∫");
	private JTextField edtUserId = new JTextField(20);
	private JPasswordField edtPwd = new JPasswordField(20);

	public FrmLogin(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnLogin);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelUser);
		workPane.add(edtUserId);
		workPane.add(labelPwd);
		workPane.add(edtPwd);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 140);
		// ∆¡ƒªæ”÷–œ‘ æ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		btnLogin.addActionListener(this);
		btnCancel.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogin) {
			StaffManager sum=new StaffManager();
			String staff_id=this.edtUserId.getText();
			int a = Integer.parseInt(staff_id);
			String staff_pwd=new String(this.edtPwd.getPassword());
			try {
				BeanStaff staff=sum.loadStaff(a);
				if(staff_pwd.equals(staff.getStaff_pwd())){
					StaffManager.currentStaff=staff;
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null,  "√‹¬Î¥ÌŒÛ","¥ÌŒÛÃ· æ",JOptionPane.ERROR_MESSAGE);
				}
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "¥ÌŒÛÃ· æ",JOptionPane.ERROR_MESSAGE);
			}
			
			
		} else if (e.getSource() == this.btnCancel) {
			System.exit(0);
		}
	}

}
