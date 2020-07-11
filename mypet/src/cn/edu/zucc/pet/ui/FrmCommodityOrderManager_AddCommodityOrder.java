package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FrmCommodityOrderManager_AddCommodityOrder extends JDialog implements ActionListener {
	private BeanOrder order=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelOrderId = new JLabel("������ţ�");
	private JLabel labelCommodityId = new JLabel("��Ʒ��ţ�");
	private JLabel labelUserId = new JLabel("�û���ţ�");
	private JLabel labelStaffId = new JLabel("ְԱ��ţ�");
	private JLabel labelCommodityNumber = new JLabel("��Ʒ������");
	private JLabel labelCommodityPrice = new JLabel("��Ʒ���ۣ�");

	private JTextField edtOrderId = new JTextField(20);
	private JTextField edtCommodityId = new JTextField(20);
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtStaffId = new JTextField(20);
	private JTextField edtCommodityNumber = new JTextField(20);
	private JTextField edtCommodityPrice = new JTextField(20);
	private JComboBox cmbDeliverState = new JComboBox(new String[] { "������", "���ջ�"});
	public FrmCommodityOrderManager_AddCommodityOrder(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelOrderId);
		workPane.add(edtOrderId);
		workPane.add(labelCommodityId);
		workPane.add(edtCommodityId);
		workPane.add(labelUserId);
		workPane.add(edtUserId);
		workPane.add(labelStaffId);
		workPane.add(edtStaffId);
		workPane.add(labelCommodityNumber);
		workPane.add(edtCommodityNumber);
		workPane.add(labelCommodityPrice);
		workPane.add(edtCommodityPrice);
		workPane.add(cmbDeliverState);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(350, 350);
		// ��Ļ������ʾ
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
			if(this.cmbDeliverState.getSelectedIndex()<0){
				JOptionPane.showMessageDialog(null,  "��ѡ������״̬","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int order_id=Integer.parseInt(this.edtOrderId.getText());
			int commodity_id=Integer.parseInt(this.edtCommodityId.getText());
			int user_id=Integer.parseInt(this.edtUserId.getText());
			int staff_id=Integer.parseInt(this.edtStaffId.getText());
			int num=Integer.parseInt(this.edtCommodityNumber.getText());
			float price=Float.parseFloat(this.edtCommodityPrice.getText());
			String deliver=this.cmbDeliverState.getSelectedItem().toString();
			BeanOrder order=new BeanOrder();
			order.setOrder_id(order_id);
			order.setUser_id(user_id);
			order.setStaff_id(staff_id);
			order.setCommodity_id(commodity_id);
			order.setNumber(num);
			order.setDeliver_condition(deliver);
			order.setSale(price);
			try {
				(new OrderManager()).createOrder(order);
				this.order=order;
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanOrder getOrder() {
		return order;
	}
	
}
