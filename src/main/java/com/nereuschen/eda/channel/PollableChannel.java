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

package com.nereuschen.eda.channel;

import com.nereuschen.eda.message.Message;

 
/**
 * Interface for Message Channels from which Messages may be actively received through polling.
 * 
 * @author Mark Fisher
 * @author Nereus Chen (nereus.chen@gmail.com)
 */
public interface PollableChannel extends MessageChannel {

	/**
	 * Receive a message from this channel, blocking indefinitely if necessary.
	 * 
	 * @return the next available {@link Message} or <code>null</code> if interrupted
	 */
	Message<?> receive();

	/**
	 * Receive a message from this channel, blocking until either a message is
	 * available or the specified timeout period elapses.
	 * 
	 * @param timeout the timeout in milliseconds
	 * 
	 * @return the next available {@link Message} or <code>null</code> if the
	 * specified timeout period elapses or the message reception is interrupted
	 */
	Message<?> receive(long timeout);

}
