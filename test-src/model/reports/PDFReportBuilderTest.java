package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnitVault;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import common.TestEnvironment;

public class PDFReportBuilderTest {
    
    String _filePath = "reportTesting.pdf";
    PDFReportBuilder _builder;
    ItemVault _itemVault = ItemVault.getInstance();
    private ProductVault _productVault = ProductVault.getInstance();
    ProductGroupVault pgv = ProductGroupVault.getInstance();
	StorageUnitVault suv = StorageUnitVault.getInstance();
    
    @Before
    public void setUp() throws Exception {
        TestEnvironment env = new TestEnvironment(4, 200);
        env.newEnvironment();
    }

    @After
    public void tearDown() throws Exception {
//        if (Desktop.isDesktopSupported()) {
//            try {
//                File myFile = new File(_filePath);
//                Desktop.getDesktop().open(myFile);
//            } catch (IOException ex) {
//                // no application registered for PDFs
//            }
//        }
    }

    @AfterClass
	public static void tearDownAfterClass() throws Exception {
		ItemVault.getInstance().clear();
		ProductVault.getInstance().clear();
		StorageUnitVault.getInstance().clear();
		ProductGroupVault.getInstance().clear();
		
	}
    
    @Test
    public void testRemovedItemsReport() {
        _filePath = "removedItems.pdf";
        _builder = new PDFReportBuilder(_filePath);
        RemovedItemsReport report = new RemovedItemsReport(new DateTime().minusDays(2));
        report.setBuilder(_builder);
        report.constructReport();
    }
    
    @Test
    public void testNMonthSupply(){
        _filePath = "nMonth.pdf";
        _builder = new PDFReportBuilder(_filePath);
        NSupplyReport report = new NSupplyReport(12);
        report.setBuilder(_builder);
        report.constructReport();
    }

    @Test
    public void testExpiredItems(){
        _filePath = "expired.pdf";
        _builder = new PDFReportBuilder(_filePath);
        ExpiredItemsReport report = new ExpiredItemsReport();
        report.setBuilder(_builder);
        report.constructReport();
    }

    @Test
    public void testNotices(){
        _filePath = "notices.pdf";
        _builder = new PDFReportBuilder(_filePath);
        NoticesReport report = new NoticesReport();
        report.setBuilder(_builder);
        report.constructReport();
    }

    @Test
    public void testStats(){
        _filePath = "statistics.pdf";
        _builder = new PDFReportBuilder(_filePath);
        StatisticReport report = new StatisticReport();
        report.setBuilder(_builder);
        report.constructReport();
    }
}
