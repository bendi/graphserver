package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

public interface Response {

    byte[] print(ProtocolPrinter protocolPrinter);

}
