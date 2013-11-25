package org.jbpm.ee.services.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.mvel2.MVEL;

public class JsonUtil {

	public static List<Map<String, Object>> readJsonObject(InputStream is) throws IOException {
		String content = IOUtils.toString(is);
		return (List<Map<String, Object>>)MVEL.eval(content, new HashMap<String, Object>());
	}
}
