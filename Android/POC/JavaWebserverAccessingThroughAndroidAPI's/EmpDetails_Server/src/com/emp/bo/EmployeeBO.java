package com.emp.bo;

import java.util.ArrayList;
import java.util.Iterator;

import com.emp.dao.GetEmpRecord;
import com.emp.dao.GetEmpRecord;
import com.emp.dto.Emp;

public class EmployeeBO {
	public ArrayList getEmployeeData(int eno,String name){
		ArrayList list=null;
		list=new ArrayList();
		
		Emp emp=validateEmployee(eno,name);
		if(!emp.equals(null)){
			 
			list.add(emp);
		}
		else{
			list.add("invaliduser");
		}
		
		return list;
		
		
	}
	
	private Emp validateEmployee(int eno,String name){
		GetEmpRecord ier=new GetEmpRecord();
		ArrayList list=ier.getEmpDetails(eno,name);
		Iterator i=list.iterator();
		while(i.hasNext()){
		Emp e=(Emp)i.next();
		if(e.equals(null)){
			return null;
			
		}
		else{
			return e;
		}
			
		}
		return null;
		

		
		
		
	}

}
