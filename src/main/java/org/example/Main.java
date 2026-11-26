package org.example;

import org.example.utils.u16;
import org.example.utils.u32;
import org.example.utils.u64;
import org.example.utils.u8;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;



public class Main {

    public static void main(String[] args) {
        try {
            u32 mapMemAdd = new u32(0x00_3b_ae_d8);

            PineClient client = new PineClient(TCPDefault.PCSX2);

            System.out.println("Emulator version: "+client.getEmulatorVersion());
            System.out.println("Game title: "+client.getGameTitle());
            System.out.println("Game serial: "+client.getGameSerial());
            System.out.println("Game UUID: "+client.getGameUUID());
            System.out.println("Game version: "+client.getGameVersion());
            System.out.println("Emulator status: "+client.getStatus());

            client.saveState(new u8(2));
            client.loadState(new u8(2));
        } catch (PineException e) {
            System.out.println(e.getMessage());
        }
    }
}
