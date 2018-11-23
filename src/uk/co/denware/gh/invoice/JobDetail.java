package uk.co.denware.gh.invoice;

public class JobDetail {

	private String title, value;
	
	public JobDetail(String title, String value) {
		this.title=title;
		this.value=value;
	}
	
	private JobDetail() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "JobDetail [title=" + title + ", value=" + value + "]";
	}
	
}
