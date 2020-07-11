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
			throw new BusinessException("账号名称必须是1-50个字");
		}
		if(!"管理员".equals(staff.getStaff_rank()) && !"职员".equals(staff.getStaff_rank())){
			throw new BusinessException("用户级别必须是职员或管理员");
		}
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff.getStaff_id());
			if(one!=null) throw new BusinessException("登陆账号已经存在");
			s.save(staff);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void changeStaffPwd(int staff_id,String oldPwd,String newPwd)throws BaseException{
		if(oldPwd==null) throw new BusinessException("原始密码不能为空");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>16) throw new BusinessException("必须为1-16个字符");
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanStaff one=(BeanStaff) s.get(BeanStaff.class,staff_id);
			if(one==null) throw new BusinessException("登陆账号不存在");
			if(!oldPwd.equals(one.getStaff_pwd())) throw new BusinessException("原始密码错误");
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
			if(one==null) throw new BusinessException("登陆账号不存在");
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
			if(one==null) throw new BusinessException("登陆账号不存在");
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
			if(one==null) throw new BusinessException("登陆账号不存在");
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
		staff.setStaff_name("侯戚通");
		staff.setStaff_pwd("1");
		staff.setStaff_rank("管理员");
		try {
			new StaffManager().createStaff(staff);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
