package br.com.tracker.model;
public enum Status {
    TODO("todo"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private final String label;

    Status(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.getLabel().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: "+value);
    }
}
