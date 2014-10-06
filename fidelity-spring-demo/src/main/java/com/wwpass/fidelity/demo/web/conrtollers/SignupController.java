package com.wwpass.fidelity.demo.web.conrtollers;

import com.wwpass.fidelity.demo.domain.DemoUser;
import com.wwpass.fidelity.demo.service.DemoService;
import com.wwpass.fidelity.demo.web.model.SignupForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SignupController {
    
    private final DemoService demoService;
    
    @Autowired
    public SignupController(DemoService demoService) {
        if (demoService == null) {
            throw new IllegalArgumentException("demoService cannot be null");
        }
        this.demoService = demoService;
    }
    
    @RequestMapping("/signup/form")
    public String signup(@ModelAttribute SignupForm signupForm) {
        return "signup";
    }
    
    @RequestMapping(value = "/signup/new", method = RequestMethod.POST)
    public String signup(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "signup";
        }
        String username = signupForm.getUsername();
        if (demoService.getUserByUsername(username) != null) {
            result.rejectValue("username", "errors.signup.username", "Username is already in use!");
            return "signup";
        }

        DemoUser user = new DemoUser();
        user.setUsername(signupForm.getUsername());
        user.setNickname(signupForm.getNickname());
        user.setPassword(signupForm.getPassword());
        user.setRoleId(DemoUser.ROLE_USER);
        
        int id = demoService.createUser(user);
        user.setId(id);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        user, 
                        user.getPassword(),
                        AuthorityUtils.createAuthorityList("ROLE_USER")
                )
        );

        redirectAttributes.addFlashAttribute("message", "You have successfully signed up and logged in.");
        return "redirect:/";
    }
    
}
