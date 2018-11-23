package uk.co.denware.gh.invoice.creator;

import static uk.co.denware.gh.invoice.tests.TestInvoice.getInvoiceData;

public class Main {

	public static void main(String[] args) {
		InvoiceCreator factory = new InvoiceCreator(getInvoiceData("/home/dendlel/tmp/test.pdf"));
		factory.createInvoice();
		
	}

}
