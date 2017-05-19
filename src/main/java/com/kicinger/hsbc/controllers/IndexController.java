package com.kicinger.hsbc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sun.misc.Contended;

/**
 * Created by krzysztofkicinger on 17/05/2017.
 */
@Controller
public class IndexController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String getIndex() {
        return "redirect:swagger-ui.html";
    }

}
