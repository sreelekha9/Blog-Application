package com.blog.login.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reply")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "reply_id")
	private int id;

	@Column(name = "message",nullable=false)
	@NotEmpty(message = "*Please provide reply message")
	private String content;    


	@ManyToOne(optional = true,fetch=FetchType.EAGER)
	@JoinColumn(name="question_id",  nullable=true)
	private Question question;

	@ManyToOne(optional = true,fetch=FetchType.EAGER)
	@JoinColumn(name = "parent_id",  nullable= true)
	private Reply parentObj;



	@OneToMany( fetch = FetchType.EAGER)
	private List<Reply> replies;


	@Column(name="created_by")
	private String createdBy;

	@CreationTimestamp
	private Date creationDate;

	@Column(name="modified_by")
	private String lastModifiedBy;

	@CreationTimestamp
	private Date lastModifiedDate;
}
