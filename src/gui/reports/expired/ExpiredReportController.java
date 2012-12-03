package gui.reports.expired;


import gui.common.Controller;
import gui.common.FileFormat;
import gui.common.IView;
import model.reports.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;


/**
 * Controller class for the expired items report view.
 */
public class ExpiredReportController extends Controller implements
        IExpiredReportController {

    /**
     * Constructor.
     *
     * @param view Reference to the expired items report view
     */
    public ExpiredReportController(IView view) {
        super(view);

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
    protected IExpiredReportView getView() {
        return (IExpiredReportView) super.getView();
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
    }

    //
    // IExpiredReportController overrides
    //

    /**
     * This method is called when any of the fields in the
     * expired items report view is changed by the user.
     */
    @Override
    public void valuesChanged() {
    }

    /**
     * This method is called when the user clicks the "OK"
     * button in the expired items report view.
     */
    @Override
    public void display() {
        ReportBuilder builder =
                (getView().getFormat() == FileFormat.HTML) ? new HTMLReportBuilder() :
                        new PDFReportBuilder();
        IReportDirector director = new ExpiredItemsReport();
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

