package gui.reports.productstats;

import ch.lambdaj.group.Group;
import common.TestEnvironment;
import gui.reports.TestPrintObject;

import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnitVault;
import model.reports.IPrintObject;
import model.item.Item;
import model.item.ItemVault;

import model.product.Product;
import model.product.ProductVault;

import java.util.ArrayList;
import java.util.List;

import model.productcontainer.StorageUnit;
import model.reports.ObjectReportBuilder;
import model.reports.ReportBuilder;
import model.reports.StatisticReport;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static ch.lambdaj.Lambda.*;
/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/3/12
 * Time: 4:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProductStatsReportTest {

    @Before
    public void before(){
        StorageUnitVault.getInstance().clear();
        ProductGroupVault.getInstance().clear();
        ProductVault.getInstance().clear();
        ItemVault.getInstance().clear();
    }

    @Test
    public void test(){
        //TestEnvironment env = new TestEnvironment(2,10000);
        //env.newEnvironment();

        StatisticReport report = new StatisticReport();
        ReportBuilder builder = new ObjectReportBuilder();
        report.setBuilder(builder);
        report.setMonths(2);
        report.constructReport();
        report.getBuilder();


    }


}
