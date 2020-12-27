package org.example;

import net.exchangenetwork.schema.sharedcromerr._2.Authenticate;
import net.exchangenetwork.wsdl.sharedcromerr.signandcor._2.SignatureAndCorService2;
import net.exchangenetwork.wsdl.sharedcromerr.signandcor._2.SignatureAndCorService2Service;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import java.net.URL;
import java.util.Collections;

public class SCSAuthOverMTOM {

    public static void main(String[] args) throws Exception {

        String adminId = args.length > 0 ? args[0] : "blah";
        String credentials = args.length > 1 ? args[1] : "blah";
        boolean enabledMtom = args.length <= 2 || Boolean.parseBoolean(args[2]);

        System.out.println("\n\n\n===============================================");
        System.out.println("Attempting to login to SCS using");
        System.out.println("\t- adminId: " + adminId);
        System.out.println("\t- credentials: " + credentials);
        System.out.println("\t- is MTOM enabled: " + enabledMtom);
        System.out.println("===============================================\n\n\n");

        String base = "https://encromerrdev.epacdxnode.net/shared-cromerr-ws/services";
        URL endPoint = new URL(base + "/SignatureAndCorService2");
        SignatureAndCorService2Service service = new SignatureAndCorService2Service(endPoint);
        SignatureAndCorService2 client = service.getSignatureAndCorService2Port();
        BindingProvider bp = (BindingProvider) client;
        SOAPBinding binding = (SOAPBinding) bp.getBinding();

        //binding.setHandlerChain(Collections.singletonList(new ActionOutHandler()));

        binding.setMTOMEnabled(enabledMtom);
        Authenticate auth = new Authenticate();
        auth.setAdminId(adminId);
        auth.setCredential(credentials);
        try {
            client.authenticate(auth);
            System.out.println("\n\n\n===============================================");
            System.out.println("Successfully authenticated with SCS!");
            System.out.println("===============================================\n\n\n");
        } catch (Exception e) {
            // The error is: The given SOAPAction \"Authenticate\"" does not match an operation.
            System.out.println("\n\n\n===============================================");
            System.out.println("Authentication failed with SCS -- Error: " + e.getLocalizedMessage());
            e.printStackTrace();
            System.out.println("===============================================\n\n\n");
        }

    }

}
