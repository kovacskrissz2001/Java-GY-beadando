package pizza;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pizza.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pizza.security.Role;
import pizza.security.User;
import pizza.security.UserRepository;
import org.springframework.security.core.*;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Controller
@EnableWebSecurity

public class HomeController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Uzenetrepo uzenetrepo;

    @Autowired
    private Pizzarepo pizzarepo;

    public static void main(String[] args) {
        SpringApplication.run(HomeController.class, args);
    }

    @GetMapping("/regisztral")
    public String greetingForm(Model model) {
        model.addAttribute("reg", new User());
        return "regisztral";
    }

    @PostMapping("/regisztral_feldolgoz")
    public String Regisztráció(@ModelAttribute User user, Model model) {
        for(User felhasznalo2: userRepo.findAll())
            if(felhasznalo2.getEmail().equals(user.getEmail())){
                model.addAttribute("uzenet", "A regisztrációs email már foglalt!");
                return "reghiba";
            }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = new Role();
        // Minden regisztrációkor USER szerepet adunk a felhasználónak:
        role.setId(3); role.setName("ROLE_USER");
        List<Role> rolelist = new ArrayList<Role>();
        rolelist.add(role);
        user.setRoles(rolelist);
        userRepo.save(user);
        model.addAttribute("id", user.getId());
        return "regjo";
    }

    @GetMapping(value = {"/Index", "/Index.html"})
    public String IndexView() {
        return "/Index";
    }

    @GetMapping(value = {"/Shop", "/Shop.html"})
    public String ShopView(Model m) {
        m.addAttribute("pizzak",pizzarepo.findAll());
        return "/Shop";
    }


    @GetMapping(value = {"/Contact", "/Contact.html"})
    public String ContactView(Model m) {
        m.addAttribute("uzenet",new UzenetekEntity());
        return "/Contact";
    }

    @PostMapping("/feldolgoz")
    public String feldolgoz(@ModelAttribute(name = "uzenet") UzenetekEntity uzenet, RedirectAttributes redirect, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "/Contact";
        }
        //https://www.baeldung.com/get-user-in-spring-security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken))
            uzenet.setIrta(authentication.getName());
        else
            uzenet.setIrta("Vendég");
        uzenet.setDatum(new Timestamp(System.currentTimeMillis()));
        uzenetrepo.save(uzenet);
        redirect.addFlashAttribute("ertesites", "Üzenetét rögzítettük! Azonosítója: "+
                uzenet.getId());
        return "redirect:/Contact";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RedirectView hibasUzenet(ConstraintViolationException exception, RedirectAttributes attributes){
        attributes.addFlashAttribute("hiba","Legalább 10 karakter hosszú legyen az üzenet");
        return new RedirectView("/Contact");
    }

    @GetMapping(value = {"/Admin", "/Admin.html"})
    public String AdminView() { return "/Admin"; }

    @GetMapping(value = {"/Dashboard", "/Dashboard.html"})
    public String DashboardView() { return "/Dashboard"; }
}