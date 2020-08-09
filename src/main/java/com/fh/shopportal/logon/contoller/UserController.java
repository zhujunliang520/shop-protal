package com.fh.shopportal.logon.contoller;

import com.fh.shopportal.logon.biz.IUserService;
import com.fh.shopportal.logon.common.ServerResponse;

import com.fh.shopportal.logon.po.User;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource(name = "userService")
    private IUserService userService;
    @Autowired
    private Producer kaptchaProducer;

    @Value("${server.port}")
    private String port;

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return port;
    }

    /**
     * springboot 生成验证码+ 写了个配置类 调用bean
     *
     * @param response
     * @param session
     */
    @RequestMapping(path = "/checkCode", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        // 生成验证码
        String text = kaptchaProducer.createText( );
        BufferedImage image = kaptchaProducer.createImage(text);
        // 将验证码存入session
        session.setAttribute("checkCode", text);
        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream( );
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    @RequestMapping("login")
    public String login() {
        return "user/login";
    }

    @RequestMapping("toPage")
    public String toPage() {
        return "user/toPage";
    }

    @RequestMapping("toZhuCe")
    public String toZhuCe() {
        return "user/toZhuCe";
    }

    @RequestMapping("toLogin")
    @ResponseBody
    public ServerResponse toLogin(String userName, String password, String checkCode, HttpSession session) {
        User user = (User) userService.getUserByUserName(userName);
        System.out.println( );
        if (user == null) {
            return ServerResponse.error(1, "用户名不存在");
        }
        if (StringUtils.isBlank(password)) {
            return ServerResponse.error(1, "密码不能为空");
        }
        if (StringUtils.isBlank(checkCode)) {
            return ServerResponse.error(1, "验证码不能为空");
        }
        if (!checkCode.equals(session.getAttribute("checkCode"))) {
            return ServerResponse.error(1, "验证码不一致空");
        }
        if (!user.getPassword( ).equals(password)) {
            return ServerResponse.error(1, "密码错误");
        } else {
            session.setAttribute("user", user);
            return ServerResponse.success(user);
        }

    }

    /**
     * 增加用户
     *
     * @param user
     * @return
     */
    @RequestMapping("addUser")
    @ResponseBody
    public ServerResponse addUser(User user) {
        User byUser = userService.getUserByUserName(user.getUserName( ));
        if (byUser != null) {
            return ServerResponse.error(1, "用户名已存在");
        }
        userService.addUser(user);
        return ServerResponse.success( );
    }
}
