package com.emp.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.jmx.SessionFactoryStub;

import com.emp.dto.Emp;

public class GetEmpRecord  {

	/**
	 * @param args
	 */
	public ArrayList getEmpDetails(int empno,String empname) {
		Configuration cfg=new Configuration();
		SessionFactory sf=null;
		ArrayList list=null;
		try{
		
		cfg.configure("emp.cfg.xml");

		sf=cfg.buildSessionFactory();
		Session hsession=sf.openSession();
		String queary ="from com.emp.dto.Emp e where e.eno=:no and e.name=:ename";
		Query query=hsession.createQuery(queary);
		query.setInteger("no",empno );
		query.setString("ename", empname);
		 list=(ArrayList)query.list();
		
		hsession.close();
		sf.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
		
	
		return list;
		
		

	}

}
