package org.collibra.challenge.graph.exception;

public class NodeOperationException extends Exception {

    private static final long serialVersionUID = -8695114579908431591L;

    public static class NodeMissingException extends NodeOperationException {

        private static final long serialVersionUID = -5048316107133144154L;
    }

    public static class NodeAlreadyExistsException extends NodeOperationException {

        private static final long serialVersionUID = 3530503023162254343L;
    }
}
