package com.baidu.ueditor.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class QStringUtil
{
    public static boolean isEmpty(String s)
    {
        return (s == null) || (s.trim().equals(""));
    }

    public static boolean notEmpty(String s)
    {
        return (s != null) && (!s.trim().equals(""));
    }

    public static boolean anyoneEmpty(String[] ss)
    {
        if (ss.length == 0) return true;

        String[] arrayOfString = ss; int j = ss.length; for (int i = 0; i < j; i++) { String s = arrayOfString[i];
        if (isEmpty(s)) return true;
    }

        return false;
    }

    public static boolean allNotEmpty(String[] ss)
    {
        if (ss.length == 0) return false;

        String[] arrayOfString = ss; int j = ss.length; for (int i = 0; i < j; i++) { String s = arrayOfString[i];
        if (isEmpty(s)) return false;
    }

        return true;
    }

    public static boolean isIn(String s, String[] ss)
    {
        if (ss != null) {
            for (String str : ss) {
                if (str.equals(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean notIn(String s, String[] ss)
    {
        return !isIn(s, ss);
    }

    public static String toStr(String s)
    {
        return s == null ? "" : s;
    }

    public static int toInt(String s)
    {
        return isEmpty(s) ? 0 : Integer.parseInt(s);
    }

    public static Boolean toBoolean(String s)
    {
        if (s != null) {
            s = s.toLowerCase();

            if (isIn(s, new String[] { "yes", "t", "1" })) {
                return Boolean.valueOf(true);
            }
            if (isIn(s, new String[] { "no", "f", "0" })) {
                return Boolean.valueOf(false);
            }
        }

        return null;
    }

    public static String toHtml(String str)
    {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }
        return str.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"")
                .replaceAll("&nbsp;", " ");
    }

    public static String fromHtml(String str)
    {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }
        StringBuffer buf = new StringBuffer();
        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (ch == '<')
                buf.append("&lt;");
            else if (ch == '>')
                buf.append("&gt;");
            else if (ch == '&')
                buf.append("&amp;");
            else if (ch == '"')
                buf.append("&quot;");
            else if (ch == ' ')
                buf.append("&nbsp;");
            else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    public static String fromException(Exception exception)
    {
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);

            exception.printStackTrace(pw);

            pw.close();
            sw.close();

            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isEmail(String s)
    {
        String pattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        return Pattern.compile(pattern).matcher(s).find();
    }

    public static boolean isNLengthNumber(String s, int n)
    {
        String pattern = "^\\d{" + n + "}$";
        return Pattern.compile(pattern).matcher(s).find();
    }

    public static String firstUpper(String s)
    {
        StringBuilder sb = new StringBuilder(s);

        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

        return sb.toString();
    }

    public static String firstLower(String s)
    {
        StringBuilder sb = new StringBuilder(s);

        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

        return sb.toString();
    }

    public static List<String> splitToList(String s, String split)
    {
        if (s == null) return null;

        return new ArrayList(Arrays.asList(s.split(split)));
    }

    public static String splitAndReturnLastString(String str, String split)
    {
        if (isEmpty(str)) {
            return "";
        }
        String[] ss = str.split(split);
        return ss[(ss.length - 1)];
    }

    public static List<String> tuoFeng(String s)
    {
        List res = new ArrayList();

        if (notEmpty(s)) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                if ((i != 0) && (Character.isUpperCase(s.charAt(i)))) {
                    res.add(sb.toString());
                    sb.delete(0, sb.length());
                }

                sb.append(s.charAt(i));

                if (i + 1 == s.length()) {
                    res.add(sb.toString());
                }
            }
        }

        return res;
    }

    public static String getClassNameFromTableName(String tableName)
    {
        StringBuilder sb = new StringBuilder();
        if (notEmpty(tableName)) {
            String[] ss = tableName.split("_");
            for (int i = 1; i < ss.length; i++) {
                sb.append(firstUpper(ss[i].toLowerCase()));
            }
        }

        return sb.toString();
    }

    public static String getTableNameFromClazzName(String clazzName)
    {
        StringBuilder sb = new StringBuilder("t");

        List<String> names = tuoFeng(clazzName);
        for (String s : names) {
            sb.append("_" + s.toLowerCase());
        }

        return sb.toString();
    }
}