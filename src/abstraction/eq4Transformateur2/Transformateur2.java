package abstraction.eq4Transformateur2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import abstraction.eq8Romu.Romu;
import abstraction.eq8Romu.bourseCacao.BourseCacao;
import abstraction.eq8Romu.bourseCacao.ExempleAcheteurBourseCacao;
import abstraction.eq8Romu.bourseCacao.ExempleVendeurBourseCacao;
import abstraction.eq8Romu.clients.ClientFinal;
import abstraction.eq8Romu.contratsCadres.SuperviseurVentesContratCadre;
import abstraction.eq8Romu.filiere.Filiere;
import abstraction.eq8Romu.filiere.IMarqueChocolat;
import abstraction.eq8Romu.produits.Chocolat;
import abstraction.eq8Romu.produits.Feve;

//Nawfel




public class Transformateur2 extends Transformateur2ContratCadre implements IMarqueChocolat{

	
	public void next() {
		super.next();
	}
	public void initialiser() {
		super.initialiser();
	}

	public Transformateur2 () {
		super();
		
		
	}
	public List<String> getMarquesChocolat() {
		return null;
	}
//		List<String> res = new LinkedList<String>();
//		//res.add("O'ptella");
//		//res.add("O'ptibon");
//		res.add("O'max");
//		return res;
//	}
}
	

