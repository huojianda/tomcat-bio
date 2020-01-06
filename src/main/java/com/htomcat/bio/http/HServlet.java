package com.htomcat.bio.http;

import java.io.IOException;

public abstract class HServlet {

    public void service(HRequest request,HResponse response) throws IOException {
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request,response);
        }else{
            doPost(request,response);
        }
    }

    public abstract void doGet(HRequest request, HResponse response) throws IOException;
    public abstract void doPost(HRequest request, HResponse response) throws IOException;

}
