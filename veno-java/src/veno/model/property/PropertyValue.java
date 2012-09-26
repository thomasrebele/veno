package veno.model.property;

public class PropertyValue {
	int id;
	int propertyId;
	String name;
	String value;
	Retrieve retrieve;
	
	public interface Retrieve {
		String getValue();
		String getName();
	}
	
	public PropertyValue(int id, int propertyId, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.propertyId = propertyId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public void triggerUpdate() {
		String newname = retrieve.getName();
		name = newname == null ? name : newname;
		String newvalue = retrieve.getValue();
		value = newvalue == null ? value : newvalue;
	}
	
	// TODO: remove
	public void setRetrieve(Retrieve retrieve) {
		this.retrieve = retrieve;
	}
}

