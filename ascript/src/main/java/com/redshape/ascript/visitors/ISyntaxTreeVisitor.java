/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ascript.visitors;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.IEvaluator;
import com.redshape.ascript.language.ast.*;

public interface ISyntaxTreeVisitor {

	public void setEvaluatorContext(IEvaluator evaluator);

	public <T> T visit(ISyntaxTree tree) throws EvaluationException;

	public <T> T visit(ScopeTreeNode node) throws EvaluationException;
	
	public <T> T visit(FunctionTreeNode node) throws EvaluationException;
	
	public <T> T visit(DeclarationTreeNode node) throws EvaluationException;
	
	public Object visit(VariableTreeNode node) throws EvaluationException;
	
	public Object visit(LiteralTreeNode node) throws EvaluationException;
	
}
