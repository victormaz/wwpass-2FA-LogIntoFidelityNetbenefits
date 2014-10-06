package com.wwpass.fidelity.demo.web.conrtollers;

import java.io.IOException;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wwpass.fidelity.demo.domain.DemoUser;
import com.wwpass.fidelity.demo.service.DemoService;
import com.wwpass.fidelity.demo.web.authentication.WwpassContainerStorage;
import com.wwpass.fidelity.demo.web.model.DashboardForm;

@Controller
public class DashboardController {

    private final WwpassContainerStorage storage;
    private final DemoService demoService;
    
    private final static String MODEL_PW_REF = "pw";
    private final static String MODEL_UID_REF = "uid";
    
    @Autowired
    public DashboardController(WwpassContainerStorage storage, DemoService demoService) {
        if (storage == null) {
            throw new IllegalArgumentException("WwpassContainerStorage cannot be null");
        }
        if (demoService == null) {
            throw new IllegalArgumentException("demoService cannot be null");
        }
        this.demoService = demoService;
        this.storage = storage;
    }

    @RequestMapping(value = "/wwpass", method = RequestMethod.POST)
    public String wwpassLogin(@ModelAttribute DashboardForm dashboardForm) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login/form?nouser";
        }
        return "redirect:/"; 
    }


    @RequestMapping("/")
    public String welcome(@ModelAttribute DashboardForm dashboardForm,
            Model model) {
    	
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DemoUser demoUser = null;
        if (principal instanceof User) {
            demoUser = demoService.getUserByUsername( ((User)principal).getUsername());
        } else {
            demoUser = (DemoUser) principal;
        }
        
        if(demoUser != null && !StringUtils.isEmpty(demoUser.getPassword()) && !StringUtils.isEmpty(demoUser.getUsername()) ){

        	model.addAttribute(MODEL_PW_REF, demoUser.getPassword());
	        model.addAttribute(MODEL_UID_REF, demoUser.getUsername());
	        
	        return "loginredirect";
        }

        
        return "dashboard";
    }
    
    @RequestMapping(value = "/attach", method = RequestMethod.POST)
    public String attach(
            @Valid DashboardForm dashboardForm,
                BindingResult result, 
            RedirectAttributes redirectAttributes) 
    {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DemoUser demoUser = null;
        if (principal instanceof User) {
            demoUser = demoService.getUserByUsername( ((User)principal).getUsername());
        } else {
            demoUser = (DemoUser) principal;
        }
        String password = dashboardForm.getPassword();
        if (password.equals("")) {
            return "dashboard";
        }
        if ( !demoUser.getPassword().equals(password) ) {
            result.rejectValue("password", "errors.dashboard.password", "Password is incorrect!");
            return "dashboard";
        }

        try {
            storage.storeUsernamePasswordInWwpass(demoUser.getUsername(), demoUser.getPassword(), dashboardForm.getTicket());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        redirectAttributes.addFlashAttribute("message", "You successfully attach your PassKey to your account.");
        return "redirect:/";

    }
    
    
}
