package org.django4j.example.hello.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
	public Object get() {
		Map<String, Object> rt = new HashMap<String, Object>();
		rt.put("hello", "hello django4j\nÄãºÃ");
		List<String> lsUser = new ArrayList<String>();
		lsUser.add("jack");
		lsUser.add("rose");
		rt.put("userList", lsUser);
		return rt;
	}

	public Object hello() {
		Map<String, Object> rt = new HashMap<String, Object>();
		rt.put("hello", "hello django4j\nÄãºÃ");
		List<String> lsUser = new ArrayList<String>();
		lsUser.add("jack");
		lsUser.add("rose");
		rt.put("userList", lsUser);
		return rt;
	}

	public Object hello(String name) {
		return name + "\nÄãºÃ";
	}

	public Object helloextend() {
		Map<String, Object> rt = new HashMap<String, Object>();
		rt.put("block1Value", "this is block1");
		rt.put("block2Value", "this is block2");
		return rt;
	}

}
