/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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

package org.jboss.as.arquillian.container.embedded;

import java.security.AccessController;
import java.security.PrivilegedAction;
import org.wildfly.security.manager.WildFlySecurityManager;

/**
 * Security actions to access system environment information.  No methods in
 * this class are to be made public under any circumstances!
 *
 * @author <a href="g.grossetien@gmail.com">Guillaume Grossetie</a>
 */
class SecurityActions {

    private SecurityActions() {
    }

    static void setSystemProperty(final String key, final String value) {
        if (! WildFlySecurityManager.isChecking()) {
            System.setProperty(key, value);
        } else {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {

                @Override
                public Void run() {
                    System.setProperty(key, value);
                    return null;
                }
            });
        }
    }
}
