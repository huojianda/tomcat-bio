package com.htomcat.bio;

import com.htomcat.http.HRequest;
import com.htomcat.http.HResponse;
import com.htomcat.http.HServlet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Tom on 2019/6/15.
 */
public class Tomcat {
    private int port = 8080;
    private ServerSocket server;
    private Map<String,HServlet> servletMapping = new HashMap<String,HServlet>();

    private Properties webxml = new Properties();
    //J2EE标准
    //Servlet
    //Request
    //Response

    private void init(){

        //加载web.xml文件,同时初始化 ServletMapping对象
        try{
            String WEB_INF = this.getClass().getResource("/").getPath();
            FileInputStream fis = new FileInputStream(WEB_INF + "web.properties");

            webxml.load(fis);

            for (Object k : webxml.keySet()) {

                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$", "");
                    String url = webxml.getProperty(key);
                    String className = webxml.getProperty(servletName + ".className");
                    //单实例，多线程
                    HServlet obj = (HServlet)Class.forName(className).newInstance();
                    servletMapping.put(url, obj);
                }

            }


        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void start(){

        //1、加载配置文件，初始化ServeltMapping
        init();

        try {
            server = new ServerSocket(this.port);

            System.out.println("GP Tomcat 已启动，监听的端口是：" + this.port);

            //2、等待用户请求,用一个死循环来等待用户请求
            while (true) {
                Socket client = server.accept();
                //4、HTTP请求，发送的数据就是字符串，有规律的字符串（HTTP协议）
                process(client);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Socket client) throws Exception {

        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        //7、Request(InputStrean)/Response(OutputStrean)
        HRequest request = new HRequest(is);
        HResponse response = new HResponse(os);

        //5、从协议内容中拿到URL，把相应的Servlet用反射进行实例化
        String url = request.getUrl();

        if(servletMapping.containsKey(url)){
            //6、调用实例化对象的service()方法，执行具体的逻辑doGet/doPost方法
            servletMapping.get(url).service(request,response);
        }else{
            response.write("404 - Not Found");
        }


        os.flush();
        os.close();

        is.close();
        client.close();
    }

    public static void main(String[] args) {
        new Tomcat().start();
    }

}
