/**
 * 
 */
package model.productidentifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.*;
import org.omg.CosNaming.NamingContextExtPackage.URLStringHelper;

import com.google.gson.JsonArray;

/**
 * Searches UPCSearchDotOrg for the product in question.
 */
public class ProductIdentifierSearchUPCDotCom extends
        ProductIdentificationPlugin {

	String _url = "http://www.searchupc.com/handlers/upcsearch.ashx?" +
				  "request_type=3&access_token=C207F8BE-D45E-4B9B-93E8-5A30DED8C686&upc=";

    public ProductIdentifierSearchUPCDotCom(ProductIdentificationPlugin successor) {
        super(successor);
    }

    @Override
    public String identify(String barcode) {
    		try {
    			String urlCat = _url + barcode;
			URL url = new URL(urlCat);
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String result = reader.readLine();
			return parseAndFindName(result);
		} catch (IOException e) {
			return handoff(barcode);
		}
    }

	private String parseAndFindName(String json) {
		JSONObject obj = (JSONObject) JSONValue.parse(json);
		JSONObject z = (JSONObject)obj.get("0");
		String s = z.get("productname").toString();
		return s;
	}
}
