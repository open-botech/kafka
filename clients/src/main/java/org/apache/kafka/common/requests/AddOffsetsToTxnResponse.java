/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.kafka.common.requests;

import org.apache.kafka.common.message.AddOffsetsToTxnResponseData;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.protocol.types.Struct;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Possible error codes:
 *
 *   - {@link Errors#NOT_COORDINATOR}
 *   - {@link Errors#COORDINATOR_NOT_AVAILABLE}
 *   - {@link Errors#COORDINATOR_LOAD_IN_PROGRESS}
 *   - {@link Errors#INVALID_PRODUCER_ID_MAPPING}
 *   - {@link Errors#INVALID_PRODUCER_EPOCH}
 *   - {@link Errors#INVALID_TXN_STATE}
 *   - {@link Errors#GROUP_AUTHORIZATION_FAILED}
 *   - {@link Errors#TRANSACTIONAL_ID_AUTHORIZATION_FAILED}
 */
public class AddOffsetsToTxnResponse extends AbstractResponse {

    public AddOffsetsToTxnResponseData data;

    public AddOffsetsToTxnResponse(AddOffsetsToTxnResponseData data) {
        this.data = data;
    }

    public AddOffsetsToTxnResponse(Struct struct, short version) {
        this.data = new AddOffsetsToTxnResponseData(struct, version);
    }

    @Override
    public Map<Errors, Integer> errorCounts() {
        return errorCounts(Errors.forCode(data.errorCode()));
    }

    @Override
    protected Struct toStruct(short version) {
        return data.toStruct(version);
    }

    @Override
    public int throttleTimeMs() {
        return data.throttleTimeMs();
    }

    public static AddOffsetsToTxnResponse parse(ByteBuffer buffer, short version) {
        return new AddOffsetsToTxnResponse(ApiKeys.ADD_OFFSETS_TO_TXN.parseResponse(version, buffer), version);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    @Override
    public boolean shouldClientThrottle(short version) {
        return version >= 1;
    }
}