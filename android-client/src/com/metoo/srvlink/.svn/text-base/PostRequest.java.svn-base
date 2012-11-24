package com.metoo.srvlink;

import java.util.ArrayList;
import java.util.List;

/**
 * Request wrapper
 * @author Theurgist
 *
 */
public class PostRequest {
	private List<StringPair> _args;
	private String _host;
	private String _preamb;
	private static Integer req_counter = 0;
	
	public PostRequest(String host) {
		_args = new ArrayList<StringPair>();
		_host = host;
		_preamb = "";
		
		AddParam("request_id", (++req_counter).toString());
	}

	public void SetPreambula(String preambula) {
		_preamb = preambula;
	}
	public void SetHost(String host) {
		_host = host;
	}
	public void AddParam(String key, String value) {
		_args.add(new StringPair(key, value));
	}
	
	public void ClearParams() {
		_args.clear();
	}
	
	public String CreateFormatedRequestString() {
		String res = _host + "/" +_preamb + "?";
		for(StringPair t : _args) {
			res += t._key + "=" + t._value + "&";
		}
		if (_args.size() > 0)
			res = res.substring(0, res.length() - 1);
		
		return res;
	}
	
}

class StringPair {
	public String _key, _value;
	public StringPair(String key, String value) {
		_key = key;
		_value = value;
	}
}
