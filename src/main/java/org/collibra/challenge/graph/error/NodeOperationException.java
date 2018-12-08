package org.collibra.challenge.graph.error;

public class NodeOperationException extends Exception {

    public static class NodeMissingException extends NodeOperationException {

    }

    public static class NodeAlreadyExistsException extends NodeOperationException {

    }
}
