/*
 * Copyright 2011 Hichem et Eric Tremblay.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.uqam.inf4100.devoir;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ca.uqam.inf4100.devoir.io.IOHelper;

public class Tp4 {
	
	private static final String inputFileName = "monnaie.txt";
	private static final String outputFileName = "resultat.txt";
	
	
	//calcul de la matrice L
	public int[][] calculMatriceC(int[] coins, int l, int[] nombreDePiece) {
		int[][] result = new int[coins.length][l+1];
		// Initialisation de la premiere ligne
		
		
		for (int j = 0; j < result[0].length; j++) {
			if ( j % coins[0] == 0) {
				result[0][j] = j / coins[0] ;
			} else {
				result[0][j] = Integer.MAX_VALUE; // On ne peut rendre la monnaie
			}
		}

		int j = 0;
		int temp = 0;
		
		for (int i = 1; i < coins.length; i++) {
			j = 0;
			for (int x = 0; x <nombreDePiece.length;x++) {
				nombreDePiece[x]=0;
			}
			while (j < result[0].length) {
				
				temp = (j / coins[i]) + result[i-1][(j % coins[i])];
				if (temp <= result[i-1][j]) {
					result[i][j] = temp;
					//pieceNombreDePiece.put(j, value)
				}
				nombreDePiece[i] = j / coins[i];
				int reste = j % coins[i];
				int k = i-1;
				while (reste != 0) {
					nombreDePiece[k] = reste / coins[k];
					reste = reste % coins[k];
					k--;
				}
				
				j++;
			}
		}
		
		
		return result;

	}
	// H : algorithme glouton optimal
	public static int[] glouton( int m, int[] p, int[] c)
    {

        int i = p.length-1;
        int[] retour = new int[p.length];
        int candidat = p[i];
        int compteur = c[m];
        int temp;

        while(compteur > 0)
        {

            while( m < candidat)
            {
                --i;
                candidat = p[i];
            }
            temp = compteur * candidat;
            while( temp > m )
            {
                --compteur;
                temp = compteur * candidat;
            }
            retour[i] = compteur;
            m = m - compteur * candidat;
            compteur = c[m];

        }
        
        return retour;
    }
	
	public static void main(String [] args) {
		
		Tp4 handle  = new Tp4();        
        IOHelper io = new IOHelper();
        try {
        	
        	BeanPiece bean = io.readFile(inputFileName);
        	int[] nombreDePieces = new int[bean.getNbPiece()];
        	bean.setMatriceC(handle.calculMatriceC(bean.getPieces(), 21, nombreDePieces));
        	io.writeFile(bean, outputFileName, handle);
    		
        	
        } catch (Exception exc) {
        	System.out.println(exc.getMessage());
        }
        	
	}
	
	public static void prt(int[] anArray) {
		if (anArray == null) {
			throw new IllegalArgumentException ("null reference");
		}
		for (int i = 0; i < anArray.length;i++) {
			System.out.printf("c[%d]=%d  ", i,  anArray[i]);
		}
		System.out.println();
	}
	
}
