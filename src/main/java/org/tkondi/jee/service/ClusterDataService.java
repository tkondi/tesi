package org.tkondi.jee.service;

import java.security.KeyStore.Entry;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.tkondi.jee.model.Cluster;

import com.mongodb.DBObject;

public class ClusterDataService {
	
	private ArrayList<Cluster> clustersData = new ArrayList<Cluster>();
	private MongoOperations mongoOperation;
	
	public ClusterDataService() {
				ApplicationContext ctx = new GenericXmlApplicationContext("spring/application-config.xml");
				//ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
				mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
				this.setClusters((DBObject)getDbClustersObject());
	}
	
	private Object getDbClustersObject(){
		Query searchUserQuery = new Query();
		searchUserQuery.fields().include("clusters").exclude("_id");
		return  mongoOperation.findOne(searchUserQuery, DBObject.class, "toload");
	}
	
	public ArrayList<Cluster> getClusters() {
		return clustersData;
	}
	
	public HashMap<String, Cluster> getClustersHashMap() {
		HashMap<String, Cluster> clustersMap = new HashMap<String, Cluster>();
		for (Cluster cluster : this.getClusters()) {
			clustersMap.put(cluster.getId(), cluster);
		}
		return clustersMap;
	}
	
	public void setClusters(DBObject cluster) {
		ArrayList<DBObject> mongo = (ArrayList<DBObject>)cluster.get("clusters"); 
		
		for (DBObject c : mongo) {
			this.clustersData.add(new Cluster(c.get("_id").toString(),(List<String>)c.get("labels"),(DBObject)c.get("content"),(Integer)c.get("size")));
		}
	}
	
	public Set<String> getLinksForCluster(Cluster mainCluster, boolean strict) {
		Map<String, Integer> clustersSubsets = new HashMap<String, Integer>();
		getSubsetsCount(mainCluster, clustersSubsets, strict);
		if(clustersSubsets.isEmpty()) {
			return null;
		} 
		
		Integer max = Collections.max(clustersSubsets.values());
		return getKeysByValue(clustersSubsets, max);		
    }

	private void getSubsetsCount(Cluster mainCluster, Map<String, Integer> clustersSubsets, boolean strict) {
		int matchFound;
		
		for (Cluster cluster : this.clustersData ) {
			if (!cluster.isEqual(mainCluster)) {
				matchFound = 0;
				for (String wdi : cluster.getLabels() ) {
					if (mainCluster.getLabels().contains(wdi)) {
						matchFound++;
					} else if (strict) {
						matchFound = 0;
						break;
					}
				}
				if (matchFound>0) {
				clustersSubsets.put(cluster.getId(),matchFound);
				}
			}
		}
	}
	
	
	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    Set<T> keys = new HashSet<T>();
	    for (java.util.Map.Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
	
	
	

}
