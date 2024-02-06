package org.se.lab;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class InfoController
{
    @GetMapping("/infos")
    String info()
    {
        return "Book API Information (Version 1.0)";
    }
}
