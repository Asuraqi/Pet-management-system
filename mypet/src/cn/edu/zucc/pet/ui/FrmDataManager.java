package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.CommodityCategoryManager;
import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.OrderManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.util.BaseException;

public class FrmDataManager extends JDialog implements ActionListener{
	private Object tblTitle[]={"职工","用户","宠物","商品分类","商品"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private JMenuBar menubar=new JMenuBar(); 
	private JPanel statusBar = new JPanel();
	float benefit=0;
	private void reloadTable(){
		try {
			
			List<BeanStaff> staffs=(new StaffManager()).loadAllStaffs(true);
			List<BeanUser> users=(new UserManager()).loadAllUsers(true);
			List<BeanPet> pets=(new PetManager()).loadAllPets(true);
			List<BeanCommodityCategory> categorys=(new CommodityCategoryManager()).loadAllCommodityCategorys(true);
			List<BeanCommodity> commoditys=(new CommodityManager()).loadAllCommoditys(true);
			tblData =new Object[10][5];
			for(int i=0;i<staffs.size();i++){
				tblData[i][0]=staffs.get(i).getStaff_name();
			}
			for(int i=0;i<users.size();i++){
				tblData[i][1]=users.get(i).getUser_name();
			}
			for(int i=0;i<pets.size();i++){
				tblData[i][2]=pets.get(i).getPet_nickname();
			}
			for(int i=0;i<categorys.size();i++){
				tblData[i][3]=categorys.get(i).getCategory_name();
			}
			for(int i=0;i<commoditys.size();i++){
				tblData[i][4]=commoditys.get(i).getCommodity_name();
			}
			
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public FrmDataManager(Frame f, String s, boolean b) {
		
		
		super(f, s, b);
		float benefit=0;
		try
		{
			List<BeanOrder> orders=(new OrderManager()).loadAllOrders(true);
			for(int i=0;i<orders.size();i++){
				if(orders.get(i).getDeliver_condition().equals("已收货"))
				benefit=benefit+orders.get(i).getNumber()*orders.get(i).getSale();
			}
			JLabel label=new JLabel("总销售额："+benefit+"");
		    statusBar.add(label);
		}
		catch(Exception e)
		{
			
		}
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
		//提取现有数据
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
	}
	
}
