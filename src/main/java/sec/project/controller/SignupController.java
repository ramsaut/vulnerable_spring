package sec.project.controller;

import ch.qos.logback.core.CoreConstants;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController {

    @Autowired
    private SignupRepository signupRepository;

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(@RequestParam String name, @RequestParam String address, Model model) {
        signupRepository.save(new Signup(name, address,"no"));
        model.addAttribute("name", name);
        return "done";
    }
    @RequestMapping(value = "/formvip", method = RequestMethod.POST)
    public String formvip(@RequestParam String name, @RequestParam String address, Model model) {
        signupRepository.save(new Signup(name, address,"yes"));
        return admin(model);
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        List<Signup> all = signupRepository.findAll();
        List<String> allNames = new ArrayList<>();
        for(Signup e : all){
            allNames.add(e.getName());
            System.out.print(e.getId());
        }
        model.addAttribute("people", all);
        return "admin";
    }
    
    
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String admin(@RequestParam Long id, Model model) {
        System.out.print(id);
        signupRepository.delete(id);
        return admin(model);
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "login";
    }

}
