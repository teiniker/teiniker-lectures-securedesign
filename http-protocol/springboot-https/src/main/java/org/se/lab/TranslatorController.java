package org.se.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslatorController
{
    @Autowired
    private TranslationService service;

    @RequestMapping("/translate/{word}")
    public String greetings(@PathVariable String word)
    {
        String translation = service.translate(word);
        return "Translate: " + word + " into " + translation;
    }
}
