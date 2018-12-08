package org.collibra.challenge.graph.error;

public class GraphOperationException extends Exception {

    public static class NodeMissingException extends GraphOperationException {

    }

    public static class NodeAlreadyExistsException extends GraphOperationException {

    }
}
