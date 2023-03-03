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

package org.apache.doris.nereids.trees.expressions.functions.scalar;

import org.apache.doris.catalog.FunctionSignature;
import org.apache.doris.nereids.trees.expressions.Expression;
import org.apache.doris.nereids.trees.expressions.functions.AlwaysNullable;
import org.apache.doris.nereids.trees.expressions.shape.UnaryExpression;
import org.apache.doris.nereids.trees.expressions.visitor.ExpressionVisitor;
import org.apache.doris.nereids.types.ArrayType;
import org.apache.doris.nereids.types.BigIntType;
import org.apache.doris.nereids.types.BooleanType;
import org.apache.doris.nereids.types.DataType;
import org.apache.doris.nereids.types.DateTimeType;
import org.apache.doris.nereids.types.DateTimeV2Type;
import org.apache.doris.nereids.types.DateType;
import org.apache.doris.nereids.types.DateV2Type;
import org.apache.doris.nereids.types.DecimalV2Type;
import org.apache.doris.nereids.types.DoubleType;
import org.apache.doris.nereids.types.FloatType;
import org.apache.doris.nereids.types.IntegerType;
import org.apache.doris.nereids.types.LargeIntType;
import org.apache.doris.nereids.types.SmallIntType;
import org.apache.doris.nereids.types.TinyIntType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * ScalarFunction 'array_min'. This class is generated by GenerateFunction.
 */
public class ArrayMin extends CompatibleTypeArrayFunction
        implements UnaryExpression, AlwaysNullable {

    public static final List<FunctionSignature> SIGNATURES = ImmutableList.of(
            FunctionSignature.ret(BooleanType.INSTANCE).args(ArrayType.of(BooleanType.INSTANCE)),
            FunctionSignature.ret(TinyIntType.INSTANCE).args(ArrayType.of(TinyIntType.INSTANCE)),
            FunctionSignature.ret(SmallIntType.INSTANCE).args(ArrayType.of(SmallIntType.INSTANCE)),
            FunctionSignature.ret(IntegerType.INSTANCE).args(ArrayType.of(IntegerType.INSTANCE)),
            FunctionSignature.ret(BigIntType.INSTANCE).args(ArrayType.of(BigIntType.INSTANCE)),
            FunctionSignature.ret(LargeIntType.INSTANCE).args(ArrayType.of(LargeIntType.INSTANCE)),
            FunctionSignature.ret(FloatType.INSTANCE).args(ArrayType.of(FloatType.INSTANCE)),
            FunctionSignature.ret(DoubleType.INSTANCE).args(ArrayType.of(DoubleType.INSTANCE)),
            FunctionSignature.ret(DecimalV2Type.SYSTEM_DEFAULT).args(ArrayType.of(DecimalV2Type.SYSTEM_DEFAULT)),
            FunctionSignature.ret(DateType.INSTANCE).args(ArrayType.of(DateType.INSTANCE)),
            FunctionSignature.ret(DateTimeType.INSTANCE).args(ArrayType.of(DateTimeType.INSTANCE)),
            FunctionSignature.ret(DateV2Type.INSTANCE).args(ArrayType.of(DateV2Type.INSTANCE)),
            FunctionSignature.ret(DateTimeV2Type.SYSTEM_DEFAULT).args(ArrayType.of(DateTimeV2Type.SYSTEM_DEFAULT))
    );

    /**
     * constructor with 1 argument.
     */
    public ArrayMin(Expression arg) {
        super("array_min", arg);
    }

    @Override
    public DataType getDataType() {
        return ((ArrayType) (child().getDataType())).getItemType();
    }

    /**
     * withChildren.
     */
    @Override
    public ArrayMin withChildren(List<Expression> children) {
        Preconditions.checkArgument(children.size() == 1);
        return new ArrayMin(children.get(0));
    }

    @Override
    public <R, C> R accept(ExpressionVisitor<R, C> visitor, C context) {
        return visitor.visitArrayMin(this, context);
    }

    @Override
    public List<FunctionSignature> getSignatures() {
        return SIGNATURES;
    }
}
