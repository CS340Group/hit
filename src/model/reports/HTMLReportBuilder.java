package model.reports;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import common.Result;

public class HTMLReportBuilder implements ReportBuilder {

	private String _filePath = "report.html";
	private FileWriter _fileWriter;
	private BufferedWriter _writer;
	private int _tableColumns;
	private boolean _tableStarted;

	public HTMLReportBuilder(String filePath){
		_filePath = filePath;
		File file = new File(_filePath);
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			_fileWriter = new FileWriter(file.getAbsoluteFile());
			_writer = new BufferedWriter(_fileWriter);
			write("<html><body>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String wrapWithTag(String tag, String body) {
		return String.format("<%s>%s</%s>", tag, body, tag);
	}
	
	private void write(String str){
		try {
			_writer.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void addHeader(String header) {
		write(wrapWithTag("h1", header));
	}

	@Override
	public void addHeading(String heading) {
		write(wrapWithTag("h2", heading));
	}

	@Override
	public void startTable(int columns) {
		_tableColumns = columns;
		_tableStarted = true;
		write("<table>");
	}

	@Override
	public Result addRow(String[] row) {
		if (!_tableStarted)
			return new Result(false, "The table has to be started first.");
		write("<tr>");
		for (String entry : row){
			write(wrapWithTag("td", entry));
		}
		for (int i=0; i < _tableColumns - row.length; i++) {
			write("<td></td>");
		}
		write("</tr>");
		return new Result(true);
	}

	@Override
	public Result endTable() {
		write("</table>");
		return new Result(true);
	}

	@Override
	public void addTextBlock(String text) {
		write(wrapWithTag("span", text));
	}

	@Override
	public void endFile() {
		write("</html></body>");
		try {
			_writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String returnReport() {
		// TODO Auto-generated method stub
		return null;
	}




}
