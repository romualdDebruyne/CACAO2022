package abstraction.eq4Transformateur2;

import java.awt.Color;
import java.util.HashMap;

import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.general.Journal;
import abstraction.eq8Romu.general.Variable;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import abstraction.eq8Romu.produits.Feve;

//auteur Marie

public abstract class Transformateur2Stock extends Transformateur2ContratCadreVendeur {
	
	private Stock<Feve> stockfeve;
	private Stock<Chocolat>  stockchocolat;
	private Stock<ChocolatDeMarque> stockchocolatdemarque;
	private Journal journalStock;
	
	private double prixstockage;
	protected double notreCapaciteStockage; //elle évolue lorsqu'on achète des new capacités de stockage
	
	public Transformateur2Stock() {
		this.journalStock=new Journal("O'ptiStock",this);
		
		//LES STOCKS INITIAUX----VALEURS A CHOISIR
		this.stockfeve=new Stock(); //50MILLIONS de stock à répartir : 40% de moyenne, 40% de basse, 10%de haute, 5% de chaque BIO
		this.stockfeve.ajouter(Feve.FEVE_BASSE, 20000000);
		this.stockfeve.ajouter(Feve.FEVE_MOYENNE, 20000000);
		this.stockfeve.ajouter(Feve.FEVE_MOYENNE_BIO_EQUITABLE, 2500000);
		this.stockfeve.ajouter(Feve.FEVE_HAUTE, 5000000);
		this.stockfeve.ajouter(Feve.FEVE_HAUTE_BIO_EQUITABLE, 2500000);
		
		//On se fixe une marque pour un type de chocolat
		ChocolatDeMarque c1=new ChocolatDeMarque(Chocolat.MQ,this.getMarquesChocolat().get(1));
		ChocolatDeMarque c0=new ChocolatDeMarque(Chocolat.BQ,this.getMarquesChocolat().get(0));
		ChocolatDeMarque c2=new ChocolatDeMarque(Chocolat.MQ_BE,this.getMarquesChocolat().get(2));
		ChocolatDeMarque c3=new ChocolatDeMarque(Chocolat.HQ,this.getMarquesChocolat().get(3));
		ChocolatDeMarque c4=new ChocolatDeMarque(Chocolat.HQ_BE,this.getMarquesChocolat().get(4));
		
		this.stockchocolatdemarque=new Stock();
		this.stockchocolatdemarque.ajouter(c1, 20000000);
		this.stockchocolatdemarque.ajouter(c0, 20000000);
		this.stockchocolatdemarque.ajouter(c2, 2500000);
		this.stockchocolatdemarque.ajouter(c3, 5000000);
		this.stockchocolatdemarque.ajouter(c4, 5000000);
		
		this.stockchocolat=new Stock();
		this.stockchocolat.ajouter(Chocolat.MQ,30000);
		this.stockchocolat.ajouter(Chocolat.BQ, 20000);
	}
	
	public void next() {
		super.next();
		//ON implemente le journal avec des infos sur nos stocks à chaque tour
				if (this.stockfeve.getStock().keySet().size()>0) {
					for (Feve f : this.stockfeve.getStock().keySet()) {
						this.journalStock.ajouter("stock de feve "+f+" : "+this.stockfeve.getStock().get(f));
					}
				}
				if (this.stockchocolatdemarque.getStock().keySet().size()>0) {
					for (ChocolatDeMarque c : this.stockchocolatdemarque.getStock().keySet()) {
						this.journalStock.ajouter("stock de chocolat de marque "+c+" : "+this.stockchocolatdemarque.getStock().get(c));
					}
				}
				
				
				
		//On paye le cout de stockage
				Filiere.LA_FILIERE.getBanque().virer(this, super.cryptogramme, Filiere.LA_FILIERE.getBanque(), this.coutStockage());
				journalStock.ajouter(Color.red,Color.white,"Le stock nous coûte "+this.coutStockage());
				journalStock.ajouter(Color.white,Color.red,"----------------------------------------------------------------------------------");
	}
	
	public void initialiser() {
		//double prixstockage=Filiere.LA_FILIERE.getIndicateur("prixstockage").getValeur();
		super.initialiser();	
		this.prixstockage=Filiere.LA_FILIERE.getParametre("Prix Stockage").getValeur();
		this.notreCapaciteStockage=Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur();

	}

	
	
public Stock<Feve> getStockfeve(){
	return this.stockfeve;
}
/*public Stock<Chocolat> getStockchocolat(){
	return this.stockchocolat;
  }*/

public Stock<ChocolatDeMarque> getStockchocolatdemarque(){
	return this.stockchocolatdemarque;
}

// Gabriel 
//public void setStock(double newst) {
//	this.stocktotalchoco = this.stocktotalchoco - newst;
//}

//Marie
public double coutStockage() {
	return 4*Filiere.LA_FILIERE.getParametre("Prix Stockage").getValeur()*(this.getStockchocolatdemarque().getStocktotal()+this.getStockfeve().getStocktotal());
}


// Marie 
//public double quantiteStockTotaleFeve(Feve produit) {
//	for(Feve f : stockfeve.getStock().keySet()) {
//		this.stocktotalfeve=this.stocktotalfeve+stockfeve.quantite_stock.get(f);
//	}
//	return this.stocktotalfeve;	
//}
//// Marie
//
//public double quantiteStockTotaleChocolat(Chocolat produit) {
//	for(Chocolat c: stockchocolat.quantite_stock.keySet()) {
//		this.stocktotalchoco=this.stocktotalchoco+stockchocolat.quantite_stock.get(c);
//	}
//	return this.stocktotalchoco;
	
//}	

//Marie

public boolean capaciteMaxAtteinte(Stock<Feve> stockfeve,Stock<Chocolat>  stockchocolat ) {
	if (((Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur())-(this.stockfeve.getStocktotal()+this.stockchocolat.getStocktotal()))>500 ) { 
		return false;	
	}else {
		return true;
	}
}

// Marie 

public double augmenterCapacite(Stock<Feve> stockfeve,Stock<Chocolat>  stockchocolat,double notreCapaciteStockage ) {
	//condition pour augmenter la capacité (par ex. dès qu'on souhaite acheter et que l'on n'a pas de capacité de stockage suffisante, on augmente notre capacité)
	this.setNotreCapaciteStockage(Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur());
	if (capaciteMaxAtteinte(stockfeve, stockchocolat)) {
		Filiere.LA_FILIERE.getParametre("limiteStockage").setValeur(this,Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur() +(1.10*(Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur()-(this.stockfeve.getStocktotal()+this.stockchocolat.getStocktotal()+500))));
		double c =this.setNotreCapaciteStockage(this.getNotreCapaciteStockage() + 1.10*(Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur()-(this.stockfeve.getStocktotal()+this.stockchocolat.getStocktotal()+500)));
		Filiere.LA_FILIERE.getBanque().virer(this, this.cryptogramme, Filiere.LA_FILIERE.getBanque(), 1.10*((Filiere.LA_FILIERE.getParametre("limiteStockage").getValeur())-(this.stockfeve.getStocktotal()+this.stockchocolat.getStocktotal())+500)*Filiere.LA_FILIERE.getIndicateur("prixEntrepot").getValeur());
	return c;
	}else {
		return this.getNotreCapaciteStockage();
	}
	
	//si validé alors on augmente le stock d'une quantité définie 
	// sinon on garde le même stock
}

public Journal getJournalStock() {
	return journalStock;
}

public double getNotreCapaciteStockage() {
	return notreCapaciteStockage;
}

public double setNotreCapaciteStockage(double notreCapaciteStockage) {
	this.notreCapaciteStockage = notreCapaciteStockage;
	return notreCapaciteStockage;
}




}

