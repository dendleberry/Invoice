package uk.co.denware.gh.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Invoice {

	private String invoicePrefix;
	private Integer invoiceNumber;
	private Integer invoiceVersion;
	private LocalDateTime creationDate;
	private Map<Integer, PartDetail> invoiceItems;
	private NameAddressBlock businessAddress;
	private NameAddressBlock customerAddress;
	private LocalDate invoiceDate;
	private Map<Integer,JobDetail> jobDetails;
	private String notes;
	private String filename;
	private String logoB64;
	private Layout dimensions;
	
	public Invoice() {
		invoiceItems = new TreeMap<>();
		jobDetails = new TreeMap<>();
	}
	
	public String getInvoicePrefix() {
		return invoicePrefix;
	}
	public void setInvoicePrefix(String invoicePrefix) {
		this.invoicePrefix = invoicePrefix;
	}
	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Integer getInvoiceVersion() {
		return invoiceVersion;
	}
	public void setInvoiceVersion(Integer invoiceVersion) {
		this.invoiceVersion = invoiceVersion;
	}
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public void addInvoiceItem(PartDetail item) {
		Integer nextIdx;
		if( invoiceItems.size() != 0 ) {
			nextIdx = Collections.max(invoiceItems.keySet()) + 1;
		} else {
			nextIdx = 0;
		}
		invoiceItems.put(nextIdx, item);
	}
	public Map<Integer, PartDetail> getInvoiceItems() {
		return invoiceItems;
	}
	public void setInvoiceItems(Map<Integer, PartDetail> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}
	public NameAddressBlock getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(NameAddressBlock businessAddress) {
		this.businessAddress = businessAddress;
	}
	public NameAddressBlock getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(NameAddressBlock customerAddress) {
		this.customerAddress = customerAddress;
	}
	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public Map<Integer, JobDetail> getJobDetails() {
		return jobDetails;
	}
	public void addJobDetail(JobDetail item) {
		Integer nextIdx;
		if( jobDetails.size() != 0 ) {
			nextIdx= Collections.max(jobDetails.keySet()) + 1;
		} else {
			nextIdx=0;
		}
		
		jobDetails.put(nextIdx, item);
	}
	public void setJobDetails(Map<Integer, JobDetail> jobDetails) {
		this.jobDetails = jobDetails;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getLogoB64() {
		return logoB64;
	}
	public void setLogoB64(String logoB64) {
		this.logoB64 = logoB64;
	}
	public Layout getLayout() {
		return dimensions;
	}
	public void setLayout(Layout dimensions) {
		this.dimensions = dimensions;
	}
	
}
