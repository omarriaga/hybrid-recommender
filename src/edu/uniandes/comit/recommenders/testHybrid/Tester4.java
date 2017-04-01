package edu.uniandes.comit.recommenders.testHybrid;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.recommender101.data.DataModel;
import org.recommender101.eval.metrics.Precision;
import org.recommender101.eval.metrics.Recall;

public class Tester4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			LocalDateTime inittime = LocalDateTime.now();
			DataModel model = new DataModel();
			LastFMDataLoader loader= new LastFMDataLoader();
			System.out.println("Cargando modelo");
			loader.setFilename("data/user_artistsload.dat");
			loader.loadData(model);
			//iniciando recomendador
			HybridRecommender recommender = new HybridRecommender();
			recommender.init();
			//cargando metricas
			DataModel model_test = new DataModel();
			System.out.println("Cargando modelo");
			loader.setFilename("data/user_artiststest.dat");
			loader.loadData(model_test);
			
			Precision precision_metric = new Precision();
			precision_metric.setRecommender(recommender);
			precision_metric.setTestDataModel(model_test);
			precision_metric.setTrainingDataModel(model);
			precision_metric.initialize();
			precision_metric.setTargetSet("allrelevantintestset");
			
			//System.out.println("precision: "+precision_metric.getEvaluationResult());
			
			Recall recall_metric = new Recall();
			recall_metric.setRecommender(recommender);
			recall_metric.setTestDataModel(model_test);
			recall_metric.setTrainingDataModel(model);
			recall_metric.initialize();
			recall_metric.setTargetSet("allrelevantintestset");
			
			//System.out.println("Recall: "+recall_metric.getEvaluationResult());
			System.out.println("iniciando evaluacion "+model_test.getUsers().size()+" usuarios");
			for (int user : model_test.getUsers()){
				System.out.println("evaluando usuario" +user);
				//recommender.recommendItemsByRatingPrediction(user)
				List<Integer> lista = recommender.recommendItems(user);
				precision_metric.addRecommendations(user, lista );
				recall_metric.addRecommendations(user, lista );
			}
			System.out.println("precision: "+precision_metric.getEvaluationResult());
			System.out.println("Recall: "+recall_metric.getEvaluationResult());
			LocalDateTime fin = LocalDateTime.now();
			Duration diff = Duration.between(inittime, fin);
			System.out.println("se demoro "+ diff.getSeconds()+ " sec");
		}catch(Exception ex){
			
		}
	}

}
