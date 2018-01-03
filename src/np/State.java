package np;

import java.awt.Point;

public class State
{
    private int[][] grid;
    private Point blank;
    private Point prev_blank;
    private int heuristic_value, size;
    private String heuristic;
    
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
    
    public State(int size)
    {
        this.size = size;
        heuristic_value = 0;
        grid = new int[size][];
        for (int i = 0; i < size; i++)
            grid[i] = new int[size];
        initialise_grid();
        prev_blank = new Point(blank.x, blank.y);
    }
    
    public State (State state, Point move)
    {
        this.size = state.get_size();
        this.heuristic = state.get_heuristic();
        this.heuristic_value = state.get_heuristic_value();
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
    }
    
    public int get_heuristic_value()
    {
        return (heuristic_value);
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
    }
     public void set_heuristic_value(int heuristic_value)
     {
         this.heuristic_value = heuristic_value;
     }
}
