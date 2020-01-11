package roMiniProject;

import java.util.ArrayList;

public class Noeud{
	
	private int idNoeud;
	private int nbVoisins;
	private ArrayList<Noeud>  successeurs;
	
	public Noeud(int id) {
 
		this.idNoeud = id;
		
	}
	public int getId() {
		
		return this.idNoeud;
	}
	// pour avoir le nombre de voisins
	public int getNbrVoisins() {
   
		return this.nbVoisins;
	}
	// Le nombre de voisins
	public void setNbrVoissins(int nbrV) {
		
		this.nbVoisins = nbrV;
	}
	// pour avoir le nombre de successeurs
	public int getNbrSuccesseurs() {
		
		return successeurs.size();
	}
	
	//Les successeurs
	public void setSuccesseurs(Noeud noeudS) {
		
		this.successeurs.add(noeudS);
	}
}
