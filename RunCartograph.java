package cartograph;

/**
 * Shell for creating new Cartograph maps
 */
public class RunCartograph {

	public static final int ROWS = 20;
	public static final int COLS = 20;

	public static final int PEAKS = 5;
	public static final double MAX_HEIGHT = 100.0;

	public static final int TROUGHS = 5;
	public static final double MIN_HEIGHT = 0.0;

	public static final int K = 4; // nearest neighbors

	public static void main(String[] args) {
		Cartograph map = new Cartograph();
		map.setRows(ROWS);
		map.setCols(COLS);
		map.setPeaks(PEAKS);
		map.setMaxHeight(MAX_HEIGHT);
		map.setTroughs(TROUGHS);
		map.setMinHeight(MIN_HEIGHT);
		map.createMapBase();
		map.createKnownSet();
		map.setK(K);
		map.generateMap();
		System.out.println(map.toString());
	}
}


