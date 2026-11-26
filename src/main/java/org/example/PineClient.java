package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Vector;

import org.example.utils.u16;
import org.example.utils.u32;
import org.example.utils.u64;
import org.example.utils.u8;

public class PineClient {

    private final Socket socket;

    public PineClient(TCP tcp) throws PineException{
        try {
            socket = new Socket(tcp.getHost(), tcp.getPort());
        } catch (IOException e) {
            throw new PineException("Could not create connection");
        }
    }

    public PineClient(TCPDefault tcp) throws PineException{
        try {
            socket = new Socket(tcp.getHost(), tcp.getPort());
        } catch (IOException e) {
            throw new PineException("Could not create connection");
        }
    }

    private void sendRequest(PineCommand command, int[] memoryAddress, int[] value) throws PineException {
        int requestLength = 5;
        if(memoryAddress!=null) {
            requestLength += 4;
        }
        if(value!=null) {
                requestLength += value.length;
        }
        int[] requestArray = new int[requestLength];
        int[] lengthLE = new u32(requestLength).toLEArray();
        System.arraycopy(lengthLE, 0, requestArray, 0, 4);
        requestArray[4] = command.getCode();
        if(memoryAddress!=null) {
            System.arraycopy(memoryAddress, 0, requestArray, 5, 4);
        }
        if(value!=null) {
            if(memoryAddress!=null)
                System.arraycopy(value, 0, requestArray, 9, value.length);
            else
                System.arraycopy(value, 0, requestArray, 5, value.length);
        }
        try {
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            for (int i : requestArray) outputStream.writeByte(i);
            outputStream.flush();
        } catch (IOException e) {
            throw new PineException("Could not write to output stream.");
        }
    }

    private byte[] parseResponse() throws PineException {
        try {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            byte[] responseSizeBytes = new byte[4];
            inputStream.readFully(responseSizeBytes);
            int responseSize = ByteBuffer.wrap(responseSizeBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();

            byte successByte = inputStream.readByte();
            if (successByte != 0) {
                throw new PineException("Operation failed.");
            }

            if(responseSize==5)
                return null;

            byte[] responseBytes = new byte[responseSize-5];
            inputStream.readFully(responseBytes);
            return responseBytes;
        } catch (IOException e) {
            throw new PineException("Could not read from input stream");
        }
    }

    private String parseStringResponse() throws PineException {
        byte[] lengthAndStringBytes = parseResponse();
        assert lengthAndStringBytes != null;
        if(lengthAndStringBytes.length <= 4) {
            throw new PineException("Incorrect length 1");
        }
        int length = ByteBuffer.wrap(lengthAndStringBytes,0,4).order(ByteOrder.LITTLE_ENDIAN).getInt();
        if(length!=lengthAndStringBytes.length-4) {
            throw new PineException("Incorrect length 2");
        }
        return new String(lengthAndStringBytes, 4, length-1, StandardCharsets.UTF_8);

    }

    public byte[] read8Bit(u32 memoryAddress) throws PineException {
        sendRequest(PineCommand.READ8BIT, memoryAddress.toLEArray(), null);
        return parseResponse();
    }

    public byte[] read16Bit(u32 memoryAddress) throws PineException {
        sendRequest(PineCommand.READ16BIT, memoryAddress.toLEArray(), null);
        return parseResponse();
    }

    public byte[] read32Bit(u32 memoryAddress) throws PineException {
        sendRequest(PineCommand.READ32BIT, memoryAddress.toLEArray(), null);
        return parseResponse();
    }

    public byte[] read64Bit(u32 memoryAddress) throws PineException {
        sendRequest(PineCommand.READ64BIT, memoryAddress.toLEArray(), null);
        return parseResponse();
    }

    public void write8Bit(u32 memoryAddress, u8 value) throws PineException {
        sendRequest(PineCommand.WRITE8BIT, memoryAddress.toLEArray(), value.toLEArray());
        parseResponse();
    }

    public void write16Bit(u32 memoryAddress, u16 value) throws PineException {
        sendRequest(PineCommand.WRITE8BIT, memoryAddress.toLEArray(), value.toLEArray());
        parseResponse();
    }

    public void write32Bit(u32 memoryAddress, u32 value) throws PineException {
        sendRequest(PineCommand.WRITE8BIT, memoryAddress.toLEArray(), value.toLEArray());
        parseResponse();
    }

    public void write64Bit(u32 memoryAddress, u64 value) throws PineException {
        sendRequest(PineCommand.WRITE8BIT, memoryAddress.toLEArray(), value.toLEArray());
        parseResponse();
    }

    public String getEmulatorVersion() throws PineException {
        sendRequest(PineCommand.EMULATORVERSION, null, null);
        return parseStringResponse();
    }

    public void saveState(u8 saveSlot) throws PineException {
        if(saveSlot.getValue()<1 || saveSlot.getValue()>10)
            throw new PineException("Incorrect save slot");
        sendRequest(PineCommand.SAVESTATE, null, saveSlot.toLEArray());
        parseResponse();
    }

    public void loadState(u8 saveSlot) throws PineException {
        if(saveSlot.getValue()<1 || saveSlot.getValue()>10)
            throw new PineException("Incorrect save slot");
        sendRequest(PineCommand.LOADSTATE, null, saveSlot.toLEArray());
        parseResponse();
    }

    public String getGameTitle() throws PineException {
        sendRequest(PineCommand.TITLE, null, null);
        return parseStringResponse();
    }

    public String getGameSerial() throws PineException {
        sendRequest(PineCommand.SERIAL, null, null);
        return parseStringResponse();
    }

    public String getGameUUID() throws PineException {
        sendRequest(PineCommand.UUID, null, null);
        return parseStringResponse();
    }

    public String getGameVersion() throws PineException {
        sendRequest(PineCommand.GAMEVERSION, null, null);
        return parseStringResponse();
    }

    public PineStatus getStatus() throws PineException {
        sendRequest(PineCommand.STATUS, null, null);
        byte[] statusBytes = parseResponse();
        assert statusBytes != null;
        int status = ByteBuffer.wrap(statusBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
        return switch (status) {
            case 0 -> PineStatus.RUNNING;
            case 1 -> PineStatus.PAUSED;
            case 2 -> PineStatus.SHUTDOWN;
            default -> throw new PineException("Incorrect status code received");
        };
    }

}
