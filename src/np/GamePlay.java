package np;

import javafx.scene.control.Label;

import javax.rmi.CORBA.Util;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class GamePlay
{
    public static int speed = 500;
    public static ArrayList<State> all_moves = new ArrayList<>();
    public static File map = null;
    public static boolean solvable;




    public static void play(Label unsolvable, int size, String heuristic)
    {
        State goal_state, current_state;
        if (map != null && MapUtils.is_map_valid())
            current_state = new State();
        else
            current_state = new State(size);
        goal_state = get_goal_state(current_state.get_size());
        current_state.set_heuristic(heuristic);
        solvable = (MapUtils.isSolvable(current_state.get_grid(), current_state.get_size())) ? false : true;
        unsolvable.setVisible(solvable ? false: true);

        if (solvable)
        {
            Utils.print(current_state);
            System.out.println("\ndid we? :" + solvable);
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
                System.out.println("done : " + all_moves.size());
                for (State p : all_moves) {
                    Utils.print(p);
                    System.out.println("\n\n");
                }
            }
        }
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
}
