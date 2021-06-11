package org.se.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController
{
    @Autowired
    private TranslationService service;

    @GetMapping("/app")
    public String greeting(@RequestParam(name="word") String word, Model model)
    {
        String translation = service.translate(word);
        model.addAttribute("word", word);
        model.addAttribute("translation", translation);
        return "translation";
    }
}
