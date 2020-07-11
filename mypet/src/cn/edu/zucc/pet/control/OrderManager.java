package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class OrderManager {
	public List<BeanOrder> loadAllOrders(boolean withDeletedOrder)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanOrder order by order_id";
			Query qry=s.createQuery(hql);
			List<BeanOrder> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createOrder(BeanOrder order)throws BaseException{
		
		
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanOrder one=(BeanOrder) s.get(BeanOrder.class,order.getOrder_id());
//			if(one!=null) throw new BusinessException("该订单已经存在");
			BeanCommodity two=(BeanCommodity) s.get(BeanCommodity.class,order.getCommodity_id());
			if(two==null) throw new BusinessException("该商品不存在");
			BeanUser three =(BeanUser) s.get(BeanUser.class,order.getUser_id());
			if(three==null) throw new BusinessException("该用户不存在");
			BeanStaff four=(BeanStaff) s.get(BeanStaff.class,order.getStaff_id());
			if(four==null) throw new BusinessException("该职员不存在");
			int number=order.getNumber();
			float price = order.getSale();
			if(number==0||price==0) throw new BusinessException("商品数量、单价不能为0");
			s.save(order);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}

	public void deleteOrder(int order_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanOrder one=(BeanOrder) s.get(BeanOrder.class,order_id);
			if(one==null) throw new BusinessException("订单不存在");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public List<BeanOrder> searchOrder(String keyword)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
			String hql="from BeanOrder where order_id like '%"+keyword+"%' or user_id like '%"+keyword+"%' or staff_id like '%"+keyword+"%' order by order_id";
			Query qry=s.createQuery(hql);
			List<BeanOrder> result=qry.list();
			tx.commit();
			return result;	
	}
	public BeanOrder loadOrder(int order_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanOrder one=(BeanOrder) s.get(BeanOrder.class,order_id);
			if(one==null) throw new BusinessException("订单不存在");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanOrder modifyOrder(BeanOrder order)throws BaseException
	{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanOrder one=(BeanOrder) s.get(BeanOrder.class,order.getOrder_id());
			if(one==null) throw new BusinessException("订单不存在");
			one.setCommodity_id(order.getCommodity_id());
			one.setDeliver_condition(order.getDeliver_condition());
			one.setNumber(order.getNumber());
			one.setSale(order.getSale());
			one.setUser_id(order.getUser_id());
			one.setStaff_id(order.getStaff_id());
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
	}
}
