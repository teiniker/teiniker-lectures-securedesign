package org.se.lab;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController
{
    @GetMapping("/set-cookie")
    public String setCookie(HttpServletResponse response)
    {
        // Create a cookie
        Cookie cookie = new Cookie("SESSIONID", "A887B32D3520132167568B487BDAF2F4");
        // cookie.setHttpOnly(true);
        // cookie.setSecure(true);
        response.addCookie(cookie);

        return "set-cookie";
    }

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
