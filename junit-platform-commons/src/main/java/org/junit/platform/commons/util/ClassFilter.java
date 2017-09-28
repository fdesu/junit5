/*
 * Copyright 2015-2017 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.junit.platform.commons.util;

import static org.apiguardian.api.API.Status.INTERNAL;

import java.util.function.Predicate;

import org.apiguardian.api.API;

/**
 * Class-related predicate holder used by reflection utilities.
 *
 * <h3>DISCLAIMER</h3>
 *
 * <p>These utilities are intended solely for usage within the JUnit framework
 * itself. <strong>Any usage by external parties is not supported.</strong>
 * Use at your own risk!
 *
 * @since 1.1
 */
@API(status = INTERNAL, since = "1.1")
public class ClassFilter implements Predicate<Class<?>> {

	public static ClassFilter of(Predicate<Class<?>> classPredicate) {
		return of(name -> true, classPredicate);
	}

	public static ClassFilter of(Predicate<String> namePredicate, Predicate<Class<?>> classPredicate) {
		return new ClassFilter(namePredicate, classPredicate);
	}

	private final Predicate<String> namePredicate;
	private final Predicate<Class<?>> classPredicate;

	private ClassFilter(Predicate<String> namePredicate, Predicate<Class<?>> classPredicate) {
		this.namePredicate = Preconditions.notNull(namePredicate, "name predicate must not be null");
		this.classPredicate = Preconditions.notNull(classPredicate, "class predicate must not be null");
	}

	public boolean match(String name) {
		return namePredicate.test(name);
	}

	public boolean match(Class<?> type) {
		return classPredicate.test(type);
	}

	@Override
	public boolean test(Class<?> type) {
		return match(type.getName()) && match(type);
	}
}
