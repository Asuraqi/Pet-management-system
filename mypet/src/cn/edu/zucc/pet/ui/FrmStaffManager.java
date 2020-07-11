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
	private Button btnAdd = new Button("���ְԱ");
	private Button btnChangePwd = new Button("��������");
	private Button btnDelete = new Button("ɾ��ְԱ");
	private Object tblTitle[]={"�˺�","����","�ȼ�","����"};
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
		//��ȡ��������
		this.reloadStaffTable();
		this.getContentPane().add(new JScrollPane(this.staffTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
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
			FrmStaffManager_AddStaff dlg=new FrmStaffManager_AddStaff(this,"���ְԱ",true);
			dlg.setVisible(true);
			if(dlg.getStaff()!=null){//ˢ�±��
				this.reloadStaffTable();
			}
		}
		else if(e.getSource()==this.btnChangePwd){
			int i=this.staffTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���˺�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ�ϸ���������","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				BeanStaff staff=this.staffs.get(i);
				FrmStaffManager_ChangePwd dlg = new FrmStaffManager_ChangePwd(this,"�����޸�",true,staff);
				dlg.setVisible(true);
				JOptionPane.showMessageDialog(null,  "����������","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				this.reloadStaffTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.staffTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ���˺�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ���˺���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String staffid=this.tblData[i][0].toString();
				int staff_id= Integer.parseInt(staffid);
				try {
					(new StaffManager()).deleteStaff(staff_id);
					this.reloadStaffTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
				
			}
		}
	}
}
