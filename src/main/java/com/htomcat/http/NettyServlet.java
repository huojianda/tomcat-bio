package com.htomcat.http;

public abstract class NettyServlet {

    public void service(NettyRequest request,NettyResponse response) throws Exception{
        if("GET".equalsIgnoreCase(request.getMethod())){
            doGet(request, response);
        }else{
            doPost(request, response);
        }
    }

    protected abstract void doPost(NettyRequest request, NettyResponse response);

    protected abstract void doGet(NettyRequest request, NettyResponse response);
}
