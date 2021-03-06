/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.streaming.cql.semanticanalyzer.parser.context;

import com.huawei.streaming.cql.exception.SemanticAnalyzerException;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.ExpressionDescribe;
import com.huawei.streaming.cql.semanticanalyzer.analyzecontext.expressiondesc.NotExpressionDesc;
import com.huawei.streaming.cql.semanticanalyzer.parsecontextreplacer.ParseContextReplacer;
import com.huawei.streaming.cql.semanticanalyzer.parsecontextwalker.ParseContextWalker;

/**
 * 逻辑表达式And的语法分析结果
 * 
 */
public class LogicExpressionNotContext extends BaseExpressionParseContext
{
    private boolean isNot = false;
    
    //EqualRelationExpressionContext
    private BaseExpressionParseContext expression;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return isNot ? " NOT " + expression.toString() : expression.toString();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void walkChild(ParseContextWalker walker)
    {
        walkExpression(walker, expression);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void walkChildAndReplace(ParseContextReplacer replacer)
    {
        if (replacer.isChildsReplaceable(expression))
        {
            expression = replacer.createReplaceParseContext();
        }
        else
        {
            expression.walkChildAndReplace(replacer);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ExpressionDescribe createExpressionDesc()
        throws SemanticAnalyzerException
    {
        if (isNot)
        {
            return new NotExpressionDesc(expression.createExpressionDesc(getSchemas()));
        }
        return expression.createExpressionDesc(getSchemas());
    }
    
    public boolean isNot()
    {
        return isNot;
    }
    
    public void setNot(boolean isnot)
    {
        this.isNot = isnot;
    }
    
    public BaseExpressionParseContext getExpression()
    {
        return expression;
    }
    
    public void setExpression(BaseExpressionParseContext expression)
    {
        this.expression = expression;
    }
    
}
