/*
 * Copyright 2002-2016 jamod & j2mod development teams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.j2mod.modbus.procimg;

/**
 * Class implementing a simple <tt>Register</tt>.
 * <p>
 * The <tt>setValue()</tt> method is synchronized, which ensures atomic access, * but no specific access order.
 *
 * @author Dieter Wimberger
 * @version 1.2rc1 (09/11/2004)
 *
 * @author Julie Haugh
 * @version 0.97 (8/12/12)
 *
 * @author Steve O'Hara (4energy)
 * @version 2.0 (March 2016)
 *
 */
public class SimpleRegister extends SynchronizedAbstractRegister implements
        Register {

    /**
     * Constructs a new <tt>SimpleRegister</tt> instance.
     *
     * @param b1
     *            the first (hi) byte of the word.
     * @param b2
     *            the second (low) byte of the word.
     */
    public SimpleRegister(byte b1, byte b2) {
        m_Register[0] = b1;
        m_Register[1] = b2;
    }

    /**
     * Constructs a new <tt>SimpleRegister</tt> instance with the given value.
     *
     * @param value
     *            the value of this <tt>SimpleRegister</tt> as <tt>int</tt>.
     */
    public SimpleRegister(int value) {
        setValue(value);
    }

    /**
     * Constructs a new <tt>SimpleRegister</tt> instance. It's state will be
     * invalid.
     *
     * Attempting to access this register will result in an
     * IllegalAddressException(). It may be used to create "holes" in a Modbus
     * register map.
     */
    public SimpleRegister() {
        m_Register = null;
    }

    public String toString() {
        if (m_Register == null) {
            return "invalid";
        }

        return getValue() + "";
    }
}