package com.example.in28minutes.springboot.myfirstwebapp.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class WelcomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String goToWelcomePage(ModelMap model) {
		model.put("name",getLoggedinUsername());
		return "welcome";
	}
	private String getLoggedinUsername()
	{
		//the Spring Security Gives the information about the authenticated user.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getName();
	} 
}


// What is happening here is that After we Login We are going to the WElcome Page.
// The SpringSecurity is giving us the Username via the function getLoggedinUsername. Now it is being stored in the Modal as name 
//model.put("name",getLoggedinUsername());
//This name is being put on the session by using the Session Attribute.

//However if the The user Directly hits the list todos page then this page code is 
//never Executed and hence the name will not be stored in the Session.