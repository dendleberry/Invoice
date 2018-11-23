package uk.co.denware.gh.invoice;

public class Layout {

	private float topPadding, bottomPadding, leftPadding, rightPadding, sectionBoundaryPadding, logoScale;
	private PaperSize paperSize;
	private int fontSize;
	
	public float getTopPadding() {
		return topPadding;
	}
	public void setTopPadding(float topPadding) {
		this.topPadding = topPadding;
	}
	public float getBottomPadding() {
		return bottomPadding;
	}
	public void setBottomPadding(float bottomPadding) {
		this.bottomPadding = bottomPadding;
	}
	public float getLeftPadding() {
		return leftPadding;
	}
	public void setLeftPadding(float leftPadding) {
		this.leftPadding = leftPadding;
	}
	public float getRightPadding() {
		return rightPadding;
	}
	public void setRightPadding(float rightPadding) {
		this.rightPadding = rightPadding;
	}
	
	public float getSectionBoundaryPadding() {
		return sectionBoundaryPadding;
	}
	public void setSectionBoundaryPadding(float sectionBoundaryPadding) {
		this.sectionBoundaryPadding = sectionBoundaryPadding;
	}
	public float getLogoScale() {
		return logoScale;
	}
	public void setLogoScale(float logoScale) {
		this.logoScale = logoScale;
	}
	public PaperSize getPaperSize() {
		return paperSize;
	}
	public void setPaperSize(PaperSize paperSize) {
		this.paperSize = paperSize;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	
}
