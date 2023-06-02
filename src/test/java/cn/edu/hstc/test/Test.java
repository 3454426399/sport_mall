package cn.edu.hstc.test;

import cn.edu.hstc.sport_mall.util.ShiroUtil;
import org.apache.shiro.util.ByteSource;

import java.io.*;

/**
 *
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String sequence = ShiroUtil.createRandomSequence();    //生成随机序列
        String salt = ByteSource.Util.bytes(sequence).toString();    //把随机序列根据特定算法转化为盐值

        String password = ShiroUtil.encrypt("Curry@87583879", "bGY0eURjaUNwYVNmYkR0RzRLa1A=");

        System.out.println(password + " " + salt);
    }
}
