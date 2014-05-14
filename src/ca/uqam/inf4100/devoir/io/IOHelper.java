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
package ca.uqam.inf4100.devoir.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;

import ca.uqam.inf4100.devoir.BeanPiece;
import ca.uqam.inf4100.devoir.Tp4;

public class IOHelper {
	
	public static String osLineSeparator = System.getProperty("line.separator");
	public static String separator = " ";
	
	public BeanPiece readFile(final String path) throws Exception {
		
		File aFile = new File(path);
		if (!aFile.exists()) {
			throw new IllegalArgumentException("Le fichier " + path + " n'existe pas");
		}
		BeanPiece bean = new BeanPiece();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(aFile));
			String nbPieces = in.readLine();
			
			// premiere ligne nombre de piece
			int nbPiece = Integer.parseInt(nbPieces);
			bean.setNbPiece(nbPiece);
			
			int[] p = new int[bean.getNbPiece()];
			StringTokenizer st = new StringTokenizer(in.readLine(), separator);
			for (int i = 0; i < p.length; i++) {
				if (st.hasMoreTokens()) {
					p[i] = Integer.parseInt(st.nextToken());
				}
			}
			bean.setPieces(p);
			
			int nombreEntreesAcalculer = Integer.parseInt(in.readLine());
			bean.setNombreDeMontantAcalculer(nombreEntreesAcalculer);
			int[] entreeMontants = new int[nombreEntreesAcalculer];
			
			int temp = 0;
			StringTokenizer entreeSt = new StringTokenizer(in.readLine(), separator);
			for (int i = 0; i < entreeMontants.length; i++) {
				if (entreeSt.hasMoreTokens()) {
					temp =  Integer.parseInt(entreeSt.nextToken());
				}
				
				if (temp < 0) {
					throw new RuntimeException("montant negatifs!");
				}
				entreeMontants[i] = temp;
			}
			
			bean.setMontantAcalculer(entreeMontants);
			
			
			
		} catch (IOException exc) {
			System.out.println(exc.getMessage());
		} finally {
			if (in != null) {
				try { in.close(); } catch (IOException exc) {}
			}
		}
		return bean;
	}
	
	public void writeFile(final BeanPiece bean, final String outputFileName, final Tp4 handle) {
		//Tp4.prt(bean.getPieces());
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new PrintWriter(new File(outputFileName)));
			for (int i = 0; i < bean.getNbPiece(); i++) {
				out.write(bean.getPieces() [i] + " ");
			}
			out.write(osLineSeparator);
			// La matrice C pour L = 20.
			for (int i = 0; i < bean.getMatriceC().length; i++) {
				for (int j = 0; j < 21; j++) {
					out.write(bean.getMatriceC()[i][j] + " ");
				}
				out.write(osLineSeparator);
			}
			
			out.write("Nombre de pieces en ordre decroissant de valeur");
			out.write(osLineSeparator);
			for (int j = 0; j < bean.getNombreDeMontantAcalculer(); j++) {
				out.write(bean.getMontantAcalculer()[j] + " : ");
				int[] nombreDePieces= new int[bean.getNbPiece()];
					bean.setMatriceC(handle.calculMatriceC(bean.getPieces(), bean.getMontantAcalculer()[j], nombreDePieces));
					
					bean.setDerniereLigneC(bean.getPieces(), bean.getMontantAcalculer()[j]);
					//int[]monnaie = Tp4.glouton(bean.getMontantAcalculer()[j], bean.getPieces(), bean.getMatriceC()[bean.getNbPiece()-1]) ;
					//affiche la derniere colone de c necessaire pour le calcul du montant
					
				    //les pieces utilisees pour la monnaie
					/*
				    for(int i = 0; i < bean.getPieces().length; ++i){
				       out.write(bean.getPieces()[i]);
				    }
				    //la derniere ligne de c
					for(int i = 0; i < bean.getLastRowC().length; ++i )
					{
						//System.out.print(bean.getLastRowC()[i]+" ");
						out.write(bean.getLastRowC()[i]+" ");
					}
					*/

					int[]monnaie = Tp4.glouton(bean.getMontantAcalculer()[j], bean.getPieces(), bean.getLastRowC() ) ;

					for(int i = monnaie.length-1; i >= 0;--i)
					{
						out.write(monnaie[i]+" ");
					}
				out.write(osLineSeparator);
			}
		} catch (IOException exc) {
			
		} finally {
			if (out != null) {
				try { out.close(); } catch (IOException exc) {}
			}
		}
		
	}


}
