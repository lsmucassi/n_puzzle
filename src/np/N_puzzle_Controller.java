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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
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
    private Button fileopener;
    @FXML
    private GridPane block;

    @FXML
    private RadioButton Manhattan;

    @FXML
    private RadioButton Misplaced_Tiles;

    @FXML
    private RadioButton blank;

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
        int size = size(three, four, five, six, seven, eight);
        String heuristic  = get_heuristic(Manhattan, Misplaced_Tiles, blank);
        block.getChildren().clear();
        block.setLayoutX(100);
        block.setLayoutY(90);
        block.setVgap(5);
        block.setHgap(5);

        Utils.play(block,size, heuristic);
    }

    @FXML
    private void chooseFIle(ActionEvent event)
    {
        FileChooser choose = new FileChooser();
        File map = choose.showOpenDialog(null);
        BufferedReader rd;
        FileReader fr;
        try
        {
            rd = new BufferedReader(new FileReader(map));
            String line;
            while((line = rd.readLine()) != null)
            {
                System.out.println(line);
            }
        }
        catch (IOException ex)
        {
            System.out.println("error in reading file");
        }

        System.out.println("wow");
    }

    @FXML
    private void activate_button(ActionEvent event)
    {
        fileopener.setDisable(false);;
        three.setDisable(true);
        four.setDisable(true);
        five.setDisable(true);;
        six.setDisable(true);;
        seven.setDisable(true);;
        eight.setDisable(true);;
    }

    @FXML
    private void activate_puzzles(ActionEvent event)
    {
        fileopener.setDisable(true);
        three.setDisable(false);
        four.setDisable(false);
        five.setDisable(false);;
        six.setDisable(false);;
        seven.setDisable(false);;
        eight.setDisable(false);;
    }

    private int size(RadioButton b3, RadioButton b4, RadioButton b5, RadioButton b6, RadioButton b7, RadioButton b8)
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

    private String get_heuristic(RadioButton manhattan, RadioButton misplaced, RadioButton linear)
    {

        if (manhattan.isSelected())
            return (manhattan.getText());
        else if (misplaced.isSelected())
            return (misplaced.getText());
        else
            return (linear.getText());

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
}