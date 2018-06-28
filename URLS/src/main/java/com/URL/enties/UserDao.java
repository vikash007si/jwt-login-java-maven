package com.URL.enties;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
 
public class UserDao {
    
    public void addUser(com.second.URLS.UserDO bean){
        Session session = SessionUtil.getSession();        
        Transaction tx = session.beginTransaction();
        addEmployee(session,bean);        
        tx.commit();
        session.close();
        
    }

	private void addEmployee(Session session, com.second.URLS.UserDO bean) {
		User user = new User();
		user.setEmail(bean.getEmail());
		//user.setUserId(bean.getUserId());
		user.setUsername(bean.getUserName());
		user.setPassword(bean.getPassword());
		session.save(user);
	}
	
	public boolean readEmployee(String email ,String pwd){
		Session session = SessionUtil.getSession();        
        Transaction tx = session.beginTransaction();
		String hql = "SELECT U.password FROM User U where U.email=:email";
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		List<String> results = query.list();
		
		  session.close();
		  
		  return results.get(0).equals(pwd);
	}
}
