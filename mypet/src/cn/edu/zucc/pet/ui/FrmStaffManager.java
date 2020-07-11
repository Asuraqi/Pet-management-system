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

import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.util.BaseException;


public class FrmStaffManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加职员");
	private Button btnChangePwd = new Button("更改密码");
	private Button btnDelete = new Button("删除职员");
	private Object tblTitle[]={"账号","姓名","等级","密码"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable staffTable=new JTable(tablmod);
	List<BeanStaff> staffs=null;
	private void reloadStaffTable(){
		try {
			staffs=(new StaffManager()).loadAllStaffs(false);
			tblData =new Object[staffs.size()][4];
			for(int i=0;i<staffs.size();i++){
				tblData[i][0]=staffs.get(i).getStaff_id();
				tblData[i][1]=staffs.get(i).getStaff_name();
				tblData[i][2]=staffs.get(i).getStaff_rank();
				tblData[i][3]=staffs.get(i).getStaff_pwd();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.staffTable.validate();
			this.staffTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmStaffManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnChangePwd);
		toolBar.add(this.btnDelete);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadStaffTable();
		this.getContentPane().add(new JScrollPane(this.staffTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnChangePwd.addActionListener(this);
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
			FrmStaffManager_AddStaff dlg=new FrmStaffManager_AddStaff(this,"添加职员",true);
			dlg.setVisible(true);
			if(dlg.getStaff()!=null){//刷新表格
				this.reloadStaffTable();
			}
		}
		else if(e.getSource()==this.btnChangePwd){
			int i=this.staffTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确认更改密码吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				BeanStaff staff=this.staffs.get(i);
				FrmStaffManager_ChangePwd dlg = new FrmStaffManager_ChangePwd(this,"密码修改",true,staff);
				dlg.setVisible(true);
				JOptionPane.showMessageDialog(null,  "密码更改完成","提示",JOptionPane.INFORMATION_MESSAGE);
				this.reloadStaffTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.staffTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "请选择账号","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"确定删除账号吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String staffid=this.tblData[i][0].toString();
				int staff_id= Integer.parseInt(staffid);
				try {
					(new StaffManager()).deleteStaff(staff_id);
					this.reloadStaffTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
