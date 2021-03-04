package org.openmrs.module.datamigration.util;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RestUtil;

import java.util.LinkedHashMap;

public class CustomRestUtil extends RestUtil {
	
	/**
	 * Wraps the exception message as a SimpleObject to be sent to client
	 * 
	 * @param ex
	 * @param reason
	 * @return
	 */
	public static SimpleObject wrapErrorResponse(Exception ex, String reason) {
		LinkedHashMap map = new LinkedHashMap();
		if (reason != null && !reason.isEmpty()) {
			map.put("message", reason + " [" + reason + "]");
		}
		//        else {
		//            map.put("message", "[" + ex.getMessage() + "]");
		//        }
		//        StackTraceElement[] steElements = ex.getStackTrace();
		//        if (steElements.length > 0) {
		//            StackTraceElement ste = ex.getStackTrace()[0];
		//            map.put("code", ste.getClassName() + ":" + ste.getLineNumber());
		//            map.put("detail", ExceptionUtils.getStackTrace(ex));
		//        } else {
		//            map.put("code", "");
		//            map.put("detail", "");
		//        }
		
		return new SimpleObject().add("error", map);
	}
}
