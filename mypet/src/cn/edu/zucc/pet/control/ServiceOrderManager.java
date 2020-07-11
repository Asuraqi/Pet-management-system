package cn.edu.zucc.pet.control;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanServiceOrder;
import cn.edu.zucc.pet.model.BeanStaff;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class ServiceOrderManager {
	public List<BeanServiceOrder> loadAllServiceOrders(boolean withDeletedServiceOrder)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanServiceOrder order by service_id";
			Query qry=s.createQuery(hql);
			List<BeanServiceOrder> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createServiceOrder(BeanServiceOrder ServiceOrder)throws BaseException{
		
		
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanServiceOrder one=(BeanServiceOrder) s.get(BeanServiceOrder.class,"ServiceOrder.getService_id()");
			if(one!=null) throw new BusinessException("该预约服务已经存在");
			BeanPet two =(BeanPet) s.get(BeanPet.class,ServiceOrder.getPet_id());
            if(two==null) throw new BusinessException("该宠物不存在");
            BeanStaff three =(BeanStaff) s.get(BeanStaff.class,ServiceOrder.getStaff_id());
            if(three==null) throw new BusinessException("该职工不存在");
			s.save(ServiceOrder);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}

	public void deleteServiceOrder(String service_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanServiceOrder one=(BeanServiceOrder) s.get(BeanServiceOrder.class,"service_id");
			if(one==null) throw new BusinessException("预约服务不存在");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanServiceOrder loadServiceOrder(String service_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanServiceOrder one=(BeanServiceOrder) s.get(BeanServiceOrder.class,"service_id");
			if(one==null) throw new BusinessException("预约服务不存在");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void modifyServiceOrder(BeanServiceOrder service_order)throws BaseException
	{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanServiceOrder one=(BeanServiceOrder) s.get(BeanServiceOrder.class,service_order.getService_id());
			if(one==null) throw new BusinessException("该用户不存在");
			one.setCommodity_id(service_order.getCommodity_id());
			one.setPet_id(service_order.getPet_id());
			one.setService_finish_time(service_order.getService_finish_time());
			one.setService_time(service_order.getService_time());
			one.setStaff_id(service_order.getStaff_id());
			s.update(one);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
	}
	public List<BeanServiceOrder> searchServiceOrder(String keyword)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
			String hql="from BeanServiceOrder where service_id like '%"+keyword+"'";//"or staff_id="+keyword+"or pet_id="+keyword+"or commodity_id="+keyword;
			Query qry=s.createQuery(hql);
			List<BeanServiceOrder> result=qry.list();
			tx.commit();
			return result;	
	}
}
