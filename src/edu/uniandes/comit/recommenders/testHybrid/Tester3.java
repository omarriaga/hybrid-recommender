package edu.uniandes.comit.recommenders.testHybrid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.recommender.AbstractRecommender;
import org.recommender101.recommender.baseline.NearestNeighbors;
import org.recommender101.recommender.extensions.contentbased.ContentBasedRecommender;

public class Tester3 extends AbstractRecommender {

	
	private DataModel model;
	private ContentBasedRecommender contentBasedRecommender;
	private NearestNeighbors itemBasedRecommender;

	@Override
	public float predictRating(int user, int item) {
		return 0;
	}

	@Override
	public List<Integer> recommendItems(int user) {
		return new LinkedList<>();
	}
	
	public static void main(String[] args) throws Exception{
		Tester3 t = new Tester3();
		t.init();
	}

	@Override
	public void init() throws Exception {
		
		this.model = new DataModel();
		LastFMDataLoader loader= new LastFMDataLoader();
		loader.setFilename("data/user_artistsload.dat");
		loader.loadData(model);
		
		//Ya fue pre-procesado
		//ContentBasedUtilities.createFeatureWeightFile("data/user_taggedartists.dat", "data/tag_weight.txt");
		this.contentBasedRecommender= new ContentBasedRecommender();
		ContentBasedRecommender.dataDirectory="data";
		contentBasedRecommender.setDataModel(model);
		contentBasedRecommender.setWordListFile("artists.dat");
		//La implementaci�n crea unos vectores de similitud, que guarda en el archivo cos-sim-vectors.txt, ya fueron calculados
		contentBasedRecommender.setFeatureWeightFile("tag_weight.txt");
		contentBasedRecommender.init();
	
		
		this.itemBasedRecommender= new NearestNeighbors();
		itemBasedRecommender.setItemBased("true");
		itemBasedRecommender.setDataModel(model);
		itemBasedRecommender.init();
	

		List<Integer> lista = contentBasedRecommender.recommendItems(2).subList(0, 500);
		List<Integer> lista2 = itemBasedRecommender.recommendItems(2);
		List<Integer> resp = new ArrayList();;
		
		for(int i=0;i<500;i++){
			int menor=lista2.indexOf(lista.get(i));
			int item=lista.get(i);
			int pos = i;
			for(int j=i+1;j<500;j++){
				if(lista.get(j)<menor){
					menor=lista2.indexOf(lista.get(j));
					item=lista.get(j);
					pos=j;
				}				
			}	
			int temp = lista.get(i);
			lista.set(i, item);
			lista.set(pos, temp);
			resp.add(item);
		}
		
		int i=1;
		for(Integer a :resp){
			System.out.println("recomendación "+i+": "+a);
			i++;
		}
		
	}

}
