/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package np;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;


public class N_puzzle_Controller implements Initializable
{
    int display_index;

    @FXML
    private Label unsolvable;

    @FXML
    private TextField filename;

    @FXML
    private Button fileopener;

    @FXML
    private Button start;

    @FXML
    private Button next;

    @FXML
    private Button prev;

    @FXML
    private GridPane grid;

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
    private void play_game(ActionEvent event)
    {
        prev.setVisible(false);
        next.setVisible(false);
        display_index = 0;
        int size = get_size(three, four);
        String heuristic  = get_heuristic(Manhattan, Hamming, Manhattan_Hamming);
        unsolvable.setVisible(false);
        grid.setVisible(false);
        GamePlay.play(size, heuristic, unsolvable, grid);
        if (GamePlay.solvable) {
            State move = GamePlay.all_moves.get(display_index++);
            grid.setLayoutX(100);
            grid.setLayoutY(90);
            grid.setVgap(5);
            grid.setHgap(5);
            grid.getChildren().clear();
            prev.setVisible(true);
            next.setVisible(true);
            draw_state(move);
        }
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
    }


    @FXML
    private void next_state(ActionEvent event)
    {
        if (display_index < GamePlay.all_moves.size())
        {
            State state = GamePlay.all_moves.get(display_index++);
            draw_state(state);
        }
    }


    @FXML
    private void prev_state(ActionEvent event)
    {
        if (display_index - 1 > 0)
        {
            State state = GamePlay.all_moves.get(--display_index - 1);
            draw_state(state);
        }
    }

    private void draw_state(State state)
    {

        int size = state.get_size();
        grid.setVisible(true);
        grid.getChildren().clear();
        int block = Utils.get_block_size_spacing(size).y,  spaces = Utils.get_block_size_spacing(size).x;
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                Text text = new Text("");
                String spacing = new String(new char[spaces]).replace('\0', ' ');
                text.setText(spacing + Integer.toString(state.get_grid()[i][j]));
                Rectangle rec = new Rectangle(block, block);
                if (state.get_grid()[i][j] ==0)
                    rec.setFill(Color.TRANSPARENT);
                else
                {
                    grid.add(rec,j,i);
                    grid.add(text,j,i);
                    rec.setFill(Color.AQUA);
                }
            }
        }
    }

    private int get_size(RadioButton b3, RadioButton b4)
    {
        if (b3.isSelected())
            return (3);
        else if (b4.isSelected())
            return (4);
        else
            return (5);
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