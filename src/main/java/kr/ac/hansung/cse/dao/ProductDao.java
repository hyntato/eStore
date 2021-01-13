package kr.ac.hansung.cse.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.hansung.cse.model.Product;

// jdbc(java) -> spring jdbc -> hibernate

@Repository
@Transactional
public class ProductDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Product getProductById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Product product = (Product) session.get(Product.class, id);
		
		return product;
	}

	public List<Product> getProducts() {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "from Product";
		
		Query<Product> query = session.createQuery(hql, Product.class);
		List<Product> productList = query.getResultList();
		
		return productList;
	}

	public void addProduct(Product product) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
		
	}

	public void deleteProduct(Product product) {
		
		Session session = sessionFactory.getCurrentSession();
		session.delete(product);
		session.flush();
		
	}

	public void updateProduct(Product product) {
		
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		session.flush();
		
	}

}

