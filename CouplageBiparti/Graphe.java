package roMiniProject;

import java.util.ArrayDeque;  
import java.util.ArrayList;  
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Graphe {
	
	//private int nbNoeuds;
	private int nbrSommet;
	protected Noeud[][] noeuds;
	
	public Graphe(Noeud[][] pnoeuds, int pnbrSommet) {
		
		this.noeuds = pnoeuds;
		this.nbrSommet = pnbrSommet;
		//this.nbNoeuds = noeuds.length;
	}
	//On verifie si c'est un neud du graphe 
	//on sait que si c inferieur a zero c'est que le noeud n'a pas d'arc et dans notre cas
	// chaque personne a au moins une tache
	public boolean isNoeud(int i, int j) {
		
		if(noeuds[i][j].getId() >= 0) {
			
			return true;
		}
		return false;
	}
	//verifie si le noeud existe
	public boolean isExist(int line, Noeud noeud) {
		
		for(int i = 0; i < nbrSommet; i++) {
			
			if(noeuds[line][i].getId() == noeud.getId()) {
				
				return true;
			}
		}
		return false;
	}
	
	public List<Noeud> neihghtbours(int pointeur){
		
		List<Noeud> sommet = new ArrayList<Noeud>();
		
		for(int i = 0; i < nbrSommet; i++)
			if(isNoeud(pointeur, i))
				sommet.add(new Noeud(noeuds[pointeur][i].getId()));
		return sommet;
	}
	
	public List<Noeud> pointeur(Noeud noeud){
		
		List<Noeud> sommet = new ArrayList<Noeud>();
		
		for(int i = 0; i < noeuds.length; i++)
			if(isExist(i, noeud))
				sommet.add(new Noeud(i));
		
		return sommet;
	}
	
	
	//Méthode CheminBFS, parcourant le graphe en largeur de la source s au puits t	
	public LinkedList<Noeud> cheminBFS(Noeud s, Noeud t){
		boolean vue[] = new boolean[noeuds.length];
		int[] niveau = new int[noeuds.length];
		//on initialise le tableau vue a false
		for(int i = 0; i < vue.length; i++) {
			vue[i] = false;
		}
		vue[s.getId()] = true;
		niveau[s.getId()] = 0;
		Queue<Noeud> queu = new ArrayDeque<Noeud>();
		LinkedList<Noeud> courtChemin = new LinkedList<Noeud>();
		queu.add(s);
		courtChemin.add(s);
		//On parcourt les autres noeuds non marqués tant que la file n'est pas vide
		while(!queu.isEmpty()) {
			//on reccupere le nouveau noeud et le supprime de la file d'attente 
			s = queu.poll();		
			//On verifie les successeurs du noeud actuel
			for(Noeud pointeur : neihghtbours(s.getId())) {
			//S'il n'est pas encore visité, on le visite 
				if(vue[pointeur.getId()] == false){
				//apres avoir visite le sommet on change son etat en deja visite
					vue[pointeur.getId()] = true;
				//on passe au niveau suivant
					niveau[pointeur.getId()] = niveau[s.getId()] + 1;
				//on ajoute le sommet dans la liste 	
					queu.add(pointeur);
					courtChemin.add(pointeur);		
				}
			}
		}
		//on retourne le chemin
		return courtChemin;	
		}

	
	public void inverseChemin(LinkedList<Noeud> chemin) {
		
		//boolean isExist = false;
		//le dernier noeud du graphe
		Noeud t  = chemin.getLast();
		
		boolean seen[] = new boolean[chemin.size()];
		
		int niveau[] = new int[chemin.size()];
		//on initialise un tableau seen a false
		for(int i = 0; i < seen.length; i++) {
			
			seen[i] = false;
		}
	//le noeud puit du graphe ou notre chemin inverse va commencer
		seen[t.getId()] = true;
	//le puit est de niveau zero	
		niveau[t.getId()] = 0;
		
		Queue<Noeud> queu = new ArrayDeque<Noeud>();
		
		LinkedList<Noeud> cheminRetour = new LinkedList<Noeud>();
		
		queu.add(t);
		//on ajoute le puit en tete dans la liste pour former le chemin retour
		cheminRetour.add(t);
		
		//tant que la file d'attente n'est pas vide on visite le sommet courant
		while(!queu.isEmpty()) {
			
		//on reccupere le sommet puis on le supprime de la file d'attente
			t = queu.poll();		
			
			//On verifie les successeurs du noeud actuel
			for(Noeud i : pointeur(t)) {
						
				//Si il n'est pas encore visite on le visite
				if(seen[i.getId()] == false){
				//apres l'avoir visite on met seen a vrai (deja visite)
					seen[i.getId()] = true;
				//on passe au niveau suivant
					niveau[i.getId()] = niveau[t.getId()] + 1;
				//on ajoute un nouveau sommet dans la file d'attente pour le visiter	
					queu.add(i);
				//on ajoute le sommet qu'on vient de visiter dans la liste
					cheminRetour.add(i);
				}
//				else if(niveau[t.getId()] == niveau[i.getId()]) {
//					
//					return false;
//				}
			}
		}
		for(int i = 0; i < cheminRetour.size(); i++)
//			if(i == cheminRetour.size() - 1)
//				System.out.print(cheminRetour.get(i).getId());
//			else
				System.out.print( "->"+cheminRetour.get(i).getId());
		
		//return isExist;
	}
	
	//Cette methode renvoie le nombre de flot max du puits t à la source s
	public int ff(Noeud s, Noeud t) {
		int flot_max = 0;
		LinkedList<Noeud> cheminBfs = new LinkedList<Noeud>();
		cheminBfs = cheminBFS(s, t);
		//liste des taches 
		List<Noeud> tache = pointeur(cheminBfs.getLast());
		//liste des personnes
		List<Noeud> personne  = neihghtbours(cheminBfs.getFirst().getId());
		
		//le nombre de successeurs du noeud t
		int nbrSuccesseur = tache.size();
		
		
		int [] nbrS = new int[nbrSuccesseur];
		//on initialise le couplage 
		Noeud[] couplage = new Noeud[nbrSuccesseur];
		for(int i = 0; i < couplage.length; i++) {
			
			couplage[i] = new Noeud(-1);
		}
		//affecte le nombre d'arc a chaque tache
		for(int i = 0; i < nbrSuccesseur; i++) {
			
			nbrS[i] = pointeur(tache.get(i)).size();

		}
		for(int i = 0; i < nbrSuccesseur; i++) {
			//indice de la tache min
			int min =  TableTemp(nbrS);
			//la tache mine	
			Noeud noeudM = tache.get(min);
			
			
		//on procede au couplage en commencant par les taches min		
			for(Noeud pred : pointeur(noeudM)) {
			//on verifie que le noeud existe et qu'il est <0 
			//<0 signifie que le couplage est initialise mais pas encores affecte a une tache
				if((!in_array(couplage, pred)) && (couplage[min].getId() < 0)) {
						
					couplage[min] = pred;
					flot_max = flot_max + 1;
				}
			}
			//la tache suivante
			nbrS[min] = nbrSuccesseur;
		}		
		System.out.println("                                                         ");
		System.out.println("Le flot maximal est : " +flot_max);
		System.out.println("                                                         ");
		
		for(int i = 0; i < couplage.length; i++) {
			
			//int num = i + 1;
			//on affiche la tache et la personne avec laquelle elle est affectee
			//System.out.print("Tâche " + num + " --> Personne " + NoeudPos(personne, couplage[i]));
			System.out.print( NoeudPos(personne, couplage[i])+" ");
		}
		System.out.println("                                                         ");


		return nbrSuccesseur;
	}

//on verifie si le noeud existe
	public boolean in_array(Noeud[] tab, Noeud n) {
		boolean exist = false;
		for(int i = 0; i < tab.length; i ++) {
			if(tab[i].getId() ==  n.getId())
				exist = true;
		}
		return exist;
	}

//retourne l'indice de la tache qui a moins de personne pour sa realisation
	public int TableTemp(int[] tab) {
		int pointeur = 0;
		int min  = tab[0];
		for(int i = 0; i < tab.length; i++) {
			if(tab[i] < min) {
				min = tab[i];
				pointeur = i;
			}
		}
		return pointeur;
	}
//retourne la position du noeud
	public int NoeudPos(List<Noeud> list, Noeud noeud) {
		int pos  = -1;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getId() == noeud.getId()) {
				pos = i +1;
			}
		}
		return pos;
	}
	
}
