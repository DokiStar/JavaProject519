package main.classes;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class JavaUtil {
    // 判断输入，如果输入"t"、"true"、"y"或"yes"则返回true，不区分大小写
    public static boolean TOF(String str) {
        switch (str) {
        case "t":
            return true;
        case "true":
            return true;
        case "y":
            return true;
        case "yes":
            return true;
        default:
            return false;
        }
    }

    // 汉字字符串转拼音
    public static String getPinyin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE); // 全大写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 无声调
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // 用v代替ü
        char[] charArray = inputString.trim().toCharArray();
        String outputString = "";
        for (int i = 0; i < charArray.length; i++) {
            try {
                if (Character.toString(charArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp;
                    temp = PinyinHelper.toHanyuPinyinStringArray(charArray[i], format);
                    outputString += temp[0];
                } else {
                    outputString += Character.toString(charArray[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return outputString.toUpperCase();
    }

    // 获取姓名首字母
    public static String firstLetter(String ename) {
        return getPinyin(ename).substring(0, 1);
    }

    // 判断是否为数字
    public static boolean isNumeric(String str) {
        int i = str.length();
        while (i-- > 0) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // 判断是否为四位整数
    public static boolean isValidNumber(int num) {
        return (num >= 1000 && num <= 9999);
    }

    // 从工号中提取数字部分，使用正则表达式
    public static String extractNumber(String inputString) {
        inputString = inputString.replaceAll("[^(0-9)]", "");
        return inputString;
    }

    // 从工号中提取字母部分
    public static String extractAlphabet(String inputString) {
        inputString = inputString.replaceAll("[^(A-Za-z)]", "");
        return inputString;
    }

}
