package abstraction.eq5Transformateur3;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import abstraction.eq8Romu.bourseCacao.BourseCacao;
import abstraction.eq8Romu.contratsCadres.Echeancier;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.contratsCadres.IAcheteurContratCadre;
import abstraction.eq8Romu.contratsCadres.IVendeurContratCadre;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.produits.Feve;

public class AcheteurContrat extends AcheteurBourse  implements IAcheteurContratCadre {

	//private int nb_nego;
	
	//Karla / Julien
	/* Initier un contrat */
	public void lanceruncontratAcheteur(Feve f, Double qtt) {
		SuperviseurVentesContratCadre superviseur = ((SuperviseurVentesContratCadre)(Filiere.LA_FILIERE.getActeur("Sup.CCadre")));
		List<IVendeurContratCadre> L = superviseur.getVendeurs(f); 
		
		Echeancier e = new Echeancier(Filiere.LA_FILIERE.getEtape()+1, 10, qtt); //qtt kg de feves par etape pendant  10 steps
		if (L.size()!=0) {
			if (L.size()== 1) {
				ExemplaireContratCadre contrat = superviseur.demandeAcheteur((IAcheteurContratCadre)Filiere.LA_FILIERE.getActeur("EQ5"), L.get(0), (Object)f,  e, this.cryptogramme, false);
				if (contrat != null) {
					this.achats.ajouter("Nouveau Contrat Cadre avec"+ contrat.getVendeur() +"sur une periode de " + contrat.getEcheancier().getNbEcheances() + " pour "+ contrat.getProduit());
					this.contratsEnCoursAchat.add(contrat);
				}
			}
			else {
				// On choisit aleatoirement
				Random randomizer = new Random();
				IVendeurContratCadre random = L.get(randomizer.nextInt(L.size()));
				ExemplaireContratCadre contrat = superviseur.demandeAcheteur((IAcheteurContratCadre)Filiere.LA_FILIERE.getActeur("EQ5"), random, (Object)f,  e, this.cryptogramme, false);
				if (contrat != null) {
					this.achats.ajouter("Nouveau Contrat Cadre avec"+ contrat.getVendeur() +"sur une periode de " + contrat.getEcheancier().getNbEcheances() + " pour "+ contrat.getProduit());
					this.contratsEnCoursAchat.add(contrat);
				}
			}
		}
		//Julien else on achete des feve par le biais de la bourse si besoin ( bourse.getCours(f).getValeur() )
	}
	


	// Julien & Karla
	public boolean achete(Object produit) {
		if  (!( produit instanceof Feve) ) {
			return false;
		}
		if (this.stockFeves.getProduitsEnStock().contains((Feve) produit)) {
			return true;
		}
		return false;
	}

	// Julien & Karla
	// On accepte tout le temps l'echeancier
	public Echeancier contrePropositionDeLAcheteur(ExemplaireContratCadre contrat) {
		List<Echeancier> listeEcheanciers=contrat.getEcheanciers();
		int l = listeEcheanciers.size();
		this.achats.ajouter("echeancier ok pour tout");
		return listeEcheanciers.get(l-1); // le dernier proposé est celui des vendeurs
	}

	// Julien & Karla
	/* On s'indexe sur la bourse : les contrats sont censés être avantageux 
	 * donc on accepte si ça l'est et sinon on propose 95% du prix de la bourse
	 */
	public double contrePropositionPrixAcheteur(ExemplaireContratCadre contrat) {
		double prixT = contrat.getPrix();
		BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
		Double seuilMax = bourse.getCours((Feve)contrat.getProduit()).getMin();
		// double seuilMax=2.0;
		if (prixT < seuilMax) { 
			this.achats.ajouter("prix acceptable");
			return prixT;
		}
		else {
			double proportion = 0.70 ;/*+0.05*this.nb_nego;*/
			double nouveauprix = proportion*prixT;
			if (nouveauprix < seuilMax) { 
				this.achats.ajouter(" essaie avec nouveau prix");
				return nouveauprix;
			}
		}
		return 0.0;
	}

	// Julien & Karla
	/* On met à jour le journal
	 * et on pourra par la suite mettre fin aux autres négociations pour un même produit : on achete tout pour l'instant
	 */
	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		this.achats.ajouter("Nouveau Contrat Cadre avec"+ contrat.getVendeur() +"sur une periode de " + contrat.getEcheancier().getNbEcheances() + " pour "+ contrat.getProduit());
		this.contratsEnCoursAchat.add(contrat);

	}

	// Julien & Karla
	// si la quantité reçue est inférieure à celle prévue : en acheter à la bourse ?
	public void receptionner(Object produit, double quantite, ExemplaireContratCadre contrat) {
		Feve f= ((Feve) produit);
		this.stockFeves.ajouter(f, quantite);		
		this.achats.ajouter("On receptionne " + quantite + "de" + produit.toString() + " achete par CC");
	}

	//Karla
	public LinkedList <ExemplaireContratCadre> majCC(LinkedList <ExemplaireContratCadre> L) {
		LinkedList <ExemplaireContratCadre> Lcopy = new LinkedList <ExemplaireContratCadre> (L); 
		for (ExemplaireContratCadre contrat : Lcopy) {
			if (contrat.getEcheancier().getStepFin() < Filiere.LA_FILIERE.getEtape()) {
				L.remove(contrat);
			}
		}
		return L ;
	}
	
	//Karla
	/* on regarde l etat de nos stocks et on lance la procédure demande 
	acheteur + get vendeur de la classe superviseur vente cadre */
	public void next() {
		super.next();
		
		/* Mise à jour de la liste des CC en cours */
		this.contratsEnCoursAchat = majCC(this.contratsEnCoursAchat);
		
		for (Feve f : this.stockFeves.getProduitsEnStock()) {
			
			/* Selon la place libre dans nos entrepots et selon l'etat de nos stocks pour cette feve,
			 * on essaie d'initier des contrats 
			 */
			Double stocktotal = this.stockFeves.getstocktotal()+this.stockChocolat.getstocktotal();
			
			if (stocktotal < this.capaciteStockageEQ5) {
				if (this.stockFeves.getstock(f) < this.SeuilMinFeves) {
					Double placeLibre = this.stockChocolat.getstocktotal() - this.stockFeves.getstocktotal();
					if (placeLibre > 0) {
					/* On essaie d'initier un contrat pour une qtt de placeLibre/nombre de types de fèves */
						Double qtt = placeLibre/4;
						lanceruncontratAcheteur(f, qtt);
					}
				}
			}
		}
	}
}
	
