package abstraction.eq6Distributeur1;
import java.util.ArrayList;
import java.util.List; 
import java.util.Map;
import abstraction.eq8Romu.produits.ChocolatDeMarque;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

public class Stock extends Distributeur1{ //Emma Humeau
	

	private double CoûtStockageTotale;
	
	private double qteStockageTotale; 

	private Map<ChocolatDeMarque,Double> StockageQte = new HashMap<ChocolatDeMarque, Double>();
	
	
	/**
	 * @return the coûtStockageTotale
	 */
	public double getCoûtStockageTotale() {
		return CoûtStockageTotale;
	}

	/**
	 * @param coûtStockageTotale the coûtStockageTotale to set
	 */
	public void setCoûtStockageTotale(double coûtStockageTotale) {
		CoûtStockageTotale = coûtStockageTotale;
	}

	/**
	 * @return the qteStockageTotale
	 */
	public double getqteStockageTotale() {
		double sumqte=0;
		
		//calcule la qte du stockage total
		for (Map.Entry<ChocolatDeMarque, Double > entry : StockageQte.entrySet()) {
           ChocolatDeMarque KeyQte = entry.getKey();
           Double ValueQte = entry.getValue();
           sumqte += ValueQte;
		}
		
		this.qteStockageTotale = sumqte;
		
		return qteStockageTotale;
	}
	

	

	


	/**
	 * @return the stockagePrix
	 */
	public Map<ChocolatDeMarque, Double> getStockagePrix() {
		return StockagePrix;
	}



	/**
	 * @param stockagePrix the stockagePrix to set
	 */
	public void setStockagePrix(ChocolatDeMarque choco, double prix) {
		StockagePrix.put(choco,prix);
	}



	/**
	 * @return the stockageQte
	 */
	public Map<ChocolatDeMarque, Double> getStockageQte() {
		return StockageQte;
	}

	public Double getStockage(ChocolatDeMarque chocolat) {//leorouppert
    return this.StockageQte.get(chocolat);//Retourne le stock du chocolat demandé
	}
	

	/**
	 * @param stockageQte the stockageQte to set
	 */
	public void addStockageQte(ChocolatDeMarque choco, double qte) {
		StockageQte.put(choco, StockageQte.get(choco)+qte);
	}
	
	public void removeStockageQte(ChocolatDeMarque choco, double qte) {
		StockageQte.replace(choco,StockageQte.get(choco),StockageQte.get(choco)-qte);
	}



//Emma Humeau tout ce qui est au dessus


	public Map<ChocolatDeMarque, Double> getMapStock() {//leorouppert
        return this.Stockage;//Retourne toute la hashmap de notre stock
    }
    
    public Double getStockage(ChocolatDeMarque chocolat) {//leorouppert
        return this.Stockage.get(chocolat);//Retourne le stock du chocolat demandé
    }
    
    public void setStockage(ChocolatDeMarque chocolat, Double quantite, Double prix) {//leorouppert
        this.Stockage.put(chocolat, ArrayList<Double>[prix][quantite]);//Modifie la valeur du stock du chocolat associé
    }
		



	public Map<ChocolatDeMarque, Double> getMapStock() {//leorouppert
        return this.StockageQte;//Retourne toute la hashmap de notre stock
    }
    
    public Double getStockageQte(ChocolatDeMarque chocolat) {//leorouppert
        return this.StockageQte.get(chocolat);//Retourne le stock du chocolat demandé
    }
    
    
}	
	

	
	



