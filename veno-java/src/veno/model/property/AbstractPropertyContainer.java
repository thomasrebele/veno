package veno.model.property;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractPropertyContainer
		implements Iterable<PropertyValue> {
	public List<PropertyValue> list;
	public static final int BASEVALUE = -1;
	private Editable editable = Editable.FALSE;
	private EditableCheck editableInterface = null;
	
	public interface EditableCheck {
		public boolean isEditable(AbstractPropertyContainer container, int index);
	}
	
	public enum Editable {
		TRUE,
		FALSE,
		INTERFACE
	}
	
	protected abstract int getBaseValueCount();
	protected abstract PropertyValue getBaseValue(int index);
	protected abstract void setBaseValue(String str, int index);
	protected Editable isEditableBaseValue(int index) {
		return null;
	}
	
	public AbstractPropertyContainer(List<PropertyValue> list) {
		this.list = list;
	}

	public PropertyValue get(int index) {
		int bvc = getBaseValueCount();
		if(index < bvc) return getBaseValue(index);
		if(list == null) return null;
		return list.get(index - bvc);
	}
	
	public void set(String str, int index) {
		System.out.printf("str " + str + " index " + index);
		int bvc = getBaseValueCount();
		if(index < bvc) {
			setBaseValue(str, index);
			return;
		}
		if(list == null) return;
		// TODO
		PropertyValue p = list.get(index - bvc);
		final String f = str;
		p.setRetrieve(new PropertyValue.Retrieve() {
			@Override
			public String getValue() {
				return f;
			}

			@Override
			public String getName() {
				return null;
			}});
		p.triggerUpdate();
	}
	
	public boolean isEditable(int index) {
		int bvc = getBaseValueCount();
		Editable value;
		if(index < bvc) {
			value = isEditableBaseValue(index);
			if(value == null) value = editable;
		}
		else if(list == null) {
			return false;
		}
		else {
			value = editable;
		}
		
		if(value == null) return false;
		switch (value) {
		case TRUE: return true;
		case FALSE: return false;
		case INTERFACE: return editableInterface == null ? 
				false : editableInterface.isEditable(this, index);
		}
		return false;
	}

	public boolean isEmpty() {
		return false;
	}

	public int size() {
		int size = 0;
		if(list != null) size = list.size();
		return size += getBaseValueCount();
	}
	
	protected PropertyValue createBV(String name, String value) {
		return new PropertyValue(BASEVALUE, BASEVALUE, name, value);
	}
	

	@Override
	public Iterator<PropertyValue> iterator() {
		Iterator<PropertyValue> it = new Iterator<PropertyValue>(){
			private int index = 0;
			@Override
			public boolean hasNext() {
				return index < size();
			}

			@Override
			public PropertyValue next() {
				return get(index++);
			}

			@Override
			public void remove() {
			}};
		return it;
	}
	
	public void setEditable(Editable state) {
		editable = state;
	}
	
	public void setEditableInterface(EditableCheck iface) {
		editableInterface = iface;
	}
}
