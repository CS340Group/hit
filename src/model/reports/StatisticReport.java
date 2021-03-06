package model.reports;

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

import static ch.lambdaj.Lambda.on;
import static ch.lambdaj.Lambda.sort;

public class StatisticReport implements IReportDirector {
    private DateMidnight endReport = DateMidnight.now();
    private ReportBuilder builder;

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public void setStartDate(DateTime date) {
        endReport = new DateMidnight(date);
    }

    private int months;

    public ReportBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(ReportBuilder reportBuilder) {
        builder = reportBuilder;

    }

    public void constructReport() {
        builder.addHeader("Statistic Report (" + String.valueOf(months) + " Months)");

		//Changed to grab ALL products
        ArrayList<Product> products = ProductVault.getInstance().findAll("FirstItemDate >= %o",
                endReport.toDateTime().minusMonths(months), true);

        List<Product> sorted = sort(products, on(Product.class).getDescriptionSort());
        Product prev = new Product();
        ArrayList<Item> items = new ArrayList<Item>();

        builder.startTable(10);
        builder.addRow(new String[]{
                "Description", "Barcode", "Size", "3-Month Supply", "Supply: Cur/Avg",
                "Supply: Min/Max",
                "Supply: Used/Added", "Shelf Life", "Used Age: Avg/Max", "Cur Age: Avg/Max"
        });
        for (Product p : sorted) {
            if (!p.getDescriptionSort().equals(prev.getDescriptionSort())) {
                getStats(items, endReport);
                items = ItemVault.getInstance().findAll("ProductId = %o", p.getId(), true);
            } else {
                items.addAll(ItemVault.getInstance().findAll("ProductId = %o", p.getId(), true));
            }
            prev = p;
        }
        getStats(items, endReport);
        builder.endTable();
        builder.endFile();
    }

    private void getStats(ArrayList<Item> items, DateMidnight endReport) {
        if (items.isEmpty())
            return;
        HashMap<DateMidnight, Integer> buckets = new HashMap<DateMidnight, Integer>();
        Stats supply = new Stats();
        Stats usedAge = new Stats();
        Stats curAge = new Stats();
        int used = 0;
        int added = 0;
        int curSupply = 0;
        ArrayList<Item> toRemove = new ArrayList<Item>();
        for (Item i : items) {
            if (i.getEntryDate().isBefore(i.getProduct().getCreationDate().toDateMidnight())){
                toRemove.add(i);
                continue;
            }
            if (buckets.containsKey(i.getEntryDate().toDateMidnight())) {
                buckets.put(i.getEntryDate().toDateMidnight(),
                        buckets.get(i.getEntryDate().toDateMidnight()) + 1);
                added++;
            } else {
                buckets.put(i.getEntryDate().toDateMidnight(), 1);
                added++;
            }

            if (i.getExitDate() == null) {
                curAge.insert(Days.daysBetween(i.getEntryDate(), endReport).getDays());
            } else {
                usedAge.insert(Days.daysBetween(i.getEntryDate(), i.getExitDate()).getDays());

                if (buckets.containsKey(i.getExitDate().toDateMidnight())) {
                    buckets.put(i.getExitDate().toDateMidnight(),
                            buckets.get(i.getExitDate().toDateMidnight()) - 1);
                    used++;
                } else {
                    buckets.put(i.getExitDate().toDateMidnight(), -1);
                    used++;
                }
            }
        }

        items.removeAll(toRemove);
        if (items.isEmpty())
            return;

        int days = Days.daysBetween(items.get(0).getProduct().getCreationDate().toDateMidnight(),
                endReport).getDays();

        if (buckets.containsKey(endReport.minusDays(days)))
            supply.insert(buckets.get(endReport.minusDays(days)));
        else
            supply.insert(0);

        for (int i = days; i > 0; i--) {
            DateMidnight today = endReport.minusDays(i);
            DateMidnight yesterday = today.minusDays(1);
            int yesterdaySupply = 0;
            int todaySupply = 0;
            if (buckets.containsKey(yesterday))
                yesterdaySupply = buckets.get(yesterday);

            if (buckets.containsKey(today))
                todaySupply = buckets.get(today);

            curSupply = yesterdaySupply + todaySupply;
            supply.insert(curSupply);
            buckets.put(today, curSupply);
        }

        builder.addRow(new String[]{
                items.get(0).getProductDescription(),
                items.get(0).getProduct().getBarcodeString(),
                items.get(0).getProduct().getSize().toString(),
                String.valueOf(items.get(0).getProduct().get3MonthSupply()),
                String.valueOf(curSupply) + " / " + String.valueOf(supply.getMean()),
                String.valueOf(supply.getMin()) + " / " + String.valueOf(supply.getMax()),
                String.valueOf(used) + " / " + String.valueOf(added),
                String.valueOf(items.get(0).getProduct().getShelfLife()) + " months",
                String.valueOf(usedAge.getMean()) + " days / " + String.valueOf(usedAge.getMax()) +
                        " days",
                String.valueOf(curAge.getMean()) + " days / " + String.valueOf(curAge.getMax()) +
                        " days"

        });


    }

}
