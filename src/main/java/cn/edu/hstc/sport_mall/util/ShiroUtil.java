package cn.edu.hstc.sport_mall.util;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * 工具类-生成随机序列 & 加密
 */
public class ShiroUtil {

    public static String createRandomSequence(){
        StringBuffer salt = new StringBuffer();
        String[] word = {"0","1","2","3","4","5","6","7","8","9",
                "a","b","c","d","e","f","g",
                "h","i","j","k","l","m","n",
                "o","p","q",    "r","s","t",
                "u","v","w",    "x","y","z",
                "A","B","C","D","E","F","G",
                "H","I","J","K","L","M","N",
                "O","P","Q",    "R","S","T",
                "U","V","W",    "X","Y","Z"
        };

        for (int i = 0; i < 20; i++){
            int randomNumber = RandomUtils.nextInt(62);
            salt.append(word[randomNumber]);
        }

        return salt.toString();
    }

    public static String encrypt(String plainText, String salt){
        String hashAlgorithmName = "SHA-1";    //使用的散列算法
        int hashIterations = 1024;

        Object result = new SimpleHash(hashAlgorithmName, plainText, salt, hashIterations);
        return result.toString();

    }
}
