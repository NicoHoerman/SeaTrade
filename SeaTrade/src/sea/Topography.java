package sea;

import java.io.Serializable;

// Topographie-Klasse 
// ein Topography-Objekt beschreibt ein Feld der Land-/Seekarte

public class Topography implements Serializable {

	private static final long serialVersionUID = 2L;
	protected Ground ground;
	protected int height; // Meerestiefe/-hoehe

	public Topography(Ground ground, int height) {
		setTopography(ground, height);
	}

	public Topography(Ground ground) {
		this(ground, 0);
	}

	public Topography() {
		this(Ground.LAND, 0);
	}

	public Ground getGround() {
		return ground;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTopography(Ground ground, int height){
		this.ground = ground;
		this.height = height;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TOPO|");
		sb.append(ground.name());
		sb.append("|");
		sb.append(height);
		return sb.toString();
	}

	public static Topography parse(String s) {
		String[] token = s.trim().split("\\|");
		if (token.length == 3) {
			if (token[0].equals("TOPO")) {
				try {
					Ground g = Ground.valueOf(token[1]);
					int temp = Integer.parseInt(token[2]);
					return new Topography(g, temp);
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

}
