package uk.co.denware.gh.invoice;

import java.util.ArrayList;
import java.util.List;

public class NameAddressBlock {
	
	private String name1, name2, add1, add2, add3, add4, postcode;

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getAdd4() {
		return add4;
	}

	public void setAdd4(String add4) {
		this.add4 = add4;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public List<String> getAddress(){
		List<String> result = new ArrayList<>();
		if( (name1 != null) && !(name1.isEmpty()) ) {
			result.add(name1);
		}
		if( (name2 != null) && !(name2.isEmpty()) ) {
			result.add(name2);
		}
		if( (add1 != null) && !(add1.isEmpty()) ) {
			result.add(add1);
		}
		if( (add2 != null) && !(add2.isEmpty()) ) {
			result.add(add2);
		}
		if( (add3 != null) && !(add3.isEmpty()) ) {
			result.add(add3);
		}
		if( (add4 != null) && !(add4.isEmpty()) ) {
			result.add(add4);
		}
		if( (postcode != null) && !(postcode.isEmpty()) ) {
			result.add(postcode);
		}
		return result;
	}

}
