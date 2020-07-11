package cn.edu.zucc.pet.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import cn.edu.zucc.pet.control.StaffManager;

public class FrmMain extends JFrame implements ActionListener,MenuListener {
	private JMenuBar menubar=new JMenuBar(); 
    private JMenu menu_Manager=new JMenu("系统管理");
    private JMenu menu_lend=new JMenu("订单管理");
    private JMenu menu_data=new JMenu("数据报表");
    private JMenu menu_help=new JMenu("帮助");
    private JMenuItem  menuItem_StaffManager=new JMenuItem("职员管理");
    private JMenuItem  menuItem_CommodityCategoryManager=new JMenuItem("商品类别管理");
    private JMenuItem  menuItem_CommodityManager=new JMenuItem("商品管理");
    private JMenuItem  menuItem_UserManager=new JMenuItem("用户管理");
    private JMenuItem  menuItem_PetManager=new JMenuItem("宠物管理");
    
    private JMenuItem  menuItem_CommodityOrder=new JMenuItem("商品订单");
    private JMenuItem  menuItem_ServiceOrder=new JMenuItem("预约服务订单");
    
    
    private JMenuItem  menuItem_Version=new JMenuItem("版本说明");
    private JMenuItem  menuItem_Introduction=new JMenuItem("软件使用说明");


    
	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("宠物服务系统");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
	    if("管理员".equals(StaffManager.currentStaff.getStaff_rank())){
	    	menu_Manager.add(menuItem_StaffManager);
	    	menuItem_StaffManager.addActionListener(this);
	    	
	    }
	    menu_Manager.add(menuItem_CommodityCategoryManager);
    	menuItem_CommodityCategoryManager.addActionListener(this);
    	menu_Manager.add(menuItem_CommodityManager);
    	menuItem_CommodityManager.addActionListener(this);
    	menu_Manager.add(menuItem_UserManager);
    	menuItem_UserManager.addActionListener(this);
    	menu_Manager.add(menuItem_PetManager);
    	menuItem_PetManager.addActionListener(this);
    	menubar.add(menu_Manager);
	    menu_lend.add(this.menuItem_CommodityOrder);
	    menuItem_CommodityOrder.addActionListener(this);
	    menu_lend.add(this.menuItem_ServiceOrder);
	    menuItem_ServiceOrder.addActionListener(this);
	    menubar.add(menu_lend);
	   
	    menubar.add(this.menu_data);
	    menu_data.addMenuListener(this);
	
	    menu_help.add(menuItem_Version);
    	menuItem_Version.addActionListener(this);
    	menu_help.add(menuItem_Introduction);
    	menuItem_Introduction.addActionListener(this);
	    menubar.add(this.menu_help);
	    this.setJMenuBar(menubar);
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+StaffManager.currentStaff.getStaff_name());
	    statusBar.add(label);
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuItem_StaffManager){
			FrmStaffManager dlg=new FrmStaffManager(this,"职员管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_CommodityCategoryManager){
			FrmCommodityCategoryManager dlg=new FrmCommodityCategoryManager(this,"商品类别管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_CommodityManager){
			FrmCommodityManager dlg=new FrmCommodityManager(this,"商品管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_UserManager){
			FrmUserManager dlg=new FrmUserManager(this,"用户管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_PetManager){
			FrmPetManager dlg=new FrmPetManager(this,"宠物管理",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_CommodityOrder){
			FrmCommodityOrderManager dlg=new FrmCommodityOrderManager(this,"商品订单",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_ServiceOrder){
			FrmServiceOrderManager dlg=new FrmServiceOrderManager(this,"预约服务订单",true);
			dlg.setVisible(true);
		}
		else if(e.getSource()==this.menuItem_Version){
            JOptionPane.showMessageDialog(null,  "当前版本：Version 1.0","版本信息",JOptionPane.NO_OPTION);    
            
        }
        else if(e.getSource()==this.menuItem_Introduction){
            JOptionPane.showMessageDialog(null,  "本系统分为系统管理、订单管理、数据报表三个主要功能","使用说明",JOptionPane.NO_OPTION);
        }
       
	}
	@Override
	public void menuSelected(MenuEvent ex) {
		// TODO Auto-generated method stub
		 if(ex.getSource()==this.menu_data){
				FrmDataManager dlg=new FrmDataManager(this,"数据报表",true);
				dlg.setVisible(true);
			}
	}
	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}
}
