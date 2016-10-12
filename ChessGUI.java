import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import org.sat4j.specs.TimeoutException;
import java.io.*;
import java.net.URISyntaxException;

import javax.imageio.*;

public class ChessGUI  {
	public int N;
	public String[] configuration;
	public static int beforeN=-1;

	private JPanel gui = null;
	private JButton[][] chessBoardSquares;
	private JPanel chessBoard;
	@SuppressWarnings("unused")
	private final JLabel message = new JLabel(
			"Chess Champ is ready to play!");
	public final int QUEEN = 0, KING = 1,
			ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
	public final int[] STARTING_ROW = {
			ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
	};
	public final int BLACK = 0, WHITE = 1;

	ChessGUI(){
	}

	ChessGUI(int n, boolean moreFlag)  {

		try {
			this.N = n;
			beforeN = N;
			initializeGui(moreFlag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	@SuppressWarnings("resource")
	public final void initializeGui (boolean moreFlag) throws IOException, TimeoutException {

		if(moreFlag){
			GenerateMore generateMore = new GenerateMore();
			generateMore.generate();
		}else{
			Cnf cnf = new Cnf();
			cnf.getCnf(N);
		}

		SATSolver SAT = new SATSolver();
		try {
			SAT.solve();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		chessBoardSquares = new JButton[N][N];

		FileReader fr = new FileReader("output.cnf");
		BufferedReader tr = new BufferedReader(fr);

		String isSat = tr.readLine();
		if (isSat.equals("SAT")) {
			configuration = new String[N];
			String cnfString =tr.readLine(); 
			configuration = cnfString.split(" ");
		}else{
			return;
		}

		this.gui = new JPanel(new BorderLayout(3, 3));

		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		gui.add(new JLabel("?"), BorderLayout.LINE_START);

		chessBoard = new JPanel(new GridLayout(0, N)) {
			private static final long serialVersionUID = 1L;

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
				try{
					int index = (ii)*N+jj;
					if(configuration[index].charAt(0) != '-'){
						File f = new File(System.getProperty("java.class.path"));
						File dir = f.getAbsoluteFile().getParentFile();
						String jarDir = dir.toString();
						BufferedImage buff = ImageIO.read(new File(jarDir+"/queen.png"));
						ImageIcon icon = new ImageIcon(buff);
						b.setIcon(icon);
					}

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
		for (int ii = 0; ii < N; ii++) {
			for (int jj = 0; jj < N; jj++) {
				chessBoard.add(chessBoardSquares[jj][ii]);
			}
		}
	}

	public final JComponent getGui() {
		return gui;
	}

	public void createGUI() throws IOException {

		Runnable r = new Runnable() {
			@SuppressWarnings("serial")
			@Override
			public void run() {
				JPanel begin_panel = new JPanel(new BorderLayout(3, 3));
				begin_panel.setBorder(new EmptyBorder(25, 25, 25, 25));
				JToolBar begin_tools = new JToolBar();
				begin_tools.setFloatable(false);
				begin_panel.add(begin_tools, BorderLayout.PAGE_START);

				JLabel m = new JLabel("Masukan ukuran n  ", JLabel.TRAILING);
				final JTextField textField = new JTextField(3);
				begin_tools.add(m); // TODO - add functionality!
				m.setLabelFor(textField);
				begin_tools.add(textField);

				Action nSizeAction = new AbstractAction("Solve") {
					@Override
					public void actionPerformed(ActionEvent e) {

						int inputN;
						try {  
							inputN = Integer.parseInt(textField.getText());
						}  
						catch(NumberFormatException nfe) {  
							JOptionPane.showMessageDialog(null, "Isi size dengan angka", "Invalid Size", JOptionPane.INFORMATION_MESSAGE);
							return;
						}  

						ChessGUI cg = new ChessGUI(inputN, false);
						JFrame f = new JFrame("N-Queens using SAT");
						if(cg.getGui() != null){
							f.add(cg.getGui());
							f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							f.setLocationByPlatform(true);
							f.pack();
							f.setMinimumSize(f.getSize());
							f.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null, "Unsatisfiable, tidak dapat dibuat.", "Unsatisfiable", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				};

				Action moreAction = new AbstractAction("More") {
					@Override
					public void actionPerformed(ActionEvent e) {

						int inputN;
						try {  
							inputN = Integer.parseInt(textField.getText());
							if(beforeN != inputN){
								JOptionPane.showMessageDialog(null, "Size berbeda. Gunakan button solve", "Error", JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}  
						catch(NumberFormatException nfe) {  
							JOptionPane.showMessageDialog(null, "Isi size dengan angka", "Invalid Size", JOptionPane.INFORMATION_MESSAGE);
							return;
						}  

						ChessGUI cg = new ChessGUI(inputN, true);
						JFrame f = new JFrame("N-Queens Another Combination");
						if(cg.getGui() != null){
							f.add(cg.getGui());
							f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							f.setLocationByPlatform(true);
							f.pack();
							f.setMinimumSize(f.getSize());
							f.setVisible(true);
						}else{
							JOptionPane.showMessageDialog(null, "Tidak ada kombinasi lain.", "Unsatisfiable", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				};

				begin_tools.add(nSizeAction);
				begin_tools.add(moreAction);

				begin_tools.addSeparator();
				JFrame begin_frame = new JFrame("N-Queens using SAT");
				begin_panel.setPreferredSize(new Dimension(400, 100));
				begin_frame.setLocation(150, 100);
				begin_frame.add(begin_panel);
				begin_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				begin_frame.pack();
				begin_frame.setMinimumSize(begin_frame.getSize());
				begin_frame.setResizable(true);
				begin_frame.setVisible(true);
			}
		};

		SwingUtilities.invokeLater(r);
	}
}