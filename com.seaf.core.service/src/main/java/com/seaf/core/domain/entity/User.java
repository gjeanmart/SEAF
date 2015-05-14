package com.seaf.core.domain.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "SEAF_USER")
@Indexed 
@AnalyzerDefs({
	@AnalyzerDef(	name = "autocompleteEdgeAnalyzerForUser",
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
public class User {

	@Id
    @Column(name="USER_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_seq")
    @SequenceGenerator(name="user_id_seq", sequenceName="user_id_seq", allocationSize=1)
    private int id;
 
	@Column(name="USER_EMAIL", unique = true, nullable = false, length = 100)
	@Field(name = "email", index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerForUser")) 
	private String email;
 
	@Column(name="USER_FIRSTNAME", nullable = false, length = 50)
	@Field(name = "firstName", index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerForUser")) 
	private String firstName;
	
	@Column(name="USER_LASTNAME", nullable = false, length = 50)
	@Field(name = "lastName", index = Index.YES, analyze = Analyze.YES, store = Store.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzerForUser")) 
	private String lastName;
	 	 
	@Column(name="USER_PASSWORD", nullable = true, length = 20)
	@Field(name = "password")
	private String password;
	
	@Column(name="USER_BIRTHDATE", nullable = true)
	@Field(name = "birthDate")
	private Date birthDate;	
	
	@Column(name="USER_ADDEDDATE", nullable = true)
	@Field(name = "addedDate")
	private Date addedDate;		
	
	@Column(name="USER_LASTMODIFIEDDATE", nullable = true)
	@Field(name = "lastModifiedDate")
	private Date lastModifiedDate;		
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name="SEAF_MEMBERSHIP", 
                joinColumns={@JoinColumn(name="USER_ID")}, 
                inverseJoinColumns={@JoinColumn(name="GROUP_ID")})
    private Set<Group> groups = new HashSet<Group>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
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

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	} 
	
	
}
