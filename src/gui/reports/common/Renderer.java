package gui.reports.common;

import java.util.ArrayList;

/**
 * Responsible for rendering {@link gui.reports.common.IReport reports} and 
 * label printouts to PDF. Also handles the rendering of reports to HTML.
 * This class can be considered a singleton. The constructor is private so 
 * that an instance has to be obtained from the class, rather than created
 * new.
 */
public class Renderer {

	/**
	 * Initialize all of the member variables.
	 */
	private Renderer(){}

	private String gridToHTML(Grid g){
		return "";
	}

	private String gridToPDF(Grid g){
		return "";
	}

	/**
	 * Accepts any of the subclasses of 
	 * {@link gui.reports.common.IReport IReport} and renders the contents out
	 * to an HTML file in the users' temporary directory. A string is returned
	 * with a path on disk to the file generated.
	 */
	public String reportToHTML(IReport report){
		return "";
	}

	/**
	 * Accepts any of the subclasses of 
	 * {@link gui.reports.common.IReport IReport} and renders the contents out
	 * to a PDF file in the users' temporary directory. A string is returned
	 * with a path on disk to the file generated.
	 */
	public String reportToPDF(IReport report){
		return "";
	}

}