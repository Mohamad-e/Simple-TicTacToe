package myapp;
/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;*/
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class MainApp extends Application{

    private InfoCenter infoCenter;
    private TileBoard tileBoard;

    public static void main(String[] args) {
        //launch(args);
        System.out.println("Hello World");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
            //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            initLayout(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("try");
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("catch");
        }
        /*primaryStage.setTitle("Hello JavaFX!");
        Button btn = new Button();
        btn.setText("Hello JavaFX!");
        btn.setOnAction( (event) -> Platform.exit() );
        Pane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 150));
        primaryStage.show();*/
    }

    private EventHandler<ActionEvent> startNewGame(){
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e){
                infoCenter.hideStartButton();
                infoCenter.updateMessage("Player X's Turn!");
                System.out.println("Game is starting!!!");
                tileBoard.startNewGame();
            } 
        };
    }

    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initTileBoard(root);
    }

    private void initTileBoard(BorderPane root) {

        tileBoard = new TileBoard(infoCenter);
        root.getChildren().add(tileBoard.getStackPane());
    }

    private void initInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();
        infoCenter.setStartButtonOnAction(startNewGame());
        root.getChildren().add(infoCenter.getStackPane());
    }
}