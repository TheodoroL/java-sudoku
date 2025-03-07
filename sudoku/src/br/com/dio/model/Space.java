package br.com.dio.model;

public class Space {
    private Integer actual;
    private final boolean fiexed;
    private final int expect;

    public Space(boolean fiexed, int expect) {
        this.fiexed = fiexed;
        this.expect = expect;
        if(fiexed) actual = expect;
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        if(fiexed) return;
        this.actual = actual;
    }

    public boolean isFiexed() {
        return fiexed;
    }

    public int getExpect() {
        return expect;
    }

    public void clearSpace(){
        setActual(null);
    }
}
