package model.reports;

public class StatisticReportPrinter implements IReportDirector {
	private ReportBuilder builder;
	public ReportBuilder getBuilder(){
		return null;
	}

	public void setBuilder(ReportBuilder reportBuilder) {
		builder = reportBuilder;
		
	}

	public void constructReport() {
		builder.addHeader("Statistic Report");


		
	}
	
}
