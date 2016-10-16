package com.example.rushabh.clothesrack;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by tushar on 9/25/2016.
 */

public class BarCodePrint {

    View myView;
    TextView BarCodeValues;
    Button printBarCodes;
    ArrayList<BarCodePrintModel> barCodePrintModel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return myView;
    }

    public void getDataForBarCodePrint(ArrayList<BarCodePrintModel> barCodeData)
    {
        barCodePrintModel = new ArrayList<BarCodePrintModel>(barCodeData);
        if(barCodePrintModel!=null && barCodePrintModel.size()>0) {
            try {
                printBarCodeValues();
            }
            catch (Exception e) {
                Log.v("", e.getMessage().toString());
            }

}
    }

    public Bitmap createQRCode(String qrInputText) {
        Point point = new Point();
        //display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            //ImageView myImage = (ImageView) myView.findViewById(R.id.img_qr_code_image);
            //myImage.setImageBitmap(bitmap);
            return bitmap;

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;

    }

    public void printBarCodeValues() throws FileNotFoundException, DocumentException {
        //String value = ((TextView) myView.findViewById(R.id.barCodeValues)).getText().toString();

        //getDataForBarCodePrint(Integer.parseInt(value));

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
        document.setMargins(10, 10, 100, 10);
        document.open();

        document.add(SetDataForPrint(barCodePrintModel.size()));


        document.close();
        //Toast.makeText(BarCodePrint, "PDF print Complete", Toast.LENGTH_LONG).show();

    }

    public PdfPTable SetDataForPrint(int size) throws FileNotFoundException, DocumentException
    {

        PdfPTable BarCodeTable = new PdfPTable(5);
        BarCodeTable.setTotalWidth(150);
        try {

            for (int i = 0; i < size; i++) {

                Bitmap bmp = createQRCode(barCodePrintModel.get(i).ItemID);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.scaleAbsolute(75, 75);

                PdfPCell cell = new PdfPCell();
                cell.addElement(image);

                cell.addElement(setBarCodeLabel(barCodePrintModel.get(i).ItemID));
                cell.addElement(setBarCodeLabel(barCodePrintModel.get(i).Product));
                cell.addElement(setBarCodeLabel(barCodePrintModel.get(i).Brand));
                cell.addElement(setBarCodeLabel(barCodePrintModel.get(i).Size));

                BarCodeTable.addCell(cell);

            }
            return BarCodeTable;
        } catch (IOException ex) {

            return null;
        }
    }

    public Paragraph setBarCodeLabel(String value)
    {
        Paragraph tempPara = new Paragraph(value);
        tempPara.setAlignment(Element.ALIGN_CENTER);
        return tempPara;
    }

}
