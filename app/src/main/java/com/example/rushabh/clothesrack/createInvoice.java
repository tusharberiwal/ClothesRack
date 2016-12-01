package com.example.rushabh.clothesrack;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tushar on 10/29/2016.
 */

public class createInvoice extends Fragment implements View.OnClickListener {

    View myView;
    Button create_Invoice;
    private static Font subFont1 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.BLACK);
    private static Font subFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD, BaseColor.BLACK);
    private static Font subFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL, BaseColor.BLACK);
    private static Font subFont4 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD, BaseColor.BLACK);

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.create_invoice, container,false);
        create_Invoice = (Button)myView.findViewById(R.id.createInvoice);
        create_Invoice.setOnClickListener(this);

        return myView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createInvoice: {
                try {
                    createInvoicePDF();
                } catch (Exception e) {
                    Log.i("", e.getMessage());
                }
                break;
            }
        }

    }

    public void createInvoicePDF() throws FileNotFoundException, DocumentException
    {
        Toast.makeText(getContext(),"start invoice",Toast.LENGTH_SHORT).show();
        File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "pdfdemo");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();

        }
        //Create time stamp

        Date date = new Date();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        File myFile = new File(pdfFolder + timeStamp + ".pdf");
        OutputStream output = new FileOutputStream(myFile);


        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, output);

        document.setMargins(30, 30, 30, 18);
        document.setMarginMirroring(true);
        document.open();
        document.addCreationDate();

        addTitlePage(document);
        addCustomerDetails( document);
        addInvoiceExtras(document);

        document.close();
        Toast.makeText(getContext(),"end invoice",Toast.LENGTH_SHORT).show();
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public static void addGaps( String text, Document doc, Font font, int newLines )
            throws DocumentException
    {
        Paragraph paragraph = new Paragraph( text, font );
        addEmptyLine( paragraph, newLines );
        doc.add( paragraph );
    }


private void addInvoiceExtras(Document document) throws DocumentException
{
    //addGaps(" ", document, subFont1, 10);

    PdfPCell displayString,gtotal;
    PdfPTable totalTable = new PdfPTable(2);
    float[] columnWidths2 = new float[] {60f,10f};
    totalTable.setWidths(columnWidths2);
    displayString = new PdfPCell(new Paragraph("Grand Total: ",subFont2));
    displayString.setBorderWidth(1);
    displayString.setHorizontalAlignment(Element.ALIGN_RIGHT);
    totalTable.addCell(displayString);

    gtotal = new PdfPCell(new Paragraph("2000",subFont1));
    displayString.setFixedHeight(20f);
    gtotal.setBorderWidth(1);
    totalTable.addCell(gtotal);

    document.add(totalTable);


    PdfPTable signatureTable = new PdfPTable(2);
    float[] columnWidths3 = new float[] {35f,35f};
    signatureTable.setWidths(columnWidths3);

    displayString = new PdfPCell(new Paragraph("",subFont2));
    displayString.setBorderWidth(1);
    signatureTable.addCell(displayString);

    displayString = new PdfPCell(new Paragraph("RUSHABH PAREKH AND COMPANY\n\n\n\n                 Authorized Signatory",subFont4));
    displayString.setFixedHeight(60f);
    displayString.setBorderWidth(1);
    signatureTable.addCell(displayString);
    document.add(signatureTable);


}

    private void addCustomerDetails(Document document) throws DocumentException
    {
        Paragraph preface = new Paragraph();

        addGaps(" ", document, subFont1, 1 );
        PdfPTable customerDetailsTable = new PdfPTable(6);
        addEmptyLine(preface, 2);

        PdfPCell address = new PdfPCell(new Paragraph("M/s: "));
        address.setBorderWidth(1);
        address.setColspan(3);
        address.setRowspan(3);
        customerDetailsTable.addCell(address);

        PdfPCell invoiceDate = new PdfPCell(new Paragraph("Date: "));
        invoiceDate.setBorderWidth(1);
        invoiceDate.setColspan(3);
        customerDetailsTable.addCell(invoiceDate);

        PdfPCell invoice_no = new PdfPCell(new Paragraph("Invoice no: "));
        invoice_no.setBorderWidth(1);
        invoice_no.setColspan(3);
        customerDetailsTable.addCell(invoice_no);

        PdfPCell extras = new PdfPCell(new Paragraph("Extras: "));
        extras.setBorderWidth(1);
        extras.setColspan(3);
        customerDetailsTable.addCell(extras);
        document.add(customerDetailsTable);

        addGaps(" ", document, subFont1, 1 );

        PdfPTable table2 = new PdfPTable(5);
        float[] columnWidths = new float[] {10f, 30f, 10f, 10f,10f};
        table2.setWidths(columnWidths);
        PdfPCell prod1;
        PdfPCell qty1,sr1,rate1,total1;

        sr1 = new PdfPCell(new Paragraph("Sr no.",subFont2));
        sr1.setBorderWidth(1);
        table2.addCell(sr1);

        prod1 = new PdfPCell(new Paragraph("Description",subFont2));
        prod1.setBorderWidth(1);

        table2.addCell(prod1);
        qty1 = new PdfPCell(new Paragraph("Quantity",subFont2));
        qty1.setBorderWidth(1);
        table2.addCell(qty1);
        rate1 = new PdfPCell(new Paragraph("Rate",subFont2));
        rate1.setBorderWidth(1);
        table2.addCell(rate1);
        total1 = new PdfPCell(new Paragraph("Total",subFont2));

    //    total1.setBorderWidthBottom(2f);
        table2.addCell(total1);


        document.add(table2);



        PdfPTable tableNew = new PdfPTable(1);

        PdfPCell a=new PdfPCell(table2);
        a.setFixedHeight(300f);
        tableNew.addCell(a);
        document.add(tableNew);
    }
    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.setIndentationLeft(225);
        preface.setAlignment(Paragraph.ALIGN_CENTER);
        preface.add(new Paragraph("INVOICE"));

        Paragraph p2 = new Paragraph();
        p2.setIndentationLeft(180);
        p2.setAlignment(Paragraph.ALIGN_CENTER);
        p2.add(new Paragraph("RUSHABH PAREKH AND COMPANY"));

        Paragraph p = new Paragraph();
        p.setIndentationLeft(200);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p.add(new Paragraph("Sunder Garden Malad West"));
        Paragraph p1 = new Paragraph();

        document.add(preface);
        document.add(p2);
        document.add(p);
        document.add(p1);
    }
}
