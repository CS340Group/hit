package gui.reports.notices;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import model.reports.HTMLReportBuilder;
import model.reports.IReportDirector;
import model.reports.NSupplyReport;
import model.reports.NoticesReport;
import model.reports.ObjectReportBuilder;
import model.reports.PDFReportBuilder;
import model.reports.ReportBuilder;
import gui.common.*;

/**
 * Controller class for the notices report view.
 */
public class NoticesReportController extends Controller implements
		INoticesReportController {

	/**
	 * Constructor.
	 * 
	 * @param view Reference to the notices report view
	 */	
	public NoticesReportController(IView view) {
		super(view);
		
		construct();
	}

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected INoticesReportView getView() {
		return (INoticesReportView)super.getView();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
	}

	//
	// IExpiredReportController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * notices report view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the notices report view.
	 */
	@Override
	public void display() {
		ReportBuilder builder = (getView().getFormat() == FileFormat.HTML) ?
							 new HTMLReportBuilder() : new PDFReportBuilder();
		IReportDirector director = new NoticesReport();
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

