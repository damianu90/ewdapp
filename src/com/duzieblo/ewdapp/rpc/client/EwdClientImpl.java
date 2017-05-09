package com.duzieblo.ewdapp.rpc.client;

import com.duzieblo.ewdapp.exception.XmlRpcFault;
import com.duzieblo.ewdapp.model.People;
import com.duzieblo.ewdapp.rpc.config.EwdRpcConfig;
import com.duzieblo.ewdapp.rpc.interfaces.EwdClient;
import java.util.ArrayList;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * @author Damian Uziębło
 */
public class EwdClientImpl implements EwdClient {

    private static final String NAMESPACE = "ewd";
    private static final String METHOD_GET_PEOPLES = "getPeoples";
    private static final String METHOD_GET_PEOPLES_NAMESPACE = NAMESPACE + "." + METHOD_GET_PEOPLES;
    
    private static final String TAG_ERROR = "FAIL";
    private static final String TAG_SUCCESS = "SUCCESS";
    
    private XmlRpcClient xmlRpcClient;
    private XmlRpcClientConfigImpl xmlRpcClientConfigImpl;
    private String login;
    private String password;
    
    public EwdClientImpl(EwdRpcConfig ewdRpcConfig, String login, String password) {
        xmlRpcClient = new XmlRpcClient();
        xmlRpcClientConfigImpl = ewdRpcConfig.getConfig();
        xmlRpcClient.setConfig(ewdRpcConfig.getConfig());
        this.login = login;
        this.password = password;
    }
    
    @Override
    public ArrayList<People> getPeoples(String city) throws XmlRpcException, XmlRpcFault {
        ArrayList params = new ArrayList();
        params.add(login);
        params.add(password);
        params.add(city);
        
        Object[] result1 = (Object[])
            xmlRpcClient.execute(METHOD_GET_PEOPLES_NAMESPACE, params);
        
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        convertToArrayList(result1, result);
        
        if (isSuccess(result)) {
            ArrayList<People> returnPeople = new ArrayList<>();
            result.remove(0); // delete data header info
            result.forEach(peopleData -> {
                People people = new People();
                people.setPesel(peopleData.get(0));
                people.setName(peopleData.get(1));
                people.setSurname(peopleData.get(2));
                people.setCity(peopleData.get(3));
                returnPeople.add(people);
            });
            return returnPeople;
        } else {
            throw new XmlRpcFault(getErrorCode(result), getErrorMessage(result));
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    private String getErrorCode(ArrayList<ArrayList<String>> result) {
        if (result != null && !result.isEmpty() && result.get(0) != null) {
            return result.get(0).get(1);
        } 
        return null;
    }
    
    private String getErrorMessage(ArrayList<ArrayList<String>> result) {
        if (result != null && !result.isEmpty() && result.get(0) != null) {
            return result.get(0).get(2);
        } 
        return null;
    }
    
    private boolean isSuccess(ArrayList<ArrayList<String>> result) {
        if (result != null && !result.isEmpty() && result.get(0) != null) {
            return result.get(0).get(0).equalsIgnoreCase(TAG_SUCCESS);
        }
        return false;
    }
    
    private void convertToArrayList(Object [] objects, ArrayList list) {
        if (list == null) {
            list = new ArrayList();
        }
        
        if (objects != null) {
            for (int i = 0; i < objects.length; i++) {
                list.add(objects[i]);
                if (objects[i] != null && isArray(objects[i])) {
                    list.set(i, new ArrayList()); 
                    convertToArrayList((Object[]) objects[i], (ArrayList) list.get(i));
                }
            }
        }
    }
    
    /**
     * Method check is the object is Array
     * @param object
     * @return - return true if the object is Array
     */
    private boolean isArray(Object object) {
        return object.getClass().isArray();
    }
    
}
