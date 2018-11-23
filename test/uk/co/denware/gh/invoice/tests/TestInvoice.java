package uk.co.denware.gh.invoice.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.denware.gh.invoice.Invoice;
import uk.co.denware.gh.invoice.PartDetail;
import uk.co.denware.gh.invoice.JobDetail;
import uk.co.denware.gh.invoice.Layout;
import uk.co.denware.gh.invoice.NameAddressBlock;
import uk.co.denware.gh.invoice.PaperSize;
import uk.co.denware.gh.invoice.creator.InvoiceCreator;

public class TestInvoice {

	private static Invoice invoice;
	private static InvoiceCreator factory;
	private static Logger logger;
	private static String filename;
	
	@BeforeClass
	public static void setup() {
		filename = "/home/dendlel/tmp/test.pdf";
		logger = LogManager.getLogger(TestInvoice.class);
		getInvoiceData(filename);
	}
	
	@Test
	public void createInvoice() {
		File output = new File(filename);
		if( output.delete() ) {
			logger.info("File {} deleted.", filename);
		}
		factory = new InvoiceCreator(invoice);
		factory.createInvoice();
		assertTrue(output.exists());
	}
	


	public static Invoice getInvoiceData(String outputFile) {
		invoice = new Invoice();
		NameAddressBlock customerAdd = new NameAddressBlock();
		NameAddressBlock businessAdd = new NameAddressBlock();
		Layout layout = new Layout();

		PartDetail item1 = new PartDetail();
		item1.setCode("A048901");
		item1.setDescription("Spark plug");
		item1.setPencePerUnit(500);
		item1.setQuantity(4);
		
		PartDetail item2 = new PartDetail();
		item2.setCode("Labour");
		item2.setDescription("Labour");
		item2.setPencePerUnit(3500);
		item2.setQuantity(2.5);
		item2.setUnit("Hrs");
		
		customerAdd.setName1("Emma");
		customerAdd.setName2("Dendle");
		customerAdd.setAdd1("124 Pier Road");
		customerAdd.setAdd2("Uplands");
		customerAdd.setAdd3("Swansea");
		customerAdd.setPostcode("SA99 4GF");
		
		businessAdd.setName1("Franks Super Garage");
		businessAdd.setAdd1("Unit 4A");
		businessAdd.setAdd2("Spanish Industrial Estate");
		businessAdd.setAdd3("Longreach Avenue");
		businessAdd.setAdd4("Swansea");
		businessAdd.setPostcode("SA1 1ER");
		
		JobDetail detail1 = new JobDetail("VRM","Y754BDE");
		JobDetail detail2 = new JobDetail("Mileage", "123504");
		JobDetail detail3 = new JobDetail("Make", "Honda");
		JobDetail detail4 = new JobDetail("Model", "Jazz");
		JobDetail detail5 = new JobDetail("Colour", "Red");
		
		layout.setTopPadding(20);
		layout.setBottomPadding(20);
		layout.setLeftPadding(10);
		layout.setRightPadding(10);
		layout.setSectionBoundaryPadding(5);
		layout.setPaperSize(PaperSize.A4);
		layout.setFontSize(12);
		layout.setLogoScale(0.1f);
		
		invoice.addInvoiceItem(item1);
		invoice.addInvoiceItem(item2);
		invoice.setCustomerAddress(customerAdd);
		invoice.setBusinessAddress(businessAdd);
		invoice.addJobDetail(detail1);
		invoice.addJobDetail(detail2);
		invoice.addJobDetail(detail3);
		invoice.addJobDetail(detail4);
		invoice.addJobDetail(detail5);
		invoice.setCreationDate(LocalDateTime.now());
		invoice.setInvoiceDate(LocalDate.now());
		invoice.setInvoiceNumber(1);
		invoice.setInvoicePrefix("A");
		invoice.setInvoiceVersion(1);
		invoice.setLogoB64("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");
		invoice.setNotes("Breaks will need changing within the next 6 months.");
		invoice.setFilename(outputFile);
		invoice.setLayout(layout);
		return invoice;
	}

	
}
