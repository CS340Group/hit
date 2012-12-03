/**
 *
 */
package model.productidentifier;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

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
        } catch (Exception e) {
            return handoff(barcode);
        }
    }

    private String parseAndFindName(String json) throws NullPointerException {
        JSONObject obj = (JSONObject) JSONValue.parse(json);
        JSONObject z = (JSONObject) obj.get("0");
        String s = z.get("productname").toString();
        return s;
    }
}
