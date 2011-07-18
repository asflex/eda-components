/*
 * Copyright 2002-2009 the original author or authors.
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

package com.nereuschen.eda.dispatcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.nereuschen.eda.Message;
import com.nereuschen.eda.core.MessageHandler;

 
/**
 * Round-robin implementation of {@link LoadBalancingStrategy}. This
 * implementation will keep track of the index of the handler that has been
 * tried first and use a different starting handler every dispatch.
 * 
 * @author Iwein Fuld
 * @author Mark Fisher
 * @author Nereus Chen (nereus.chen@gmail.com)
 */
public class RoundRobinLoadBalancingStrategy implements LoadBalancingStrategy {

	private final AtomicInteger currentHandlerIndex = new AtomicInteger();


	/**
	 * Returns an iterator that starts at a new point in the list every time the
	 * first part of the list that is skipped will be used at the end of the
	 * iteration, so it guarantees all handlers are returned once on subsequent
	 * <code>next()</code> invocations.
	 */
	public final Iterator<MessageHandler> getHandlerIterator(final Message<?> message, final List<MessageHandler> handlers) {
		int size = handlers.size();
		if (size == 0) {
			return handlers.iterator();
		}
		int nextHandlerStartIndex = getNextHandlerStartIndex(size);
		List<MessageHandler> reorderedHandlers = new ArrayList<MessageHandler>(
				handlers.subList(nextHandlerStartIndex, size));
		reorderedHandlers.addAll(handlers.subList(0, nextHandlerStartIndex));
		return reorderedHandlers.iterator();
	}

	/**
	 * Keeps track of the last index over multiple dispatches. Each invocation
	 * of this method will increment the index by one, overflowing at
	 * <code>size</code>.
	 */
	private int getNextHandlerStartIndex(int size) {
		int indexTail = currentHandlerIndex.getAndIncrement() % size;
		return indexTail < 0 ? indexTail + size : indexTail;
	}

}
