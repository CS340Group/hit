package model.reports;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PDFReportBuilderTest {
	
	String filePath = "reportTesting.pdf";
	PDFReportBuilder _builder;

	@Before
	public void setUp() throws Exception {
		 _builder = new PDFReportBuilder(filePath);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		_builder.addHeader("SILLY REPORT!");
		_builder.addHeading("WHOHHH");
		_builder.startTable(4);
		String[] row1 = {"one", "two", "three", "four"};
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.endTable();
		_builder.addTextBlock("THIS IS A TEXT BLOCK!");
		_builder.startTable(4);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.addRow(row1);
		_builder.endTable();
		_builder.endFile();
		
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filePath);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }	
	}

}
