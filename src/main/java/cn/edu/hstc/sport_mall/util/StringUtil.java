package cn.edu.hstc.sport_mall.util;

/**
 * 工具类-关键字高亮显示
 */
public class StringUtil {
    private static String PRE = "<span style=\"color: red\">";
    private static String POST = "</span>";
    private static final int PRELENGTH = PRE.length();
    private static final int POSTLENGTH = POST.length();

    /*
     * 高亮显示
     * 对于给定的字符串str,在该字符串中所有的keyword前面加上特定字符串进行修饰
     */
    public static String decorate(String str, String keyword, String color){
        StringBuilder sb = new StringBuilder(str);

        if (color.equals("red"))
            PRE = "<span style=\"color: red\">";
        else
            PRE = "<span style=\"color: #33CC00;\">";

        for (int i = 0; i < sb.length(); ){
            if (!sb.substring(i, sb.length()).contains(keyword))
                break;

            sb.insert(sb.indexOf(keyword, i), PRE).insert(sb.indexOf(keyword, i) + keyword.length(), POST);
            i += PRELENGTH + keyword.length() + POSTLENGTH;
        }
        return sb.toString();
    }
}
