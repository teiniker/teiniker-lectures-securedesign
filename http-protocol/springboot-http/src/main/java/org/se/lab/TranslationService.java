package org.se.lab;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService
{
    private Map<String, String> dictionary;

    public TranslationService()
    {
        dictionary = new HashMap<String, String>();
        dictionary.put("katze", "cat");
        dictionary.put("maus", "mouse");
        dictionary.put("pferd", "horse");
        //...
    }

    public String translate(String word)
    {
        if(dictionary.containsKey(word.toLowerCase()))
            return dictionary.get(word.toLowerCase());
        else
            return "unknown";
    }
}
