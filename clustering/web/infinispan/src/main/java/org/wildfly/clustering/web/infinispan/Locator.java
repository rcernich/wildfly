/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.wildfly.clustering.web.infinispan;

import org.infinispan.Cache;
import org.jboss.as.clustering.infinispan.invoker.CacheInvoker;

/**
 * Locates a value from the cache.
 * @author Paul Ferraro
 */
public interface Locator<K, V> {
    /**
     * Creates a value in the cache, if it does not already exist.
     * @param id the cache entry identifier.
     * @return the new value, or the existing value the cache entry already exists.
     */
    V createValue(K id);

    /**
     * Locates the value in the cache with the specified identifier.
     * @param id the cache entry identifier
     * @return the value of the cache entry, or null if not found.
     */
    V findValue(K id);

    /**
     * Reusable creation operation.
     */
    class CreateOperation<K, V> implements CacheInvoker.Operation<K, V, V> {
        private final K key;
        private final V value;

        public CreateOperation(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public V invoke(Cache<K, V> cache) {
            return cache.putIfAbsent(this.key, this.value);
        }
    }

    /**
     * Reusable lookup operation.
     */
    class FindOperation<K, V> implements CacheInvoker.Operation<K, V, V> {
        private final K key;

        public FindOperation(K key) {
            this.key = key;
        }

        @Override
        public V invoke(Cache<K, V> cache) {
            return cache.get(this.key);
        }
    }
}
