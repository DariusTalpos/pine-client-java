# A PINE (Protocol for Instrumentation of Emulators) client in Java

This repository contains a collection of Java classes and enums designed to allow communication via PINE to the [PCSX2](https://pcsx2.net/) and [RPCS3](https://rpcs3.net/) emulators on Windows operating systems, allowing users to read to and write from memory addresses and view game data amongst others.

The project was inspired by the Rust library implementation developed by Lei Cao, available [here](https://github.com/caol64/pine-client), created under the MIT license available at `LICENSE-Lei-Cao`.

## PineException

An exception class which will be thrown when issues appear during the creation of objects from the other classes or during their methods.

## u8/u16/u32/u64

Special classes designed to store unsigned 8-bit, 16-bit, 32-bit and 64-bit numerical values respectively. These classes are used for creating values of different sizes to be sent to or read from memory addresses (which are always expected to be `u32` objects). 

The `toLEArray()` method is used to obtain a little-endian byte array of the value stored within.

## TCP/TCPDefaults

`TCP` is a wrapper class that contains the host and port (refered to in PINE as 'slot') that will be used to connect to an emulator instance.
`TCPDefaults` is an enum that contains default TCP host and port data for both PCSX2 and RPCS3.

## PineCommand

An enum containing every type of command (associated with it's operation code) that can be sent via PINE to the emulator. These are:

<o1>
  <li>read an 8-bit/16-bit/32-bit/64-bit value from a memory address</li>
  <li>write an 8-bit/16-bit/32-bit/64-bit value to a memory address</li>
  <li>load from a save state</li>
  <li>save to a save state</li>
  <li>obtain the emulator's version</li>
  <li>obtain a game's title/serial code/UUID/version/status</li>
</o1>

## PineStatus

An enum containing the three states a game can be in at any given moment: running, paused or shut down.

## PineClient

The class that creates the connection to the emulators using Java sockets and allows sending requests and gaining responses using the socket's output stream and input stream respectively.

It contains methods for every type of operation described in `PineCommand`.
