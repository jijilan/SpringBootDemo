package com.czjsharebed.dlc.utils;

import com.czjsharebed.dlc.exception.MyException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本编辑器将Base64转换图片路径
 */
public class RegularUtil {
    /**
     * 将img标签中的src进行二次包装
     * @param content 内容
     * @return
     */
    public static String repairContent(String content,HttpServletRequest request,String path) throws MyException {
        String patternStr="<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern = Pattern.compile(patternStr,Pattern.COMMENTS);
        Matcher matcher = pattern.matcher(content);
        String result=content;
        while(matcher.find()) {
            String src = matcher.group(2);
            String base64Img=src.substring(23);
            String resultUrl= UploadFileUtil.base64Upload(base64Img,path);
            //将保存图片路径填充到src当中
            result=result.replaceAll(escapeExprSpecialWord(src),escapeExprSpecialWord(resultUrl));
            }
        return result;
    }
    /**
     * 转义正则特殊字符 （$()*+.[]?\^{},|）
     *
     * @param keyword
     * @return
     */
    public static String escapeExprSpecialWord(String keyword) {
        if (!StringUtils.isNotBlank(keyword)) {
            return keyword;
        }
        String[] fbsArr = { "\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|" };
        for (String key : fbsArr) {
            if (keyword.contains(key)) {
                keyword = keyword.replace(key, "\\" + key);
            }
        }
        return keyword;
    }
}
