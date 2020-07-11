package cn.edu.zucc.pet.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanUser;
import cn.edu.zucc.pet.model.BeanPet;
import cn.edu.zucc.pet.model.BeanCommodity;
import cn.edu.zucc.pet.model.BeanCommodityCategory;
import cn.edu.zucc.pet.model.BeanOrder;
import cn.edu.zucc.pet.util.BaseException;
import cn.edu.zucc.pet.util.BusinessException;
import cn.edu.zucc.pet.util.DBUtil;
import cn.edu.zucc.pet.util.DbException;
import cn.edu.zucc.pet.util.HibernateUtil;

public class PetManager {
	public List<BeanPet> loadAllPets(boolean withDeletedPet)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			String hql="from BeanPet order by pet_id";
			Query qry=s.createQuery(hql);
			List<BeanPet> result=qry.list();
			tx.commit();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public void createPet(BeanPet pet)throws BaseException{
		
		
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			
			BeanPet one=(BeanPet) s.get(BeanPet.class,pet.getPet_id());
			if(one!=null) throw new BusinessException("该宠物已经存在");
			else {
				BeanUser two=(BeanUser) s.get(BeanUser.class,pet.getUser_id());
				if(two==null) throw new BusinessException("该主人不存在");
			}
			
			s.save(pet);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public List<BeanPet> searchPet(String keyword)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
			String hql="from BeanPet where pet_nickname like '%"+keyword+"%'";
			Query qry=s.createQuery(hql);
			List<BeanPet> result=qry.list();
			tx.commit();
			return result;	
	}
	public void deletePet(int pet_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanPet one=(BeanPet) s.get(BeanPet.class,pet_id);
			if(one==null) throw new BusinessException("宠物不存在");
			s.delete(one);
			tx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanPet loadPet(int pet_id)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanPet one=(BeanPet) s.get(BeanPet.class,pet_id);
			if(one==null) throw new BusinessException("宠物不存在");
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	public BeanPet modifyPet(BeanPet pet)throws BaseException{
		Session s=HibernateUtil.getSession();
		Transaction tx=s.beginTransaction();
		try {
			BeanPet one=(BeanPet) s.get(BeanPet.class,pet.getPet_id());
			if(one==null) throw new BusinessException("该宠物不存在");
			one.setPet_category(pet.getPet_category());
			one.setPet_id(pet.getPet_id());
			one.setPet_nickname(pet.getPet_nickname());
			one.setPet_photo(pet.getPet_photo());
			s.update(one);
			tx.commit();
			return one;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new DbException(e);
		}
		
	}
	

}
