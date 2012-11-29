/**
 * 
 */
package model.productidentifier;

import org.apache.ws.commons.util.NamespaceContextImpl;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.common.TypeFactoryImpl;
import org.apache.xmlrpc.common.XmlRpcController;
import org.apache.xmlrpc.common.XmlRpcStreamConfig;
import org.apache.xmlrpc.parser.DateParser;
import org.apache.xmlrpc.parser.TypeParser;
import org.apache.xmlrpc.serializer.DateSerializer;
import org.apache.xmlrpc.serializer.TypeSerializer;
import org.xml.sax.SAXException;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Attempts to identify a product by searching Amazon.
 */
public class ProductIdentifierUPCDatabaseDotCom extends ProductIdentificationPlugin {

    private XmlRpcClient client;
    private final String UPCDATABASE_URL = "http://www.upcdatabase.com/xmlrpc";
    private final String LOOKUP_METHOD = "lookup";
    private final String RPC_KEY = "0c607dbbf75d1416cd833c1d4d66fb1a727b48e4";

    public ProductIdentifierUPCDatabaseDotCom(ProductIdentificationPlugin successor){
        super(successor);
        XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
        try {
            config.setServerURL(new URL(UPCDATABASE_URL));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        client = new XmlRpcClient();
        client.setConfig(config);
        client.setTypeFactory(new MyTypeFactory(client));
    }

    @Override
    public String identify(String barcode) {
        Map<String,String> params = new HashMap<String, String>();
        params.put("rpc_key", RPC_KEY);
        params.put("upc", barcode);
        try {
            HashMap result = (HashMap) client.execute(LOOKUP_METHOD, new Object[] {params});
            return result.get("description").toString();
        } catch (NullPointerException e) {
            return handoff(barcode);
        } catch (Exception e) {
            e.printStackTrace();
            return handoff(barcode);
        }
    }

    private class MyTypeFactory extends TypeFactoryImpl {
        public MyTypeFactory(XmlRpcController pController) {
            super(pController);
        }

        private DateFormat newFormat() {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        }

        public TypeParser getParser(XmlRpcStreamConfig pConfig, NamespaceContextImpl pContext, String pURI, String pLocalName) {
            if (DateSerializer.DATE_TAG.equals(pLocalName)) {
                return new DateParser(newFormat());
            } else {
                return super.getParser(pConfig, pContext, pURI, pLocalName);
            }
        }

        public TypeSerializer getSerializer(XmlRpcStreamConfig pConfig, Object pObject) throws SAXException {
            if (pObject instanceof Date) {
                return new DateSerializer(newFormat());
            } else {
                return super.getSerializer(pConfig, pObject);
            }
        }
    }
}
