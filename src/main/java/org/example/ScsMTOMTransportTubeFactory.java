package org.example;

import com.sun.xml.internal.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.internal.ws.api.pipe.TransportTubeFactory;
import com.sun.xml.internal.ws.api.pipe.Tube;
import com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe;

/**
 * Inspired by https://stackoverflow.com/questions/31288328/rewriting-content-type-header-and-mime-boundaries-with-jax-ws-and-mtom-xop
 *
 * Enabled in the resources/META-INF/services/com.sun.xml.internal.ws.api.pipe.TransportTubeFactory file
 *
 * @see ScsMTOMCodec
 */
public class ScsMTOMTransportTubeFactory extends TransportTubeFactory {

    @Override
    public Tube doCreate(ClientTubeAssemblerContext context) {
        return createDefault(context);
    }

    @Override
    protected Tube createHttpTransport(ClientTubeAssemblerContext context) {
        return new HttpTransportPipe(new ScsMTOMCodec(context.getCodec()), context.getBinding());
    }

}
