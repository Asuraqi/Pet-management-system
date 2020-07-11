package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.CommodityCategoryManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmCommodityCategoryManager_AddCommodityCategory extends JDialog implements ActionListener {
	private BeanCommodityCategory CommodityCategory=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelID = new JLabel("类别编号：");
	private JLabel labelName = new JLabel("类别名称：");
	private JLabel labelDescribe = new JLabel("类别描述：");
	private JTextField edtID = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtDescribe = new JTextField(20);
	public FrmCommodityCategoryManager_AddCommodityCategory(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelID);
		workPane.add(edtID);
		workPane.add(labelName);
		workPane.add(edtName);
		workPane.add(labelDescribe);
		workPane.add(edtDescribe);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(300, 300);
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
			
			String name=this.edtName.getText();
			String describe=this.edtDescribe.getText();
			int id=Integer.parseInt(this.edtID.getText());
			this.CommodityCategory=new BeanCommodityCategory();
			this.CommodityCategory.setCategory_id(id);
			this.CommodityCategory.setCategory_name(name);
			this.CommodityCategory.setCategory_describe(describe);
			try {
				(new CommodityCategoryManager()).createCommodityCategory(this.CommodityCategory);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.CommodityCategory=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanCommodityCategory getCommodityCategory() {
		return CommodityCategory;
	}
	
}
