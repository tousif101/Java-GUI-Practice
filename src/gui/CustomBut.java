package gui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Created by tousifchowdhury on 4/21/15.
 */
public class CustomBut extends Button {
    private int index;
    private ImageView pic;

    /**
     *
     * @param pic the picture of professor
     */
    public CustomBut(int index, ImageView pic) {
        this.index = index;

        this.setGraphic(pic);

    }

    //Rest are getter and setter methods.

    public ImageView getPic() {
        return pic;
    }

    public void setPic(ImageView pic) {
        this.setGraphic(pic);
    }



    public void setIndx(int index) {
        this.index = index;
    }
    public int getIndx() {
        return index;
    }


}
