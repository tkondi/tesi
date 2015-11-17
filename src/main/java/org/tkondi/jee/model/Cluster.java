package org.tkondi.jee.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.DBObject;


@Document(collection = "cluster")
public class Cluster {

	@Id
	private String id;

	private List<String> labels;
	private DBObject content;
	
	
	public Cluster(String id, List<String> labels, DBObject content, int size) {
		super();
		this.id = id;
		this.labels = labels;
		this.content = content;
		this.size = size;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public DBObject getContent() {
		return content;
	}
	public void setContent(DBObject content) {
		this.content = content;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public boolean isEqual(Cluster cluster) {
		return (this.getId()==cluster.getId())?true:false;
	}
	
	private int size;
	
	//getter, setter, toString, Constructors

}
