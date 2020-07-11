package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmCommodityManager_AddCommodity extends JDialog implements ActionListener {
	private BeanCommodity Commodity=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelCommodityId = new JLabel("商品编号：");
	private JLabel labelCommodityName = new JLabel("商品名称：");
	private JLabel labelCommodityBrand = new JLabel("商品品牌：");
	private JLabel labelCommodityPrice = new JLabel("商品价格：");
	private JLabel labelCommodityBarcode = new JLabel("  二维码：");
	private JLabel labelCommodityCategory = new JLabel("商品类别：");
	private JTextField edtId = new JTextField(20);
	private JTextField edtName = new JTextField(20);
	private JTextField edtBrand = new JTextField(20);
	private JTextField edtPrice = new JTextField(20);
	private JTextField edtBarcode = new JTextField(20);
	private Map<String,BeanCommodityCategory> CommodityCategoryMap_name=null;
	private JComboBox cmbCommodityCategory=null;
	public FrmCommodityManager_AddCommodity(JDialog f, String s, boolean b,Map<String,BeanCommodityCategory> rtMap) {
		super(f, s, b);
		this.CommodityCategoryMap_name=rtMap;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelCommodityId);
		workPane.add(edtId);
		workPane.add(labelCommodityName);
		workPane.add(edtName);
		workPane.add(labelCommodityBrand);
		workPane.add(edtBrand);
		workPane.add(labelCommodityPrice);
		workPane.add(edtPrice);
		workPane.add(labelCommodityBarcode);
		workPane.add(edtBarcode);
		workPane.add(labelCommodityCategory);
		//提取读者类别信息
		String[] strTypes=new String[this.CommodityCategoryMap_name.size()+1];
		strTypes[0]="";
		java.util.Iterator<BeanCommodityCategory> itRt=this.CommodityCategoryMap_name.values().iterator();
		int i=1;
		while(itRt.hasNext()){
			strTypes[i]=itRt.next().getCategory_name();
			i++;
		}
		cmbCommodityCategory=new JComboBox(strTypes);
		workPane.add(cmbCommodityCategory);
		
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
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			if(this.cmbCommodityCategory.getSelectedIndex()<0){
				JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int id=Integer.parseInt(this.edtId.getText());
			String name=this.edtName.getText();
			String brand=this.edtBrand.getText();
			float price=Float.parseFloat(this.edtPrice.getText());
			int barcode=Integer.parseInt(this.edtBarcode.getText());
			BeanCommodity r=new BeanCommodity();
			r.setCommodity_id(id);
			r.setCommodity_name(name);
			r.setCommodity_brand(brand);
			r.setCommodity_barcode(barcode);
			r.setCommodity_price(price);
			String rtName=this.cmbCommodityCategory.getSelectedItem().toString();
			BeanCommodityCategory rt=this.CommodityCategoryMap_name.get(rtName);
			if(rt==null){
				JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			r.setCategory_id(rt.getCategory_id());
			r.setCategory_name(rtName);
			try {
				(new CommodityManager()).createCommodity(r);
				this.Commodity=r;
				this.setVisible(false);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanCommodity getCommodity() {
		return Commodity;
	}
	
}
