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
package com.j2mod.modbus.io;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Class implementing a byte array output stream with
 * a DataInput interface.
 *
 * @author Dieter Wimberger
 * @version 1.2rc1 (09/11/2004)
 *
 * @author Steve O'Hara (4energy)
 * @version 2.0 (March 2016)
 *
 */
public class BytesOutputStream extends FastByteArrayOutputStream implements DataOutput {

    private DataOutputStream m_Dout;

    /**
     * Constructs a new <tt>BytesOutputStream</tt> instance with
     * a new output buffer of the given size.
     *
     * @param size the size of the output buffer as <tt>int</tt>.
     */
    public BytesOutputStream(int size) {
        super(size);
        m_Dout = new DataOutputStream(this);
    }

    /**
     * Constructs a new <tt>BytesOutputStream</tt> instance with
     * a given output buffer.
     *
     * @param buffer the output buffer as <tt>byte[]</tt>.
     */
    public BytesOutputStream(byte[] buffer) {
        buf = buffer;
        count = 0;
        m_Dout = new DataOutputStream(this);
    }

    /**
     * Returns the reference to the output buffer.
     *
     * @return the reference to the <tt>byte[]</tt> output buffer.
     */
    public synchronized byte[] getBuffer() {
        byte[] dest = new byte[buf.length];
        System.arraycopy(buf, 0, dest, 0, dest.length);
        return dest;
    }

    public void reset() {
        count = 0;
    }

    public void writeBoolean(boolean v) throws IOException {
        m_Dout.writeBoolean(v);
    }

    public void writeByte(int v) throws IOException {
        m_Dout.writeByte(v);
    }

    public void writeShort(int v) throws IOException {
        m_Dout.writeShort(v);
    }

    public void writeChar(int v) throws IOException {
        m_Dout.writeChar(v);
    }

    public void writeInt(int v) throws IOException {
        m_Dout.writeInt(v);
    }

    public void writeLong(long v) throws IOException {
        m_Dout.writeLong(v);
    }

    public void writeFloat(float v) throws IOException {
        m_Dout.writeFloat(v);
    }

    public void writeDouble(double v) throws IOException {
        m_Dout.writeDouble(v);
    }

    public void writeBytes(String s) throws IOException {
        int len = s.length();
        for (int i = 0; i < len; i++) {
            this.write((byte)s.charAt(i));
        }
    }

    public void writeChars(String s) throws IOException {
        m_Dout.writeChars(s);
    }

    public void writeUTF(String str) throws IOException {
        m_Dout.writeUTF(str);
    }

}