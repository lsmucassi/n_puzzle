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
    
    public static int speed = 500;
    public static ArrayList<State> all_moves = new ArrayList<>();
    public static File map;

    private static boolean isSolvable(int[][] grid, int size)
    {
        int inversions = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < size; i++)
        {
            for(int j = 0; j < size;j++)
                numbers.add(grid[i][j]);
        }
        int row = 0;
        int blankRow = 0;
        for (int i = 0; i < numbers.size(); i++)
        {
               if (i % size == 0)
                    row++;
               if (numbers.get(i) == 0)
               {
                    blankRow = row;
                    continue;
               }
               for (int j = i + 1; j < numbers.size(); j++)
               {
                    if (numbers.get(i) > numbers.get(j) && numbers.get(j) != 0)
                        inversions++;
                }
        }

        if (size % 2 == 0)
        {
                if (blankRow % 2 == 0)
                    return (inversions % 2 == 0);
                 else
                    return (inversions % 2 != 0);

        }
        else
               return (inversions% 2 == 0);
    }

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
    
    public static State get_goal_state(int size)
    {
        State state = new State(size);
        for (int number = 1; number <= size * size; number++)
        {
            Point coords = Utils.get_ideal_coords(number, size);
            state.get_grid()[coords.y][coords.x] = (number == size * size) ? 0 : number;
        }
        return (state);
    }
    
    private static boolean does_game_continue(State one, State two)
    {
        for (int i = 0; i < one.get_size(); i++)
        {
            for (int j = 0; j < one.get_size(); j++)
            {
                if (one.get_grid()[i][j] != two.get_grid()[i][j])
                    return (true);
            }
        }
        return (false);
    }
    
    private static ArrayList<State> get_possible_states(State state)
    {
        ArrayList<State> states = new ArrayList<>();
        Point temp;
   
        temp = new Point(state.get_blank().x + 1, state.get_blank().y);
        if (temp.x < state.get_size() && !temp.equals(state.get_prev_blank()))
            states.add(new State(state, temp));
         
        temp = new Point(state.get_blank().x - 1, state.get_blank().y);
        if (temp.x >= 0 && !temp.equals(state.get_prev_blank()))
            states.add(new State(state, temp));
        
        temp = new Point(state.get_blank().x, state.get_blank().y + 1);
        if (temp.y < state.get_size() && !temp.equals(state.get_prev_blank()))
            states.add(new State(state, temp));
        
        temp = new Point(state.get_blank().x, state.get_blank().y - 1);
        if (temp.y >= 0 && !temp.equals(state.get_prev_blank()))
            states.add(new State(state, temp));
        
        return (states);
    }

    private static void IDA(State start, State goal)
    {
        int threshold, result, found = -1,round = 0;

        start.set_heuristic_value();
        threshold = start.get_fscore();

        while(true)
        {
            all_moves.clear();
            result = search(start, goal, threshold);
            ++round;

            if(result == found)
                break;
            threshold = result;
        }

    }
    private static int search(State state, State goal, int threshold) {

        state.set_heuristic_value();
        all_moves.add(state);
        if (state.get_fscore() > threshold) {
           all_moves.remove(state);
            return state.get_fscore();
        }
        if (does_game_continue(state, goal) == false)
            return -1;

        int min = 2147483647;
        ArrayList<State> possible_moves = get_possible_states(state);
        for (State move : possible_moves) {
            int result = search(move, goal, threshold);
            if(result == -1)
                return (-1);
            if(result < min)
                min = result;
        }
        return (min);
    }



    private static Point get_block_size_spacing(int size)
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

    private static void print(State state)
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

    public static void play(GridPane Pane, Label unsolvable, int size, String heuristic)
    {
        State goal_state = get_goal_state(size), current_state = new State(size);
        current_state.set_heuristic(heuristic);
        if (isSolvable(current_state.get_grid(), size) == false) {
            unsolvable.setVisible(true);
            return;
        }
        else
        {

            int block_size = get_block_size_spacing(size).y, spaces = get_block_size_spacing(size).x;

            print(current_state);
            System.out.println("\ndid we?\n");
            IDA(current_state, goal_state);
            {
               /* Pane.getChildren().clear();
                for (int i = 0; i < size; i++)
                {
                    for (int j ..
                    = 0; j < size; j++)
                    {
                        Text text = new Text("");
                        String spacing = new String(new char[spaces]).replace('\0', ' ');
                        text.setText(spacing + Integer.toString(current_state.get_grid()[i][j]));
                        Rectangle rec = new Rectangle(block_size, block_size);
                        if (current_state.get_grid()[i][j] ==0)
                            rec.setFill(Color.TRANSPARENT);
                        else
                        {
                            Pane.add(rec,j,i);
                            Pane.add(text,j,i);
                            rec.setFill(Color.AQUA);
                        }
                    }
                }*/
                // print(_state);
                System.out.println("done : " + all_moves.size());
                for (State p : all_moves) {
                    print(p);
                    System.out.println("\n\n");
                }
            }
        }
    }
}
