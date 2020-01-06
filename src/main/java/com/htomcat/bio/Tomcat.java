package com.htomcat.bio;

import com.htomcat.bio.http.HRequest;
import com.htomcat.bio.http.HResponse;
import com.htomcat.bio.http.HServlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Tomcat {

    private int port = 8080;

    private ServerSocket server;
    /**
     * 容器  handleMapping
     */
    private Map<String,HServlet> servletMapping = new HashMap<>();

    private Properties webXml = new Properties();

    private void init(){
        try{
            String web_inf = this.getClass().getResource("/").getPath();

            FileInputStream fis = new FileInputStream(web_inf + "web.properties");

            webXml.load(fis);
            for (Object k : webXml.keySet()) {

                String key = k.toString();
                if(key.endsWith(".url")){
                    String servletName = key.replaceAll("\\.url$","");
                    String url = webXml.getProperty(key);
                    String className = webXml.getProperty(servletName + ".className");
                    HServlet obj = (HServlet) Class.forName(className).newInstance();

                    servletMapping.put(url,obj);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(){

        //加载配置，初始化mapping
        init();

        try{

            server = new ServerSocket(this.port);

            System.out.println("My Tomcat 启动， 监听端口号:"+this.port);

            while (true){
                Socket client = server.accept();
                //处理http请求
                process(client);
            }

        }catch (Exception e){

            e.printStackTrace();
        }

    }

    private void process(Socket client) throws IOException {

        InputStream is = client.getInputStream();
        OutputStream os = client.getOutputStream();

        HRequest request = new HRequest(is);
        HResponse response = new HResponse(os);

        String url = request.getUrl();
        if(servletMapping.containsKey(url)){
            servletMapping.get(url).service(request,response);
        }else{
            response.write("404 - Not Found");
        }

        os.flush();
        os.close();

        is.close();
        client.close();
    }

    public static void main(String [] args){
        new Tomcat().start();
    }
}
