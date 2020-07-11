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

public class FrmCommodityOrderManager_ModifyCommodityOrder extends JDialog implements ActionListener {
	private BeanOrder order=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelOrderId = new JLabel("订单编号：");
	private JLabel labelCommodityId = new JLabel("商品编号：");
	private JLabel labelUserId = new JLabel("用户编号：");
	private JLabel labelStaffId = new JLabel("职员编号：");
	private JLabel labelCommodityNumber = new JLabel("商品数量：");
	private JLabel labelCommodityPrice = new JLabel("商品单价：");
	private JLabel labelDeliverCondition = new JLabel("配送状态：");
	private JTextField edtOrderId = new JTextField(20);
	private JTextField edtCommodityId = new JTextField(20);
	private JTextField edtUserId = new JTextField(20);
	private JTextField edtStaffId = new JTextField(20);
	private JTextField edtCommodityNumber = new JTextField(20);
	private JTextField edtCommodityPrice = new JTextField(20);
	private JTextField edtDeliverCondition = new JTextField(20);
	public FrmCommodityOrderManager_ModifyCommodityOrder(JDialog f, String s, boolean b,BeanOrder order) {
		super(f, s, b);
		this.order=order;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);	
		workPane.add(labelOrderId);
		this.edtOrderId.setText(order.getOrder_id()+"");
		this.edtOrderId.setEnabled(false);
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
		workPane.add(labelDeliverCondition);
		workPane.add(edtDeliverCondition);
		
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmCommodityOrderManager_ModifyCommodityOrder.this.order=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.order=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			int order_id=Integer.parseInt(this.edtOrderId.getText());
			int commodity_id=Integer.parseInt(this.edtCommodityId.getText());
			int user_id=Integer.parseInt(this.edtUserId.getText());
			int staff_id=Integer.parseInt(this.edtStaffId.getText());
			int num=Integer.parseInt(this.edtCommodityNumber.getText());
			float price=Float.parseFloat(this.edtCommodityPrice.getText());
			String deliver_condition=this.edtDeliverCondition.getText();
			BeanOrder order=new BeanOrder();
			order.setOrder_id(order_id);
			order.setUser_id(user_id);
			order.setStaff_id(staff_id);
			order.setCommodity_id(commodity_id);
			order.setNumber(num);
			order.setDeliver_condition(deliver_condition);
			order.setSale(price);
			try {
				(new OrderManager()).modifyOrder(order);
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanOrder getOrder() {
		return order;
	}
	
}
