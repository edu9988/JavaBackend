package com.project.nameless.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.project.nameless.model.User;
import com.project.nameless.model.UserService;

import java.lang.reflect.Field;

@Controller
public class IndexController {

    @Autowired private ApplicationContext context;

    @GetMapping("/")
    public String index(){
	System.out.println( "\nGET /\n" );
	return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
	System.out.println( "\nGET /login"
	    + " Model = " + model + "\n"
	);
	model.addAttribute( "user" , new User() );
	return "login";
    }

    @PostMapping("/login")
    public String login( Model model , @ModelAttribute User u ){
	System.out.println( "\nPOST /login"
	    + " Model = " + model + "User = "
	    + u + "\n"
	);
	UserService us = context.getBean( UserService.class );
	if( us.validateUser( u ) )
	    return "redirect:/authenticated";
	else{
	    String err = "Invalid user name and password combination";
	    model.addAttribute( "feedback" , err );
	    return "login";
	}
    }

    @GetMapping("/signup")
    public String signup(Model model){
	System.out.println( "\nGET /signup"
	    + " Model = " + model + "\n"
	);
	model.addAttribute( "user" , new User() );
	return "signup";
    }

    @PostMapping("/signup")
    public String signup( Model model , @ModelAttribute User u ){
	System.out.println( "\nPOST /signup"
	    + " Model = " + model + "User = "
	    + u + "\n"
	);
	UserService us = context.getBean( UserService.class );
	try{
	    if( us.insertUser( u ) )
		return "redirect:/registered";
	    else{
		System.out.println( "Setting feedback:Name unavailable" );
		model.addAttribute( "feedback" , "Name unavailable" );
		return "signup";
	    }
	}
	catch(Exception e){
	    System.out.println( "Setting feedback:Name unavailable" );
	    model.addAttribute( "feedback" , "Name unavailable" );
	    return "signup";
	}
    }

    @GetMapping("/registered")
    public String registered(){
	System.out.println( "\nGET /registered\n" );
	return "registered";
    }

    @GetMapping("/authenticated")
    public String authenticated(){
	System.out.println( "\nGET /authenticated\n" );
	return "authenticated";
    }

    @GetMapping("/list")
    public String list( Model model ){
	System.out.println( "\nGET /list\n" );
	UserService us = context.getBean( UserService.class );
	List<Map<String,Object>> list = us.listUsers();
	model.addAttribute( "list" , list );
	return "list";
    }

    @GetMapping("/edit/{uid}")
    public String edit( @PathVariable("uid") int uid , Model model ){
	System.out.println( "\nGET /edit/" + uid + "\n" );
	UserService us = context.getBean( UserService.class );
	Map<String,Object> user = us.getUser(uid).get(0);
	String uname = (String) user.get("uname");
	String pwd = (String) user.get("pwd");
	model.addAttribute( "user" , new User( uname , pwd ) );
	model.addAttribute( "uname" , uname );
	model.addAttribute( "pwd" , pwd );
	return "edit";
    }

    @PostMapping("/edit/{uid}")
    public String edit( @PathVariable("uid") int uid , Model model , @ModelAttribute User u ){
	System.out.println( "\nPOST /edit/" + uid + "\n" );
	UserService us = context.getBean( UserService.class );
	us.setUser( uid , u );
	return "redirect:/list";
    }

    @PostMapping("/delete/{uid}")
    public String delete( @PathVariable("uid") int uid ){
	System.out.println( "\nPOST /delete/" + uid + "\n" );
	UserService us = context.getBean( UserService.class );
	us.rmUser( uid );
	return "redirect:/list";
    }

    @GetMapping("/hash")
    public String hash(){
	System.out.println( "\nGET /hash\n" );
	return "hash";
    }

    @GetMapping("/hlogin")
    public String hlogin(Model model){
	System.out.println( "\nGET /hlogin"
	    + " Model = " + model + "\n"
	);
	model.addAttribute( "user" , new User() );
	return "hlogin";
    }

    @PostMapping("/hlogin")
    public String hlogin( Model model , @ModelAttribute User u ){
	System.out.println( "\nPOST /hlogin"
	    + " Model = " + model + "User = "
	    + u + "\n"
	);
	UserService us = context.getBean( UserService.class );
	if( us.hvalidateUser( u ) )
	    return "redirect:/hauthenticated";
	else{
	    model.addAttribute( "feedback" , "Invalid user name and password combination" );
	    return "hlogin";
	}
    }

    @GetMapping("/hsignup")
    public String hsignup(Model model){
	System.out.println( "\nGET /hsignup"
	    + " Model = " + model + "\n"
	);
	model.addAttribute( "user" , new User() );
	return "hsignup";
    }

    @PostMapping("/hsignup")
    public String hsignup( Model model , @ModelAttribute User u ){
	System.out.println( "\nPOST /hsignup"
	    + " Model = " + model + "User = "
	    + u + "\n"
	);
	UserService us = context.getBean( UserService.class );
	if( us.hinsertUser( u ) )
	    return "redirect:/hregistered";
	else{
	    System.out.println( "Setting feedback:Name unavailable" );
	    String err = "Name unavailable";
	    model.addAttribute( "feedback" , err );
	    return "hsignup";
	}
    }

    @GetMapping("/hregistered")
    public String hregistered(){
	System.out.println( "\nGET /hregistered\n" );
	return "hregistered";
    }

    @GetMapping("/hauthenticated")
    public String hauthenticated(){
	System.out.println( "\nGET /hauthenticated\n" );
	return "hauthenticated";
    }

    @GetMapping("/hlist")
    public String hlist( Model model ){
	System.out.println( "\nGET /hlist\n" );
	UserService us = context.getBean( UserService.class );
	List<Map<String,Object>> list = us.listUsers();
	model.addAttribute( "list" , list );
	return "hlist";
    }

    @GetMapping("/hedit/{uid}")
    public String hedit( @PathVariable("uid") int uid , Model model ){
	System.out.println( "\nGET /hedit/" + uid + "\n" );
	UserService us = context.getBean( UserService.class );
	Map<String,Object> user = us.getUser(uid).get(0);
	String uname = (String) user.get("uname");
	String pwd = (String) user.get("pwd");
	model.addAttribute( "user" , new User( uname , pwd ) );
	model.addAttribute( "uname" , uname );
	model.addAttribute( "pwd" , pwd );
	return "hedit";
    }

    @PostMapping("/hedit/{uid}")
    public String hedit( @PathVariable("uid") int uid , Model model , @ModelAttribute User u ){
	System.out.println( "\nPOST /hedit/" + uid + "\n" );
	UserService us = context.getBean( UserService.class );
	us.hsetUser( uid , u );
	return "redirect:/hlist";
    }

    @PostMapping("/hdelete/{uid}")
    public String hdelete( @PathVariable("uid") int uid ){
	System.out.println( "\nPOST /hdelete/" + uid + "\n" );
	UserService us = context.getBean( UserService.class );
	us.rmUser( uid );
	return "redirect:/hlist";
    }
}
