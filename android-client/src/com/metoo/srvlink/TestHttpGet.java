///**
// * 
// */
package com.metoo.srvlink;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.metoo.common.AppLog;

public class TestHttpGet {
    public void executeHttpGet() throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://google.com/"));
            
            AppLog.W("Trying to connect..");
            HttpResponse response = client.execute(request);
            
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            AppLog.W("Got content");
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            
            String page = sb.toString();
            AppLog.W("CONTENT: " + page);
            System.out.println(page);
            }
        finally 
        {
            if (in != null) {
                try {
                    in.close();
                    } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}