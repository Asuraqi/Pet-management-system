package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.CommodityCategoryManager;
import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmCommodityCategoryManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加商品类别");
	private Button btnModify = new Button("修改商品类别");
	private Button btnDelete = new Button("删除商品类别");
	private Object tblTitle[]={"类别编号","类别名称","类别描述"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable CommodityCategoryTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			List<BeanCommodityCategory> category =(new CommodityCategoryManager()).loadAllCommodityCategorys(false);
			tblData =new Object[category.size()][3];
			for(int i=0;i<category.size();i++){
				tblData[i][0]=category.get(i).getCategory_id()+"";
				tblData[i][1]=category.get(i).getCategory_name();
				tblData[i][2]=category.get(i).getCategory_describe()+"";
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.CommodityCategoryTable.validate();
			this.CommodityCategoryTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCommodityCategoryManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.CommodityCategoryTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnAdd){
			FrmCommodityCategoryManager_AddCommodityCategory dlg=new FrmCommodityCategoryManager_AddCommodityCategory(this,"添加商品类别",true);
			dlg.setVisible(true);
			if(dlg.getCommodityCategory()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.CommodityCategoryTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类别","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			int n=Integer.parseInt(this.tblData[i][0].toString());
			BeanCommodityCategory CommodityCategory=new BeanCommodityCategory();
			CommodityCategory.setCategory_id(n);
			CommodityCategory.setCategory_name(this.tblData[i][1].toString());
			CommodityCategory.setCategory_describe(this.tblData[i][2].toString());
			FrmCommodityCategoryManager_ModifyCommodityCategory dlg=new FrmCommodityCategoryManager_ModifyCommodityCategory(this,"添加商品类别",true,CommodityCategory);
			dlg.setVisible(true);
			if(dlg.getCommodityCategory()!=null){//刷新表格
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.CommodityCategoryTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择商品类别","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除该类别吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				int n=Integer.parseInt(this.tblData[i][0].toString());
				try {
					(new CommodityCategoryManager()).deleteCommodityCategory(n);
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
