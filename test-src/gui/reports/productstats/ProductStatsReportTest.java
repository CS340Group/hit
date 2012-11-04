package gui.reports.productstats;

import gui.reports.TestPrintObject;
import gui.reports.common.IPrintObject;
import model.product.Product;
import model.product.ProductVault;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/3/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductStatsReportTest {

    public IPrintObject generateReport(int months){
        TestPrintObject report = new TestPrintObject();

        ArrayList<Product> product = ProductVault.getInstance().findAll("");

        return report;
    }
}
