package model.reports;

import ch.lambdaj.group.Group;
import model.common.Stats;
import model.item.Item;
import model.item.ItemVault;
import model.product.Product;
import model.product.ProductVault;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ch.lambdaj.Lambda.*;

public class StatisticReport implements IReportDirector {
	private ReportBuilder builder;

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    private int months;

	public ReportBuilder getBuilder(){
		return null;
	}

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}

	public void constructReport() {
		builder.addHeader("Statistic Report");

        ArrayList<Product> products = ProductVault.getInstance().findAll("CreationDate > %o", DateTime.now().minusMonths(months));

        List<Product> sorted = sort(products,on(Product.class).getDescriptionSort());
        Product prev = new Product();
        ArrayList<Item> items = new ArrayList<Item>();

        builder.startTable();
        builder.addRow(new String[]{
                "Description", "Barcode", "Size", "3-Month Supply", "Supply: Cur/Avg", "Supply: Min/Max",
                "Supply: Used/Added", "Shelf Life", "Used Age: Avg/Max", "Cur Age: Avg/Max"
        });
        for(Product p : sorted){
            if(!p.getDescriptionSort().equals(prev.getDescriptionSort())){
                getStats(items);
                items = ItemVault.getInstance().findAll("ProductId = %o", p.getId());
            } else {
                items.addAll(ItemVault.getInstance().findAll("ProductId = %o", p.getId()));
            }
            prev = p;
        }
        getStats(items);
        builder.endTable();
	}

    private void getStats(ArrayList<Item> items){
        if(items.isEmpty())
            return;
        HashMap<DateMidnight, Integer> buckets = new HashMap<DateMidnight, Integer>();
        Stats supply = new Stats();
        Stats usedAge = new Stats();
        Stats curAge = new Stats();
        int used = 0;
        int added = 0;
        int curSupply = 0;
        for(Item i : items){
            if(buckets.containsKey(i.getEntryDate().toDateMidnight())){
                buckets.put(i.getEntryDate().toDateMidnight(), buckets.get(i.getEntryDate().toDateMidnight()) + 1);
                added++;
            } else {
                buckets.put(i.getEntryDate().toDateMidnight(), 1);
                added++;
            }

            if(i.getExitDate() == null){
                curAge.insert(Days.daysBetween(i.getEntryDate(), DateTime.now()).getDays());
            } else {
                usedAge.insert(Days.daysBetween(i.getEntryDate(), i.getExitDate()).getDays());

                if(buckets.containsKey(i.getExitDate().toDateMidnight())){
                    buckets.put(i.getExitDate().toDateMidnight(), buckets.get(i.getExitDate().toDateMidnight())-1);
                    used++;
                } else {
                    buckets.put(i.getExitDate().toDateMidnight(), -1);
                    used++;
                }
            }
        }
        int days = Days.daysBetween(DateMidnight.now().minusMonths(getMonths()), DateMidnight.now()).getDays();
        //first day of the period
        if(buckets.containsKey(DateMidnight.now().minusDays(days)))
            supply.insert(buckets.get(DateMidnight.now().minusDays(days)));
        else
            supply.insert(0);

        for(int i = days-1; i > 0; i--){
            DateMidnight today = DateMidnight.now().minusDays(i);
            DateMidnight yesterday = today.minusDays(1);
            int yesterdaySupply = 0;
            int todaySupply = 0;
            if(buckets.containsKey(yesterday))
                yesterdaySupply = buckets.get(yesterday);

            if(buckets.containsKey(today))
                todaySupply = buckets.get(today);

            curSupply = yesterdaySupply + todaySupply;
            supply.insert(curSupply);
            buckets.put(today,curSupply);
        }

        builder.addRow(new String[]{
                items.get(0).getProductDescription(),
                items.get(0).getBarcodeString(),
                items.get(0).getProduct().getSize().toString(),
                String.valueOf(items.get(0).getProduct().get3MonthSupply()),
                String.valueOf(curSupply) + " / " + String.valueOf(supply.getMean()),
                String.valueOf(supply.getMin()) + " / " + String.valueOf(supply.getMax()),
                String.valueOf(used) + " / " + String.valueOf(added),
                String.valueOf(items.get(0).getProduct().getShelfLife()) + " months",
                String.valueOf(usedAge.getMean()) + " days / " + String.valueOf(usedAge.getMax()) + " days",
                String.valueOf(curAge.getMean()) + " days / " + String.valueOf(curAge.getMax()) + " days"

        });
    }
	
}
