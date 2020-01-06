package com.htomcat.bio.servlet;

import com.htomcat.bio.http.HRequest;
import com.htomcat.bio.http.HResponse;
import com.htomcat.bio.http.HServlet;

import java.io.IOException;

public class ServletDemo2 extends HServlet {
    @Override
    public void doGet(HRequest request, HResponse response) throws IOException {
        this.doPost(request,response);
    }

    @Override
    public void doPost(HRequest request, HResponse response) throws IOException {
        response.write("this is my tomcat servlet demo2");
    }
}
