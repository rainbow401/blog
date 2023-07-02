package com.blog.server;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownSummaryTest {

    public static String extractContent(String markdownText) {
        String regex = "^(?!#).{1,}";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(markdownText);

        if (matcher.find()) {
            return matcher.group().trim();
        }

        return null;
    }

    public static void main(String[] args) {
        String markdownText = "# 15. IOC：刷新容器-循环依赖与解决方案\n" +
                "\n" +
                "【本篇独立于IOC容器刷新步骤的全过程，由于此部分理解比较困难，小伙伴在阅读时一定要仔细、速度放慢，有必要的话一定要配合IDE和自己编写的测试代码，实际Debug走一遍来看】\n" +
                "\n" +
                "## 1. 编写测试代码\n" +
                "\n" +
                "为演示循环依赖的效果，咱来编写两个组件，模拟人与猫的关系：人养猫，猫依赖人。";

        String content = extractContent(markdownText);
        if (content != null) {
            System.out.println("Content: " + content);
        } else {
            System.out.println("No content found.");
        }
    }
}
