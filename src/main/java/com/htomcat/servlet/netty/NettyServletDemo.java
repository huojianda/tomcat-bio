package com.htomcat.servlet.netty;

import com.htomcat.http.NettyRequest;
import com.htomcat.http.NettyResponse;
import com.htomcat.http.NettyServlet;

public class NettyServletDemo extends NettyServlet {
    @Override
    protected void doPost(NettyRequest request, NettyResponse response) {
        response.write("This is netty tomcat servlet");
    }

    @Override
    protected void doGet(NettyRequest request, NettyResponse response) {
        this.doPost(request,response);
    }
}
