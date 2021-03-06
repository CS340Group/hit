package model.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import common.Result;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class PDFReportBuilder implements ReportBuilder {

    private Document _d;
    private PdfWriter _writer;
    private Boolean _open;
    private PdfContentByte _rawContent;
    private PdfPTable _table;
    private int _itemsAdded;
            // Keeps track of how many items have been added,
            // in case we need to fill up dummy items.
    private boolean _tableStarted;
    private int _columns;
    private String _reportName = "PdfReport.pdf";

    public PDFReportBuilder() {
        setUpDocument();
    }

    public PDFReportBuilder(String filepath) {
        _reportName = filepath;
        setUpDocument();
    }

    private void setUpDocument() {
        _d = new Document();
        try {
            _writer = PdfWriter.getInstance(_d, new FileOutputStream(_reportName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            assert false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            assert false;
        }

        _d.setMargins(15, 15, 15, 15);
        _d.open();

        _open = true;
        _rawContent = _writer.getDirectContent();
    }

    @Override
    public void addHeader(String header) {
        addTextAtSize(header + "\n", 20);
    }

    @Override
    public void addHeading(String heading) {
        addTextAtSize(heading + "\n", 14);
    }

    @Override
    public void startTable(int columns) {
        _columns = columns;
        _tableStarted = true;
        _table = new PdfPTable(_columns);
        _table.setHorizontalAlignment(Element.ALIGN_CENTER);
        _table.setWidthPercentage(100);
        _itemsAdded = 0;
    }

    @Override
    public Result addRow(String[] row) {
        if (!_tableStarted)
            return new Result(false, "The table must be started before adding a row, silly!");
        for (String s : row) {
            // Add some text to a chunk, and the image to a chunk.
            Font font = new Font(Font.FontFamily.HELVETICA, 8);
            Chunk c1 = new Chunk(s);
            c1.setFont(font);
            Phrase p = new Phrase(c1);

            PdfPCell cell = new PdfPCell(p);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            cell.setBorder(1);
            _table.addCell(cell);

            _itemsAdded++;
        }
        ;
        fillNullCells();
        return new Result(true);
    }

    @Override
    public Result endTable() {
        if (!_open)
            return new Result(false, "Not open!");
        fillNullCells();
        try {
            _d.add(_table);
        } catch (DocumentException e) {
            return new Result(false, "I couldn't add this table.");
        }
        return new Result(true);
    }

    /**
     * This is a convenience function to make it easy to fill up the remaining table cells.
     */
    private void fillNullCells() {
        int itemsToAdd = _columns - (_itemsAdded % _columns);
        while (itemsToAdd > 0) {
            PdfPCell c = new PdfPCell();
            c.setBorder(0);
            _table.addCell(c);
            itemsToAdd--;
            _itemsAdded++;
        }
    }

    public void addTextAtSize(String text, int size) {
        // Add some text to a chunk, and the image to a chunk.
        Font font = new Font(Font.FontFamily.HELVETICA, size);
        Chunk c1 = new Chunk(text);
        c1.setFont(font);
        Phrase p = new Phrase(c1);
        try {
            _d.add(p);
        } catch (DocumentException e) {
            e.printStackTrace();
            assert false;
        }
    }

    @Override
    public void addTextBlock(String text) {
        addTextAtSize(text + "\n", 8);
    }

    @Override
    public void endFile() {
        _d.close();
        _open = false;
    }

    @Override
    public String returnReport() {
        return _reportName;
    }


}
