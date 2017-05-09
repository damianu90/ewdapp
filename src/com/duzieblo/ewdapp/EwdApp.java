
package com.duzieblo.ewdapp;

import static com.duzieblo.ewdapp.Config.*;
import com.duzieblo.ewdapp.exception.XmlRpcFault;
import com.duzieblo.ewdapp.model.People;
import com.duzieblo.ewdapp.rpc.client.EwdClientImpl;
import com.duzieblo.ewdapp.rpc.config.EwdRpcConfig;
import com.duzieblo.ewdapp.xml.PeopleXmlParser;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.apache.xmlrpc.XmlRpcException;

/**
 * @author Damian Uziębło
 */
public class EwdApp {
    private static final String CITY = "Katowice";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<People> peoples = new ArrayList<>();
        
        try {
            EwdRpcConfig config = new EwdRpcConfig(URL);
            EwdClientImpl ewdClientImpl = new EwdClientImpl(config,
                    LOGIN, PASSWORD);
            peoples = ewdClientImpl.getPeoples(CITY);
        } catch (MalformedURLException ex) {
            System.out.println("Fail URL!"); //TODO use logger!
        } catch (XmlRpcFault e) {
            System.out.println("Server response with error!"); //TODO use logger!
            System.out.println("Error code: " + e.getErrorCode()); //TODO use logger!
            System.out.println("Error message: " + e.getErrorMessage()); //TODO use logger!
        } catch (XmlRpcException e) {
            System.out.println(e.getMessage()); //TODO use logger!
        }
        
        PeopleXmlParser parser = new PeopleXmlParser(peoples);
        try {
            parser.saveXmlToFile(PEOPLE_OUT_FILE);
        } catch (IOException e) {
            System.out.println("Error while write data to file " + PEOPLE_OUT_FILE);
        }
    }
    
    
    
}
