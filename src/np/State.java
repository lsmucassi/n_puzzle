package np;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class State
{
    private int[][] grid;
    private Point blank;
    private Point prev_blank;
    private int heuristic_value, size, level;
    private String heuristic;

    
    public State(int size)
    {
        this.size = size;
        heuristic_value = 0;
        level = 0;
        grid = new int[size][];
        for (int i = 0; i < size; i++)
            grid[i] = new int[size];
        initialise_grid();
        prev_blank = new Point(blank.x, blank.y);
    }
    
    public State (State state, Point move)
    {
        this.size = state.get_size();
        this.level = state.get_level() + 1;
        this.heuristic = state.get_heuristic();
        this.blank = new Point(move.x, move.y);
        this.prev_blank = new Point(state.get_blank().x, state.get_blank().y);
        this.grid = new int[size][];
        for (int i = 0; i < size; i++)
            this.grid[i] = new int[size];
        for (int i = 0; i < this.size; i++)
        {
            for (int j = 0; j < this.size; j++)
                this.grid[i][j] = state.get_grid()[i][j];
        }
        this.grid[state.get_blank().y][state.get_blank().x] = this.grid[move.y][move.x];
        this.grid[move.y][move.x] = 0;
        this.set_heuristic_value();
    }

    public State()
    {
        heuristic_value = 0;
        level = 0;
        BufferedReader br;
        try
        {
            int row = 0;
            int col;
            if (GamePlay.map != null)
            {
                br = new BufferedReader(new FileReader(GamePlay.map));
                String line;
                while ((line = br.readLine()) != null)
                {
                    line = line.trim();
                    if (MapUtils.is_comment(line))
                        continue;
                    line = MapUtils.cut_comment_off(line);
                    if (MapUtils.isNumeric(line))
                    {
                        this.size = Integer.parseInt(line);
                        grid = new int[size][];
                        for (int i = 0; i < size; i++)
                            grid[i] = new int[size];
                    }
                    else
                    {
                        col = 0;
                        String[] items = line.split("\\s+");
                        for (String str : items)
                        {
                            grid[row][col++] = Integer.parseInt(str);
                            if (grid[row][col-1] == 0)
                            {
                                blank = new Point(col - 1, row);
                                prev_blank = new Point(blank.x, blank.y);
                            }
                        }
                        row++;
                    }
                }
            }
        }

        catch (IOException ex)
        {
            System.out.println("Error in reading file");
        }
    }

    private void initialise_grid()
    {
        int total = size * size;
        int row, col;

        col = (int)(Math.random()*size);
        row = (int)(Math.random()*size);
        blank = new Point(col, row);
        for (int num = 1; num < total; num++)
        {
            do
            {
                row = (int)(Math.random()*size);//generates a random number in range [0,size -1]
                col = (int)(Math.random()*size);//generates a random number in range [0,size -1]
            }
            while ((col == blank.x && row == blank.y) || grid[row][col] != 0);
            grid[row][col] = num;
        }
    }

    public int get_level()
    {
        return (level);
    }

    public int get_fscore()
    {
        this.set_heuristic_value();
        return (heuristic_value + level);
    }
    
    public String get_heuristic()
    {
        return (heuristic);
    }
       
    public int get_size()
    {
        return (size);
    }
       
    public Point get_prev_blank()
    {
        return (prev_blank);
    }
    
    public int[][] get_grid()
    {
        return (grid);
    }
    
    public Point get_blank()
    {
        return (blank);
    }
   
    public void set_heuristic(String heuristic)
    {
        this.heuristic = heuristic;
        set_heuristic_value();
    }

    public int getHeuristic_value()
    {
        return (heuristic_value);
    }

    private void set_heuristic_value()
     {
         if (heuristic.equals("Manhattan"))
             heuristic_value = Heuristics.Manhattan(this);
         if (heuristic.equals("Hamming-Distance"))
             heuristic_value = Heuristics.Hamming_Distance(this);
         if (heuristic.equals("Manhattan-Hamming"))
             heuristic_value = Heuristics.Manhattan_Hamming(this);
     }
}
