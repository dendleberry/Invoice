package uk.co.denware.gh.invoice;

public class PartDetail {

	private String code;
	private String description;
	private double quantity;
	private Integer pencePerUnit;
	private String unit;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public Integer getPencePerUnit() {
		return pencePerUnit;
	}
	public void setPencePerUnit(Integer pencePerUnit) {
		this.pencePerUnit = pencePerUnit;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
