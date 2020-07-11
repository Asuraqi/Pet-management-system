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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.pet.control.UserManager;
import cn.edu.zucc.pet.control.CommodityManager;
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

public class FrmServiceOrderManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���");
	private Button btnModify = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��");
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"������","��Ʒ���","������","ְԱ���","ԤԼʱ��","���ʱ��"};
	private Object tblData[][];
	List<BeanServiceOrder> service_orders=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable dataTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			service_orders=(new ServiceOrderManager()).searchServiceOrder(this.edtKeyword.getText());
			tblData =new Object[service_orders.size()][6];
			for(int i=0;i<service_orders.size();i++){
				tblData[i][0]=service_orders.get(i).getService_id();
				tblData[i][1]=service_orders.get(i).getCommodity_id();
				tblData[i][2]=service_orders.get(i).getPet_id();
				tblData[i][3]=service_orders.get(i).getStaff_id();
				tblData[i][4]=service_orders.get(i).getService_time();
				tblData[i][5]=service_orders.get(i).getService_finish_time();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.dataTable.validate();
			this.dataTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmServiceOrderManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(btnDelete);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
		
		// ��Ļ������ʾ
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnDelete.addActionListener(this);
		this.btnSearch.addActionListener(this);
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
			FrmServiceOrderManager_AddServiceOrder dlg=new FrmServiceOrderManager_AddServiceOrder(this,"��Ӷ���",true);
			dlg.setVisible(true);
			if(dlg.getServiceOrder()!=null){//ˢ�±��
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanServiceOrder service_order=this.service_orders.get(i);
			
			FrmServiceOrderManager_ModifyServiceOrder dlg=new FrmServiceOrderManager_ModifyServiceOrder(this,"�޸Ķ���",true,service_order);
			dlg.setVisible(true);
			if(dlg.getServiceOrder()!=null){//ˢ�±��
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.dataTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ�񶩵�","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanServiceOrder service_order=this.service_orders.get(i);
			
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ��"+service_order.getService_id()+"��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new ServiceOrderManager()).deleteServiceOrder(service_order.getService_id());
					this.reloadTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource()==this.btnSearch){
			this.reloadTable();
		}
		
	}
}
