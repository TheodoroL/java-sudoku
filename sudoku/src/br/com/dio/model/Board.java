package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.nonNull;

public class Board {
    private final List<List<Space>> spaces;

    public Board(List<List<Space>> spaces) {
        this.spaces = spaces;
    }

    public List<List<Space>> getSpaces() {
        return spaces;
    }

    public GameStatusEnum getSatus(){
        //essa condição verifica se a posição não é fixa e também está com valor inserido
        if(spaces.stream().flatMap(Collection::stream).noneMatch(s-> !s.isFiexed() && nonNull(s.getActual())))
            return  GameStatusEnum.NON_STATUS;

        return spaces.stream()
                .flatMap(Collection::stream)
                .anyMatch(s -> nonNull(s.getActual())) ? GameStatusEnum.INCOMPLETE : GameStatusEnum.COMPLETE;
    }

    public boolean hasErrors(){
        if(getSatus() == GameStatusEnum.NON_STATUS) return false;

        return spaces.stream().flatMap(Collection::stream)
                .anyMatch(s->
                        nonNull(s.getActual()) &&
                                !s.getActual().equals(s.getExpect()));
    }

    public boolean changeValue(final int col, final  int row, final int value){
        var space = spaces.get(col).get(row);
        if(space.isFiexed()) return  false;
        space.setActual(value);
        return true;
    }

    public boolean clearValue(final int col, final int row){
        var space = spaces.get(col).get(row);
        if(space.isFiexed()) return  false;
        space.clearSpace();
        return true;
    }

    //fazer reset no sudoku
    public void reset(){
        spaces.forEach(c-> c.forEach(Space::clearSpace));
    }
    //verifica se o jogo terminou
    public boolean gamerIsFinished(){
        return !hasErrors() && getSatus() == GameStatusEnum.COMPLETE;
    }
}
