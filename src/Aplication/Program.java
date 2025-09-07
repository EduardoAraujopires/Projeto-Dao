package Aplication;

import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
	  Scanner sc = new Scanner(System.in);
		SellerDao sellerDao = DaoFactory.createSellerDao();

		System.out.println("==== Test 1: seller findById ====");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

		System.out.println("\n==== Test 2: seller findByDepartment ====");
		Department department = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(department);
		for (Seller obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n==== Test 3: seller findAll ====");

		list = sellerDao.findAll();
		for (Seller obj : list) {
			System.out.println(obj);
		}
		/*
		System.out.println("\n==== Test 4: seller insert ====");
		Seller funcionario = new Seller(null, "Eduardo", "eduardo@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(funcionario);
		System.out.println("Inserted! new Id = "+ funcionario.getId());
		*/
		System.out.println("\n==== Test 5: seller update ====");
		seller  = sellerDao.findById(1);
		seller.setName("Maria");
		seller.setEmail("maria@gmail.com");
		sellerDao.update(seller);
		System.out.println("update Completed");
		
		System.out.println("\n==== Test 6: seller delete ====");
		System.out.println("enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete Completed");
		sc.close();
	}
}
