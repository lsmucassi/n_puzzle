/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author budas
 */
public class N_puzzle_Controller implements Initializable
{

    @FXML
    private Label unsolvable;

    @FXML
    private TextField filename;

    @FXML
    private Button fileopener;

    @FXML
    private Button start;

    @FXML
    private GridPane block;

    @FXML
    private RadioButton Manhattan;

    @FXML
    private RadioButton Hamming;

    @FXML
    private RadioButton Manhattan_Hamming;

    @FXML
    private RadioButton three;

    @FXML
    private RadioButton four;

    @FXML
    private RadioButton five;

    @FXML
    private RadioButton six;

    @FXML
    private RadioButton seven;

    @FXML
    private RadioButton eight;

    @FXML
    private void play_game(ActionEvent event)
    {
        int size = get_size(three, four, five, six, seven);
        String heuristic  = get_heuristic(Manhattan, Hamming, Manhattan_Hamming);
        block.setLayoutX(100);
        block.setLayoutY(90);
        block.setVgap(5);
        block.setHgap(5);
        unsolvable.setVisible(false);
        GamePlay.play(size, heuristic);
    }

    @FXML
    private void chooseFIle(ActionEvent event)
    {
        FileChooser choose = new FileChooser();
        GamePlay.map = choose.showOpenDialog(null);
        if (GamePlay.map == null)
            start.setDisable(true);
        else
        {
            start.setDisable(false);
            filename.setText(GamePlay.map.getAbsolutePath().toString());
        }
    }

    @FXML
    private void activate_button(ActionEvent event)
    {
        fileopener.setDisable(false);
        filename.setDisable(false);
        three.setDisable(true);
        four.setDisable(true);
        five.setDisable(true);
        six.setDisable(true);
        seven.setDisable(true);
        eight.setDisable(true);
        if (GamePlay.map == null)
            start.setDisable(true);
    }

    @FXML
    private void activate_puzzles(ActionEvent event)
    {
        start.setDisable(false);
        fileopener.setDisable(true);
        filename.setText("...");
        GamePlay.map = null;
        filename.setDisable(true);
        three.setDisable(false);
        four.setDisable(false);
        five.setDisable(false);
        six.setDisable(false);
        seven.setDisable(false);
        eight.setDisable(false);
    }

    private int get_size(RadioButton b3, RadioButton b4, RadioButton b5, RadioButton b6, RadioButton b7)
    {
        if (b3.isSelected())
            return (3);
        else if (b4.isSelected())
            return (4);
        else if (b5.isSelected())
            return (5);
        else if (b6.isSelected())
            return (6);
        else if (b7.isSelected())
            return (7);
        else
            return (8);
    }

    private String get_heuristic(RadioButton manhattan, RadioButton hamming, RadioButton manhattan_hamming)
    {

        if (manhattan.isSelected())
            return (manhattan.getText());
        else if (hamming.isSelected())
            return (hamming.getText());
        else
            return (manhattan_hamming.getText());
    }
    
   @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }
    
}