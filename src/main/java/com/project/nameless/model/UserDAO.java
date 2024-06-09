package com.project.nameless.model;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import jakarta.annotation.PostConstruct;

import java.sql.Types;

@Repository
public class UserDAO{
    
    @Autowired DataSource dataSource;
    
    JdbcTemplate jdbc;

    @PostConstruct
    private void initialize(){
	jdbc = new JdbcTemplate( dataSource );
    }

    public boolean insertUser( User u ){
	String sql = "INSERT INTO tb_user (uname,pwd) " +
	    "VALUES (?,?)";

	Object[] obj = new Object[2];
	obj[0] = u.getUname();
	obj[1] = u.getPwd();

	try{
	    jdbc.update(sql,obj);
	}
	catch( DuplicateKeyException e ){
	    System.out.println( "\nUserDAO.insertUser():"
		+ "Insert query DuplicateKeyException:"
		+ e + "\n"
	    );
	    return false;
	}
	return true;
    }

    public boolean validateUser( User u ){
	String sql = "SELECT EXISTS( SELECT * FROM tb_user " +
	    "WHERE uname = ? AND pwd = ?)";

	Object[] obj = new Object[2];
	obj[0] = u.getUname();
	obj[1] = u.getPwd();

	int[] types = new int[2];
	types[0] = Types.VARCHAR;
	types[1] = Types.VARCHAR;

	try{
	    Boolean ans = jdbc.queryForObject(sql,obj,types,(rs,rowNum) -> 
		Boolean.valueOf( rs.getBoolean("exists")));
	    if( !ans ){
		//do something;
	    }
	    return ans;
	}
	catch( DataAccessException e ){
	    System.out.println( "\nUserDAO.validateUser():"
		+ "Select query DataAccessException:"
		+ e +"\n"
	    );
	    return false;
	}
    }

    public List<Map<String, Object>> listUsers() {
	String sql = "SELECT * FROM tb_user";
	return jdbc.queryForList( sql );
    }

    public List<Map<String,Object>> getUser( int id ){
	String sql = "SELECT * FROM tb_user WHERE uid = ?";
	Object[] obj = new Object[1];
	obj[0] = id;
	return jdbc.queryForList( sql , obj );
    }

    public void setUser( int uid , User u ){
	String sql = "UPDATE tb_user SET pwd = ? "
	    + "WHERE uid = ?";
	Object[] obj = new Object[2];
	obj[0] = u.getPwd();
	obj[1] = uid;
	jdbc.update( sql , obj );
    }

    public void rmUser( int uid ){
	String sql = "DELETE FROM tb_user WHERE uid = ?";
	Object[] obj = new Object[1];
	obj[0] = uid;
	jdbc.update( sql , obj );
    }


    public boolean hinsertUser( User u ){
	String sql = "SELECT EXISTS( SELECT * FROM tb_user " +
	    "WHERE uname = ?)";

	Object[] obj = new Object[1];
	obj[0] = u.getUname();

	int[] types = new int[1];
	types[0] = Types.VARCHAR;

	Boolean ans = jdbc.queryForObject(sql,obj,types,(rs,rowNum) -> 
	    Boolean.valueOf( rs.getBoolean("exists")));

	if( ans ){
	    System.out.println( "User exists\n" );
	    return false;
	}

	sql = "INSERT INTO tb_user (uname,pwd) " +
	    "VALUES (?,crypt(?,gen_salt('bf',8)))";

	obj = new Object[2];
	obj[0] = u.getUname();
	obj[1] = u.getPwd();

	try{
	    jdbc.update(sql,obj);
	}
	catch( Exception e ){
	    System.out.println( "\nUserDAO.hinsertUser():"
		+ "Insert query Exception:"
		+ e + "\n"
	    );
	    return false;
	}
	return true;
    }

    public boolean hvalidateUser( User u ){
	String sql = "SELECT EXISTS( SELECT * FROM tb_user " +
	    "WHERE uname = ? AND pwd = crypt(?,pwd))";

	Object[] obj = new Object[2];
	obj[0] = u.getUname();
	obj[1] = u.getPwd();

	int[] types = new int[2];
	types[0] = Types.VARCHAR;
	types[1] = Types.VARCHAR;

	try{
	    Boolean ans = jdbc.queryForObject(sql,obj,types,(rs,rowNum) -> 
		Boolean.valueOf( rs.getBoolean("exists")));
	    if( !ans ){
		//do something;
	    }
	    return ans;
	}
	catch( DataAccessException e ){
	    System.out.println( "\nUserDAO.hvalidateUser():"
		+ "Select query DataAccessException:"
		+ e +"\n"
	    );
	    return false;
	}
    }

    public void hsetUser( int uid , User u ){
	String sql = "UPDATE tb_user SET pwd = "
	    + "crypt(?,gen_salt('bf',8)) "
	    + "WHERE uid = ?";
	Object[] obj = new Object[2];
	obj[0] = u.getPwd();
	obj[1] = uid;
	jdbc.update( sql , obj );
    }
}
