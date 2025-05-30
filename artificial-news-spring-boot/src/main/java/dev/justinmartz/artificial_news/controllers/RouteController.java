package dev.justinmartz.artificial_news.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {

    @RequestMapping(value = "/{path:[^.]*}")
        public String redirectSingle() {
            return "forward:/";
    }

   @GetMapping("/*/{path:[^.]*}")
        public String redirectNested() {
            return "forward:/";
    }
}