package br.com.cwi.crescer.api.domain.enums;

public enum LeagueTitle {
    GASTADOR(1),
    POUPADOR(2),
    INVESTIDOR(3),
    INVESTIDOR_MESTRE(4);

    private final int order;

    LeagueTitle(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
