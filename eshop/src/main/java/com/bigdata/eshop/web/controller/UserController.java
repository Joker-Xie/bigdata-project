package com.bigdata.eshop.web.controller;

import com.bigdata.eshop.model.User;
import com.bigdata.eshop.service.UserService;
import com.bigdata.eshop.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Resource(name = "userService")
    private UserService us;
    /*
     * 去注册页面
     * */
    @RequestMapping(value = "/toRegPage", method = RequestMethod.GET)
    public String toRegPage() {
        return "userRegPage";
    }
    /*
    * 注册操作
    * */
    @RequestMapping(value = "/doReg",method = RequestMethod.POST)
    public String doReg(User user){
        us.saveEntity(user);
        System.out.println("注册成功！");
        return "loginPage";
    }
    /*
    * 到达登录页面
    * */
    @RequestMapping(value = "/toLoginPage",method = RequestMethod.GET)
    public String toLoginPage(){
        return "loginPage";
    }
    /*
    * User:封装的客户端提交的user信息
    * s: session对象，用来保存登录成的用户名。
    * m: 登录失败，向客户端回传失败的信息的载体
    * 登录提交验证
    * */
    @RequestMapping(value = "/doLogin" ,method = RequestMethod.POST)
    public String doLogin(User user, HttpSession s, Model m){
        String hql = "from User u where u.name = ? and u.password = ?";
        List<User> list = us.findByHQL(hql,user.getName(),user.getPassword());
        if(list.isEmpty() || list== null){
            m.addAttribute("error","用户名/密码验证失败，请重试！！");
        }
        else {
            User u = list.get(0);
            s.setAttribute("name",user.getName());
        }
        return "index";
    }
}
