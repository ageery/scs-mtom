package org.example;

import com.sun.xml.internal.ws.api.message.Packet;
import com.sun.xml.internal.ws.api.pipe.Codec;
import com.sun.xml.internal.ws.api.pipe.ContentType;
import com.sun.xml.internal.ws.encoding.ContentTypeImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScsMTOMCodec implements Codec {

    private static final String START_INFO_REGEX_STRING =
            "start-info=\"application\\/soap\\+xml;action=\\\\\"([A-Za-z0-9_]+)\\\\\"\"";
    private static final Pattern START_INFO_REGEX = Pattern.compile(START_INFO_REGEX_STRING);

    private Codec handler;

    public ScsMTOMCodec(Codec handler) {
        this.handler = handler;
    }

    @Override
    public String getMimeType() {
        return handler.getMimeType();
    }

    @Override
    public ContentType getStaticContentType(Packet packet) {
        ContentType ct = handler.getStaticContentType(packet);
        String contentType = ct.getContentType();
        Matcher matcher = START_INFO_REGEX.matcher(contentType);
        if (matcher.find()) {
            String action = matcher.group(1);
            contentType = contentType.replaceAll(START_INFO_REGEX_STRING, "");
            contentType = contentType + "start-info=\"application/soap+xml\";action=\"" + action + "\"";
            ct = new ContentTypeImpl(contentType);
        }
        return ct;
    }

    @Override
    public ContentType encode(Packet packet, OutputStream out) throws IOException {
        return handler.encode(packet, out);
    }

    @Override
    public ContentType encode(Packet packet, WritableByteChannel buffer) {
        return handler.encode(packet, buffer);
    }

    @Override
    public Codec copy() {
        return handler.copy();
    }

    @Override
    public void decode(InputStream in, String contentType, Packet response) throws IOException {
        handler.decode(in, contentType, response);
    }

    @Override
    public void decode(ReadableByteChannel in, String contentType, Packet response) {
        handler.decode(in, contentType, response);
    }

}
