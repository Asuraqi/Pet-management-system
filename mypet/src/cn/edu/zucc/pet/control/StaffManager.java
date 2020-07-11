package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class StaffManager {
	public static BeanStaff currentStaff=null;
	public List<BeanStaff> loadAllStaffs(boolean withDeletedStaff)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanStaff order by staff_id";
			Query qry=s.createQuery(hql);
			List<BeanStaff> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createStaff(BeanStaff staff)throws BaseException{
		
		if(staff.getStaff_name()==null || "".equals(staff.getStaff_name()) || staff.getStaff_name().length()>50){
			throw new BusinessException("�˺����Ʊ�����1-50����");
		}
		if(!"����Ա".equals(staff.getStaff_rank()) && !"ְԱ".equals(staff.getStaff_rank())){
			throw new BusinessException("�û����������ְԱ�����Ա");
		}
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff.getStaff_id());
			if(one!=null) throw new BusinessException("��½�˺��Ѿ�����");
			s.save(staff);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void changeStaffPwd(int staff_id,String oldPwd,String newPwd)throws BaseException{
		if(oldPwd==null) throw new BusinessException("ԭʼ���벻��Ϊ��");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>16) throw new BusinessException("����Ϊ1-16���ַ�");
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff_id);
			if(one==null) throw new BusinessException("��½�˺Ų�����");
			if(!oldPwd.equals(one.getStaff_pwd())) throw new BusinessException("ԭʼ�������");
			one.setStaff_pwd(newPwd);
			s.update(one);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void resetStaffPwd(int staff_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff_id);
			if(one==null) throw new BusinessException("��½�˺Ų�����");
			one.setStaff_pwd("123456");
			s.update(one);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void deleteStaff(int staff_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff_id);
			if(one==null) throw new BusinessException("��½�˺Ų�����");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanStaff loadStaff(int staff_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff_id);
			if(one==null) throw new BusinessException("��½�˺Ų�����");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public static void main(String[] args){
		BeanStaff staff=new BeanStaff();
		staff.setStaff_id(1);
		staff.setStaff_name("����ͨ");
		staff.setStaff_pwd("1");
		staff.setStaff_rank("����Ա");
		try {
			new StaffManager().createStaff(staff);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
