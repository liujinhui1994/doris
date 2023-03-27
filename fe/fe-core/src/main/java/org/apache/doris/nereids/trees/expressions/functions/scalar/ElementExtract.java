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
import org.apache.doris.nereids.trees.expressions.functions.ExplicitlyCastableSignature;
import org.apache.doris.nereids.trees.expressions.shape.BinaryExpression;
import org.apache.doris.nereids.trees.expressions.visitor.ExpressionVisitor;
import org.apache.doris.nereids.types.ArrayType;
import org.apache.doris.nereids.types.BigIntType;
import org.apache.doris.nereids.types.BooleanType;
import org.apache.doris.nereids.types.DateTimeType;
import org.apache.doris.nereids.types.DateTimeV2Type;
import org.apache.doris.nereids.types.DateType;
import org.apache.doris.nereids.types.DateV2Type;
import org.apache.doris.nereids.types.DecimalV2Type;
import org.apache.doris.nereids.types.DecimalV3Type;
import org.apache.doris.nereids.types.DoubleType;
import org.apache.doris.nereids.types.FloatType;
import org.apache.doris.nereids.types.IntegerType;
import org.apache.doris.nereids.types.LargeIntType;
import org.apache.doris.nereids.types.SmallIntType;
import org.apache.doris.nereids.types.StringType;
import org.apache.doris.nereids.types.TinyIntType;
import org.apache.doris.nereids.types.VarcharType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * ScalarFunction '%element_extract%'. This class is generated by GenerateFunction.
 */
public class ElementExtract extends ScalarFunction
        implements BinaryExpression, ExplicitlyCastableSignature, AlwaysNullable {

    public static final List<FunctionSignature> SIGNATURES = ImmutableList.of(
            FunctionSignature.ret(BooleanType.INSTANCE).args(ArrayType.of(BooleanType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(TinyIntType.INSTANCE).args(ArrayType.of(TinyIntType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(SmallIntType.INSTANCE).args(ArrayType.of(SmallIntType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(IntegerType.INSTANCE).args(ArrayType.of(IntegerType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(BigIntType.INSTANCE).args(ArrayType.of(BigIntType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(LargeIntType.INSTANCE).args(ArrayType.of(LargeIntType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(DateTimeType.INSTANCE).args(ArrayType.of(DateTimeType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(DateType.INSTANCE).args(ArrayType.of(DateType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(DateTimeV2Type.SYSTEM_DEFAULT)
                    .args(ArrayType.of(DateTimeV2Type.SYSTEM_DEFAULT), BigIntType.INSTANCE),
            FunctionSignature.ret(DateV2Type.INSTANCE).args(ArrayType.of(DateV2Type.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(FloatType.INSTANCE).args(ArrayType.of(FloatType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(DoubleType.INSTANCE).args(ArrayType.of(DoubleType.INSTANCE), BigIntType.INSTANCE),
            FunctionSignature.ret(DecimalV2Type.SYSTEM_DEFAULT)
                    .args(ArrayType.of(DecimalV2Type.SYSTEM_DEFAULT), BigIntType.INSTANCE),
            FunctionSignature.ret(DecimalV3Type.WILDCARD)
                    .args(ArrayType.of(DecimalV3Type.WILDCARD), BigIntType.INSTANCE),
            FunctionSignature.ret(VarcharType.SYSTEM_DEFAULT)
                    .args(ArrayType.of(VarcharType.SYSTEM_DEFAULT), BigIntType.INSTANCE),
            FunctionSignature.ret(StringType.INSTANCE).args(ArrayType.of(StringType.INSTANCE), BigIntType.INSTANCE)
    );

    /**
     * constructor with 2 arguments.
     */
    public ElementExtract(Expression arg0, Expression arg1) {
        super("%element_extract%", arg0, arg1);
    }

    /**
     * withChildren.
     */
    @Override
    public ElementExtract withChildren(List<Expression> children) {
        Preconditions.checkArgument(children.size() == 2);
        return new ElementExtract(children.get(0), children.get(1));
    }

    @Override
    public <R, C> R accept(ExpressionVisitor<R, C> visitor, C context) {
        return visitor.visitElementExtract(this, context);
    }

    @Override
    public List<FunctionSignature> getSignatures() {
        return SIGNATURES;
    }
}
