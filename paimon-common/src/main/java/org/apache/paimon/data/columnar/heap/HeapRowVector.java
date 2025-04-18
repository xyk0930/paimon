/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.paimon.data.columnar.heap;

import org.apache.paimon.data.columnar.ColumnVector;
import org.apache.paimon.data.columnar.ColumnarRow;
import org.apache.paimon.data.columnar.RowColumnVector;
import org.apache.paimon.data.columnar.VectorizedColumnBatch;
import org.apache.paimon.data.columnar.writable.WritableColumnVector;

/** This class represents a nullable heap row column vector. */
public class HeapRowVector extends AbstractStructVector
        implements WritableColumnVector, RowColumnVector {

    private VectorizedColumnBatch vectorizedColumnBatch;

    public HeapRowVector(int len, ColumnVector... fields) {
        super(len, fields);
        vectorizedColumnBatch = new VectorizedColumnBatch(children);
    }

    @Override
    public ColumnarRow getRow(int i) {
        ColumnarRow columnarRow = new ColumnarRow(vectorizedColumnBatch);
        columnarRow.setRowId(i);
        return columnarRow;
    }

    @Override
    public VectorizedColumnBatch getBatch() {
        return vectorizedColumnBatch;
    }

    @Override
    void reserveForHeapVector(int newCapacity) {
        // Nothing to store.
    }

    public void setFields(WritableColumnVector[] fields) {
        System.arraycopy(fields, 0, this.children, 0, fields.length);
        this.vectorizedColumnBatch = new VectorizedColumnBatch(children);
    }
}
