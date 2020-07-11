package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class UserManager {
	public List<BeanUser> loadAllUsers(boolean withDeletedUser)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanUser order by user_id";
			Query qry=s.createQuery(hql);
			List<BeanUser> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createUser(BeanUser user)throws BaseException{
		
		
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanUser one=(BeanUser) s.get(BeanUser.class,user.getUser_id());
			if(one!=null) throw new BusinessException("该用户已经存在");
			int size=user.getUser_phone_number().length();
			if(size!=11&&!isNumeric(user.getUser_phone_number())) throw new BusinessException("请输入11位正确手机号");
			
			else
			{
				s.save(user);
			}
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	
	
	public void deleteUser(int user_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanUser one=(BeanUser) s.get(BeanUser.class,user_id);
			if(one==null) throw new BusinessException("该用户不存在");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanUser loadUser(int user_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanUser one=(BeanUser) s.get(BeanUser.class,user_id);
			if(one==null) throw new BusinessException("该用户不存在");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public List<BeanUser> searchUser(String keyword)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
			String hql="from BeanUser where user_name like '%"+keyword+"%' or user_phone_number like '%"+keyword+"%'";
			Query qry=s.createQuery(hql);
			List<BeanUser> result=qry.list();
			tx.commit();
			return result;	
	}
	public BeanUser modifyUser(BeanUser user)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanUser one=(BeanUser) s.get(BeanUser.class,user.getUser_id());
			if(one==null) throw new BusinessException("该用户不存在");
			one.setUser_id(user.getUser_id());
			one.setUser_name(user.getUser_name());
			one.setUser_phone_number(user.getUser_phone_number());
			one.setUser_webmail(user.getUser_webmail());
			s.update(one);
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public static boolean isNumeric(String str){
	   for (int i = str.length();--i>=0;){  
	       if (!Character.isDigit(str.charAt(i))){
	           return false;
	       }
	   }
	   return true;
	}
	
	
}
