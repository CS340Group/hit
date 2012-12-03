package model.reports;

import model.item.Item;
import model.product.Product;
import model.productcontainer.ProductGroup;
import model.productcontainer.StorageUnit;

public interface Ivisitor {
    public void visit(Item item);

    public void visit(Product product);

    public void visit(ProductGroup productGroup);

    public void visit(StorageUnit storageUnit);
}
