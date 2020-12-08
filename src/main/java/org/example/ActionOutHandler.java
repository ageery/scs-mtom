package org.example;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JAX-WS outbound handler that rewrites the SOAP action inside the Content-Type header to work with MTOM.
 * Rewrites: ...start-info="application/soap+xml; action=\"Authenticate\""
 * To: ...start-info="application/soap+xml"; action="Authenticate"
 */
public class ActionOutHandler implements SOAPHandler<SOAPMessageContext> {
	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		String contentType = (String) context.get("Content-Type");
		if (contentType == null) {
			throw new RuntimeException("SOAPMessageContext#get(\"Content-Type\") returned null.");
		}
		Matcher matcher = Pattern.compile("start-info=\"application\\/soap\\+xml; action=\\\\\"([A-Za-z0-9_]+)\\\\\"\"").matcher(contentType);
		if (matcher.find()) {
			String action =  matcher.group(1);
			contentType = contentType.replaceAll("start-info=\"application\\/soap\\+xml; action=\\\\\"([A-Za-z0-9_]+)\\\\\"\"", "");
			contentType = contentType + "start-info=\"application/soap+xml\"; action=\"" + action + "\"";
			context.put("Content-Type", contentType);
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public void close(MessageContext context) {

	}
}
