package com.web.controller;

import com.web.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/index")
    public ModelAndView show(){
        ModelAndView modelAndView = new ModelAndView();

        List<?> list = testService.userAll();
        for (Object o : list) {
            System.out.println(o);
        }
        modelAndView.setViewName("/index");

        return modelAndView;
    }
}
