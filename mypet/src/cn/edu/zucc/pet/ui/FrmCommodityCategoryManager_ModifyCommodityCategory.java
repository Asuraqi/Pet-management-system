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
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmCommodityCategoryManager_ModifyCommodityCategory extends JDialog implements ActionListener {
	private BeanCommodityCategory CommodityCategory=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelName = new JLabel("类别名称：");
	private JLabel labelDescribe = new JLabel("类别描述：");
	
	private JTextField edtName = new JTextField(20);
	private JTextField edtDescribe = new JTextField(20);
	public FrmCommodityCategoryManager_ModifyCommodityCategory(JDialog f, String s, boolean b,BeanCommodityCategory rt) {
		super(f, s, b);
		this.CommodityCategory=rt;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelName);
		this.edtName.setText(rt.getCategory_name());
		workPane.add(edtName);
		workPane.add(labelDescribe);
		this.edtDescribe.setText(rt.getCategory_describe()+"");
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmCommodityCategoryManager_ModifyCommodityCategory.this.CommodityCategory=null;
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.CommodityCategory=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			String name=this.edtName.getText();
			String describe=this.edtDescribe.getText();
			this.CommodityCategory.setCategory_describe(describe);
			this.CommodityCategory.setCategory_name(name);;
			try {
				(new CommodityCategoryManager()).modifyCommodityCategory(this.CommodityCategory);
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
