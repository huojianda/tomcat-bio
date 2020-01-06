package com.htomcat.servlet.bio;

import com.htomcat.http.HRequest;
import com.htomcat.http.HResponse;
import com.htomcat.http.HServlet;

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
