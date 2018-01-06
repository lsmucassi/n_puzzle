package np;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public class GamePlay
{
    public static int speed = 500, space, time;
    public static Stack<State> all_moves = new Stack<>();
    public static File map = null;
    public static boolean solvable;


    public static void play(int size, String heuristic)
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
        solvable = !MapUtils.isSolvable(current_state);
        if (solvable)
        {
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
                System.out.println("space complexity : " + space);
                System.out.println("time complexity : " + time);
                System.out.println("number of moves : " + (all_moves.size() - 1));
                for (State p : all_moves) {
                    Utils.print(p);
                    System.out.println("\n\n");
                }
            }
        }
        else
        {
            System.out.println("This Map is unsolvable\n");
            Utils.print(current_state);
        }
        System.exit(0);
    }

    private static void IDA(State start, State goal)
    {
        int threshold, result, found = -1;

        threshold = start.get_fscore();
        all_moves.push(start);
        space = 1;
        time = 0;
        while(true)
        {
            result = search(goal, threshold);

            if(result == found)
                break;
            threshold = result;
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
            if (!is_inside(move))
            {
                all_moves.push(move);
                space++;
                int result = search(goal, threshold);
                if (result == -1)
                    return (-1);
                if (result < min)
                    min = result;
                all_moves.pop();
                time++;
            }
        }
        return (min);
    }

    private static boolean is_inside(State move)
    {
        for (State state : all_moves)
        {
            if (do_states_match(state, move))
                return (true);
        }
        return (false);
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
