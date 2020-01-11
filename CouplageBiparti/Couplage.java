package roMiniProject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Couplage {
	

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Donner le chemin du fichier");
		String chemin = sc.nextLine();
		
		Graphe graphe = creeReseau(chemin);
		
		
		
		//Affichage du chemin BFS
		System.out.println("Notre Chemin BFS :");
		
		
		LinkedList<Noeud> cheminBfs = new LinkedList<Noeud>();
		int n=0;
		int t=0;
		try {
			BufferedReader reader= new BufferedReader(new FileReader(chemin));
			String currentLine = reader.readLine();
			//System.out.println( currentLine);
			n=Integer.parseInt(currentLine);
			t=(n*2)+1;
			cheminBfs = graphe.cheminBFS(new Noeud(0), new Noeud(t));
			
			for(int i = 0; i < cheminBfs.size(); i++)
				if(i == cheminBfs.size() -1)
					System.out.print(cheminBfs.get(i).getId());
				else
					System.out.print(cheminBfs.get(i).getId() + "->");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		//Affiche chemin inverse
		System.out.println("\n\nChemin BFS inverse :");
		
		 //inverseChemin(cheminBfs);
		graphe.inverseChemin(cheminBfs);
		
		//flot max
		
		System.out.println("\n\nFord Fulkerson :");
		
		int flot_max = graphe.ff(new Noeud(0), new Noeud(t));
		
		
	}
// fonction CreeReseau-------------------------------------------------------------	
	public static Graphe creeReseau(String fichier) {
			
		ArrayList<String> arrayList = new ArrayList<String>();
		Noeud[][] noeuds = null;
		BufferedReader reader;
		int tailleTab = 0;
			
		try {
			reader = new BufferedReader(new FileReader(fichier));
				
			String currentLine;
				
			while((currentLine = reader.readLine()) != null) {
			//on ajoute les taches du fichier dans un arrayList
				arrayList.add(currentLine);
			}
			//on reccupere le nombre de personne et de tache de la premiere ligne
			tailleTab = Integer.parseInt(arrayList.get(0));
//			String tache = arrayList.get(2);
//			System.out.println(tache);
//			
			
			String personne = "";
			//on ajoute les personnes dans la variable personne
			for(int i = tailleTab + 1; i <= tailleTab * 2; i++) {
				
				if(i < tailleTab * 2) {
					personne  += i + " ";
				
				
				}
				else
					personne  += i;
				
			}
			//on enleve l'indice zero de l'arrayList qui represente le nombre de personne
			arrayList.remove(0);
			//on ajoute les sommets n+1 qui representent les personnes dans un arrayList 
			arrayList.add(0, personne);
			
			//nombre de tache=nombre de personne
			int width  = tailleTab;
			//nombre de tache + nombre de personne + source et puit
			int lenght = (tailleTab * 2) + 2;
			
			noeuds = new Noeud[lenght][width];
				
			for(int i = 0; i < lenght; i++){
				
				for(int j = 0; j < width; j++) {
						
					noeuds[i][j] = new Noeud(-1);
					
					
				}
			}	
			//les personnes de n+1--6 a n+n--10
			String[] s = arrayList.get(0).split(" ");
			
			//i=0 et i<n*2+2
			for(int i = 0; i < s.length; i++) {
	// On cree les noeuds personnes
	//les personnes se trouvent a l'indice 0 de l'arrayList			
				noeuds[0][i] = new Noeud(Integer.parseInt(s[i]));
				
			}
		//pour chaque personne on va assigne les taches comme indique le fichier txt
		//pour chaque personne on a au max n taches	
			for(int i = 1; i <= tailleTab; i++) {
					
				String[] taches = arrayList.get(i).split(" ");
				
				int k = tailleTab + i;
				
				for(int j = 0; j < taches.length; j++) {
						
					noeuds[k][j] = new Noeud(Integer.parseInt(taches[j]));
					
				}
			}
			//test----------affichage tache pour chaque personne
			for(int k=tailleTab+1; k<=tailleTab*2; k++) {
				for(int j=0; j<5; j++) {
					
					System.out.println(noeuds[k][j].getId());
					
					
				}
			}
			//-------------
			
			//-------on construit les autres sommets du graphe
			for(int i = 1; i < tailleTab + 1; i++) {
				
				noeuds[i][0] = new Noeud(noeuds.length - 1);
				//System.out.println(noeuds.length);
				
			}
			
		}catch(FileNotFoundException e) {
				
			System.out.println("Source Date Not Found :" + e);
				
		}catch(IOException e) {
				
			System.out.println("Error " + e);
		}
		return new Graphe(noeuds, tailleTab);
	}
	//--------------------------------------------------------------------
}