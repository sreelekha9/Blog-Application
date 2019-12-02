package com.blog.login.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.login.dto.ReplyDTO;
import com.blog.login.model.Question;
import com.blog.login.model.Reply;
import com.blog.login.service.QuestionService;
import com.blog.login.service.ReplyService;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/get-all")
	public List<Question> getQuestions()
	{
		
		return questionService.getQuestions();
	}
	
	@PostMapping("")
	public ResponseEntity<Question>  addQuestion(@RequestBody Question question)

	{
		Question question1 = questionService.addQuestion(question);
		  HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Question> responseEntity =
			      new ResponseEntity<Question>(
			    		  question1, headers, HttpStatus.CREATED);
			  return responseEntity;
	}
	
	@GetMapping(value="/comments/{questionid}",produces=MediaType.APPLICATION_JSON_VALUE)
	public List<ReplyDTO> getComments(@PathVariable int questionid)
	{
		//List<Reply> questions=new ArrayList<>();
		//questions.add(Reply.builder().id(1).content("content").parent(null).build());
		
		return replyService.getReplyForQuestion(questionid);
	}
	
	@PostMapping(value="/comments",consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reply> addComment(@RequestBody Reply reply)
	{
		Reply reply1 = replyService.addReply(reply);
		  HttpHeaders headers = new HttpHeaders();
		
		ResponseEntity<Reply> responseEntity =
			      new ResponseEntity<Reply>(
			    		  reply1, headers, HttpStatus.CREATED);
			  return responseEntity;
	}
	
}
