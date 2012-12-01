package common;

import model.common.Size;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import model.productcontainer.ProductGroup;
import model.productcontainer.ProductGroupVault;
import model.productcontainer.StorageUnit;
import model.productcontainer.StorageUnitVault;
import model.storage.SerializationDAOFactory;
import model.storage.StorageManager;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: nethier
 * Date: 11/6/12
 * Time: 12:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestEnvironment {

    private int sus;
    private int pgs;
    private int ps;
    private int is;
    private int months;

    public TestEnvironment(int months, int models){
        this.months = months;
        sus = models/6;
        pgs = models/6;
        ps = models/6;
        is = models;
        StorageManager.getInstance().setFactory(new SerializationDAOFactory());
    }

    public void clear() {
        StorageUnitVault.getInstance().clear();
        ProductGroupVault.getInstance().clear();
        ProductVault.getInstance().clear();
        ItemVault.getInstance().clear();
    }

    public void newEnvironment(){
        ArrayList<StorageUnit> storageUnits = new ArrayList<StorageUnit>();
        ArrayList<ProductGroup> productGroups = new ArrayList<ProductGroup>();
        ArrayList<Product> products = new ArrayList<Product>();

        int days = Days.daysBetween(DateMidnight.now().minusMonths(months), DateMidnight.now()).getDays();
        SecureRandom random = new SecureRandom();

        //sus
        for(int i = 0; i < sus; i++){
            StorageUnit su = new StorageUnit();
            su.setRootParentId(-1);
            su.setName(new BigInteger(130, random).toString(32));
            Result r = su.validate();
            assert r.getStatus();
            r = su.save();
            assert r.getStatus();
            storageUnits.add(su);
        }

        //pgs
        for(int i = 0; i < pgs; i++){
            ProductGroup pg = new ProductGroup();
            pg.set3MonthSupply(new Size(3, "count"));
            pg.setRootParentId(storageUnits.get(((Number)(Math.random() * storageUnits.size())).intValue()).getId());
            if((Math.random() * 5)<3 && !productGroups.isEmpty())
                pg.setParentId(productGroups.get(((Number)(Math.random() * productGroups.size())).intValue()).getId());
            else
                pg.setParentId(pg.getRootParentId());
            pg.setName(new BigInteger(130, random).toString(32));
            Result r = pg.validate();
            assert r.getStatus();
            r = pg.save();
            assert r.getStatus();
            productGroups.add(pg);
        }

        //ps
        for(int i = 0; i <ps; i++){
            Product p = new Product();
            p.setDescription(new BigInteger(130, random).toString(32));
            p.set3MonthSupply(((Number) (Math.random() * months)).intValue() + 1);
            p.setBarcode(new BigInteger(100, random).toString(10));
            p.setShelfLife(((Number) (Math.random() * months)).intValue() + 1);
            p.setStorageUnitId(storageUnits.get(((Number) (Math.random() * storageUnits.size())).intValue()).getId());
            p.setSize(new Size(1, "count"));
            if((Math.random() * 5)<3)
                p.setContainerId(productGroups.get(((Number)(Math.random() * productGroups.size())).intValue()).getId());
            else
                p.setContainerId(p.getStorageUnitId());

            p.setCreationDate(DateTime.now().minusDays(((Number) (Math.random() * days)).intValue()));

            Result r = p.validate();
            if(!r.getStatus())
                assert r.getStatus();
            r = p.save();
            assert r.getStatus();
            products.add(p);
        }

        //is
        for(int i = 0; i < is; i++){
            Item item = new Item();

            item.setProductId(products.get(((Number)(Math.random() * products.size())).intValue()).getId());
            int hours = ((Number)(Math.random() * 100)).intValue();
            while(item.getProduct().getCreationDate().plusHours(hours).isAfter(DateTime.now()))
                hours = ((Number)(Math.random() * 100)).intValue();
            item.setEntryDate(item.getProduct().getCreationDate().plusHours(hours));

            hours = ((Number)(Math.random() * 500)).intValue();
            while(item.getEntryDate().plusHours(hours).isAfter(DateTime.now()))
                hours = ((Number)(Math.random() * 100)).intValue();

            if((Math.random() * 5) < 3){
                item.setExitDate(item.getEntryDate().plusHours(hours));
                item.delete();
            }

            Result r = item.validate();
            assert r.getStatus();
            r = item.save();
            assert r.getStatus();
        }
    }


}
