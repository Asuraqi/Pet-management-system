package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.edu.zucc.pet.control.ServiceOrderManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanServiceOrder;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;
import java.text.SimpleDateFormat;

public class FrmServiceOrderManager_AddServiceOrder extends JDialog implements ActionListener {
	private BeanServiceOrder service_order=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelServiceId = new JLabel("服务编号：");
	private JLabel labelCommodityId = new JLabel("商品编号：");
	private JLabel labelPetId = new JLabel("宠物编号：");
	private JLabel labelStaffId = new JLabel("职员编号：");
	private JLabel labelServiceTime = new JLabel("预约时间：");
	private JLabel labelFinishTime = new JLabel("完成时间：");
	
	private JTextField edtServiceId = new JTextField(20);
	private JTextField edtCommodityId = new JTextField(20);
	private JTextField edtPetId = new JTextField(20);
	private JTextField edtStaffId = new JTextField(20);
	private JTextField edtServiceTime = new JTextField(20);
	private JTextField edtFinishTime = new JTextField(20);
	public FrmServiceOrderManager_AddServiceOrder(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelServiceId);
		workPane.add(edtServiceId);
		workPane.add(labelCommodityId);
		workPane.add(edtCommodityId);
		workPane.add(labelPetId);
		workPane.add(edtPetId);
		workPane.add(labelStaffId);
		workPane.add(edtStaffId);
		workPane.add(labelServiceTime);
		this.edtServiceTime.setText(new Timestamp(System.currentTimeMillis()).toString());
		workPane.add(edtServiceTime);
		workPane.add(labelFinishTime);
		this.edtFinishTime.setText(new Timestamp(System.currentTimeMillis()).toString());
		workPane.add(edtFinishTime);

		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(360,360);
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
			try {
			String service_id=this.edtServiceId.getText();
			int commodity_id=Integer.parseInt(this.edtCommodityId.getText());
			int pet_id=Integer.parseInt(this.edtPetId.getText());
			int staff_id=Integer.parseInt(this.edtStaffId.getText());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			
				Timestamp service_time=Timestamp.valueOf(this.edtServiceTime.getText());
				Timestamp finish_time=Timestamp.valueOf(this.edtFinishTime.getText());
				BeanServiceOrder service_order=new BeanServiceOrder();
				service_order.setCommodity_id(commodity_id);
				service_order.setPet_id(pet_id);
				service_order.setStaff_id(staff_id);
				service_order.setService_id(service_id);
				service_order.setService_time(service_time);
				service_order.setService_finish_time(finish_time);
				
					(new ServiceOrderManager()).createServiceOrder(service_order);
					this.service_order=service_order;
					this.setVisible(false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			
			
			
			
		}
		
	}
	public BeanServiceOrder getServiceOrder() {
		return service_order;
	}
	
}
