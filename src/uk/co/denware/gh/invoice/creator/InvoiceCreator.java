package uk.co.denware.gh.invoice.creator;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import uk.co.denware.gh.invoice.Invoice;
import uk.co.denware.gh.invoice.JobDetail;
import uk.co.denware.gh.invoice.exceptions.ImageCreationException;

public class InvoiceCreator {

	private Invoice invoice;
	private float startX, startY, cursorX, cursorY, maxX , maxY, minX, minY, yFromTop, maxSectionHeight;
	private float centerX, centerY;
	private float lineHeight;
	private static final float MM_CONVERSION = 2.83464585714f; //pixels per mm
	
	private PDRectangle pageSize;
	Logger logger;

	public InvoiceCreator(Invoice invoice) {
		logger = LogManager.getLogger(InvoiceCreator.class);
		this.invoice = invoice;
	}

	private InvoiceCreator() {

	}

	public void createInvoice() {
		logger.traceEntry();
		setPageSize();
		calcMaxDimensions();
		calcMinDimensions();
		setStartingPosition();
		setPageCentres();
		
		
		try (PDDocument doc = new PDDocument()) {
			PDPage page = new PDPage(pageSize);
			doc.addPage(page);

			try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
				
				cursorX = startX;
				cursorY = startY;
				
				PDImageXObject img = getPdImage(doc, invoice.getLogoB64());
				
				addLogo(img, contents);
				addInvoiceNumber(img, contents);
				addBusinessAddress(contents);
				addSectionBoundary(contents);
				addJobDetails(contents);
				addSectionBoundary(contents);

				contents.close();
			}

			doc.save(invoice.getFilename());
			doc.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.fatal(e.getMessage());
		} catch (IllegalStateException e2) {
			e2.printStackTrace();
			logger.fatal(e2.getMessage());
		} catch (Exception e3) {
			e3.printStackTrace();
			logger.fatal(e3.getMessage());
		}
	}
	
	private void setPageCentres() {
		centerX = pageSize.getWidth() / 2;
		centerY = pageSize.getHeight() / 2;
	}

	private byte[] decodeBase64String(String b64) {
		byte[] result;
		result = Base64.getDecoder().decode(b64);
		logger.info("Byte array length ={}", result.length);
		return result;
	}
	
	private BufferedImage getBufferedImage(byte[] imageBytes) throws IOException, ImageCreationException {
		BufferedImage result = ImageIO.read(new ByteArrayInputStream(imageBytes));
		if( result == null ) {
			throw new ImageCreationException("Error reading input stream.");
		}
		logger.traceExit(result);
		return result;
	}
	
	private PDImageXObject getPdImage(PDDocument doc, String b64String) throws IOException, ImageCreationException {
		byte[] byteArray = decodeBase64String(b64String);
		if( byteArray == null || byteArray.length == 0) {
			throw new IOException("Byte array is empty or null.");
		}
		BufferedImage bi = getBufferedImage(byteArray);
		if( bi == null ) {
			throw new IOException("BufferedImage is null.");
		}
		PDImageXObject result =  LosslessFactory.createFromImage(doc,bi);
		logger.traceExit(result);
		return result;
	}

	private void setPageSize() {
		switch (invoice.getLayout().getPaperSize()) {
		case A3:
			pageSize = PDRectangle.A3;
			break;
		case A4:
			pageSize = PDRectangle.A4;
			break;
		case A5:
			pageSize = PDRectangle.A5;
			break;
		default:
			pageSize = PDRectangle.A4;
			break;
		}
		logger.info("Page size set to {}, dimensions of page are {}x {}y.", invoice.getLayout().getPaperSize(), pageSize.getUpperRightX(), pageSize.getUpperRightY());
	}
	
	private PDType1Font getFont(String font, String style) {
		PDType1Font result = null;
		String catString = font.toLowerCase().trim() + style.toUpperCase().trim();
		switch (catString) {
		case "helveticaNONE":
			result = PDType1Font.HELVETICA;
			break;
		case "helveticaBOLD":
			result = PDType1Font.HELVETICA_BOLD;
			break;
		case "courierNONE":
			result = PDType1Font.COURIER;
			break;
		case "courierBOLD":
			result = PDType1Font.COURIER_BOLD;
			break;
		case "timesNONE":
			result = PDType1Font.TIMES_ROMAN;
			break;
		case "timesBOLD":
			result = PDType1Font.TIMES_BOLD;
			break;
		default:
			result = PDType1Font.COURIER;
			break;
		}
		return result;
	}
	
	private void calcMaxDimensions() {
		maxX = pageSize.getUpperRightX() - getPxFromMm(invoice.getLayout().getRightPadding());
		maxY = pageSize.getUpperRightY() - getPxFromMm(invoice.getLayout().getTopPadding());
		yFromTop = 0f;
		maxSectionHeight = 0f;
		logger.info("Max dimensions set to {}x {}y {}yFromTop {}maxSectionHeight", maxX, maxY, yFromTop,maxSectionHeight);
	}
	
	private void calcMinDimensions() {
		minX = getPxFromMm(invoice.getLayout().getLeftPadding());
		minY = getPxFromMm(invoice.getLayout().getBottomPadding());
		logger.info("Max dimensions set to {}x {}y", maxX, maxY);
	}
	
	private void setStartingPosition() {
		startX = minX;
		startY = maxY;
		logger.info("Start dimensions set to {}x {}y", startX, startY);
	}
	
	private float getPxFromMm(float mm) {
		logger.traceEntry("getPxFromMm({})", mm);
		float result = mm * MM_CONVERSION;
		logger.traceExit(result);
		return result;
	}
	
	
	private float getImageHeight(PDImageXObject img) {
		logger.traceEntry("getImageHeight({})", img);
		float result = img.getHeight() * invoice.getLayout().getLogoScale();
		logger.traceExit(result);
		return result;
	}
	
	private float getImageWidth(PDImageXObject img) {
		logger.traceEntry("getImageWidth({})", img);
		float result = img.getWidth() * invoice.getLayout().getLogoScale();
		logger.traceExit(result);
		return result;
	}
	
	private void updateCursorY(float y) {
		logger.traceEntry("updateCursorY({})", y);
		if( cursorY >  y ) {
			cursorY = y;
			logger.trace("cursorY updated.");
		}
		logger.traceExit("Y position now {}.", cursorY);
	}
	
	private void addInvoiceItem(PDPageContentStream cs, InvoiceItem ii) throws IOException {
		logger.traceEntry("addInvoiceItem({}, {}", cs, ii);
		cs.beginText();
		cs.setFont(ii.getFont(), ii.getFontSize());
		float x = ii.getStartX();
		float y = ii.getStartY();
		float endY = y;
		lineHeight = ii.getLineHeight();
		cs.newLineAtOffset(x, y + lineHeight);
		for( String line : ii.getMessage() ) {
			cs.newLineAtOffset(0f, lineHeight * -1);
			cs.showText(line);
			endY = endY - lineHeight;
		}
		cs.endText();
		updateCursorY(endY);
	}
	
	private void addSectionBoundary(PDPageContentStream cs) throws IOException {
		float boundaryPadding = getPxFromMm(invoice.getLayout().getSectionBoundaryPadding());
		float y = (cursorY + lineHeight) - boundaryPadding;
		cs.moveTo(cursorX, y);
		cs.lineTo(maxX, y);
		cs.stroke();
		updateCursorY(y - lineHeight - boundaryPadding);
	}
	
	private void addJobDetails(PDPageContentStream cs) throws IOException {
		logger.traceEntry("addJobDetails({})", cs);
		InvoiceItem jobDetails = new InvoiceItem(getFont("Helvetica","none"), 12);
		Map<Integer,JobDetail> sortedMap = new TreeMap<>(invoice.getJobDetails());
		List<JobDetail> list = new ArrayList<>(sortedMap.values());
		logger.trace("Job Details list size is {}, first title = {} value = {}", list.size(), list.get(0).getTitle(), list.get(0).getValue());
		
		List<String> titles = new ArrayList<>();
		List<String> values = new ArrayList<>();
		for( JobDetail jd : list ) {
			titles.add(jd.getTitle());
			values.add(jd.getValue());
		}
		
		float x = cursorX;
		float y = cursorY;
		
		jobDetails.setMessage(titles);
		jobDetails.setStartX(x);
		jobDetails.setStartY(y);
		addInvoiceItem(cs, jobDetails);
		
		x = x + jobDetails.getMaxWidth() + getPxFromMm(5f);
		
		jobDetails.setMessage(values);
		jobDetails.setStartX(x);
		addInvoiceItem(cs, jobDetails);
	}
	
	private void addLogo(PDImageXObject img, PDPageContentStream cs) throws IOException, IllegalStateException {
		logger.traceEntry("addLogo({}, {})", img, cs);
		float imgHeight = getImageHeight(img);
		float x = cursorX;
		float y = cursorY - imgHeight;
		updateCursorY(y);
		cs.drawImage(img, x, y, getImageWidth(img), getImageHeight(img));
	}
	
	private void addInvoiceNumber(PDImageXObject img, PDPageContentStream cs) throws IOException {
		logger.traceEntry("addInvoiceNumber({}, {})", img, cs);
		String msg = "INVOICE NO. " + invoice.getInvoicePrefix() + invoice.getInvoiceNumber();
		List<String> message = new ArrayList<>();
		message.add(msg);
		
		InvoiceItem invoiceNumber = new InvoiceItem(getFont("Helvetica","none"), 24);
		invoiceNumber.setMessage(message);
		
		float widthOfMsg = invoiceNumber.getMaxWidth();
		float x = centerX - (widthOfMsg / 2); 
		float y = cursorY + (getImageHeight(img) / 2);
		
		invoiceNumber.setStartX(x);
		invoiceNumber.setStartY(y);
		addInvoiceItem(cs, invoiceNumber);
	}
	
	private void addBusinessAddress(PDPageContentStream cs) throws IOException {
		logger.traceEntry("addBusinessAddress({})", cs);
		List<String> address = invoice.getBusinessAddress().getAddress();
		
		InvoiceItem businessAddress = new InvoiceItem(getFont("Helvetica","none"), 12);
		businessAddress.setMessage(address);
		
		float widestPoint = businessAddress.getMaxWidth();
		float x = maxX - widestPoint;
		float y = maxY;
		
		businessAddress.setStartX(x);
		businessAddress.setStartY(y);
		
		addInvoiceItem(cs, businessAddress);
	}
}
