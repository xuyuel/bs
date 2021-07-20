package com.sjzc.kt.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.sjzc.kt.dao.MemberMapper;
import com.sjzc.kt.dao.QuestionResponseMapper;
import com.sjzc.kt.entity.Member;
import com.sjzc.kt.entity.QuestionResponse;
import com.sjzc.kt.service.MemberService;

@Controller
public class RaiseController {

	@Autowired
	private QuestionResponseMapper questionResponseMapper;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberMapper memberMapper;
	
	 @Autowired
	 private SimpMessageSendingOperations simpMessageSendingOperations;
	
	@MessageMapping("/raiseAdd")
	@SendTo("/topic/public/")
    public void addUser(@Payload QuestionResponse questionResponse, SimpMessageHeaderAccessor headerAccessor) {

        //LOGGER.info("User added in Chatroom:" + chatMessage.getSender());
        try {
        	//举手信息插库
        	//QuestionResponse questionResponse = new QuestionResponse();
        	//questionResponse.setMemberId(raiseEntity.getMemberId());
        	//questionResponse.setQuestionId(raiseEntity.getQuestionId());
        	questionResponse.setAnswer(2);
        	questionResponse.setCreateTime(new Date());
        	questionResponse.setDelState(1);
        	
        	questionResponseMapper.insertSelective(questionResponse);
        	//raiseEntity.setResponseId(questionResponse.getResponseId());
        	Member member = memberService.selectMemberById(questionResponse.getMemberId());
			Map<String, Object> map1 = new HashMap<>();
			map1.put("number", member.getNumber());
			map1.put("name", member.getName());
			map1.put("createTimeFormat", questionResponse.getCreateTime().getTime());
			questionResponse.setMap(map1);
			
            headerAccessor.getSessionAttributes().put("questionId", questionResponse.getQuestionId());
            headerAccessor.getSessionAttributes().put("memberId", questionResponse.getMemberId());
            String url = "/topic/public/"+questionResponse.getQuestionId()+"/message";
            
            simpMessageSendingOperations.convertAndSend(url,questionResponse);
            //redisTemplate.opsForSet().add(onlineUsers, chatMessage.getSender());
            //redisTemplate.convertAndSend(userStatus, JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
           // LOGGER.error(e.getMessage(), e);
        }
//        return questionResponse;
    }
}
