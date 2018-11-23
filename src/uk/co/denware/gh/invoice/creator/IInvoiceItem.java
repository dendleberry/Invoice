package uk.co.denware.gh.invoice.creator;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDType1Font;

public interface IInvoiceItem {
	
	float getStartX();
	float getStartY();
	Integer getFontSize();
	PDType1Font getFont();
	List<String> getMessage();
	float getMaxWidth() throws IOException;
	float getHeight();
	float getLineHeight();
	
}
