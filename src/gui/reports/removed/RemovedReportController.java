package gui.reports.removed;

import gui.common.Controller;
import gui.common.FileFormat;
import gui.common.IView;
import model.item.Item;
import model.item.ItemVault;
import model.reports.*;
import model.storage.StorageManager;
import org.joda.time.DateTime;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Controller class for the removed items report view.
 */
public class RemovedReportController extends Controller implements
        IRemovedReportController {

    /**
     * Constructor.
     *
     * @param view Reference to the removed items report view
     */
    public RemovedReportController(IView view) {
        super(view);
        Item item = (Item) StorageManager.getInstance().getFactory().getMiscStorageDAO().get(1);

        DateTime last;
        if (item != null)
            last = item.getEntryDate();
        else
            last = ItemVault.getInstance().sinceLastRemovedReport;
        if (last == null) {
            last = new DateTime(2000, 1, 1, 1, 1);
            //getView().enableSinceDate(false);
            getView().enableSinceLast(false);
        }
        getView().setSinceLastValue(last.toDate());
        construct();
    }

    //
    // Controller overrides
    //

    /**
     * Returns a reference to the view for this controller.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post Returns a reference to the view for this controller.}
     */
    @Override
    protected IRemovedReportView getView() {
        return (IRemovedReportView) super.getView();
    }

    /**
     * Sets the enable/disable state of all components in the controller's view.
     * A component should be enabled only if the user is currently
     * allowed to interact with that component.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post The enable/disable state of all components in the controller's view
     * have been set appropriately.}
     */
    @Override
    protected void enableComponents() {
        getView().enableSinceDateValue(getView().getSinceDate());
    }

    /**
     * Loads data into the controller's view.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
        if (ItemVault.getInstance().sinceLastRemovedReport != null) {

            this.getView().setSinceLast(true);
            this.getView()
                    .setSinceLastValue(ItemVault.getInstance().sinceLastRemovedReport.toDate());
            this.getView()
                    .setSinceDateValue(ItemVault.getInstance().sinceLastRemovedReport.toDate());
        } else {
            this.getView().setSinceDate(true);
            this.getView().setSinceDateValue(new DateTime().now().toDate());
        }
    }

    //
    // IExpiredReportController overrides
    //

    /**
     * This method is called when any of the fields in the
     * removed items report view is changed by the user.
     */
    @Override
    public void valuesChanged() {
        enableComponents();
    }

    /**
     * This method is called when the user clicks the "OK"
     * button in the removed items report view.
     */
    @Override
    public void display() {
        ReportBuilder builder =
                (getView().getFormat() == FileFormat.HTML) ? new HTMLReportBuilder() :
                        new PDFReportBuilder();
        IReportDirector director;

        if (this.getView().getSinceDate() == true)
            director = new RemovedItemsReport(new DateTime(this.getView().getSinceDateValue()));
        else {
            if ((ItemVault.getInstance().sinceLastRemovedReport) == null)
                director = new RemovedItemsReport(new DateTime(0));
            else
                director = new RemovedItemsReport(ItemVault.getInstance().sinceLastRemovedReport);
        }

        director.setBuilder(builder);
        director.constructReport();
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(builder.returnReport());
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

}

