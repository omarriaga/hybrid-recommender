package edu.uniandes.comit.recommenders.testHybrid;

import java.util.LinkedList;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.recommender.AbstractRecommender;
import org.recommender101.recommender.baseline.NearestNeighbors;
import org.recommender101.recommender.extensions.contentbased.ContentBasedRecommender;

public class HybridRecommender extends AbstractRecommender {

	
	private DataModel model;
	private ContentBasedRecommender contentBasedRecommender;
	private NearestNeighbors itemBasedRecommender;

	@Override
	public float predictRating(int user, int item) {
		return 0;
	}

	@Override
	public List<Integer> recommendItems(int user) {
		try{
			System.out.println("obteniendo recomendacion por contenido");
			List<Integer> items_content = contentBasedRecommender.recommendItems(user);
			for (int item : items_content){
				model.addRating(user, item, contentBasedRecommender.predictRating(user, item));
			}
			itemBasedRecommender.init();
			System.out.println("obteniendo refinacion por filtro colaborativo");
			List<Integer> recomendacion = itemBasedRecommender.recommendItems(user);
			return recomendacion;
		}catch(Exception ex){
			ex.printStackTrace();
			return new LinkedList<>();
		}
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
		itemBasedRecommender.setNeighbors("30");
		itemBasedRecommender.init();
	
	}

}
