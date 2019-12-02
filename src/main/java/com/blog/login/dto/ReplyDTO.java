package com.blog.login.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;

import com.blog.login.model.Question;
import com.blog.login.model.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	private int id;
	private String content;    
	//private QuestionDTO question;
	private Integer parent;
	//private List<ReplyDTO> replies;
	private String createdBy;
	private Date creationDate;
	private String lastModifiedBy;
	private Date lastModifiedDate;
}
