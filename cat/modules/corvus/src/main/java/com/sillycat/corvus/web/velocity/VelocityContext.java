package com.sillycat.corvus.web.velocity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.velocity.context.AbstractContext;

public class VelocityContext extends AbstractContext implements Cloneable {

	private static final long serialVersionUID = -6825676035752112331L;

	Map<String,Object> context = new HashMap<String,Object>();

	public boolean internalContainsKey(Object key) {
		return context.containsKey(key);
	}

	public Object internalGet(String key) {
		return context.get(key);
	}

	public Object[] internalGetKeys() {
		List<String> keyList = new ArrayList<String>();
		for (Iterator<String> itr = context.keySet().iterator(); itr.hasNext();) {
			keyList.add(itr.next());
		}
		return keyList.toArray();
	}

	public Object internalPut(String key, Object value) {
		context.put(key, value);
		return value;
	}

	public Object internalRemove(Object key) {
		if (context.containsKey(key)) {
			context.remove(key);
		}
		return null;
	}

	public Object clone() {
		VelocityContext clone = null;
		try {
			clone = (VelocityContext) super.clone();
			clone.context = new HashMap<String,Object>(context);
		} catch (CloneNotSupportedException ignored) {
		}
		return clone;
	}

	public void clear() {
		context.clear();
	}
	
}
