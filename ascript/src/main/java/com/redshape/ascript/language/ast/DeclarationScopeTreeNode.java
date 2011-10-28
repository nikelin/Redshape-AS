package com.redshape.ascript.language.ast;

import com.redshape.ascript.language.ast.AbstractSyntaxTreeNode;

/**
 * @author nikelin
 * @date 19/04/11
 * @package com.redshape.ascript.language.ast
 */
public class DeclarationScopeTreeNode extends AbstractSyntaxTreeNode
									  implements IScopeTreeNode {

	@Override
	public boolean isComposed() {
		return false;
	}

	@Override
	public String toString() {
		return "{Function End}";
	}

}
