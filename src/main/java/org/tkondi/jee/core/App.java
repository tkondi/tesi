package org.tkondi.jee.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.tkondi.jee.model.Cluster;
import org.tkondi.jee.service.ClusterDataService;

public class App {

    public static void main(String[] args) {
    		
    	ClusterDataService clusersDataService = new ClusterDataService();
		ArrayList<Cluster> clusers = clusersDataService.getClusters();
		HashMap<String, Cluster> clusterHashMap = clusersDataService.getClustersHashMap();
		
		// Print clusters links
		for (Cluster findLinks : clusers) {
			
			System.out.println("Cluster id: " +  findLinks.getId() + ", labels: " + findLinks.getLabels().toString());
			System.out.println("Linked with: ");
			
			try { 
				for (String key : clusersDataService.getLinksForCluster(findLinks, false)) {
					System.out.println("Cluster id: " +  clusterHashMap.get(key).getId() + ", labels: " + clusterHashMap.get(key).getLabels().toString());
				}
			} catch (NullPointerException e){ 
				System.out.println("No Match");
			}
			
			
			System.out.println("\nStrict linked with: ");
			try {
			for (String key : clusersDataService.getLinksForCluster(findLinks, true)) {
				System.out.println("Cluster id: " +  clusterHashMap.get(key).getId() + ", labels: " + clusterHashMap.get(key).getLabels().toString());
			}
			} catch (NullPointerException e){ 
				System.out.println("No Match");
			}
			System.out.println("\n\n");
		}
	
    }
 
}

