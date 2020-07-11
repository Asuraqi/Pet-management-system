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

import cn.edu.zucc.pet.control.CommodityCategoryManager;
import cn.edu.zucc.pet.control.CommodityManager;
import cn.edu.zucc.pet.control.PetManager;
import cn.edu.zucc.pet.control.StaffManager;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;

public class FrmCommodityManager extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("���");
	private Button btnModify = new Button("�޸�");
	private Button btnDelete = new Button("ɾ��");
	private Map<String,BeanCommodityCategory> CommodityCategoryMap_name=new HashMap<String,BeanCommodityCategory>();
	private Map<Integer,BeanCommodityCategory> CommodityCategoryMap_id=new HashMap<Integer,BeanCommodityCategory>();
	private JComboBox cmbCommodityCategory=null;
	private JTextField edtKeyword = new JTextField(10);
	private Button btnSearch = new Button("��ѯ");
	private Object tblTitle[]={"��Ʒ���","������","��Ʒ����","��������","�̱�","��Ʒ�۸�","��ά��"};
	private Object tblData[][];
	List<BeanCommodity> Commoditys=null;
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable CommodityTable=new JTable(tablmod);
	private void reloadTable(){
		try {
			
			Commoditys=(new CommodityManager()).searchCommodity(this.edtKeyword.getText());
			tblData =new Object[Commoditys.size()][7];
			for(int i=0;i<Commoditys.size();i++){
				tblData[i][0]=Commoditys.get(i).getCommodity_id();
				tblData[i][1]=Commoditys.get(i).getCategory_id();
				tblData[i][2]=Commoditys.get(i).getCommodity_name();
				tblData[i][3]=Commoditys.get(i).getCategory_name();
				tblData[i][4]=Commoditys.get(i).getCommodity_brand();
				tblData[i][5]=Commoditys.get(i).getCommodity_price();
				tblData[i][6]=Commoditys.get(i).getCommodity_barcode();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.CommodityTable.validate();
			this.CommodityTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmCommodityManager(Frame f, String s, boolean b) {
		super(f, s, b);
		//��ȡ���������Ϣ
		List<BeanCommodityCategory> categorys=null;
		try {
			categorys = (new CommodityCategoryManager()).loadAllCommodityCategorys(false);
			String[] strTypes=new String[categorys.size()+1];
			strTypes[0]="";
			for(int i=0;i<categorys.size();i++) {
				CommodityCategoryMap_name.put(categorys.get(i).getCategory_name(),categorys.get(i));
				CommodityCategoryMap_id.put(categorys.get(i).getCategory_id(), categorys.get(i));
				strTypes[i+1]=categorys.get(i).getCategory_name();
			}
			cmbCommodityCategory=new JComboBox(strTypes);
		} catch (BaseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		toolBar.add(btnModify);
		toolBar.add(btnDelete);
		toolBar.add(cmbCommodityCategory);
		toolBar.add(edtKeyword);
		toolBar.add(btnSearch);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//��ȡ��������
		this.reloadTable();
		this.getContentPane().add(new JScrollPane(this.CommodityTable), BorderLayout.CENTER);
		
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
			FrmCommodityManager_AddCommodity dlg=new FrmCommodityManager_AddCommodity(this,"�����Ʒ",true,this.CommodityCategoryMap_name);
			dlg.setVisible(true);
			if(dlg.getCommodity()!=null){//ˢ�±��
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnModify){
			int i=this.CommodityTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanCommodity Commodity=this.Commoditys.get(i);
			
			FrmCommodityManager_ModifyCommodity dlg=new FrmCommodityManager_ModifyCommodity(this,"�޸���Ʒ",true,this.CommodityCategoryMap_name,Commodity);
			dlg.setVisible(true);
			if(dlg.getCommodity()!=null){//ˢ�±��
				this.reloadTable();
			}
		}
		else if(e.getSource()==this.btnDelete){
			int i=this.CommodityTable.getSelectedRow();
			if(i<0) {
				JOptionPane.showMessageDialog(null,  "��ѡ����Ʒ","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			BeanCommodity Commodity=this.Commoditys.get(i);
			if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ������Ʒ��","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				try {
					(new CommodityManager()). deleteCommodity(Commodity.getCommodity_id());
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
