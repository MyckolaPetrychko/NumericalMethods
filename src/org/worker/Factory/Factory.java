package org.worker.Factory;

import org.worker.implement.Method1;
import org.worker.interfaces.Method;

public class Factory {

	public static Method getMethod(Method m) {
		Method nm = null;
		if (m instanceof Method1) {
			nm = new Method1();
		} else
			nm = m;
		return nm;
	}
}
