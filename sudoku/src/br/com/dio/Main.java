package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;
import br.com.dio.util.BoardTemplate;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import static java.util.stream.Collectors.toMap;

public class Main {
    private final  static Scanner scanner = new Scanner(System.in);
    private static Board board;
    private final static int BOARD_LIMIT =9;

    public static void main(String[] args) {
        //vai preparar os valores das posição do game
        final var position = Stream.of(args)
                .collect(toMap(
                        k-> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        int opiton = -1;

        while (true){
            menu();
            opiton = scanner.nextInt();
            switch (opiton){
                case 1 -> startGame(position);
                case 2 -> inputNumber();
                case 3 ->removeNumber();
                case 4 ->showCurrentGame();
                case 5 ->showGameStatus();
                case 6 ->clearGame();
                case 7 ->finishGame();
                case 8 -> System.exit(0);
                default -> System.out.println("Opção invalida");
            }
        }

    }

    private static void startGame(Map<String, String> position) {
        if(nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }
        List<List<Space>> spaces = new ArrayList<>();

        for(int c = 0; c < BOARD_LIMIT; c++){
            spaces.add(new ArrayList<>());
            for(int i = 0; i < BOARD_LIMIT; i++) {
                var positionConfig = position.get("%s,%s".formatted(c, i));
                //pegando o expect
                var expect = Integer.parseInt(positionConfig.split(",")[0]);
                //pegando o fixed
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                //criando o nosso objeto Space
                var currentSpace = new Space(fixed, expect);
                spaces.get(c).add(currentSpace);
            }
        }
        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");
    }

    private static void inputNumber() {
        if(isNull(board)){
            System.out.println("o jogo não foi iniciado");
            return;
        }
        try {
            System.out.print("informe a coluna em que o número vai ser inserido:");
            int col = runUnitGetValidNumber(0, 8);
            System.out.print("informe  a linha  em que o número vai ser inserido:");
            int row = runUnitGetValidNumber(0, 8);
            System.out.printf("informe o valor que vai entrar na [%d:%d]", col, row);
            int value = runUnitGetValidNumber(1, 9);

            if (!board.changeValue(col, row, value)) {
                System.out.printf("A posição [%d:%d] tem um valor fixo\n", col, row);
                return;
            }
        }
        catch (Exception e){
            System.out.println("Erro:"+e);
        }
    }
    private static void removeNumber() {
        if(isNull(board)){
            System.out.println("o jogo não foi iniciado");
            return;
        }
        try {
            System.out.print("informe a coluna em que o número vai ser inserido:");
            int col = runUnitGetValidNumber(0, 8);
            System.out.print("informe  a linha  em que o número vai ser inserido:");
            int row = runUnitGetValidNumber(0, 8);

            if (!board.clearValue(col, row)) {
                System.out.printf("A posição [%d:%d] tem um valor fixo\n", col, row);
                return;
            }
        }catch (Exception e){
            System.out.println("Erro:"+e);
        }
    }

    private static void showCurrentGame() {
        if(isNull(board)){
            System.out.println("o jogo não foi iniciado");
            return;
        }
        var args = new Object[81];
        var argPos = 0;
        for(int i = 0; i< BOARD_LIMIT; i++){
            for(var col : board.getSpaces()){

                args[argPos++] = " " + (isNull(col.get(i).getActual())?" ": col.get(i).getActual());
            }
        }
        System.out.println("O seu jogo se encontra da seguinte forma:");
        System.out.printf((BoardTemplate.BOARD_TEMPLATE) + "%n", args);
    }
    private static void showGameStatus() {
        if(isNull(board)){
            System.out.println("o jogo não foi iniciado");
            return;
        }
        System.out.printf("O jogo se encontra atualmente com status %s\n", board.getSatus().getLabel());

        if(!board.hasErrors()){
            System.out.println("O jogo não contem erros");
            return;
        }
        System.out.println("O jogo contem erros");

    }

    private static void clearGame() {
        if(isNull(board)){
            System.out.println("o jogo não foi iniciado");
            return;
        }
        System.out.print("O jogador quer realmente limpar o jogo ? [sim/não]:");
        String confirm = scanner.next();

        while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")){
            System.out.print("digita sim para sim ou digite não:");
            confirm = scanner.next();
        }
        if(confirm.equalsIgnoreCase("sim")){
            board.reset();
        }
    }

    private static void finishGame() {
        if(isNull(board)){
            System.out.println("o jogo não foi iniciado");
            return;
        }
        if(board.gamerIsFinished()){
            System.out.println("Parabéns, você concluiu o jogo");
            showCurrentGame();
            board = null;
        }
        else if(board.hasErrors()) System.out.println("O seu jogo contém erros, verifique seu board e ajuste-o");
        else System.out.println("Você ainda precisa preencher algum espaço");

    }


    public static void menu(){
        List<String> listaOpcao = Arrays.asList("Iniciar o jogo", "colocar um número", "Remover um número", "Visualizar o jogo atual", "Verificar status do jogo", "Limpar o jogo", "Finalizar o jogo", "Sair");
        for(int c= 0; c< listaOpcao.size(); c++){
            System.out.printf("%d - %s\n",c+1, listaOpcao.get(c));
        }

    }

    private static int runUnitGetValidNumber(final int min, final int max){
        var current  = scanner.nextInt();
        while (current < min || current > max){
            System.out.printf("Informe o número entre %d e %d\n",min, max);
            current  = scanner.nextInt();

        }
        return current;
    }
}