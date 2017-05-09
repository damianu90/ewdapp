package com.duzieblo.ewdapp.rpc.interfaces;

import com.duzieblo.ewdapp.exception.XmlRpcFault;
import com.duzieblo.ewdapp.model.People;
import java.util.ArrayList;
import org.apache.xmlrpc.XmlRpcException;

/**
 *
 * @author Damian Uziębło
 */
public interface EwdClient {
    ArrayList<People> getPeoples(String city) throws XmlRpcException, XmlRpcFault;
}
