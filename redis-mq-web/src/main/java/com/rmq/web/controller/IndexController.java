package com.rmq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author mqz
 * @description
 * @since 2020/5/21
 */
@Controller
@RequestMapping("/index")
@ApiIgnore
public class IndexController {


    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/home")
    public String home(){
        return "home";
    }


    @RequestMapping(value = "/system/user")
    public String user(){
        return "system/user";
    }

    @RequestMapping(value = "/system/role")
    public String role(){
        return "system/role";
    }

    @RequestMapping(value = "/system/roleSave")
    public String roleSave(){
        return "system/role-save";
    }

    @RequestMapping(value = "/system/userSave")
    public String userSave(){
        return "system/user-save";
    }


    @RequestMapping(value = "/system/log")
    public String log(){
        return "system/log";
    }

    @RequestMapping(value = "/system/menu")
    public String menu(){
        return "system/menu";
    }

    @RequestMapping(value = "/system/menuSave")
    public String menuSave(){
        return "system/menu-save";
    }

    @RequestMapping(value = "/welcome")
    public String welcome(){
        return "welcome";
    }

}
