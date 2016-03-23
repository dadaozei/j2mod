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
package com.j2mod.modbus.msg;

import com.j2mod.modbus.Modbus;
import com.j2mod.modbus.procimg.InputRegister;
import com.j2mod.modbus.procimg.Register;
import com.j2mod.modbus.procimg.SimpleRegister;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Class implementing a <tt>ReadWriteMultipleResponse</tt>.
 *
 * @author Julie (jfh@ghgande.com)
 * @version @version@ (@date@)
 *
 * @author Steve O'Hara (4energy)
 * @version 2.0 (March 2016)
 *
 */
public final class ReadWriteMultipleResponse extends ModbusResponse {

    private int m_ByteCount;
    private InputRegister[] m_Registers;

    /**
     * Constructs a new <tt>ReadWriteMultipleResponse</tt> instance.
     *
     * @param registers the Register[] holding response registers.
     */
    public ReadWriteMultipleResponse(InputRegister[] registers) {
        super();

        setFunctionCode(Modbus.READ_WRITE_MULTIPLE);
        setDataLength(registers.length * 2 + 1);

        m_Registers = registers;
        m_ByteCount = registers.length * 2 + 1;
    }

    /**
     * Constructs a new <tt>ReadWriteMultipleResponse</tt> instance.
     *
     * @param count the number of Register[] holding response registers.
     */
    public ReadWriteMultipleResponse(int count) {
        super();

        setFunctionCode(Modbus.READ_WRITE_MULTIPLE);
        setDataLength(count * 2 + 1);

        m_Registers = new InputRegister[count];
        m_ByteCount = count * 2 + 1;
    }

    /**
     * Constructs a new <tt>ReadWriteMultipleResponse</tt> instance.
     */
    public ReadWriteMultipleResponse() {
        super();

        setFunctionCode(Modbus.READ_WRITE_MULTIPLE);
    }

    /**
     * Returns the number of bytes that have been read.
     *
     * @return the number of bytes that have been read as <tt>int</tt>.
     */
    public int getByteCount() {
        return m_ByteCount;
    }

    /**
     * Returns the number of words that have been read. The returned value
     * should be half of the the byte count of this
     * <tt>ReadWriteMultipleResponse</tt>.
     *
     * @return the number of words that have been read as <tt>int</tt>.
     */
    public int getWordCount() {
        return m_ByteCount / 2;
    }

    /**
     * Returns the <tt>Register</tt> at the given position (relative to the
     * reference used in the request).
     *
     * @param index the relative index of the <tt>InputRegister</tt>.
     *
     * @return the register as <tt>InputRegister</tt>.
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public InputRegister getRegister(int index) {
        if (m_Registers == null) {
            throw new IndexOutOfBoundsException("No registers defined!");
        }

        if (index < 0) {
            throw new IndexOutOfBoundsException("Negative index: " + index);
        }

        if (index >= getWordCount()) {
            throw new IndexOutOfBoundsException(index + " > " + getWordCount());
        }

        return m_Registers[index];
    }

    /**
     * Returns the value of the register at the given position (relative to the
     * reference used in the request) interpreted as unsigned short.
     *
     * @param index the relative index of the register for which the value should
     *              be retrieved.
     *
     * @return the value as <tt>int</tt>.
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public int getRegisterValue(int index) throws IndexOutOfBoundsException {
        return getRegister(index).toUnsignedShort();
    }

    /**
     * Returns the reference to the array of registers read.
     *
     * @return a <tt>InputRegister[]</tt> instance.
     */
    public synchronized InputRegister[] getRegisters() {
        InputRegister[] dest = new InputRegister[m_Registers.length];
        System.arraycopy(m_Registers, 0, dest, 0, dest.length);
        return dest;
    }

    /**
     * Sets the entire block of registers for this response
     */
    public void setRegisters(InputRegister[] registers) {
        m_ByteCount = registers.length * 2 + 1;
        setDataLength(m_ByteCount);

        m_Registers = registers;
    }

    public void writeData(DataOutput dout) throws IOException {
        dout.writeByte(m_ByteCount);

        for (int k = 0; k < getWordCount(); k++) {
            dout.write(m_Registers[k].toBytes());
        }
    }

    public void readData(DataInput din) throws IOException {
        m_ByteCount = din.readUnsignedByte();

        m_Registers = new Register[getWordCount()];

        for (int k = 0; k < getWordCount(); k++) {
            m_Registers[k] = new SimpleRegister(din.readByte(), din.readByte());
        }

        setDataLength(m_ByteCount + 1);
    }

    public byte[] getMessage() {
        byte result[];

        result = new byte[getWordCount() * 2 + 1];

        int offset = 0;
        result[offset++] = (byte)m_ByteCount;

        for (InputRegister m_Register : m_Registers) {
            byte[] data = m_Register.toBytes();

            result[offset++] = data[0];
            result[offset++] = data[1];
        }
        return result;
    }
}