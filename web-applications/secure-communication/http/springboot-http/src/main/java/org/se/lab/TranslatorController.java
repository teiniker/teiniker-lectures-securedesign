package org.se.lab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TranslatorController
{
    @Autowired
    private TranslationService service;

    @RequestMapping(value ="/translate/{word}", method = GET)
    public String greetings(
            @PathVariable String word,
            @RequestHeader("accept-language") String language)
    {
        String translation = service.translate(word);
        System.out.println("accept-language: " + language);
        return "Translate: " + word + " into " + translation;
    }

}
