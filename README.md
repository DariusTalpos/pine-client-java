# A PINE (Protocol for Instrumentation of Emulators) written in Java

This repository contains classes and enums designed to allow communication via PINE to the [PCSX2](https://pcsx2.net/) and [RPCS3](https://rpcs3.net/) emulators on Windows operating systems, allowing users to read to and write from memory addresses, view game data amongst others.

## PineException

An exception class which will be thrown when issues appear during the creation of objects from the other classes or during their methods.

## u8/u16/u32/u64

Special classes designed to store unsigned 8-bit, 16-bit, 32-bit and 64-bit numerical values respectively. These classes are used for creating values of different sizes to memory addresses which are always expected to be `u32` objects. 

The `toLEArray()` method is used to obtain a little-endian byte array of the value stored within.

## TCP/TCPDefaults

`TCP` is a wrapper class that contains the host and port (refferred in PINE as 'slot') that will be used to connect to an emulator instance.
`TCPDefaults` is an enum that contains default TCP host and port data for both PCSX2 and RPCS3.

