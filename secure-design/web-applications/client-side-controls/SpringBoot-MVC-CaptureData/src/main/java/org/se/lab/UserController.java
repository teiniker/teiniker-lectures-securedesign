package org.se.lab;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController
{
    @PostMapping("/controller")
    public String addUser(@RequestParam(name="id") int id,
                          @RequestParam(name="username") String username,
                          @RequestParam(name="password") String password,
                          Model model)
    {
        // Do something with the user data

        model.addAttribute("id", id);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        return "display";
    }
}
