package cn.edu.hstc.sport_mall.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller层：负责图形验证码 & 短信验证码的生成
 */
@Controller
public class AssistController {

    /*
     * 生成图形验证码，并响应给客户端
     */
    @RequestMapping("createGraphicsCode")
    @ResponseBody
    public String createGraphicsCode(HttpServletRequest req, HttpServletResponse resp){
        //创建一张图片     单位:像素
        BufferedImage image = new BufferedImage(200, 100, BufferedImage.TYPE_INT_RGB);

        //透明的玻璃     向画板上画内容之前必须先设置画笔.
        Graphics2D gra = image.createGraphics();

        gra.setColor(Color.WHITE);
        //从哪个坐标开始填充, 后两个参数,矩形区域
        gra.fillRect(0, 0, 200, 100);

        List<Integer> randList = new ArrayList<>();
        Random random =new Random();
        for (int i = 0 ;i<4;i++) {
            randList.add(random.nextInt(10));
        }

        //设置字体
        gra.setFont(new Font("宋体",Font.ITALIC|Font.BOLD,40));
        Color[] colors = new Color[]{Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN,Color.PINK,Color.GRAY};
        for (int i = 0; i < randList.size(); i++) {
            gra.setColor(colors[random.nextInt(colors.length)]);
            gra.drawString(randList.get(i)+"", i*40, 70+(random.nextInt(21)-10));
        }

        for (int i = 0; i < 2; i++) {
            gra.setColor(colors[random.nextInt(colors.length)]);
            //画横线
            gra.drawLine(0, random.nextInt(101), 200, random.nextInt(101));
        }

        ServletOutputStream outputStream = null;
        try {
            outputStream = resp.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            //工具类
            ImageIO.write(image, "jpg", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //生成图形验证码
        String graphicsCode = ""+randList.get(0)+randList.get(1)+randList.get(2)+randList.get(3);
        //把图形验证码放入到session中
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.getSession().setAttribute("graphicsCode",graphicsCode);

        return graphicsCode;    //返回新的图新验证码给客户端，便于页面局部刷新
    }
}