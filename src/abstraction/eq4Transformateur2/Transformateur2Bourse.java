package abstraction.eq4Transformateur2;

import java.util.List;

import abstraction.eq8Romu.bourseCacao.BourseCacao;
import abstraction.eq8Romu.bourseCacao.ExempleAbsAcheteurBourseCacao;
import abstraction.eq8Romu.bourseCacao.IAcheteurBourse;
import abstraction.eq8Romu.contratsCadres.ExemplaireContratCadre;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.filiere.IFabricantChocolatDeMarque;
import abstraction.eq8Romu.general.Journal;
import abstraction.eq8Romu.general.Variable;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;
import abstraction.eq8Romu.produits.Gamme;

// Marie et JAd

public abstract class Transformateur2Bourse extends Transformateur2Transfo implements IAcheteurBourse{
	
	private Journal journalAchat;
	private double quantiteEnKg;
	private double prixseuilBG;
	private double prixseuilMG;
	private double minBG;
	private double minMG;
	

	public Transformateur2Bourse() {
		super();
		this.journalAchat=new Journal("O'ptits Achats",this);
		this.quantiteEnKg=quantiteEnKg;
		this.prixseuilBG=prixseuilBG;
		this.prixseuilMG=prixseuilMG;
		this.minBG=minBG;
		this.minMG=minMG;
	}

	public void next() {
		super.next();
		if(Filiere.LA_FILIERE.getEtape()>2) {
			BourseCacao bourse = (BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
			double somme1 = 0;
			for (int i=0; i<3;i++ ) {
				somme1 = somme1 +  bourse.getCours(Feve.FEVE_BASSE).getValeur(Filiere.LA_FILIERE.getEtape()-3+i);
			}
			this.prixMinB.setValeur(superviseur, somme1/3); 
			double somme2 = 0;
			for (int i=0; i<3;i++ ) {
				somme2 = somme2 +  bourse.getCours(Feve.FEVE_MOYENNE).getValeur(Filiere.LA_FILIERE.getEtape()-3+i);
			}
			this.prixMinM.setValeur(superviseur, somme2/3);
			double somme3 = 0;
			for (int i=0; i<3;i++ ) {
				somme3 = somme3 +  bourse.getCours(Feve.FEVE_MOYENNE_BIO_EQUITABLE).getValeur(Filiere.LA_FILIERE.getEtape()-3+i);
			}
			this.prixMinMb.setValeur(superviseur, somme3/3);
			double somme4 = 0;
			for (int i=0; i<3;i++ ) {
				somme4 = somme4 +  bourse.getCours(Feve.FEVE_HAUTE).getValeur(Filiere.LA_FILIERE.getEtape()-3+i);
			}
			this.prixMinH.setValeur(superviseur, somme4/3);
			double somme5 = 0;
			for (int i=0; i<3;i++ ) {
				somme5 = somme5 +  bourse.getCours(Feve.FEVE_HAUTE_BIO_EQUITABLE).getValeur(Filiere.LA_FILIERE.getEtape()-3+i);
			}
			this.prixMinHb.setValeur(superviseur, somme5/3);
		}
		this.getJournalcours().ajouter("La moyenne des cours pour la fève basse sur les trois derniers mois est"+ this.prixMinB.getValeur() + "");
		this.getJournalcours().ajouter("La moyenne des cours pour la fève moyenne sur les trois derniers mois est"+ this.prixMinM.getValeur()+ "");
		this.getJournalcours().ajouter("La moyenne des cours pour la fève moyenne bioéquitable sur les trois derniers mois est"+ this.prixMinMb.getValeur()+ "");
		this.getJournalcours().ajouter("La moyenne des cours pour la fève haute sur les trois derniers mois est"+ this.prixMinH.getValeur()+ "");
		this.getJournalcours().ajouter("La moyenne des cours pour la fève haute bioéquitable sur les trois derniers mois est"+ this.prixMinHb.getValeur()+ "");
		this.journalAchat.ajouter("---------------------------------------------------------------------------------------------------------------");
		}
	
	public void initialiser() {
		super.initialiser();
	}
	
	
// Marie 
	
/*public double prixSeuilBG(double prixseuilBG, double minBG) {
	BourseCacao b	=(BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
	if(Filiere.LA_FILIERE.getEtape()<27) {
		for(int i=0;i<5;i++) {
			if(b.getCours(Feve.FEVE_BASSE).getMin()<minBG) {
				this.minBG=b.getCours(Feve.FEVE_BASSE).getMin();
			}
			
		}
		}else {
			//prendre le min de la même période de l'année dernière
		
	}
}

public double prixSeuilMG() {
	BourseCacao b	=(BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
	if(Filiere.LA_FILIERE.getEtape()<27) {
		for(int i=0;i<5;i++) {
			if(b.getCours(Feve.FEVE_BASSE).getMin()<minBG) {
				this.minBG=b.getCours(Feve.FEVE_BASSE).getMin();
			}
			
		}
		}else {
			//prendre le min de la même période de l'année dernière
		
	}	
}


public double demande(Feve f, double cours) {
	if ((f.getGamme().equals(Gamme.MOYENNE) && !f.isBioEquitable()) && (cours < prixSeuilMG())) {
			return this.getCapaciteStockageFixe().getValeur()*0.4-this.getStockfeve().getQuantite(f);
			
	  }	else if ((f.getGamme().equals(Gamme.BASSE) && !f.isBioEquitable())&& (cours< prixSeuilBG())) {
			return this.getCapaciteStockageFixe().getValeur()*0.6-this.getStockfeve().getQuantite(f);	
		}
			
	 else {
		return 0.0;
	}
	
}


//Marie et Jad

/*	public double demande(Feve f, double cours) {
		BourseCacao b	=(BourseCacao)(Filiere.LA_FILIERE.getActeur("BourseCacao"));
		b.getCours(Feve.FEVE_BASSE);
		if (cours < this.getPrixSeuil().getValeur()) {
			if(f.getGamme().equals(Gamme.MOYENNE) && !f.isBioEquitable()) {
				
				//On verifie qu'on a la capacite de paiemeent nécessaire, puis on demande
				if (Filiere.LA_FILIERE.getBanque().verifierCapacitePaiement(this, this.cryptogramme, cours*Math.max(0,this.getCapaciteStockageFixe().getValeur()-this.getStockfeve().getQuantite(f)))) {
				return Math.max(0,this.getCapaciteStockageFixe().getValeur()-this.getStockfeve().getQuantite(f));

//	public double demande(Feve f, double cours) {
//		if (cours < this.getPrixSeuil().getValeur()) {
//			if(!f.isBioEquitable()) {
//				
//				//On verifie qu'on a la capacite de paiemeent nécessaire, puis on demande
//				if (Filiere.LA_FILIERE.getBanque().verifierCapacitePaiement(this, this.cryptogramme, cours*Math.max(0.001,this.getStockReferenceFeve().getQuantite(f)-this.getStockfeve().getQuantite(f)))) {
//				return Math.max(0,this.getStockReferenceFeve().getQuantite(f)-this.getStockfeve().getQuantite(f));
//			}
//				else { 
//					return 0.0;}}
//			
//		} 
//		else {
//			return 0.0;
//		}
//		return 0.0;
//		}
	
//Jad
	//déroulement :

	//1)comparaison cours et prix seuil pour la feve
	//2)vérifiaction des besoins
	//3)vérification des capacités de paiement
	//4)achat si totu est validé
	 
	 */
	public double demande(Feve f, double cours) {

			if(cours<this.getPrixSeuil(f).getValeur()) {

				double besoin=Math.max(0.001,this.getStockReferenceFeve().getQuantite(f)-this.getStockfeve().getQuantite(f));

				if(Filiere.LA_FILIERE.getBanque().verifierCapacitePaiement(this, this.cryptogramme, cours*besoin)){
					return besoin;
				}

			}
		
		

		return 0.0;

		}





//Marie et Jad
	public void notificationAchat(Feve f, double quantiteEnKg, double coursEnEuroParKg) {
		this.getStockfeve().ajouter(f,quantiteEnKg);
		Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), coursEnEuroParKg*quantiteEnKg);
		this.getJournalAchat().ajouter("Achat de "+quantiteEnKg+" kg de fèves "+f+" pour "+coursEnEuroParKg +" €/kg");
	}

//Marie et Jad
	public void notificationBlackList(int dureeEnStep) {
		//this.journal.ajouter("Aie... je suis blackliste... Nous sommes pauvres... :(");
		
	}


	@Override
	//public double demande(Feve f, double cours) {
		// TODO Auto-generated method stub
	///	return 0;
	//}

	public Journal getJournalAchat() {
		return journalAchat;
	}



	
}
