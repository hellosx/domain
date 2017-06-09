package com.domain.process;import com.domain.dao.TableOperator;import com.domain.util.HttpUtil;import com.domain.util.StringUtil;import com.sun.jersey.spi.resource.Singleton;import org.apache.log4j.Logger;import org.springframework.context.support.ClassPathXmlApplicationContext;import org.springframework.stereotype.Controller;import redis.clients.jedis.Jedis;import javax.ws.rs.POST;import javax.ws.rs.Path;/** * Created by sunxin(hysx8@sina.com) on 2017/6/6. */@Controller@Singleton@Path("web")public class DefaultProcess extends Thread{    private static Logger logger = Logger.getLogger("log");    private final static String ALIYUN_URL_PREFIX = "https://checkapi.aliyun.com/check/checkdomain?callback=jQuery1111046676790810234237_1496729548476&domain=";    private final static String ALIYUN_URL_SUFFIX = "&token=check-web-hichina-com%3Acv4zcy4201wa0fgld78uwf0iqfhcn43g&_=1496729548487";    private final static String DOMAIN_SUFFIX = ".com";    private final static String WANWANG = "http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=";    private final static int DOMAIN_LENGTH = 5;    private final static int INSERT_TO_DB = 100;    @POST    @Path("process")    public void process(){        Jedis jedis = new Jedis("localhost");        //计数判断是否存储到数据库的阈值        int count = 0;        while (true){            String domainResult = "211";            //产生一个随机位数字符串(需要查询的域名)            String random = StringUtil.randomString(DOMAIN_LENGTH);            //查找到直到缓存内不存在的域名            while (StringUtil.isNotBlank(jedis.get(random))){                random = StringUtil.randomString(DOMAIN_LENGTH);            }            //查询过的域名放到redis            jedis.set(random, random);            //GET调用接口判断是否被注册            String url = WANWANG + random + DOMAIN_SUFFIX;            String result = HttpUtil.get(url);            String[] array = result.split("original");            String status = array[1].substring(1, 4);            //未注册            if ("210".equals(status)){                count++;                if (count >= 100){                    insert();                }                jedis.set("count1", String.valueOf(count));                logger.info(random + DOMAIN_SUFFIX);            }            //sleep三秒重复调用            try {                Thread.sleep(3000L);            }            catch (Throwable e){                e.printStackTrace();            }        }    }    @POST    @Path("test")    public void insert(){        try {            ClassPathXmlApplicationContext consumerContext= new ClassPathXmlApplicationContext("applicationContext.xml");		    //获取bean            TableOperator tableOperator = (TableOperator) consumerContext.getBean("springTableOperatorBean");            tableOperator.createTable();        }        catch (Exception e){            e.printStackTrace();        }    }}