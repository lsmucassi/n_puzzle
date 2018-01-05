package np;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Utils
{
    

    public static boolean is_in_place(int number, State game)
    {
        
        Point coords = get_ideal_coords(number, game.get_size());
        if (game.get_grid()[coords.y][coords.x] == number)
            return (true);
        return (false);
    }

    public static Point get_ideal_coords(int number, int size)
    {
        Point coords;

        int factor = size - 1, circle = 0;
        
        if (number == 0)
            number = size * size;
        while (factor != 0 && (number - 4*factor) > 0)
        {
            circle++;
            number-=4*factor;
            factor -= 2;
        }
        if (number - factor <= 0)
            coords = new Point(circle + number - 1, circle);
        else if (number - 2*factor <= 0)
            coords = new Point(size - circle - 1, circle + number - factor - 1);
        else if (number - 3*factor <= 0)
            coords = new Point(size - circle - number + 2*factor, size - circle - 1);
        else
            coords = new Point(circle, size - circle - number + 3*factor);
        return (coords);
    }
    
    public static int get_distance(Point one, Point two)
    {
        return (Math.abs(one.x - two.x) + Math.abs(one.y - two.y));
    }
    


    public static Point get_block_size_spacing(int size)
    {
        Point point;
        if (size == 3)
            point = new Point(13, 100);
        else if (size == 4)
            point = new Point(10, 80);
        else if (size == 5)
            point = new Point(7, 60);
        else if (size == 6)
            point = new Point(6, 55);
        else if (size == 7)
            point = new Point(5, 45);
        else
            point = new Point(4, 40);

        return (point);
    }

    public static void print(State state)
    {
        for(int i = 0;i <state.get_size();i++)
        {
            for(int j=0; j<state.get_size();j++) {
                System.out.print(state.get_grid()[i][j] + " ");
                if (state.get_grid()[i][j] <= 9)
                    System.out.print(" ");
            }
            System.out.println("");
        }
    }


}
