package com.blog.login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.login.model.Reply;
import com.blog.login.model.User;

@Repository("replyRepository")
public interface ReplyRepository extends JpaRepository<Reply, Long> {

	@Query("select r from Reply r where question_id=:questionid ")
	List<Reply> findByQuestionId(@Param("questionid")Integer  questionid);

	@Query("select r from Reply r where parent_id=:parentid")
	List<Reply>  findByParentId(@Param("parentid")Integer id);
    
}