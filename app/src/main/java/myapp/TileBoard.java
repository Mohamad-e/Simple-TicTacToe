package myapp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class TileBoard {
    public enum PlayerTurn{
        X,
        O
    }

    private StackPane pane;
    private InfoCenter infoCenter;
    private Tile[][] tiles = new Tile[3][3];
    private Line winningLine;

    

    private PlayerTurn playerTurn = PlayerTurn.X;
    private boolean isEndOfGame = false;

    public TileBoard(InfoCenter infoCenter){
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TILE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((UIConstants.TILE_BOARD_HEIGHT /  2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllTiles();

        winningLine = new Line();
        pane.getChildren().add(winningLine);
    }

    public StackPane getStackPane(){
        return pane;
    }

    private void addAllTiles(){
        for(int row=0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                Tile tile = new Tile();
                tile.getStackPane().setTranslateX((col * 100) - 100);
                tile.getStackPane().setTranslateY((row * 100) - 99);
                pane.getChildren().add(tile.getStackPane());
                tiles[row][col] = tile;
            }
        }
    }

    public void changePlayerTurn(){
        if (playerTurn == PlayerTurn.X){
            playerTurn = PlayerTurn.O;
        } else {
            playerTurn = PlayerTurn.X;

        }
    }

    public void startNewGame(){
        isEndOfGame = false;
        playerTurn = PlayerTurn.X;
        for(int row=0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                tiles[row][col].setValue("");
            }
        }
        winningLine.setVisible(false);

    }

    public String getPlayerTurn(){
        return String.valueOf((playerTurn));
    }

    public void checkForWinner(){
        checkRowsForWinner();
        checkColsForWinner();
        checkTopLeftToBottomRightForWinner();
        checkTopRightToBottomLeftForWInner();
        checkForStalemate(); 
    }

    private void checkRowsForWinner() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'checkRowsForWinner'");
        for(int row=0; row < 3; row++){
            if(tiles[row][0].getValue().equals(tiles[row][1].getValue()) &&
            tiles[row][0].getValue().equals(tiles[row][2].getValue()) &&
            !tiles[row][0].getValue().isEmpty()){
                String winner = tiles[row][0].getValue();
                endGame(winner, new WinningTiles(tiles[row][0], tiles[row][1], tiles[row][2]));
                return;
            }
        }
    }

    private void checkColsForWinner() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'checkColsForWinner'");
        if(!isEndOfGame){
            for(int col=0; col < 3; col++){
                if(tiles[0][col].getValue().equals(tiles[1][col].getValue()) &&
                tiles[0][col].getValue().equals(tiles[2][col].getValue()) &&
                !tiles[0][col].getValue().isEmpty()){
                    String winner = tiles[0][col].getValue();
                    endGame(winner, new WinningTiles(tiles[0][col], tiles[1][col], tiles[2][col]));
                }
            }
        }
    }

    private void checkTopLeftToBottomRightForWinner() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'checkTopLeftToBottomRightForWinner'");
        if (!isEndOfGame){
            if(tiles[0][0].getValue().equals(tiles[1][1].getValue()) && tiles[0][0].getValue().equals(tiles[2][2].getValue()) && !tiles[0][0].getValue().isEmpty()){
                String winner = tiles[0][0].getValue();
                endGame(winner, new WinningTiles(tiles[0][0], tiles[1][1], tiles[2][2]));
                return;
            }
        } 
    }

    private void checkTopRightToBottomLeftForWInner() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'checkTopRightToBottomLeftForWInner'");
        if (!isEndOfGame){
            if(tiles[0][2].getValue().equals(tiles[1][1].getValue()) && tiles[0][2].getValue().equals(tiles[2][0].getValue()) && !tiles[0][2].getValue().isEmpty()){
                String winner = tiles[0][2].getValue();
                endGame(winner, new WinningTiles(tiles[0][2], tiles[1][1], tiles[2][0]));
                return;
            }
        } 
    }

    private void checkForStalemate() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'checkForStalemate'");
        if(!isEndOfGame){
            for(int row = 0; row < 3; row++){
                for(int col = 0; col < 3; col++){
                    if(tiles[row][col].getValue().isEmpty()){
                        return;
                    }
                }
            }

            isEndOfGame = true;
            infoCenter.updateMessage("stalemate...");
            infoCenter.showStartButton();

        }
    }

    private void endGame(String winner, WinningTiles winningTiles){
        isEndOfGame = true; 
        drawWinningLine(winningTiles);
        infoCenter.updateMessage("Player " + winner + " wins!");
        infoCenter.showStartButton();
    }

    private void drawWinningLine(WinningTiles winningTiles){
        winningLine.setStartX(winningTiles.start.getStackPane().getTranslateX());
        winningLine.setStartY(winningTiles.start.getStackPane().getTranslateY());
        winningLine.setEndX(winningTiles.end.getStackPane().getTranslateX());
        winningLine.setEndY(winningTiles.end.getStackPane().getTranslateY());
        winningLine.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
        winningLine.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
        winningLine.setVisible(true);
    }

    private class WinningTiles{
        Tile start;
        Tile middle;
        Tile end;

        public WinningTiles(Tile start, Tile middle, Tile end){
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }

    private class Tile{
        private StackPane pane;
        private Label label;

        public Tile(){
            pane = new StackPane();
            pane.setMinSize(100, 100);

            Rectangle border = new Rectangle();
            border.setWidth(100);
            border.setHeight(100);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);

            label = new Label ("");
            label.setAlignment(Pos.CENTER);
            label.setFont((Font.font(24)));
            pane.getChildren().add(label);

            pane.setOnMouseClicked(event -> {
                if(label.getText().isEmpty() && !isEndOfGame){
                    label.setText(getPlayerTurn());
                    if(getPlayerTurn().charAt(0) == 'X'){
                        label.setTextFill(Color.GREEN);
                    } else {
                        label.setTextFill(Color.RED);
                    }
                    changePlayerTurn();
                    checkForWinner();
                }
            });
        }

        public StackPane getStackPane(){
            return pane;
        }

        public String getValue(){
            return label.getText();
        }

        public void setValue(String value){
            label.setText(value);
        }

    }

}
