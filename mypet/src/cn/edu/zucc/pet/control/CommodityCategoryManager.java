package cn.edu.zucc.pet.control;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class CommodityCategoryManager {
	
	public List<BeanCommodityCategory> loadAllCommodityCategorys(boolean withDeletedCommodityCategory)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanCommodityCategory order by category_id";
			Query qry=s.createQuery(hql);
			List<BeanCommodityCategory> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createCommodityCategory(BeanCommodityCategory CommodityCategory)throws BaseException{
		
		
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanCommodityCategory one=(BeanCommodityCategory) s.get(BeanCommodityCategory.class,CommodityCategory.getCategory_id());
			if(one!=null) throw new BusinessException("����Ʒ�����Ѿ�����");
				s.save(CommodityCategory);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	
	
	public void deleteCommodityCategory(int CommodityCategory_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanCommodityCategory one=(BeanCommodityCategory) s.get(BeanCommodityCategory.class,CommodityCategory_id);
			if(one==null) throw new BusinessException("����Ʒ���಻����");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanCommodityCategory loadCommodityCategory(int category_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanCommodityCategory one=(BeanCommodityCategory) s.get(BeanCommodityCategory.class,category_id);
			if(one==null) throw new BusinessException("����Ʒ���಻����");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanCommodityCategory modifyCommodityCategory(BeanCommodityCategory CommodityCategory)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanCommodityCategory one=(BeanCommodityCategory) s.get(BeanCommodityCategory.class,CommodityCategory.getCategory_id());
			if(one==null) throw new BusinessException("����Ʒ���಻����");
			one.setCategory_describe(CommodityCategory.getCategory_describe());
			one.setCategory_name(CommodityCategory.getCategory_name());
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public static void main(String[] args){
		BeanCommodityCategory category=new BeanCommodityCategory();
		category.setCategory_id(1);
		category.setCategory_name("ϴ��");
		category.setCategory_describe("Ϊ�������ȫ������������");
		
		try {
			new CommodityCategoryManager().createCommodityCategory(category);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
