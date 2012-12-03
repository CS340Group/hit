package model.reports;


import common.TestEnvironment;
import model.common.ModelFacade;
import model.item.Item;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.StorageUnit;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;
import static org.junit.Assert.*;

public class StatisticsReportTest {

    TestEnvironment _env;

    @Before
    public void setUp() throws Exception {
        _env = new TestEnvironment(12, 1000);
    }

    @After
    public void tearDown() throws Exception {
        _env.clear();
    }

    @Test
    public void testMultiYear() throws Exception {
        StorageUnit su = new StorageUnit();
        su.setName("Test");
        su.validate();
        su.save();

        Product p = new Product();
        p.setContainerId(su.getId());
        p.generateTestData();
        p.setCreationDate(DateMidnight.now().minusYears(1).toDateTime());
        p.setStorageUnitId(su.getId());
        p.validate();
        p.save();

        Item i = new Item();
        i.generateTestData();
        i.setProductId(p.getId());
        i.validate();
        i.save();

        Product p2 = new Product();
        p2.generateTestData();
        p2.setCreationDate(DateTime.now().minusMonths(18));
        p2.setDescription("Something");
        p2.setContainerId(su.getId());
        p2.setStorageUnitId(su.getId());
        p2.setBarcode("1233");
        p2.validate();
        p2.save();

        Item i2 = new Item();
        i2.generateTestData();
        i2.setProductId(p2.getId());
        i2.setEntryDate(p.getCreationDate().minusMonths(1));
        i2.validate();
        i2.save();

        ObjectReportBuilder builder = new ObjectReportBuilder();
        StatisticReport report = new StatisticReport();
        report.setMonths(15);
        report.setBuilder(builder);
        report.constructReport();
        PrintObject obj = (PrintObject) builder.returnObject();
        assertEquals(obj.getTable(0).getCell(1,0), p.getDescription());
        assertEquals(obj.getTable(0).getCell(1,1), p.getBarcode());
        assertEquals(obj.getTable(0).getCell(1,2), p.getSize().toString());
        assertEquals(obj.getTable(0).getCell(1,3), String.valueOf(p.get3MonthSupply()));
    }

    @Test
    public void testSort() throws Exception {
        Random rand = new Random();
        int max = 12;
        int months = rand.nextInt(max + 1);
        _env.newEnvironment();

        ObjectReportBuilder builder = new ObjectReportBuilder();
        StatisticReport report = new StatisticReport();
        report.setMonths(months);
        report.setBuilder(builder);
        report.constructReport();
        PrintObject obj = (PrintObject) builder.returnObject();

        List<Product> products = sort(
                ProductVault.getInstance().findAll("CreationDate > %o", DateTime.now().minusMonths(months), true),
                on(Product.class).getDescriptionSort()
        );
        int i = 1;
        Product prev = new Product();
        for(Product p : products){
            assertEquals(obj.getTable(0).getCell(i,0), p.getDescription());
            assertEquals(obj.getTable(0).getCell(i,1), p.getBarcode());
            assertEquals(obj.getTable(0).getCell(i,2), p.getSize().toString());
            assertEquals(obj.getTable(0).getCell(i,3), String.valueOf(p.get3MonthSupply()));
            i++;
        }
    }

    @Test
    public void testBadItemDate(){
        StorageUnit su = new StorageUnit();
        su.setName("Test");
        su.validate();
        su.save();

        Product p = new Product();
        p.setContainerId(su.getId());
        p.generateTestData();
        p.setCreationDate(DateTime.now());
        p.setStorageUnitId(su.getId());
        p.validate();
        p.save();

        Item i = new Item();
        i.generateTestData();
        i.setProductId(p.getId());
        i.setEntryDate(p.getCreationDate().minusMonths(4));
        i.validate();
        i.save();

        ObjectReportBuilder builder = new ObjectReportBuilder();
        StatisticReport report = new StatisticReport();
        report.setMonths(1);
        report.setBuilder(builder);
        report.constructReport();
        PrintObject obj = (PrintObject) builder.returnObject();

        assertEquals(obj.getTable(0).rowCount(), 1);
    }

    @Test
    public void testProductInMultipleSU() throws Exception {
        StorageUnit su = new StorageUnit();
        su.setName("Test");
        su.validate();
        su.save();

        StorageUnit su2 = new StorageUnit();
        su2.setName("Test2");
        su2.validate();
        su2.save();

        Product p = new Product();
        p.setContainerId(su.getId());
        p.generateTestData();
        p.setCreationDate(DateTime.now());
        p.setStorageUnitId(su.getId());
        p.validate();
        p.save();

        Item i = new Item();
        i.generateTestData();
        i.setProductId(p.getId());
        i.validate();
        i.save();

        Product p2 = new Product(p);
        p2.setId(-1);
        p2.setContainerId(su2.getId());
        p2.setStorageUnitId(su2.getId());
        p2.validate();
        p2.save();

        Item i2 = new Item();
        i2.generateTestData();
        i2.setProductId(p2.getId());
        i2.validate();
        i2.save();


        ObjectReportBuilder builder = new ObjectReportBuilder();
        StatisticReport report = new StatisticReport();
        report.setMonths(13);
        report.setBuilder(builder);
        report.constructReport();
        PrintObject obj = (PrintObject) builder.returnObject();

        assertEquals(obj.getTable(0).rowCount(), 2);
        assertEquals(obj.getTable(0).getCell(1,0), p.getDescription());
    }

    @Test
    public void testNotNullBuilder(){
        ObjectReportBuilder builder = new ObjectReportBuilder();
        StatisticReport report = new StatisticReport();
        report.setMonths(12);
        report.setBuilder(builder);
        report.constructReport();
        assertNotNull(report.getBuilder());
    }

    @Test
    public void testGetMonths(){
        int months = new Random().nextInt(12 + 1);
        ObjectReportBuilder builder = new ObjectReportBuilder();
        StatisticReport report = new StatisticReport();
        report.setMonths(months);
        assertEquals(months,report.getMonths());
    }
}