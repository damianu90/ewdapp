package com.duzieblo.ewdapp.rpc.config;


import java.net.MalformedURLException;
import java.net.URL;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * @author Damian Uziębło
 */
public class EwdRpcConfig {
    private XmlRpcClientConfigImpl config;
    private String sUrl;
    private URL url;
    
    public EwdRpcConfig(String url) throws MalformedURLException {
        this.sUrl = url;
        createConfig();
    }
    
    private void createConfig() throws MalformedURLException {
        config = new XmlRpcClientConfigImpl();
        URL server = new URL(sUrl);
        config.setServerURL(server);
        config.setEnabledForExtensions(false);
    }

    public String getUrl() {
        return sUrl;
    }
    
    public void setConnectionTimeOut(int timeOut) {
        config.setConnectionTimeout(timeOut);
    }
    
    public void getConnectionTimeOut() {
        config.getConnectionTimeout();
    }

    public XmlRpcClientConfigImpl getConfig() {
        return config;
    }
    
    
    
}
