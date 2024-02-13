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
                          @RequestParam(name="data") String data,
                          Model model)
    {
        // TODO: Input Validation

        model.addAttribute("id", id);
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("data", data);

        return "display";
    }
}
