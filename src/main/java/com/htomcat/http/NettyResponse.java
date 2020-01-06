package com.htomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class NettyResponse {


    private ChannelHandlerContext ctx;

    private HttpRequest req;

    public NettyResponse(ChannelHandlerContext ctx, HttpRequest req) {
        this.ctx = ctx;
        this.req = req;
    }

    public void write(String out){
        try{
            if(Objects.equals(out,null)|| out.length() == 0){
                return;
            }
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                    //设置http版本
                    HttpVersion.HTTP_1_1,
                    //响应状态
                    HttpResponseStatus.OK,
                    //输出编码
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8"))
            );
            response.headers().set("Content-Type","text/html;");
            //长连接设置
//            if(HttpUtil.isKeepAlive(response)){
//                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//            }
            ctx.write(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
