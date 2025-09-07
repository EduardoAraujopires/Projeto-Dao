package Aplication;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		DepartmentDao depDao = DaoFactory.createDepartmentDao();

		System.out.println("=====Test 1: department: update ======");
		Department dep = new Department();
		dep = depDao.findById(1);
		dep.setName("Games");
		depDao.update(dep);
		System.out.println("Update completed");
		System.out.println();
		System.out.println("=====Test 2: department: insert ======");
		Department der = new Department(null, "Estudo");
		depDao.insert(der);
		System.out.println("Insert id: " + der.getId());
		System.out.println();
		System.out.println("=====Test 3: department: findById ======");
		Department d = depDao.findById(2);
		System.out.println(d);
		System.out.println();
		System.out.println("=====Test 4: department: findAll ======");
		List<Department> list = depDao.findAll();
		for (Department lista : list) {
			System.out.println(lista);
		}
		System.out.println("findAll! completed");
		System.out.println();
		System.out.println("=====Test 5: department: deleteById ======");
		System.out.println("Informe um id de um departamento para que seja deletado");
		int id = sc.nextInt();
		depDao.deleteById(id);
		System.out.println("Id deletado! completed");

		sc.close();
	}

}
