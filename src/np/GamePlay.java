package np;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class GamePlay
{
    public static int space , time;
    public static Stack<State> all_moves, closed;
    public static File map = null;
    public static boolean solvable;


    public static void play(int size, String heuristic, Label lbl_solve, GridPane grid)
    {
        State goal_state, current_state = null;
        if (map != null)
        {
            if (MapUtils.is_map_valid())
                current_state = new State();
            else
                MapUtils.map_err_exit(map.getAbsolutePath());
        }
        else
            current_state = new State(size);
        goal_state = get_goal_state(current_state.get_size());
        current_state.set_heuristic(heuristic);
        solvable = !MapUtils.isSolvable(current_state);;
        if (solvable)
        {
            all_moves = new Stack<>();
            closed = new Stack<>();
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;
            System.out.println("\u001B[32m" + "Searching for optimal sequence..." + "\u001B[0m" +"\n");
            IDA(current_state, goal_state);
            elapsedTime = (new Date()).getTime() - startTime;
            System.out.println("\ntime in seconds : " + elapsedTime/1000.0);
            System.out.println("time complexity : " + time);
            System.out.println("space complexity : " + space);
            System.out.println("number of moves : " + (all_moves.size() - 1));
            for (State p : all_moves) {
                System.out.println("\n");
                Utils.print(p);
            }
        }
        else
        {
            System.out.println("This Map is unsolvable\n");

            lbl_solve.setVisible(true);
            Utils.print(current_state);
            grid.setVisible(false);
            grid.setDisable(true);
            try { Thread.sleep(2000);}
            catch (Exception ex){}
            System.exit(0);
        }
    }

    private static void IDA(State start, State goal)
    {
        int threshold, result, found = -1;

        threshold = start.get_fscore();

        time = 0;
        space = 1;
        while(true)
        {
            all_moves.push(start);
            result = search(goal, threshold);
            if(result == found)
                break;
            threshold = result;
            all_moves.clear();
        }

    }

    private static int search(State goal, int threshold) {

        State state = all_moves.peek();

        if (state.get_fscore() > threshold)
            return state.get_fscore();

        if (do_states_match(state, goal))
            return -1;

        int min = 2147483647;
        ArrayList<State> possible_moves = get_possible_states(state);
        for (State move : possible_moves)
        {
            if (!all_moves.contains(move))
            {
                all_moves.push(move);
                space++;
                int result = search(goal, threshold);
                if (result == -1)
                    return (-1);
                if (result < min)
                    min = result;
                closed.push(all_moves.pop());
                time++;
            }
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

    private static boolean do_states_match(State one, State two)
    {
        for (int i = 0; i < one.get_size(); i++)
        {
            for (int j = 0; j < one.get_size(); j++)
            {
                if (one.get_grid()[i][j] != two.get_grid()[i][j])
                    return (false);
            }
        }
        return (true);
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
