/**
 * 
 */
package com.metoo.srvlink.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.metoo.common.androidutils.AndroidAppLog;
import com.metoo.common.androidutils.IAsyncTaskNotifyer;

import android.os.AsyncTask;

/**
 * @author Theurgist
 * Provides connection to server: sends requests and invokes subscriber's
 * routines on some result
 */
public class Connector
{
	/**
	 * Protocol,  for example "http"
	 */
	protected String protocol;
	/**
	 *  Server path, for example "google.com"
	 */
	protected String baseUri;
	
	protected boolean allowMultiple;
	protected BackgroundRequest onAir;
	
	public Connector(String protocol, String baseUri)
	{
		this.protocol = protocol;
		this.baseUri = baseUri;
		this.allowMultiple = false;
		this.onAir = null;
	}
	
	/*
	 * Request is only a shortened URI, without basis which is provided on object construction
	 */
	public boolean SendSimpleRequest(ServerRequest request, IAsyncTaskNotifyer<String, String, String> callback)
	{
		try {
			if ((onAir == null) || allowMultiple) {
				onAir = new BackgroundRequest(callback);
				onAir.execute(protocol + "://" + baseUri + request.CreateFormatedRequestString());
				return true;
			}
			else {
				return false;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean IsBusy() {
		return onAir != null;
	}


	/**
	 * Асинхронный отправитель запроса
	 */
	class BackgroundRequest extends AsyncTask<String, Void, String>
	{
	    protected IAsyncTaskNotifyer<String, String, String> callb;
	    protected BufferedReader in;
	    protected Integer bytesRead;
	    protected boolean inProgress;
	    protected boolean isErroneus;
	    
	    public BackgroundRequest(IAsyncTaskNotifyer<String, String, String> Callback) throws Exception
	    {
	    	if (Callback == null)
	    		throw new Exception("Callback is needed");
	    	
	    	callb = Callback;
	    	bytesRead = 0;
	    	inProgress = false;
	    	isErroneus = false;
	    }
	    
	    protected String doInBackground(String... urls) {
	        //int nArgs = urls.length;
	        String result = "";
	        inProgress = true;
	        isErroneus = false;
	        
	        try {        		
	            HttpClient client = new DefaultHttpClient();
	            client.getParams().setParameter(
	            	    "http.useragent",
	            	    "MeToo/0.1 (Android; U; ru-RU)"
	            	);
	            
	            HttpGet request = new HttpGet();
	            request.setURI(new URI(urls[0]));
	            
	            AndroidAppLog.W("Request '" + urls[0] + "' - trying to connect");
	            HttpResponse response = client.execute(request);
	            
	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	
	            AndroidAppLog.W("Request '" + urls[0] + "' - got content");
	            StringBuffer sb = new StringBuffer("");
	            String line = "";
	            String NL = System.getProperty("line.separator");
	            while ((line = in.readLine()) != null) 
	            {
	            	bytesRead += line.length();
	                sb.append(line + NL);
	            }
	            in.close();
	            
	            result = sb.toString();
	            AndroidAppLog.W("Request '" + urls[0] + "' - completed");
	        } 
	        catch (URISyntaxException e) {
	        	result = e.toString();
	        	isErroneus = true;
			} 
	        catch (ClientProtocolException e) {
	        	result = e.toString();
	        	isErroneus = true;
			}
	        catch (IOException e) {
	        	result = e.toString();
	        	isErroneus = true;
			}
	        finally 
	        {
	            if (in != null) {
	                try {
	                    in.close();
	                } 
	                catch (IOException e) {
	                	result = e.toString();
	                }
	            }
	            inProgress = false;

		    	if (onAir == this)
		    		onAir = null;
	        }
	        
	        return result;
	    }
	
	    protected void onProgressUpdate() {
	        //setProgressPercent(progress[0]);
	    	callb.onProgress("Bytes read: " + bytesRead);
	    }
	
	    protected void onPostExecute(String result) {
	    	if (isErroneus)
	    		callb.onError(result);
	    	else
	    		callb.onSuccess(result);
	    }
	    
	}

}
