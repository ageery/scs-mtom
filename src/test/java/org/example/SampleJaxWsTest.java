package org.example;

import net.exchangenetwork.schema.sharedcromerr._2.Authenticate;
import net.exchangenetwork.schema.sharedcromerr._2.AuthenticateResponse;
import net.exchangenetwork.wsdl.sharedcromerr.signandcor._2.SignatureAndCorService2;
import net.exchangenetwork.wsdl.sharedcromerr.signandcor._2.SignatureAndCorService2Service;
import org.junit.jupiter.api.Test;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import java.net.URI;
import java.util.Collections;

public class SampleJaxWsTest {

    @Test
    public void testClient() throws Exception {
        // Initialize client
        URI wsdl = SampleJaxWsTest.class.getResource("/SignatureAndCorService2.wsdl").toURI();
        SignatureAndCorService2Service service = new SignatureAndCorService2Service(wsdl.toURL());
        SignatureAndCorService2 clientMtom = service.getSignatureAndCorService2Port();
        configureClient(clientMtom, "https://encromerrdev.epacdxnode.net/shared-cromerr-ws/services/SignatureAndCorService2", true);

        // Authenticate
        Authenticate authRequest = new Authenticate();
        authRequest.setAdminId("testing");
        authRequest.setCredential("testing");
        AuthenticateResponse authResponse = clientMtom.authenticate(authRequest);
        System.out.println(authResponse.getSecurityToken());
    }

    protected static void configureClient(Object service, String address, Boolean enableMtom) {
        BindingProvider bp = (BindingProvider) service;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, address);
        binding.setHandlerChain(Collections.singletonList(new ActionOutHandler()));

        if (enableMtom) {
            binding.setMTOMEnabled(true);
        }
    }

}
