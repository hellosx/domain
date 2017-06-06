package com.domain.process;

import com.alibaba.fastjson.JSON;
import com.domain.entity.Domain;
import com.domain.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sunxin(hysx8@sina.com) on 2017/6/6.
 */
public class DomainSearch {

    private final static String ALIYUN_URL_PREFIX = "https://checkapi.aliyun.com/check/checkdomain?callback=jQuery1111046676790810234237_1496729548476&domain=";

    private final static String ALIYUN_URL_SUFFIX = "&token=check-web-hichina-com%3Acv4zcy4201wa0fgld78uwf0iqfhcn43g&_=1496729548487";

    private final static String DOMAIN_SUFFIX = ".com";

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            String random = RandomString(4);
            String url = ALIYUN_URL_PREFIX + random + DOMAIN_SUFFIX + ALIYUN_URL_SUFFIX;
            String result = HttpUtil.get(url);
            String str = extractMessageByRegular(result);
            Domain domain = JSON.parseObject(str, Domain.class);
            if (domain.getAvail() == 1){
                System.out.println(str);
            }
        }
    }

    public static String extractMessageByRegular(String msg){

        List<String> list=new ArrayList<String>();
        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
        Matcher m = p.matcher(msg);
        while(m.find()){
            String str = m.group();
            list.add(str.substring(1, m.group().length()-1));
        }
        return list.get(0);
    }


    /** 产生一个随机的字符串*/
    public static String RandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = new Random().nextInt(26);
            buf.append(str.charAt(number));
        }
        return buf.toString().toLowerCase();
    }


}
