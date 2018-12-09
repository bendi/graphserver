package org.collibra.challenge.protocol.commands;

import org.collibra.challenge.protocol.ProtocolPrinter;

public class NodeOperationErrorResponse implements Response {

    public enum ErrorType {NotFound, AlreadyExists}

    private final ErrorType errorType;

    /**
     * @param errorType
     */
    public NodeOperationErrorResponse(ErrorType errorType)
    {
        this.errorType = errorType;
    }

    public ErrorType getErrorType()
    {
        return errorType;
    }

    @Override
    public byte[] print(ProtocolPrinter protocolPrinter)
    {
        return protocolPrinter.print( this );
    }

}
