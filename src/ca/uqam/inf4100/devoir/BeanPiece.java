/*
 Réalisé par :
 Hichem
 et
 EricTremblay
 */
package ca.uqam.inf4100.devoir;

import java.util.List;

public class BeanPiece {
	
	private int nbPiece;
	private int[] pieces;
	private int nombreDeMontantAcalculer;
	private int[] montantAcalculer;
	private int[][] matriceC;
	private List<Integer> nombreDePieceParType;
	private int[]derniereLigneC;
	
	public int getNbPiece() {
		return nbPiece;
	}
	public int[] getPieces() {
		return pieces;
	}
	public int getNombreDeMontantAcalculer() {
		return nombreDeMontantAcalculer;
	}
	public int[] getMontantAcalculer() {
		return montantAcalculer;
	}
	public void setNbPiece(int nbPiece) {
		this.nbPiece = nbPiece;
	}
	public void setPieces(int[] pieces) {
		this.pieces = pieces;
	}
	public void setNombreDeMontantAcalculer(int nombreDeMontantAcalculer) {
		this.nombreDeMontantAcalculer = nombreDeMontantAcalculer;
	}
	public void setMontantAcalculer(int[] montantAcalculer) {
		this.montantAcalculer = montantAcalculer;
	}
	public int[][] getMatriceC() {
		return matriceC;
	}
	public void setMatriceC(int[][] matriceC) {
		this.matriceC = matriceC;
	}
	public List<Integer> getNombreDePieceParType() {
		return nombreDePieceParType;
	}
	public void setNombreDePieceParType(List<Integer> nombreDePieceParType) {
		this.nombreDePieceParType = nombreDePieceParType;
	}
	public void setDerniereLigneC(final int[]pieces,final int montant){	
		derniereLigneC = lastRow( pieces, montant + 1);
	}
	public int[] getLastRowC(){
		return derniereLigneC;
	}
	// F: Algorithme de calcul de la derniere ligne de C
	private  int[] lastRow(final int[] coins, final int len) {
		int[] result = new int[len];
		// Initialisation de la premiere ligne
		for (int j = 0; j < result.length; j++) {
			if ( j % coins[0] == 0) {
				result[j] = j / coins[0] ;
			} else {
				result[j] = Integer.MAX_VALUE; // On ne peut rendre la monnaie
			}
		}

		int j = 0;
		int temp = 0;

		for (int i = 0; i < coins.length; i++) {
			j = 0;
			while (j < result.length) {
				temp = (j / coins[i]) + result[(j % coins[i])];
				if (temp <= result[j]) {
					result[j] = temp;
				}
				j++;
			}
		}

		return result;
	}
	

}
