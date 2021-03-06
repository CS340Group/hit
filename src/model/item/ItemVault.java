package model.item;

import common.Result;
import model.common.Barcode;
import model.common.IModel;
import model.common.Vault;

import java.util.ArrayList;


/**
 * The Item class provides a way to query for ItemModels within the select data backend
 * (A Model superclass or interface could be created for this)
 * <PRE>
 * Item.find(2) // returns ItemModel with id of 2
 * </PRE>
 * Other findBy* methods may be implemented.
 */
public class ItemVault extends Vault {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    static ItemVault currentInstance;

    /**
     * Private constructor, for the singleton design pattern.
     */
    private ItemVault() {
        currentInstance = this;
    }

    /**
     * Returns a reference to the only instance allowed for this class.
     */
    public static synchronized ItemVault getInstance() {
        if (currentInstance == null) currentInstance = new ItemVault();
        return currentInstance;
    }


    /**
     * Returns just one item based on the query sent in.
     * If you need more than one item returned use FindAll
     *
     * @param query of form obj.attr = value
     */
    public Item find(String query, Object... params) {
        return (Item) findPrivateCall(query, params);
    }

    /**
     * Returns a list of Items which match the criteria
     *
     * @param query of form obj.attr = value
     */
    public ArrayList<Item> findAll(String query, Object... params) {
        return (ArrayList) this.findAllPrivateCall(query, params);
    }


    /**
     * Checks if the model passed in already exists in the current map
     * - Item must have a unique barcode
     *
     * @param model
     * @return Result of the check
     */
    public Result validateNew(IModel model) {
        assert (model != null);

        //Since we set the barcode at save we don't need to check it here
        int count = 0;//findAll("Barcode = %o",  model.getBarcode().toString()).size();
        if (count == 0) {
            model.setValid(true);
            return new Result(true);
        } else
            return new Result(false, "Duplicate barcode");
    }


    /**
     * Adds the Item to the map if it's new.  Should check before doing so.
     *
     * @param model Item to add
     * @return Result of request
     */
    public Result saveNew(Item model) {
        if (!model.isValid())
            return new Result(false, "Model must be valid prior to saving,");

        int id = 0;
        if (dataVault.isEmpty())
            id = 0;
        else
            id = (int) dataVault.lastKey() + 1;

        model.setId(id);
        model.setBarcode(new Barcode(String.valueOf(id)));
        model.setSaved(true);
        this.addModel(new Item(model));
        storageManager.getAppropriateDAO(model).insert(model);
        return new Result(true);
    }

    public Item get(int id) {
        return (Item) this.getPrivateCall(id);
    }

    protected Item getNewObject() {
        return new Item();
    }

    protected Item getCopiedObject(IModel model) {
        return new Item((Item) model);
    }


}

