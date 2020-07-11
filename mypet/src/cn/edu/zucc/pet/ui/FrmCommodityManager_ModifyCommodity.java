package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class FrmCommodityManager_ModifyCommodity extends JDialog implements ActionListener {
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
	public FrmCommodityManager_ModifyCommodity(JDialog f, String s, boolean b,Map<String,BeanCommodityCategory> rtMap,BeanCommodity r) {
		super(f, s, b);
		this.Commodity=r;
		this.CommodityCategoryMap_name=rtMap;
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelCommodityId);
		this.edtId.setEnabled(false);
		this.edtId.setText(this.Commodity.getCommodity_id()+"");
		workPane.add(edtId);
		workPane.add(labelCommodityName);
		this.edtName.setText(r.getCommodity_name());
		workPane.add(edtName);
		workPane.add(labelCommodityBrand);
		this.edtName.setText(r.getCommodity_barcode()+"");
		workPane.add(edtBrand);
		workPane.add(labelCommodityPrice);
		this.edtName.setText(r.getCommodity_price()+"");
		workPane.add(edtPrice);
		workPane.add(labelCommodityBarcode);
		this.edtName.setText(r.getCommodity_barcode()+"");
		workPane.add(edtBarcode);
		workPane.add(labelCommodityCategory);
		//提取读者类别信息
		String[] strTypes=new String[this.CommodityCategoryMap_name.size()+1];
		strTypes[0]="";
		java.util.Iterator<BeanCommodityCategory> itRt=this.CommodityCategoryMap_name.values().iterator();
		int i=1;
		int oldIndex=0;
		while(itRt.hasNext()){
			BeanCommodityCategory rt=itRt.next();
			strTypes[i]=rt.getCategory_name();
			if(this.Commodity.getCategory_id()==rt.getCategory_id()){
				oldIndex=i;
			}
			i++;
		}
		cmbCommodityCategory=new JComboBox(strTypes);
		this.cmbCommodityCategory.setSelectedIndex(oldIndex);
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
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				FrmCommodityManager_ModifyCommodity.this.Commodity=null;
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			this.Commodity=null;
			return;
		}
		else if(e.getSource()==this.btnOk){
			if(this.cmbCommodityCategory.getSelectedIndex()<0){
				JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanCommodity commodity=new BeanCommodity();
			 int commodity_id=Integer.parseInt(this.edtId.getText());                 
			 String commodity_name=this.edtName.getText();
			 String  commodity_brand=this.edtBrand.getText();
			 float  commodity_price=Float.parseFloat(this.edtPrice.getText());
		     int  commodity_barcode=Integer.parseInt(this.edtBarcode.getText()); ;
		     commodity.setCommodity_id(commodity_id);
		     commodity.setCommodity_barcode(commodity_barcode);
		     commodity.setCommodity_brand(commodity_brand);
		     commodity.setCommodity_name(commodity_name);
		     commodity.setCommodity_price(commodity_price);
			String rtName=this.cmbCommodityCategory.getSelectedItem().toString();
			BeanCommodityCategory rt=this.CommodityCategoryMap_name.get(rtName);
			if(rt==null){
				JOptionPane.showMessageDialog(null, "请选择商品类别","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				if(this.Commodity.getCategory_id()!=rt.getCategory_id()){
					(new CommodityManager()).changeCommodityCategory(this.Commodity.getCommodity_id(),rt.getCategory_id());
				}
					(new CommodityManager()).modifyCommodity(commodity);
				
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
