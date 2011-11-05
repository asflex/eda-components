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

package com.nereuschen.eda.channel.impl;

import com.nereuschen.eda.dispatcher.LoadBalancingStrategy;
import com.nereuschen.eda.dispatcher.impl.RoundRobinLoadBalancingStrategy;
import com.nereuschen.eda.dispatcher.impl.UnicastingDispatcher;

/**
 * A channel that invokes a single subscriber for each sent Message. The
 * invocation will occur in the sender's thread.
 * 
 * @author Dave Syer
 * @author Mark Fisher
 * @author Iwein Fuld
 * @author Oleg Zhurakousky
 * @author Nereus Chen (nereus.chen@gmail.com)
 */
public class DirectChannel extends AbstractSubscribableChannel {

	private final UnicastingDispatcher dispatcher = new UnicastingDispatcher();

	/**
	 * Create a channel with default {@link RoundRobinLoadBalancingStrategy}
	 */
	public DirectChannel() {
		this(new RoundRobinLoadBalancingStrategy());
	}

	/**
	 * Create a DirectChannel with a {@link LoadBalancingStrategy}. The strategy
	 * <emphasis>must not</emphasis> be null.
	 */
	public DirectChannel(LoadBalancingStrategy loadBalancingStrategy) {
		this.dispatcher.setLoadBalancingStrategy(loadBalancingStrategy);
	}

	/**
	 * Specify whether the channel's dispatcher should have failover enabled. By
	 * default, it will. Set this value to 'false' to disable it.
	 */
	public void setFailover(boolean failover) {
		this.dispatcher.setFailover(failover);
	}

	@Override
	protected UnicastingDispatcher getDispatcher() {
		return this.dispatcher;
	}

}
