package com.emp.controller;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.emp.bo.EmployeeBO;
import com.emp.dao.GetEmpRecord;
import com.emp.dto.Emp;



public class EmpController 

{
	int no;
	String name;

	
public String getEmployeeDetails(int no,String name){
		
		this.no=no;
		this.name=name;

		JSONObject responseObject=new JSONObject();
		EmployeeBO ebo=new EmployeeBO();
		ArrayList list=ebo.getEmployeeData(no,name);
		Iterator<Emp> e=list.iterator();
		while(e.hasNext()){
			Emp emp=(Emp)e.next();
			System.out.print(emp.getEno());
			System.out.print(emp.getName());
			System.out.println(emp.getSalary());
			responseObject.put("eno",emp.getEno());
			responseObject.put("ename",emp.getName());
			responseObject.put("salary",emp.getSalary());
			System.out.println("json format"+responseObject); 
			
			
			
		}
		return responseObject.toString();
		
	}


}
