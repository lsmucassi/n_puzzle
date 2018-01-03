package np;

import java.awt.Point;

public class Heuristics 
{

    public static int Manhattan(State game)
    {
        int value = 0;
        
        for (int i = 0; i < game.get_size(); i++)
        {
            for (int j = 0; j < game.get_size(); j++)
            {
                if (!Utils.is_in_place(game.get_grid()[i][j], game))
                {
                    Point one = new Point(j, i);
                    Point two = Utils.get_ideal_coords(game.get_grid()[i][j], game.get_size());
                    if (game.get_grid()[i][j] != 0)
                        value += Utils.get_distance(one, two);
                }
            }
        }
        return (value);
    }
    
    public static int Misplaced_Tiles(State game)
    {
        int value = 0;
        
        for (int i = 0; i < game.get_size(); i++)
        {
            for (int j = 0; j < game.get_size(); j++)
            {
                if (!Utils.is_in_place(game.get_grid()[i][j], game))
                    value += 1;
            }
        }
        return (value);
    }
}
