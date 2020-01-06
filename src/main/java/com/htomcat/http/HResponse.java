package com.htomcat.http;

import java.io.IOException;
import java.io.OutputStream;

public class HResponse {

    private OutputStream out;

    public HResponse(OutputStream out){
        this.out = out;
    }

    public void write(String s) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\n")
                .append("Content-type: text/html;\n")
                .append("\r\n")
                .append(s);
        out.write(sb.toString().getBytes());
    }
}
