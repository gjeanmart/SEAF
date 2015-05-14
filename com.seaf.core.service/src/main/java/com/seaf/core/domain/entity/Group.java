package com.seaf.core.domain.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.hibernate.annotations.Proxy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Proxy(lazy=false)
@Table(name = "SEAF_GROUP")
@Indexed 
@AnalyzerDefs({
	@AnalyzerDef(	name = "autocompleteEdgeAnalyzerForGroup",
				tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),

				filters = {
					@TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
						@Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
						@Parameter(name = "replacement", value = " "),
						@Parameter(name = "replace", value = "all") }),
						@TokenFilterDef(factory = LowerCaseFilterFactory.class),
						@TokenFilterDef(factory = StopFilterFactory.class),
					@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
						@Parameter(name = "minGramSize", value = "1"),
						@Parameter(name = "maxGramSize", value = "50") }) })
})
public class Group {

	@Id
    @Column(name="GROUP_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="group_id_seq")
    @SequenceGenerator(name="group_id_seq", sequenceName="group_id_seq", allocationSize=1)
    private int id;
 
	@Column(name="GROUP_NAME", unique = true, nullable = false, length = 20)
	@Field(name = "name", index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerForGroup"))
	private String name;
	
	@Column(name="GROUP_DESCRIPTION", nullable = false, length = 255)
	private String description;
	
	@Column(name="GROUP_ADDEDDATE", nullable = true)
	@Field(name = "addedDate")
	private Date addedDate;		
	
	@Column(name="GROUP_LASTMODIFIEDDATE", nullable = true)
	@Field(name = "lastModifiedDate")
	private Date lastModifiedDate;	
	
    @ManyToMany(fetch = FetchType.EAGER, mappedBy="groups")
    private Set<User> users = new HashSet<User>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
 
}
