package com.blog.login.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.login.model.Question;
import com.blog.login.model.User;

import net.bytebuddy.asm.Advice.Return;

@Repository("questionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
}