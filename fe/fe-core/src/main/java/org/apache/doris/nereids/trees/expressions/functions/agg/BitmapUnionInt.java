// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.nereids.trees.expressions.functions.agg;

import org.apache.doris.catalog.FunctionSignature;
import org.apache.doris.nereids.exceptions.AnalysisException;
import org.apache.doris.nereids.trees.expressions.Expression;
import org.apache.doris.nereids.trees.expressions.functions.AlwaysNotNullable;
import org.apache.doris.nereids.trees.expressions.functions.ExplicitlyCastableSignature;
import org.apache.doris.nereids.trees.expressions.shape.UnaryExpression;
import org.apache.doris.nereids.trees.expressions.visitor.ExpressionVisitor;
import org.apache.doris.nereids.types.BigIntType;
import org.apache.doris.nereids.types.BitmapType;
import org.apache.doris.nereids.types.DataType;
import org.apache.doris.nereids.types.IntegerType;
import org.apache.doris.nereids.types.SmallIntType;
import org.apache.doris.nereids.types.TinyIntType;
import org.apache.doris.nereids.types.coercion.Int64OrLessType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * AggregateFunction 'bitmap_union_int'. This class is generated by GenerateFunction.
 */
public class BitmapUnionInt extends AggregateFunction
        implements UnaryExpression, ExplicitlyCastableSignature, AlwaysNotNullable {

    public static final List<FunctionSignature> SIGNATURES = ImmutableList.of(
            FunctionSignature.ret(BigIntType.INSTANCE).args(SmallIntType.INSTANCE),
            FunctionSignature.ret(BigIntType.INSTANCE).args(TinyIntType.INSTANCE),
            FunctionSignature.ret(BigIntType.INSTANCE).args(IntegerType.INSTANCE)
    );

    /**
     * constructor with 1 argument.
     */
    public BitmapUnionInt(Expression arg) {
        super("bitmap_union_int", arg);
    }

    /**
     * constructor with 1 argument.
     */
    public BitmapUnionInt(boolean distinct, Expression arg) {
        this(arg);
    }

    @Override
    public void checkLegalityBeforeTypeCoercion() {
        DataType argumentType = getArgumentType(0);
        if (!(argumentType instanceof Int64OrLessType)) {
            throw new AnalysisException("BITMAP_UNION_INT params only support TINYINT or SMALLINT or INT");
        }
    }

    @Override
    protected List<DataType> intermediateTypes() {
        return ImmutableList.of(BitmapType.INSTANCE);
    }

    /**
     * withDistinctAndChildren.
     */
    @Override
    public BitmapUnionInt withDistinctAndChildren(boolean distinct, List<Expression> children) {
        Preconditions.checkArgument(children.size() == 1);
        return new BitmapUnionInt(children.get(0));
    }

    @Override
    public <R, C> R accept(ExpressionVisitor<R, C> visitor, C context) {
        return visitor.visitBitmapUnionInt(this, context);
    }

    @Override
    public List<FunctionSignature> getSignatures() {
        return SIGNATURES;
    }
}
