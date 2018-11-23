package uk.co.denware.gh.invoice.creator;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class InvoiceItem implements IInvoiceItem {

	private float startX;
	private float startY;
	private Integer fontSize;
	private PDType1Font font;
	private List<String> message;
	Logger logger = LogManager.getLogger(InvoiceItem.class);
	
	
	/**
	 * @param msg the message
	 * @param x the starting x position
	 * @param y the starting y position
	 * @param font the font
	 * @param fontSize the font size
	 */
	public InvoiceItem(List<String> msg, float x, float y, PDType1Font font, Integer fontSize) {
		this.message=msg;
		this.startX=x;
		this.startY=y;
		this.font=font;
		this.fontSize=fontSize;
	}
	
	public InvoiceItem(PDType1Font font, Integer fontSize) {
		this.font=font;
		this.fontSize=fontSize;
	}
	
	private InvoiceItem() {
		
	}
	
	public void setStartX(float startX) {
		this.startX = startX;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

	@Override
	public float getStartX() {
		return startX;
	}

	@Override
	public float getStartY() {
		return startY;
	}

	@Override
	public Integer getFontSize() {
		return fontSize;
	}

	@Override
	public PDType1Font getFont() {
		return font;
	}

	@Override
	public List<String> getMessage() {
		return message;
	}
	
	@Override
	public float getMaxWidth() throws IOException {
		logger.traceEntry("getMaxWidth()");
		float result = 0f;
		for( String element : message ) {
			float comparator = font.getStringWidth(element) / 1000 * fontSize;
			if( comparator > result ) {
				result = comparator;
			}
		}
		logger.traceExit(result);
		return result;
	}
	
	@Override
	public float getHeight() {
		logger.traceEntry("getHeight()");
		float result = 0f;
		result = getLineHeight() * message.size();
		logger.traceExit(result);
		return result;
	}
	
	@Override
	public float getLineHeight() {
		logger.traceEntry("getLineHeight()");
		float result = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
		logger.traceExit(result);
		return result;
	}

	@Override
	public String toString() {
		return "InvoiceItem [startX=" + startX + ", startY=" + startY + ", fontSize=" + fontSize + ", font=" + font
				+ ", message=" + message + "]";
	}
	

}
