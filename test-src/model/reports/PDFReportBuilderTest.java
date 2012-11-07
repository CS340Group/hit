package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.item.Item;
import model.item.ItemVault;
import model.product.Product;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Result;

public class PDFReportBuilderTest {
	
	String filePath = "reportTesting.pdf";
	PDFReportBuilder _builder;
	ItemVault _ItemVault = ItemVault.getInstance();

	@Before
	public void setUp() throws Exception {
		 _builder = new PDFReportBuilder(filePath);
	}

	@After
	public void tearDown() throws Exception {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filePath);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
	}

	@Test
	public void test() {
		Product product = new Product();
		product.generateTestData();
		Result result = product.save();
		for (int i = 0; i < 25; i++) {
			Item item = new Item();
			item.generateTestData();
			item.setProduct(product);
			result = item.save();
			item.delete();
		}
		
		RemovedItemsReport report = new RemovedItemsReport(new DateTime().minusDays(2));
		report.setBuilder(_builder);
		report.constructReport();
	}
}
