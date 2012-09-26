package com.redshape.form.fields;

public class InputField extends AbstractField<String> {
	private static final long serialVersionUID = -6803108853561548397L;

	public enum Type {
		TEXT,
		PASSWORD,
		HIDDEN,
		FILE,
		SUBMIT,
		RESET
	}
	
	private InputField.Type type;
	
	public InputField( InputField.Type type ) {
		this( type, null );
	}
	
	public InputField( InputField.Type type, String id ) {
		this( type, id, id);
	}
	
	public InputField( InputField.Type type, String id, String name ) {
		super(id, name);
		
		this.type = type;
	}
	
	public InputField.Type getType() {
		return this.type;
	}

    @Override
    public void resetState() {
        if ( this.getType().equals(InputField.Type.SUBMIT ) ) {
            return;
        }

        super.resetState();
    }

}
