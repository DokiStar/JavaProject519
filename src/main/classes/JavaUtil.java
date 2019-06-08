package main.classes;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class JavaUtil {
    // �ж����룬�������"t"��"true"��"y"��"yes"�򷵻�true�������ִ�Сд
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

    // �����ַ���תƴ��
    public static String getPinyin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE); // ȫ��д
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // ������
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // ��v���樹
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

    // ��ȡ��������ĸ
    public static String firstLetter(String ename) {
        return getPinyin(ename).substring(0, 1);
    }

    // �ж��Ƿ�Ϊ����
    public static boolean isNumeric(String str) {
        int i = str.length();
        while (i-- > 0) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // �ж��Ƿ�Ϊ��λ����
    public static boolean isValidNumber(int num) {
        return (num >= 1000 && num <= 9999);
    }

    // �ӹ�������ȡ���ֲ��֣�ʹ��������ʽ
    public static String extractNumber(String inputString) {
        inputString = inputString.replaceAll("[^(0-9)]", "");
        return inputString;
    }

    // �ӹ�������ȡ��ĸ����
    public static String extractAlphabet(String inputString) {
        inputString = inputString.replaceAll("[^(A-Za-z)]", "");
        return inputString;
    }

}
