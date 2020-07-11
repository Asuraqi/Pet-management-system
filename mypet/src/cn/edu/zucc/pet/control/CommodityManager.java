package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanServiceOrder;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class CommodityManager {

	public List<BeanCommodity> loadAllCommoditys(boolean withDeletedCommodity)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanCommodity order by commodity_id";
			Query qry=s.createQuery(hql);
			List<BeanCommodity> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createCommodity(BeanCommodity commodity)throws BaseException{
		
		
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanCommodity one=(BeanCommodity) s.get(BeanCommodity.class,commodity.getCommodity_id());
			if(one!=null) throw new BusinessException("该商品已经存在");
			else
			{
				s.save(commodity);
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	
	
	public void deleteCommodity(int commodity_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanCommodity one=(BeanCommodity) s.get(BeanCommodity.class,commodity_id);
			if(one==null) throw new BusinessException("该商品不存在");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanCommodity loadCommodity(int commodity_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanCommodity one=(BeanCommodity) s.get(BeanCommodity.class,commodity_id);
			if(one==null) throw new BusinessException("该商品不存在");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public List<BeanCommodity> searchCommodity(String keyword)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
			String hql="from BeanCommodity where commodity_name like '%"+keyword+"%' order by commodity_id";
			Query qry=s.createQuery(hql);
			List<BeanCommodity> result=qry.list();
			tx.commit();
			return result;	
	}
	public  void changeCommodityCategory(int Commodity_id,int Category_id) throws BaseException
	{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		BeanCommodity commodity=(BeanCommodity)s.get(BeanCommodity.class, Commodity_id);
		BeanCommodityCategory commoditycategory=(BeanCommodityCategory)s.get(BeanCommodityCategory.class, Category_id);
		commodity.setCategory_id(Category_id);
		commodity.setCategory_name(commoditycategory.getCategory_name());
		s.update(commodity);
		tx.commit();
	}
	public  void modifyCommodity(BeanCommodity commodity)
	{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		BeanCommodity one=(BeanCommodity)s.get(BeanCommodity.class,commodity.getCommodity_id());
		one.setCommodity_name(commodity.getCommodity_name());
		one.setCommodity_barcode(commodity.getCommodity_barcode());
		one.setCommodity_price(commodity.getCommodity_price());
		one.setCommodity_brand(commodity.getCommodity_brand());
		s.update(commodity);
		tx.commit();
	}
}
