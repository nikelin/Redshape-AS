package com.redshape.form;

import com.redshape.form.decorators.IDecorator;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface for forms and their content.
 * 
 * Needs to implement most common form item. 
 * 
 * @author nikelin
 */
public interface IFormItem extends Serializable {
	
	/**
	 * Set name to field
	 * @param name
	 */
	public void setName( String name );

	/**
	 * Returns field name
	 * @return
	 */
	public String getName();
	
	/**
	 * Returns current form item identifier
	 * @return
	 */
	public String getId();
	
	/**
	 * ID associated with current form item.
	 * After rendering it's parameter will be the value for XML `id` attribute
	 * node.
	 * 
	 * @return
	 */
	public void setId( String id );
	
	/**
	 * Return string representation of current form item after
	 * applying all decorators associated with them.
	 * 
	 * As a rule this method must delegate logic of rendering process
	 * to the external rendering entity such like IFormItemRenderer<?>.
	 * 
	 * @return
	 */
	public String render();
	
	/**
	 * Return string representation of current form item after
	 * applying all decorators associated with them.
	 * 
	 * As a rule this method must delegate logic of rendering process
	 * to the external rendering entity such like IFormItemRenderer<?>.
	 * 
	 * @param mode
	 * @return
	 */
	public String render( RenderMode mode );
	
	/**
	 * Check that specified attribute presents in 
	 * the related collection
	 * @return
	 */
	public boolean hasAttribute( String name );
	
	/**
	 * Return field attributes map.
	 * 
	 * @return
	 */
	public Map<String, Object> getAttributes();
	
	/**
	 * Adopt new attribute to the related attributes collection.
	 * All specified attributes will be rendered as XML-attribute for current item.
	 * 
	 * @param name
	 * @param value
	 */
	public void setAttribute( String name, Object value );
	
	/**
	 * Find item with specified name in related collection
	 * @param <T>
	 * @param name
	 * @return
	 */
	public <T> T getAttribute( String name );
	
	/**
	 * Adopt given objects to the related decorators collection
	 * @param decorators
	 */
	public void setDecorators( IDecorator[] decorators );
	
	/**
	 * Adopt new decorator to the related decorators collection
	 * @param decorator
	 */
	public void setDecorator( IDecorator decorator );
	
	/**
	 * Check is that given decorator type was applied to the current 
	 * item
	 * 
	 * @return
	 */
	public boolean hasDecorator( Class<? extends IDecorator> decorator );

	/**
	 * Adopt given objects to the related decorators collection
	 * @param decorators
	 */
	public void setDecorators(List<IDecorator> decorators);

	/**
	 * Return related decorators collection.
	 * @return
	 */
	public Collection<IDecorator> getDecorators();
	
	/**
	 * Unbind all decorators associated with current field.
	 */
	public void clearDecorators();
	
	/**
	 * Proceed validation
	 * @return 
	 */
	public boolean isValid();
	
	/**
	 * Return current item context (sub form / root )
	 * 
	 * @return
	 */
	public IForm getContext();
	
	/**
	 * Change current item context (after adopting as another context structure part)
	 * 
	 * @param form
	 */
	public void setContext( IForm form );

    /**
     * Reset validation result for current field and its children
     * elements
     */
    public void resetMessages();

    /**
     * Reset state
     */
    public void resetState();

	/**
	 * Return full name for the current field.
	 * 
	 * Full name includes not only field self name but also 
	 * path from root form which contains all sub form names as 
	 * a nodes separated by "." (dot)
	 * 
	 * @return
	 */
	public String getCanonicalName();

	/**
	 * Add error message to the given field
	 *
	 * @param message
	 */
	public void addMessage( String message );

	public List<String> getMessages();
}
