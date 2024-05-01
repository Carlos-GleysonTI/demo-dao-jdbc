package application;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Progam2 {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("==== TEST 1: Department findById(department por id) ====");
		Department department = departmentDao.findById(2);  
		System.out.println(department);
		
		
		System.out.println("\n====  TEST 2: Department findAll(department por ordem alfabe) ====");
		List<Department> list = departmentDao.findAll();
		//vamos pecor a lista
		for(Department d : list) {
			System.out.println(d);
		}
		
		System.out.println("\n=== TEST 3: insert =======");
		Department newDepartment = new Department(null, "Music");//null e o id autocrementado
		departmentDao.insert(newDepartment);//funçao inserir
		System.out.println("Inserted! New id: " + newDepartment.getId());
		
		System.out.println("\n=== TEST 4: update =======");
		Department dep2 = departmentDao.findById(1);//vamos atulizar o id  = 1
		dep2.setName("Food");//vai ser agora Food
		departmentDao.update(dep2);
		System.out.println("Update completed");
		
		sc.close();
	}
}
