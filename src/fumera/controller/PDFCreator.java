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
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import fumera.model.Siparis;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Tansel Altınel
 */
public class PDFCreator {

    //Sayi to Kelime
        final private static String[] units = { "","","İki","Üç","Dört","Beş","Altı","Yedi","Sekiz","Dokuz", "On",
        "Onbir","Oniki","Onüç","Ondört","Onbeş","Onaltı", "Onyedi", "Onsekiz","Ondokuz"};
        final private static String[] tens = {"","","Yirmi","Otuz","Kırk","Elli","Altmış","Yetmiş","Seksen","Doksan"};
        private String teklif = "";
        
        private BaseColor colorMagenta = new BaseColor(79, 129, 189);
        private BaseColor colorGrey = new BaseColor(153, 153, 153);
        private Font blueFont;
        private Font greyFont;
        private Font blackFont;
        private BaseFont base = null;
    
    public PDFCreator( Siparis siparis, String fileName, String directory){
        // Text values

        try {
            base = BaseFont.createFont( "fumera/fonts/calibri.ttf", "Cp1254", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            FileLogger.hata( e.toString());
            System.out.println( e.toString());
        }
        
        blueFont = new Font( base, 12, Font.NORMAL, colorMagenta);
        greyFont = new Font( base, 12, Font.NORMAL, colorGrey);;
        blackFont = new Font( base, 12, Font.NORMAL, BaseColor.BLACK);
        
        // File
        
        // Document
        Document document = new Document( PageSize.A4, 15, 15, 15, 15);
        
        try {
            //////////////////////
            PdfWriter writer = PdfWriter.getInstance( document, new FileOutputStream( directory + File.separator + fileName + ".pdf"));
            writer.setPageEvent( new HeaderAndFooter());
            document.open();
            document.addAuthor("Mustafa Akarsu");
            document.addCreationDate();
            
            /////////////////////
            // HEADER
            PdfPTable headerTable = new PdfPTable( 2);
            headerTable.setWidthPercentage(100);
            Image image = null;
            try {
                image = Image.getInstance( getClass().getResource("/fumera/icons/logo/logo.png"));
                image.scalePercent( 60f);
            } catch (    BadElementException | IOException e) {
                FileLogger.hata( e.toString());
                System.out.println( e.toString());
            }
            PdfPCell imageCell = new PdfPCell( image);
            imageCell.setHorizontalAlignment( Element.ALIGN_LEFT);
            imageCell.setBorder( Rectangle.NO_BORDER);
            PdfPCell contactCell = new PdfPCell( new Paragraph("info@akarsuguc.com.tr\r\nwww.akarsuguc.com.tr", greyFont));
            contactCell.setHorizontalAlignment( Element.ALIGN_RIGHT);
            contactCell.setVerticalAlignment( Element.ALIGN_MIDDLE);
            contactCell.setBorder( Rectangle.NO_BORDER);
            headerTable.addCell( imageCell);
            headerTable.addCell( contactCell);
            headerTable.setSpacingAfter( 20f);
            headerTable.setSpacingBefore( 5f);
            /////////////////////
            /////////////////////
            // Firma Bilgileri
            blueFont = new Font( base, 12, Font.BOLD, colorMagenta);
            PdfPTable firmaTable = new PdfPTable(2);
            firmaTable.setWidthPercentage( 100f);
            firmaTable.setHorizontalAlignment( Element.ALIGN_LEFT);
            firmaTable.setWidths( new int[]{1,6});
            PdfPCell firma = new PdfPCell( new PdfPCell( new Phrase("Firma :  ", blueFont)));
            firma.setBorder( Rectangle.NO_BORDER);
            firma.setHorizontalAlignment( Element.ALIGN_RIGHT);
            PdfPCell firmaValue = new PdfPCell( new PdfPCell( new Phrase( siparis.getFirma().getFirma_adi(), blackFont)));
            firmaValue.setBorder( Rectangle.NO_BORDER);
            firmaValue.setHorizontalAlignment( Element.ALIGN_LEFT);
            firmaTable.addCell( firma);
            firmaTable.addCell( firmaValue);
            firmaTable.completeRow();
            PdfPCell ilgili = new PdfPCell( new Phrase("İlgili :  ", blueFont));
            ilgili.setBorder( Rectangle.NO_BORDER);
            ilgili.setHorizontalAlignment( Element.ALIGN_RIGHT);
            PdfPCell ilgiliValue = new PdfPCell( new Phrase( siparis.getFirma().getIlgili_adi(), blackFont));
            ilgiliValue.setBorder( Rectangle.NO_BORDER);
            ilgiliValue.setHorizontalAlignment( Element.ALIGN_LEFT);
            firmaTable.addCell( ilgili);
            firmaTable.addCell( ilgiliValue);
            firmaTable.completeRow();
            PdfPCell ePosta = new PdfPCell( new Phrase("E-Posta :  ", blueFont));
            ePosta.setBorder( Rectangle.NO_BORDER);
            ePosta.setHorizontalAlignment( Element.ALIGN_RIGHT);
            PdfPCell ePostaValue = new PdfPCell( new Phrase( siparis.getFirma().getMail(), blackFont));
            ePostaValue.setBorder( Rectangle.NO_BORDER);
            ePostaValue.setHorizontalAlignment( Element.ALIGN_LEFT);
            firmaTable.addCell( ePosta);
            firmaTable.addCell( ePostaValue);
            firmaTable.completeRow();
            PdfPCell telefon = new PdfPCell( new Phrase("Telefon :  ", blueFont));
            telefon.setBorder( Rectangle.NO_BORDER);
            telefon.setHorizontalAlignment( Element.ALIGN_RIGHT);
            PdfPCell telefonValue = new PdfPCell( new Phrase( siparis.getFirma().getTel(), blackFont));
            telefonValue.setBorder( Rectangle.NO_BORDER);
            telefonValue.setHorizontalAlignment( Element.ALIGN_LEFT);
            firmaTable.addCell( telefon);
            firmaTable.addCell( telefonValue);
            firmaTable.completeRow();
            PdfPCell fax = new PdfPCell( new Phrase("Fax :  ", blueFont));
            fax.setBorder( Rectangle.NO_BORDER);
            fax.setHorizontalAlignment( Element.ALIGN_RIGHT);
            PdfPCell faxValue = new PdfPCell( new Phrase( siparis.getFirma().getFax(), blackFont));
            faxValue.setBorder( Rectangle.NO_BORDER);
            faxValue.setHorizontalAlignment( Element.ALIGN_LEFT);
            firmaTable.addCell( fax);
            firmaTable.addCell( faxValue);
            firmaTable.completeRow();
            firmaTable.setSpacingAfter( 20f);
            //firmaTable.setSpacingBefore( 100f);
            /////////////////////
            /////////////////////
            // Tarih
            blueFont = new Font( base, 13, Font.BOLD, colorMagenta);
            PdfPTable tarihTable = new PdfPTable(1);
            tarihTable.setWidthPercentage( 100f);
            PdfPCell tarihCell = new PdfPCell( new Paragraph( siparis.getSiparis_tarih().toString(), blueFont));
            tarihCell.setHorizontalAlignment( Element.ALIGN_RIGHT);
            tarihCell.setBorder( Rectangle.NO_BORDER);
            tarihTable.addCell( tarihCell);
            tarihTable.setSpacingAfter( 5f);
            /////////////////////
            /////////////////////
            // Teklif veya Sipariş
            PdfPTable teklifTable = new PdfPTable( 1);
            blueFont = new Font( base, 15, Font.BOLD, colorMagenta);
            if( siparis.getDurum().equalsIgnoreCase("Teklif"))
                teklif = "Teklif";
            else
                teklif = "Sipariş";
            PdfPCell teklifCell = new PdfPCell( new Paragraph( teklif, blueFont));
            teklifCell.setHorizontalAlignment( Element.ALIGN_CENTER);
            teklifCell.setBorder( Rectangle.NO_BORDER);
            teklifTable.addCell( teklifCell);
            teklifTable.setSpacingAfter( 10f);
            /////////////////////
            /////////////////////
            // Ürünler
            blackFont = new Font( base, 11, Font.BOLD, BaseColor.BLACK);
            PdfPTable urunTable = new PdfPTable( 5);
            urunTable.setKeepTogether( true);
            urunTable.setWidthPercentage( 100f);
            urunTable.setWidths( new int[]{1,8,1,4,4});
            PdfPCell urunHeaderCell1 = new PdfPCell( new Paragraph("No", blackFont));
            urunHeaderCell1.setBorderColor( colorMagenta);
            urunHeaderCell1.setBorderWidth( 2f);
            urunHeaderCell1.setHorizontalAlignment( Element.ALIGN_CENTER);
            urunHeaderCell1.setVerticalAlignment( Element.ALIGN_MIDDLE);
            urunHeaderCell1.setPaddingBottom( 6f);
            PdfPCell urunHeaderCell2 = new PdfPCell( new Paragraph("Ürün Açıklaması", blackFont));
            urunHeaderCell2.setBorderColor( colorMagenta);
            urunHeaderCell2.setBorderWidth( 2f);
            urunHeaderCell2.setHorizontalAlignment( Element.ALIGN_CENTER);
            urunHeaderCell2.setVerticalAlignment( Element.ALIGN_MIDDLE);
            urunHeaderCell2.setPaddingBottom( 6f);
            PdfPCell urunHeaderCell3 = new PdfPCell( new Paragraph("Birim", blackFont));
            urunHeaderCell3.setBorderColor( colorMagenta);
            urunHeaderCell3.setBorderWidth( 2f);
            urunHeaderCell3.setHorizontalAlignment( Element.ALIGN_CENTER);
            urunHeaderCell3.setVerticalAlignment( Element.ALIGN_MIDDLE);
            urunHeaderCell3.setPaddingBottom( 6f);
            PdfPCell urunHeaderCell4 = new PdfPCell( new Paragraph("Birim Fiyatı", blackFont));
            urunHeaderCell4.setBorderColor( colorMagenta);
            urunHeaderCell4.setBorderWidth( 2f);
            urunHeaderCell4.setHorizontalAlignment( Element.ALIGN_CENTER);
            urunHeaderCell4.setVerticalAlignment( Element.ALIGN_MIDDLE);
            urunHeaderCell4.setPaddingBottom( 6f);
            PdfPCell urunHeaderCell5 = new PdfPCell( new Paragraph("Toplam Fiyatı", blackFont));
            urunHeaderCell5.setBorderColor( colorMagenta);
            urunHeaderCell5.setBorderWidth( 2f);
            urunHeaderCell5.setHorizontalAlignment( Element.ALIGN_CENTER);
            urunHeaderCell5.setVerticalAlignment( Element.ALIGN_MIDDLE);
            urunHeaderCell5.setPaddingBottom( 6f);
            urunTable.addCell( urunHeaderCell1);
            urunTable.addCell( urunHeaderCell2);
            urunTable.addCell( urunHeaderCell3);
            urunTable.addCell( urunHeaderCell4);
            urunTable.addCell( urunHeaderCell5);
            urunTable.completeRow();
            blackFont = new Font( base, 11, Font.NORMAL, BaseColor.BLACK);
            for( int i = 0; i < siparis.getUrunler().size(); i++) {
                PdfPCell urunCell1 = new PdfPCell( new Paragraph( (i+1) + "", blackFont));
                urunCell1.setBorderColor( colorMagenta);
                urunCell1.setHorizontalAlignment( Element.ALIGN_CENTER);
                PdfPCell urunCell2 = new PdfPCell( new Paragraph( siparis.getUrunler().get(i).getUrunAdi(), blackFont));
                urunCell2.setBorderColor( colorMagenta);
                PdfPCell urunCell3 = new PdfPCell( new Paragraph( siparis.getUrunler().get(i).getUrunAdedi() + "", blackFont));
                urunCell3.setBorderColor( colorMagenta);
                urunCell3.setHorizontalAlignment( Element.ALIGN_CENTER);
                PdfPCell urunCell4 = new PdfPCell( new Paragraph( siparis.getUrunler().get(i).getUrunFiyati() + "", blackFont));
                urunCell4.setBorderColor( colorMagenta);
                urunCell4.setHorizontalAlignment( Element.ALIGN_RIGHT);
                PdfPCell urunCell5 = new PdfPCell( new Paragraph( (siparis.getUrunler().get(i).getUrunAdedi() *
                        siparis.getUrunler().get(i).getUrunFiyati()) + "" , blackFont));
                urunCell5.setBorderColor( colorMagenta);
                urunCell5.setHorizontalAlignment( Element.ALIGN_RIGHT);
                urunTable.addCell( urunCell1);
                urunTable.addCell( urunCell2);
                urunTable.addCell( urunCell3);
                urunTable.addCell( urunCell4);
                urunTable.addCell( urunCell5);
                urunTable.completeRow();
            }
            urunTable.setSpacingAfter( 30f);
            /////////////////////
            /////////////////////
            // Teklif / Sipariş Bilgileri & Toplam vs
            
            /////////////////////
            /////////////////////
            // Yalnız: bilmem kaç lira
            blackFont = new Font( base, 12, Font.NORMAL, BaseColor.BLACK);
            PdfPTable toplamTable = new PdfPTable( 1);
            toplamTable.setWidthPercentage( 100f);
            PdfPCell toplamCell = new PdfPCell( new Paragraph("              Yalnız: " 
                    + convert( (int)siparis.getToplam()) + " Türk Lirası", blackFont));
            toplamCell.setBorderColor( colorMagenta);
            toplamCell.setBorderWidth( 2f);
            toplamCell.setHorizontalAlignment( Element.ALIGN_LEFT);
            toplamCell.setPaddingBottom( 6f);
            toplamTable.addCell( toplamCell);
            toplamTable.setSpacingAfter( 20f);
            /////////////////////
            /////////////////////
            // Saygılarımla vs
            PdfPTable imzaTable = new PdfPTable( 1);
            blackFont = new Font( base, 12, Font.NORMAL, BaseColor.BLACK);
            String imza =   "Saygılarımla  \r\n" +
                            "Mustafa Akarsu";
            PdfPCell imzaCell = new PdfPCell( new Paragraph( imza, blackFont));
            imzaCell.setHorizontalAlignment( Element.ALIGN_RIGHT);
            imzaCell.setBorder( Rectangle.NO_BORDER);
            imzaTable.addCell( imzaCell);
            
            /////////////////////
            /////////////////////////////////////////////////////////////////////
            ////////////////////
            // Add Everything
            document.add( headerTable);
            document.add( firmaTable);
            document.add( tarihTable);
            document.add( teklifTable);
            document.add( urunTable);
            document.add( toplamTable);
            document.add( imzaTable);
            //document.add( footerTable);
            document.close();
        } catch (FileNotFoundException | DocumentException e) {
            FileLogger.hata( e.toString());
        }
    }
    
    private static String convert( int toplam){
        if( toplam < 20) return units[toplam];
        if( toplam < 100) return tens[toplam/10] + ((toplam % 10 > 0)? " " + convert(toplam % 10):"");
        if( toplam < 1000) return units[toplam/100] + " Yüz" + ((toplam % 100 > 0)?" " + convert(toplam % 100):"");
        if( toplam < 1000000) return convert(toplam / 1000) + " Bin " + ((toplam % 1000 > 0)? " " + convert(toplam % 1000):"") ;
        return convert( toplam / 1000000) + " Million " + ((toplam % 1000000 > 0)? " " + convert(toplam % 1000000):"") ;
    }
    
    private class HeaderAndFooter extends PdfPageEventHelper{
        
        public HeaderAndFooter(){
            super();
        }
        /*
        @Override
        public void onStartPage(PdfWriter writer,Document document) {
            
            PdfContentByte cb = writer.getDirectContent();
            
            String address3 = "info@akarsuguc.com.tr";
            String address4 = "www.akarsuguc.com.tr";
            
            Image image = null;
            try {
                image = Image.getInstance( getClass().getResource("/fumera/icons/logo/logo.png"));
                //image.scalePercent( 60f);
            } catch (    BadElementException | IOException e) {
                FileLogger.hata( e.toString());
                System.out.println( e.toString());
            }
            Phrase headerAdd1 = new Phrase( new Chunk( image, 0, 0));
            Phrase headerAdd2 = new Phrase( address3, greyFont);
            Phrase headerAdd3 = new Phrase( address4, greyFont);
            
            ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, headerAdd1, document.leftMargin(), document.top(), 0);
            ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, headerAdd2, document.rightMargin() - 2f, document.top() + 250, 0);
            ColumnText.showTextAligned( cb, Element.ALIGN_CENTER, headerAdd3, document.rightMargin() + 2f, document.top() - 250, 0);
        }*/
        
        @Override
        public void onEndPage( PdfWriter writer, Document document) {
            
            PdfContentByte cb = writer.getDirectContent();
            /////////////////////
            /////////////////////
            // Footer
            //PdfPTable footerTable = new PdfPTable( 1);
            blueFont = new Font( base, 11, Font.NORMAL, colorMagenta);
            String address1 = "1233 Sokak ( Eski 56 Sok. ) No:86 Ostim OSB / Ankara";
            String address2 = "Telefon : 0312 385 32 37 - 385 32 38      Fax : 0312 386 35 49";            
            //PdfPCell footerCell = new PdfPCell( new Paragraph(address, blueFont));
            //footerCell.setHorizontalAlignment( Element.ALIGN_CENTER);
            //footerCell.setBorder( Rectangle.NO_BORDER);
            //footerTable.addCell( footerCell);
            
            // Footer
            Phrase footerAdd1 = new Phrase(address1, blueFont);
            Phrase footerAdd2 = new Phrase(address2, blueFont);

            ColumnText.showTextAligned( cb, Element.ALIGN_CENTER,  footerAdd1, document.leftMargin() + 250f, document.bottom() + 30, 0);
            ColumnText.showTextAligned( cb, Element.ALIGN_CENTER,  footerAdd2, document.leftMargin() + 250f, document.bottom() + 15, 0);
            
        }
    }
}
