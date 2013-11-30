/*
 * Fumera Ar-Ge Yazılım Müh. İml. San. ve Tic. Ltd. Şti. | Copyright 2012-2013
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions 
 * are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright 
 * notice, this list of conditions, and the following disclaimer. 
 * 
 * 2. Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions, and the following disclaimer in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * 3. The name of the author may not be used to endorse or promote products 
 * derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF TITLE, NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY 
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED 
 * AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR 
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package fumera.controller;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fumera.model.Siparis;
import java.awt.Color;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tansel Altınel
 */
public class PDFCreator {
    
    public PDFCreator( Siparis siparis, String fileName, String directory){

        // Text values
        Font font;
        BaseColor colorMagenta = new BaseColor(79, 129, 189);
        BaseColor colorGrey = new BaseColor(153, 153, 153);
        
        // File
        System.out.println(directory + File.separator + fileName + ".pdf");
        
        // Document
        Document document = new Document();
        
        try {
            //////////////////////
            PdfWriter.getInstance( document, new FileOutputStream( directory + File.separator + fileName + ".pdf"));
            document.open();
            document.addAuthor("Mustafa Akarsu");
            document.addCreationDate();
            
            /////////////////////
            // HEADER
            PdfPTable headerTable = new PdfPTable( 2);
            font = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, colorGrey);
            Image image = null;
            try {
                image = Image.getInstance( getClass().getResource("/fumera/icons/logo/logo.png"));
                image.scaleAbsolute(333f, 100f);
            } catch (    BadElementException | IOException e) {
                FileLogger.hata( e.toString());
                System.out.println( e.toString());
            }
            PdfPCell imageCell = new PdfPCell( image);
            imageCell.setHorizontalAlignment( Element.ALIGN_LEFT);
            imageCell.setBorder( Rectangle.NO_BORDER);
            PdfPCell contactCell = new PdfPCell( new Paragraph("info@akarsuguc.com.tr\r\nwww.akarsuguc.com.tr", font));
            contactCell.setHorizontalAlignment( Element.ALIGN_RIGHT);
            contactCell.setBorder( Rectangle.NO_BORDER);
            headerTable.addCell( imageCell);
            headerTable.addCell( contactCell);
            /////////////////////
            /////////////////////
            // Firma Bilgileri
            
            /////////////////////
            /////////////////////
            // Tarih
            
            /////////////////////
            /////////////////////
            // Teklif veya Sipariş
            
            /////////////////////
            /////////////////////
            // Ürünler
            
            /////////////////////
            /////////////////////
            // Teklif / Sipariş Bilgileri & Toplam vs
            
            /////////////////////
            /////////////////////
            // Yalnız: bilmem kaç lira
            
            /////////////////////
            /////////////////////
            // Saygılarımla vs
            
            /////////////////////
            /////////////////////
            // Adres
            PdfPTable footerTable = new PdfPTable( 1);
            font = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL, colorMagenta);
            String address = "1233 Sokak ( Eski 56 Sok. ) No:86 Ostim OSB / Ankara\r\n" +
                            "Telefon : 0312 385 32 37 - 385 32 38 Fax : 0312 386 35 49";
            PdfPCell footerCell = new PdfPCell( new Paragraph(address, font));
            footerCell.setHorizontalAlignment( Element.ALIGN_CENTER);
            footerCell.setBorder( Rectangle.NO_BORDER);
            footerTable.addCell( footerCell);
            /////////////////////
            document.add( headerTable);
            document.add( footerTable);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            FileLogger.hata( e.toString());
        }
    }
}
