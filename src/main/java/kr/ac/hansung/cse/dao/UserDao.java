package kr.ac.hansung.cse.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import kr.ac.hansung.cse.model.User;

@Repository
@Transactional
public class UserDao {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		session.saveOrUpdate(user);
		session.flush();
	}

	public User getUserById(int userId) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, userId);
		
		return user;
	}

	public User getUserByUsername(String username) {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "from User where username=?0";
		
		Query<User> query = session.createQuery(hql, User.class);
		query.setParameter(0, username);
		User user= (User) query.getSingleResult();
		
		return user;
	}

	public List<User> getAllUsers() {
		Session session = sessionFactory.getCurrentSession();
		
		String hql = "from User";
		
		Query<User> query = session.createQuery(hql, User.class);
		List<User> userList = query.getResultList();
		
		return userList;
	}

}
