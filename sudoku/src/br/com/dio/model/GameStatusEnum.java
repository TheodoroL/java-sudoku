package br.com.dio.model;

public enum GameStatusEnum {
    NON_STATUS("Não iniciado"),
    INCOMPLETE("Incompleto"),
    COMPLETE("completo");

    final String label;

    GameStatusEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
