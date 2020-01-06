package com.htomcat.bio.http;

import java.io.InputStream;

public class HRequest {

    private String method;
    private String url;

    public HRequest(InputStream in){
        try{
            /**
             * 获取http协议内容
             */
            String content ="";
            byte[] buff = new byte[1024];
            int len = 0;
            if((len = in.read(buff))>0){
                content = new String(buff,0,len);
            }
            String line = content.split("\\n")[0];
            String [] arr = line.split("\\s");

            this.method = arr[0];
            this.url = arr[1].split("\\?")[0];

            System.out.println(content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public String getMethod() {
        return method;
    }
    public String getUrl() {
        return url;
    }
}
