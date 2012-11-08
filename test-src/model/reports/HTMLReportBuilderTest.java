package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import model.item.ItemVault;
import model.product.ProductVault;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.TestEnvironment;

public class HTMLReportBuilderTest {
    
    String _filePath = "reportTesting.html";
    HTMLReportBuilder _builder;
    ItemVault _itemVault = ItemVault.getInstance();
    private ProductVault _productVault = ProductVault.getInstance();
    TestEnvironment _env;

    @Before
    public void setUp() throws Exception {
        _env = new TestEnvironment(4, 200);
        _env.newEnvironment();
    }

    @After
    public void tearDown() throws Exception {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(_filePath);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    @Test
    public void testRemovedItemsReport() {
        _filePath = "removedItems.html";
        _builder = new HTMLReportBuilder(_filePath);
        RemovedItemsReport report = new RemovedItemsReport(new DateTime().minusDays(2));
        report.setBuilder(_builder);
        report.constructReport();
    }
    
    @Test
    public void testNMonthSupply(){
        _filePath = "nMonth.html";
        _builder = new HTMLReportBuilder(_filePath);
        NSupplyReport report = new NSupplyReport(12);
        report.setBuilder(_builder);
        report.constructReport();
    }

    @Test
    public void testExpiredItems(){
        _filePath = "expired.html";
        _builder = new HTMLReportBuilder(_filePath);
        ExpiredItemsReport report = new ExpiredItemsReport();
        report.setBuilder(_builder);
        report.constructReport();
    }

    @Test
    public void testNotices(){
        _filePath = "notices.html";
        _builder = new HTMLReportBuilder(_filePath);
        NoticesReport report = new NoticesReport();
        report.setBuilder(_builder);
        report.constructReport();
    }

    @Test
    public void testStats(){
        _filePath = "statistics.html";
        _builder = new HTMLReportBuilder(_filePath);
        StatisticReport report = new StatisticReport();
        report.setMonths(12);
        report.setBuilder(_builder);
        report.constructReport();
    }
}
