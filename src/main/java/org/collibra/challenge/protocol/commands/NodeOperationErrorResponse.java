package org.collibra.challenge.protocol.commands;

public class NodeOperationErrorResponse implements Response {

    public enum ErrorType {NotFound, AlreadyExists}

    private final ErrorType errorType;

    /**
     * @param errorType
     */
    public NodeOperationErrorResponse(ErrorType errorType) {
        this.errorType = errorType;
    }

    @Override
    public String toResponseString() {
        switch (errorType) {
            case NotFound:
                return "ERROR: NODE NOT FOUND";
            case AlreadyExists:
                return "ERROR: NODE ALREADY EXISTS";
        }

        throw new IllegalStateException("Unknown error type: " + errorType);
    }
}
