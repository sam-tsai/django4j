package org.django4j.app.tapestry;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TTT {

	public void doAct(@Q("s") String a) {
		System.out.println(a);
	}

	/**
	 * @param args
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		Class cls = TTT.class;
		TTT t = (TTT) cls.newInstance();
		Method m = cls.getMethod("doAct",String.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("s", "aaa");

		String[] paramStrVals = new String[m.getParameterTypes().length];
		Annotation[][] allParamsAnns = m.getParameterAnnotations();
		if (m.getParameterTypes().length == allParamsAnns.length) {
			int i = 0;
			for (Annotation[] paramAnns : allParamsAnns) {
				for (Annotation paramAnn : paramAnns) {
					if (paramAnn.annotationType().equals(Q.class)) {
						String pName = ((Q) paramAnn).value();
						paramStrVals[i] = map.get(pName);
					}
				}
				i++;
			}
		}

		m.invoke(t, paramStrVals);
	}
}
