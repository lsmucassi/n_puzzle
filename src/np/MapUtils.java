package np;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapUtils
{
    public static boolean isSolvable(State state)
    {
        int inversions = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < state.get_size(); i++)
        {
            for(int j = 0; j < state.get_size(); j++)
            {
                if (state.get_grid()[i][j] != 0)
                    numbers.add(state.get_grid()[i][j]);
            }
        }
        for (int i = 0; i < numbers.size(); i++)
        {
            for (int j = i + 1; j < numbers.size(); j++)
            {
                if (numbers.get(i) > numbers.get(j))
                    inversions++;
            }
        }
        if (state.get_size() % 2 == 0)
        {
            if (state.get_blank().y % 2 == 0)
                return (inversions % 2 == 0);
            else
                return (inversions % 2 != 0);
        }
        else
            return (inversions% 2 == 0);

    }

    public static boolean is_map_valid()
    {
        BufferedReader br;
        try
        {
            if (GamePlay.map != null)
            {
                br = new BufferedReader(new FileReader(GamePlay.map));
                String line;
                int size = 0, lines = 0;
                while ((line = br.readLine()) != null)
                {
                    line = line.trim();
                    if (is_comment(line))
                        continue ;
                    line = cut_comment_off(line);
                    if (isNumeric(line))
                    {
                        if (size > 0)
                            return (false);
                        size = Integer.parseInt(line);
                        if (size < 2)
                            return (false);
                    }
                    else
                    {
                        lines++;
                        if (size > 0 && lines <= size)
                        {
                            String[] items = line.split("\\s+");
                            if (items.length != size)
                                return (false);
                            for (String str : items)
                            {
                                if (isNumeric(str) == false)
                                    return (false);
                            }
                        }
                        else
                            return (false);
                    }
                }
                if (lines != size)
                    return (false);
                return (true);
            }
            else
                return (false);
        }

        catch (IOException ex)
        {
            System.out.println("Error in reading file");
            System.exit(0);
        }
        return (true);
    }

    public static boolean is_comment(String line)
    {
        String tmp = line.split("\\s+")[0];
        if (tmp.charAt(0)== '#')
            return true;
        else
            return false;
    }

    public static  String cut_comment_off(String line)
    {
        return line.trim().split("#")[0];
    }

    public static boolean isNumeric(String line)
    {
        for (char c : line.toCharArray())
        {
            if (!Character.isDigit(c))
                return (false);
        }
        return (true);
    }

    public static void map_err_exit(String filename)
    {
        System.out.println("Invalid map discovered in file : " + filename);
        System.exit(0);
    }
}
