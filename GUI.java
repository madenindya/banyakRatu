import java.awt.*;
import javax.swing.*;

public class GUI extends JPanel
{
    protected JPanel[][] squares;
    protected JTextField textField;
    /** Creates a new instance of Board */
    public GUI()
    {
        setSize(800,700);
        setLayout(new GridLayout(16,16));
        squares = new JPanel[16][16];
    }

    public GUI(int h, int w, int n)
    {
        setSize(h,w);
        setLayout(new GridLayout(n,n));
        squares = new JPanel[n][n];
    }

    public void createSquares(int n)
    {
        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                JPanel panel = new JPanel();
                panel.setBackground(getColor(i,j));
                add(panel);
                squares[i][j] = panel;
            }
        }
    }

    protected Color getColor(int x, int y)
    {
        if((x+y)%2 == 0)
            return Color.WHITE;
            return Color.BLACK;
    }

    public static void main(String args[])
    {
        int n = 16;
        GUI t = new GUI(800,700, n);
        else
        t.createSquares(n);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,700);
        frame.setResizable(false);
        frame.add(t);
        frame.setVisible(true);
    }

}
