package com.blog.login.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.blog.login.model.Question;
import com.blog.login.repository.QuestionRepository;

@Service("questionService")
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	public List<Question> getQuestions() {
		// List<Question> quests=new ArrayList<>();
		// quests.add(Question.builder().id(1).content("Sample question 1?").build());
		// quests.add(Question.builder().id(2).content("Sample question 2?").build());
		return questionRepository.findAll();

	}

	public Question addQuestion(Question question) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		question.setCreatedBy(username);
		question.setLastModifiedBy(username);
		return questionRepository.save(question);

	}
}
