package org.example;

public enum PineCommand {
    READ8BIT(0x00),
    READ16BIT(0x01),
    READ32BIT(0x02),
    READ64BIT(0x03),
    WRITE8BIT(0x04),
    WRITE16BIT(0x05),
    WRITE32BIT(0x06),
    WRITE64BIT(0x07),
    EMULATORVERSION(0X08),
    SAVESTATE(0X09),
    LOADSTATE(0X0A),
    TITLE(0X0B),
    SERIAL(0X0C),
    UUID(0X0D),
    GAMEVERSION(0X0E),
    STATUS(0X0F);

    private final int code;

    PineCommand(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
