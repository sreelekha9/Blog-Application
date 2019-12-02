package com.blog.login.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.login.dto.ReplyDTO;
import com.blog.login.model.Reply;
import com.blog.login.repository.QuestionRepository;
import com.blog.login.repository.ReplyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReplyService {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ReplyRepository replyRepository;
	
	//@Autowired
	private ModelMapper modelMapper;
	

	@Transactional
	public Reply addReply(Reply reply) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		reply.setCreatedBy(username);
		reply.setLastModifiedBy(username);
		
		//Question question=questionRepository.findById((long) reply.getQuestion().getId()).get();
		//reply.setQuestion(question);
		// TODO Auto-generated method stub
		return replyRepository.save(reply);
	}

	@Transactional(readOnly=true)
	public List<ReplyDTO> getReplyForQuestion(int questionid) {
		
		List<ReplyDTO> repliesDTO=new ArrayList<>();
		// TODO Auto-generated method stub
		List<Reply> replies= replyRepository.findByQuestionId(questionid);
		
		for(Reply reply:replies)
		{
			ReplyDTO result=convertToDto(reply);
			repliesDTO.add(result);
			//populateReplies(result);
		}
		return repliesDTO;
	}
	private ReplyDTO convertToDto(Reply reply) {
		modelMapper=new ModelMapper();
		modelMapper.addConverter(new Converter<Reply, Integer>() {

			@Override
			public Integer convert(MappingContext<Reply, Integer> context) {
				// TODO Auto-generated method stub
				return context.getSource() == null ? null : context.getSource().getId();
			}
		});
		ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);
		
		
	    return replyDTO;
	}
	
	private void populateReplies(ReplyDTO replyDTO)
	{
		List<ReplyDTO> repliesDTO=new ArrayList<>();
		List<Reply> replies=replyRepository.findByParentId(replyDTO.getId());
		
		for(Reply reply:replies)
		{
			ReplyDTO result=convertToDto(reply);
			repliesDTO.add(result);
			populateReplies(result);
		}
		//replyDTO.setReplies(repliesDTO);		
	}
	
	

}
