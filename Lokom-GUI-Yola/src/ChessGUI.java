import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class ChessGUI  {
	public  int N = 4;
	public  String[] configuration;

	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JButton[][] chessBoardSquares = new JButton[N][N];
	private Image[][] chessPieceImages = new Image[2][6];
	private JPanel chessBoard;
	private final JLabel message = new JLabel(
			"Chess is ready to play!");
	public  final int QUEEN = 0, KING = 1,
			ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
	public  final int[] STARTING_ROW = {
			ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
	};
	public  final int BLACK = 0, WHITE = 1;

	public final void initializeGui() {
		// create the images for the chess pieces
		
		System.out.println("JALAN GUI");

		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);
		Action newGameAction = new AbstractAction("Solve") {

			@Override
			public void actionPerformed(ActionEvent e) {
				setupNewGame();
			}
		};
		tools.add(newGameAction);
		tools.addSeparator();
		JLabel n = new JLabel("n-size", JLabel.TRAILING);
		JTextField textField = new JTextField(5);
		tools.add(n); // TODO - add functionality!
		n.setLabelFor(textField);
		tools.add(textField);
		tools.addSeparator();
		tools.add(message);

		gui.add(new JLabel("?"), BorderLayout.LINE_START);

		chessBoard = new JPanel(new GridLayout(0, N)) {
			/**
			 * Override the preferred size to return the largest it can, in
			 * a square shape.  Must (must, must) be added to a GridBagLayout
			 * as the only component (it uses the parent as a guide to size)
			 * with no GridBagConstaint (so it is centered).
			 */
			@Override
			public final Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				Dimension prefSize = null;
				Component c = getParent();
				if (c == null) {
					prefSize = new Dimension(
							(int)d.getWidth(),(int)d.getHeight());
				} else if (c!=null &&
						c.getWidth()>d.getWidth() &&
						c.getHeight()>d.getHeight()) {
					prefSize = c.getSize();
				} else {
					prefSize = d;
				}
				int w = (int) prefSize.getWidth();
				int h = (int) prefSize.getHeight();
				// the smaller of the two sizes
				int s = (w>h ? h : w);
				return new Dimension(s,s);
			}
		};
		chessBoard.setBorder(new CompoundBorder(
				new EmptyBorder(8,8,8,8),
				new LineBorder(Color.BLACK)
				));
		// Set the BG to be ochre
		Color ochre = new Color(204,119,34);
		chessBoard.setBackground(ochre);
		JPanel boardConstrain = new JPanel(new GridBagLayout());
		boardConstrain.setBackground(ochre);
		boardConstrain.add(chessBoard);
		gui.add(boardConstrain);

		// create the chess board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < chessBoardSquares.length; ii++) {
			for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
				JButton b = new JButton();
				b.setMargin(buttonMargin);
				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				try{
//					int index = (ii)*N+jj;
//					//System.out.println("index = " + index);
//					if(configuration[(ii)*N+jj].charAt(0) != '-'){
//						BufferedImage buff = ImageIO.read(new File("queen.png"));
//						ImageIcon icon = new ImageIcon(buff);
//						b.setIcon(icon);
//					}

				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}

				if ((jj % 2 == 1 && ii % 2 == 1)
						|| (jj % 2 == 0 && ii % 2 == 0)) {
					b.setBackground(Color.WHITE);
				} else {
					b.setBackground(Color.BLACK);
				}
				chessBoardSquares[jj][ii] = b;
			}
		}

		/*
		 * fill the chess board
		 */
		// fill the black non-pawn piece row
		for (int ii = 0; ii < N; ii++) {
			for (int jj = 0; jj < N; jj++) {
				chessBoard.add(chessBoardSquares[jj][ii]);
			}
		}
	}

	public final JComponent getGui() {
		return gui;
	}

	/**
	 * Initializes the icons of the initial chess board piece places
	 */
	private final void setupNewGame() {
		message.setText("Make your move!");
	}

	public boolean checkSAT() throws IOException{

		FileReader fr = new FileReader("output.cnf");
		BufferedReader tr = new BufferedReader(fr);

		configuration = new String[N];
		String isSat = tr.readLine();
		if (isSat.equals("SAT")) {
			configuration = tr.readLine().split(" ");
			N = (int) Math.sqrt(configuration.length+1);
			return true;
		}
		return false;
	}

	public void createChess() throws IOException {
		
		initializeGui();

		Runnable r = new Runnable() {

			@Override
			public void run() {
				ChessGUI cg = new ChessGUI();
				JFrame f = new JFrame("ChessChamp");
				f.add(cg.getGui());
				// Ensures JVM closes after frame(s) closed and
				// all non-daemon threads are finished
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				// See http://stackoverflow.com/a/7143398/418556 for demo.
				f.setLocationByPlatform(true);
				// ensures the frame is the minimum size it needs to be
				// in order display the components within it
				f.pack();
				// ensures the minimum size is enforced.
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		};

		SwingUtilities.invokeLater(r);
	}
}