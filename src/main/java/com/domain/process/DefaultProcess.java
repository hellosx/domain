package com.domain.process;import com.domain.util.HttpUtil;import com.domain.util.StringUtil;import com.sun.jersey.spi.resource.Singleton;import org.apache.log4j.Logger;import org.springframework.stereotype.Controller;import redis.clients.jedis.Jedis;import javax.ws.rs.POST;import javax.ws.rs.Path;import java.util.ArrayList;import java.util.List;import java.util.Random;import java.util.regex.Matcher;import java.util.regex.Pattern;/** * Created by sunxin(hysx8@sina.com) on 2017/6/6. */@Controller@Singleton@Path("web")public class DefaultProcess extends Thread{    private static Logger logger = Logger.getLogger("log");    private final static String ALIYUN_URL_PREFIX = "https://checkapi.aliyun.com/check/checkdomain?callback=jQuery1111046676790810234237_1496729548476&domain=";    private final static String ALIYUN_URL_SUFFIX = "&token=check-web-hichina-com%3Acv4zcy4201wa0fgld78uwf0iqfhcn43g&_=1496729548487";    private final static String DOMAIN_SUFFIX = ".com";    private final static String WANWANG = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=";    private final static int DOMAIN_LENGTH = 4;//    public static void main(String[] args) {////        Jedis jedis = new Jedis("localhost");//        jedis.set("aa", "cc");//        System.out.println("Connection to server sucessfully");//        //查看服务是否运行//        System.out.println("Server is running: "+jedis.ping());//////        String domainResult = "211";////        do {//            String random = RandomString(1);//            String url = WANWANG + random + DOMAIN_SUFFIX;//            String result = HttpUtil.get(url);//            System.out.println(result);////            String[] array = result.split("original");////            String status = array[1].substring(1, 4);////            if ("210".equals(status)){//                domainResult = "210";//                logger.info(random + DOMAIN_SUFFIX);//            }//            try {//                Thread.sleep(1000L);//            }//            catch (Throwable e){//                e.printStackTrace();//            }//        }//        while ("211".equals(domainResult));//        int domainResult;//        do//        {//            String random = RandomString(5);//            String url = ALIYUN_URL_PREFIX + random + DOMAIN_SUFFIX + ALIYUN_URL_SUFFIX;//            String result = HttpUtil.get(url);//            String str = extractMessageByRegular(result);//            Domain domain = JSON.parseObject(str, Domain.class);//            domainResult = domain.getAvail();//            if (domainResult == 1){//                logger.info(str);//            }//        } while(domainResult ==0);//    }    public static void main(String[] args) {        Jedis jedis = new Jedis("localhost");        System.out.println(jedis.get("111"));    }    @POST    @Path("process")    public void process(){        Jedis jedis = new Jedis("localhost");        String domainResult = "211";        String random = RandomString(DOMAIN_LENGTH);        System.out.println(jedis.get("111"));        System.out.println(jedis.get(random));        //查找到直到缓存内不存在的域名        while (StringUtil.isNotBlank(jedis.get(random))){            random = RandomString(DOMAIN_LENGTH);        }        jedis.set(random, random);        do {            String url = WANWANG + random + DOMAIN_SUFFIX;            String result = HttpUtil.get(url);            logger.info(result);            String[] array = result.split("original");            String status = array[1].substring(1, 4);            if ("210".equals(status)){                domainResult = "210";                logger.info(random + DOMAIN_SUFFIX);            }            try {                Thread.sleep(5000L);            }            catch (Throwable e){                e.printStackTrace();            }        }        while ("211".equals(domainResult));    }    public static String extractMessageByRegular(String msg) {        List<String> list = new ArrayList<String>();        Pattern p = Pattern.compile("(\\[[^\\]]*\\])");        Matcher m = p.matcher(msg);        while (m.find()) {            String str = m.group();            list.add(str.substring(1, m.group().length() - 1));        }        return list.get(0);    }    /**     * 产生一个随机的字符串     */    public static String RandomString(int length) {        String str = "abcdefghijklmnopqrstuvwxyz";        StringBuffer buf = new StringBuffer();        for (int i = 0; i < length; i++) {            int number = new Random().nextInt(26);            buf.append(str.charAt(number));        }        return buf.toString().toLowerCase();    }}