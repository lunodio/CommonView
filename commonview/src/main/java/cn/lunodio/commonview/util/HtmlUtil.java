package cn.lunodio.commonview.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
    public static String getFirstImg(String html) {
        if (TextUtil.isEmpty(html)) return null;
        Pattern p_image;
        Matcher m_image;
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(html);
        if (m_image.find()) {
            //得到<img />数据
            String img = m_image.group();
            //匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            if (m.find()) {
                return m.group(1);
            } else {
                //无src
                return null;
            }
        } else {
            //无图片
            return null;
        }
    }

    public static List<String> getUrls(String html) {
        if (TextUtil.isEmpty(html)) return null;
        List<String> pics = new ArrayList<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(html);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }

    public static Set<String> getImgUrl(String html) {
        if (TextUtil.isEmpty(html)) return null;
        Set<String> pics = new HashSet<>();
        String img = "";
        Pattern p_image;
        Matcher m_image;
        //     String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(html);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }


    /**
     * 过滤HTML标签输出文本
     *
     * @param html 原字符串
     * @return 过滤后字符串
     */
    public static String html2Text(String html) {
        if (TextUtil.isEmpty(html)) {
            return "";
        }

        // 含html标签的字符串
        String htmlStr = html.trim();
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_space;
        Matcher m_space;
        Pattern p_escape;
        Matcher m_escape;

        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";

            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";

            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";

            // 定义空格回车换行符
            String regEx_space = "\\s*|\t|\r|\n";

            // 定义转义字符
            String regEx_escape = "&.{2,6}?;";

            // 过滤script标签
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");

            // 过滤style标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");

            // 过滤html标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");

            // 过滤空格回车标签
            p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
            m_space = p_space.matcher(htmlStr);
            htmlStr = m_space.replaceAll("");

            // 过滤转义字符
            p_escape = Pattern.compile(regEx_escape, Pattern.CASE_INSENSITIVE);
            m_escape = p_escape.matcher(htmlStr);
            htmlStr = m_escape.replaceAll("");

            textStr = htmlStr;

        } catch (Exception ignored) {
        }

        // 返回文本字符串
        return textStr;
    }
}
