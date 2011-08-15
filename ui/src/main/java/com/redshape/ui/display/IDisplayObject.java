package com.redshape.ui.display;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.display
 * @date 8/14/11 12:08 AM
 */
public interface IDisplayObject {

	public Integer getWidth();

	public void setWidth( Integer width );

	public Integer getHeight();

	public void setHeight( Integer height );

	public void setVisible( boolean value );

	public void invalidate();

	public void repaint();

}
