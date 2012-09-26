package com.redshape.search.query;

import com.redshape.search.query.terms.*;

/**
 * Search term builders interface
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query
 * @date 9/5/11 2:06 PM
 */
public interface ISearchTermBuilder {

	/**
	 * Create new named field term by a field name and search term.
	 *
	 * @param name
	 * @param term
	 * @return
	 */
	public IFieldTerm field( String name, ISearchTerm term );

	/**
	 * Create new named field term by field name and list of terms which needs to be
	 * applied
	 *
	 * @param names
	 * @param term
	 * @return
	 */
	public IGroupingTerm field( String[] names, ISearchTerm term, Operation operation );

	/**
	 * Create terms group by a given terms list
	 * @param list
	 * @return
	 */
	public IGroupingTerm group( Operation operation, ISearchTerm... list );

	/**
	 * Create new range term by a given #from# and #to# constraints
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public IBinaryTerm to( ISearchTerm from, ISearchTerm to, RangeType type );

	/**
	 * Create logical AND term by a given left and right terms
	 * @param left
	 * @param right
	 * @return
	 */
	public IBinaryTerm and( ISearchTerm left, ISearchTerm right );

	/**
	 * Create logical OR term by a given left and right terms
	 * @param left
	 * @param right
	 * @return
	 */
    public IBinaryTerm or( ISearchTerm left, ISearchTerm right );

	/**
	 * Create logical NOT term by a given term as operand
	 * @param operand
	 * @return
	 */
    public IUnaryTerm not( ISearchTerm operand );

	/**
	 * Create equal comparison term by a given operand and value
	 * @param operand
	 * @param value
	 * @return
	 */
    public IBinaryTerm eq( IUnaryTerm operand, IUnaryTerm value );

	/**
	 * Create scalar value term by a given parameter as value
	 * @param value
	 * @return
	 */
    public IScalarTerm literal( Object value );
}
