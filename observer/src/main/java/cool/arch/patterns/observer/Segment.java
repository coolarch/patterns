package cool.arch.patterns.observer;

/*
 * #%L Patterns - Observable %% Copyright (C) 2015 CoolArch %% Licensed to the Apache Software
 * Foundation (ASF) under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. #L%
 */

import static java.util.Objects.requireNonNull;

class Segment<T> {

	private final T item;

	private final Segment<T> tail;

	Segment(final T item, final Segment<T> tail) {
		this.item = requireNonNull(item, "item shall not be null");
		this.tail = tail;
	}

	/**
	 * @return the item
	 */
	public final T getItem() {
		return item;
	}

	/**
	 * @return the tail
	 */
	public final Segment<T> getTail() {
		return tail;
	}
}
