/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nereuschen.eda.message.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.nereuschen.eda.message.Message;
import com.nereuschen.eda.message.MessageHeaders;
import com.nereuschen.eda.util.Assert;
import com.nereuschen.eda.util.ObjectUtils;
 
/**
 * Base Message class defining common properties such as id, payload, and headers.
 * Once created this object is immutable.
 * 
 * @author Mark Fisher
 * @author Nereus Chen (nereus.chen@gmail.com)
 */
public class GenericMessage<T> implements Message<T>, Serializable {

	private static final long serialVersionUID = 3649200745084232821L;

	private final T payload;

	private final MessageHeaders headers;


	/**
	 * Create a new message with the given payload.
	 * 
	 * @param payload the message payload
	 */
	public GenericMessage(T payload) {
		this(payload, null);
	}

	/**
	 * Create a new message with the given payload. The provided map
	 * will be used to populate the message headers
	 * 
	 * @param payload the message payload
	 * @param headers message headers
	 * @see MessageHeaders
	 */
	public GenericMessage(T payload, Map<String, Object> headers) {
		Assert.notNull(payload, "payload must not be null");
		if (headers == null) {
			headers = new HashMap<String, Object>();
		}
		else {
			headers = new HashMap<String, Object>(headers);
		}
		this.headers = new MessageHeaders(headers);
		this.payload = payload;
	}


	public MessageHeaders getHeaders() {
		return this.headers;
	}

	public T getPayload() {
		return this.payload;
	}

	public String toString() {
		return "[Payload=" + this.payload + "][Headers=" + this.headers + "]";
	}

	public int hashCode() {
		return this.headers.hashCode() * 23 + ObjectUtils.nullSafeHashCode(this.payload);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj != null && obj instanceof GenericMessage<?>) {
			GenericMessage<?> other = (GenericMessage<?>) obj;
			if (!this.headers.getId().equals(other.headers.getId())) {
				return false;
			}
			return this.headers.equals(other.headers)
					&& this.payload.equals(other.payload);
		}
		return false;
	}

}
