/*
 * File: ConcentrationGUI.java
 *
 * This application uses java.util.Observer notifications.
 */

package gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.ConcentrationModel;



import javafx.scene.layout.*;




/**
 * The ConcentrationGUI application is the GUI for Concentration.
 *
 * @author YOUR NAME HERE
 */
public class ConcentrationGUI extends Application implements Observer {

    private BorderPane border;
    private TilePane tile;
    private Button resetbut;
    private Button undobut;
    private Button card;
    private Label movLab;


    /**
     * model for the game
     */
    private ConcentrationModel model;


    /**
     * Image shown for a face-down card
     */
    private Image faceDown;

    // OTHER DECLARATIONS GO HERE.

    /**
     * Images shown for the face up cards.
     * Indices are the numbers the model associates with each card
     * and that determine what matches what.
     * This means that there are half as many images as there are
     * cards on the board.
     */
    private final ArrayList<Image> faceUps =
            new ArrayList<>( ConcentrationModel.NUM_PAIRS );

    /**
     * Load up the card images provided for the game.
     */
    private void installImages() {

        this.faceDown =
                new Image(getClass().getResourceAsStream("EmptyHead.jpg"));

        ArrayList<String> imagenames =
                new ArrayList<>(ConcentrationModel.NUM_PAIRS);
        imagenames.add("atd.jpg");
        imagenames.add("bks.jpg");
        imagenames.add("jeh.jpg");
        imagenames.add("axm.jpg");
        imagenames.add("rwd.jpg");
        imagenames.add("lm.jpg");
        imagenames.add("sps.jpg");
        imagenames.add("tjb.jpg");

        Class<?> myClass = this.getClass();
        for (int n = 0; n < ConcentrationModel.NUM_PAIRS; ++n) {
            try {
                String imageName = imagenames.get(n);
                InputStream imgStream = myClass.getResourceAsStream(imageName);
                Image image = new Image(imgStream);
                this.faceUps.add(image);
                imgStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    //ConcentrationModel model;

    /**
     */
    @Override
    /**
     * init in class Application
     */
    public void init() {
        // TODO non-graphic setup code goes here.
        this.card = new Button();

        this.model = new ConcentrationModel();
        model.addObserver(this);

        this.tile = new TilePane();
        this.border = new BorderPane();
        this.resetbut = new Button("Reset");
        this.undobut = new Button("Undo");
        //this.movlab = new Label();


    }

    /**
     * start constructs the layout for the game.
     *
     * @param stage container (window) in which to render the UI
     */
    @Override
    public void start(Stage stage) {
        // TODO fill this method in, probably with the help of
        // subordinate private methods.

        //BorderPane border = new BorderPane();
        Label alab = new Label("select the first card");
        alab.setAlignment(Pos.CENTER_LEFT);
        this.border.setTop(alab);

        installImages();

        this.tile.setPrefRows(4);
        this.tile.setPrefRows(4);






        for (int i = 0; i < 16 ; i++) {

            int indx = i;
            ImageView imge = new ImageView(faceDown);
            imge.setFitHeight(80);
            imge.setFitWidth(80);

            CustomBut butt = new CustomBut(model.getCards().get(i).getNumber(),imge);
            butt.setPrefSize(80,80);

            butt.setOnAction(event -> model.selectCard(indx));
            this.tile.getChildren().add(butt);

        }

        this.border.setCenter(this.tile);
        BorderPane bot = new BorderPane();
        HBox Hbot = new HBox();
        this.resetbut.setOnAction(event ->model.reset());

        this.undobut.setOnAction(event -> model.undo());

        Hbot.getChildren().add(resetbut);
        Hbot.getChildren().add(undobut);
        bot.setCenter(Hbot);

        this.movLab = new Label(" 0 Moves");
        movLab.setAlignment(Pos.CENTER_RIGHT);

        bot.setRight(movLab);
        this.border.setBottom(bot);


        Scene scene = new Scene(this.border);
        stage.setTitle("Conc");
        stage.setScene(scene);


        stage.show();

    }


    /**
     * update in interface Observer
     */
    @Override
    public void update(Observable o, Object arg) {
        // TODO Write your observer code here.
        int numcards = model.howManyCardsUp();
        if(numcards==0){
            movLab.setText("Select first card");
        }
        else if(numcards ==1){
            movLab.setText("Select Another Card");
        }
        else{
            movLab.setText("No Match: Undo or select");
        }
        for(int i =0; i<16; i++){
            CustomBut butt = (CustomBut) this.tile.getChildren().get(i);
            if (model.getCards().get(i).isFaceUp()){
                ImageView imge = new ImageView();
                imge = new ImageView(faceUps.get(butt.getIndx()));
                imge.setFitWidth(80);
                imge.setFitHeight(80);
                butt.setPic(imge);
            }
            else{
                ImageView dwn = new ImageView(faceDown);
                dwn.setFitHeight(80);
                dwn.setFitWidth(80);
                butt.setPic(dwn);
            }
        }
        if(model.getMoveCount() == 0){
            for (int i = 0; i < 16; i++){
                CustomBut butt = (CustomBut) this.tile.getChildren().get(i);
                butt.setIndx(model.getCards().get(i).getNumber());
                ImageView dwn = new ImageView(faceDown);
                dwn.setFitHeight(80);
                dwn.setFitWidth(80);
                butt.setPic(dwn);


            }
        }

        movLab.setText(model.getMoveCount()+"Moves");

    }

    // OTHER CODE GOES HERE

    /**
     * main entry point launches the JavaFX GUI.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
