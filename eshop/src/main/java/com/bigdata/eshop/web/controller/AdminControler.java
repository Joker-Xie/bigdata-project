package com.bigdata.eshop.web.controller;

import com.bigdata.eshop.model.User;
import com.bigdata.eshop.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class AdminControler {
    @Resource
    private UserService us ;

    @RequestMapping(value = "/admin/userlist",method = RequestMethod.GET)
    public String userList(Model m){
        List<User> allUsers = us.findAllEntites();
        m.addAttribute("allUsers",allUsers);
        return "userlist";
    }
    @RequestMapping(value = "/admin/delUser",method = RequestMethod.GET)
    public String delUser(@RequestParam("uid") int id){
        User u = new User();
        u.setId(id);
        us.deleteEntity(u);
        return "redirect:/admin/userlist";
    }
    @RequestMapping(value = "/admin/viewUser",method = RequestMethod.GET)
    public String viewUser(@RequestParam("uid") int id,Model m){
        User u = us.getEntity(id);
        m.addAttribute("user",u);
        return "view";
    }
    @RequestMapping(value = "/admin/editUser",method = RequestMethod.GET)
    public String toUpdate(@RequestParam("uid") int id,Model m){
        User u = us.getEntity(id);
        m.addAttribute("user",u);
        return "editUserPage";
    }

    @RequestMapping(value = "/admin/updateUser",method = RequestMethod.POST)
    public String updateUser(User u){
        us.updateEntity(u);
        return "redirect:/admin/userlist";
    }
}
