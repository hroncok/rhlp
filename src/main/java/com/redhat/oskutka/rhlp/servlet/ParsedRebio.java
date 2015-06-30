package com.redhat.oskutka.rhlp.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.annotation.WebServlet;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Servlet implementation class Rebio
 */
@WebServlet("/ParsedRebio")
public class ParsedRebio extends ParsingRestaurantGetter {
	private static final long serialVersionUID = 3901775532572221827L;

	protected String getUrl() {
        return "http://www.rebio.cz/Rebio-Park/Nase-nabidka/Jidelni-listek-Rebio-Park/2897.file.ashx";
    }
	
	protected String[] getDays() {
		return new String[]{"Pondělí", "Úterý", "Středa", "Čtvrtek", "Pátek", "Přijímáme objednávky", "Neděle"};
	}

	protected String getDayOpeningTag() {
		return "";
	}
	
	protected String getDayClosingTag() {
		return "";
	}
	protected String getFreshMenuHTML() {
        try
        {
            URL url = new URL(getUrl());
            PDFParser parser = new PDFParser((InputStream) url.getContent());
            parser.parse();
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            String parsedText = stripper.getText(new PDDocument(parser.getDocument()));
            return "<pre>" + parseHTML(parsedText) + "</pre>";
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
