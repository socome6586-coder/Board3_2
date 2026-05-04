package com.green.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.green.user.dto.UserDto;
import com.green.user.mapper.UserMapper;

@Controller
@RequestMapping("/Users")
public class UserController {
	
	@Autowired
	private UserMapper userMapper;

	// /Users/WriteForm()
	@RequestMapping("/WriteForm")
	public ModelAndView writeForm() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/write");
		mv.addObject("msg", "조민석");
		
		return mv;
	}
	
	// http://localhost:8080/Users/Write?userid=&passwd=&username=&email=
	@RequestMapping("/Write")
	public ModelAndView write(UserDto userDto) {
		System.out.println("유저 컨트롤러 속 유저DTO : " + userDto);
		
		// 넘어온 data 로 db 에 저장
		userMapper.insertUser(userDto);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		
		return mv;
	}
	
	// /Users/List
	@RequestMapping("/List")
	public ModelAndView  list() { 
		
		List<UserDto> userList = userMapper.getUserList();
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/list");
		mv.addObject("userList", userList);
		
		return mv;
	}
	
	// Users/Delete?userid=${user.userid}
	@RequestMapping("/Delete")
	public  ModelAndView  delete(UserDto userDto) {
		
		userMapper.deleteUser(userDto);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		return mv;
	}
	
	//	/Users/UpdateForm?userid=${user.userid}
	@RequestMapping("/UpdateForm")
	public ModelAndView  updateForm( UserDto userDto ) {
		System.out.println("들어온거:" + userDto);
		UserDto  user = userMapper.getUser(userDto);
		System.out.println("조회한거:" + user);
		
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/update");
		mv.addObject("user", user);
		
		return mv;
	}
	
	// /Users/Update
	@RequestMapping("/Update")
	public ModelAndView update(UserDto userDto) {
		
		userMapper.updateUser(userDto);
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/Users/List");
		return mv;
	}

	// 아이디 중복 확인 - 결과 문자열을 리턴
	// <b class="green">사용가능한 아이디입니다</b>
	// <b class="red">사용할 수 없는 아이디입니다</b>
	// /Users/IdDupCheck2?userid=sky
	@GetMapping("/IdDupCheck2")
	@ResponseBody // return 되는 글자는 jsp 가 아니다
	public UserDto idDupCheck2(UserDto userDto) {
		
		UserDto user   = userMapper.getIdDupCheck(userDto); // 조회한 userid
		if(user == null)
			user = new UserDto();
		return user;
	}
	
	// /Users/DupCheckWindow
	@GetMapping("/DupCheckWindow")
	public ModelAndView dupCheckWindow() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/idcheck");
		return mv;
	}
	
	// 중복확인
	// /Users/DupCheck?userid=aaa
	@RequestMapping("/DupCheck")
	public ModelAndView dupCheck(UserDto userDto) {
		
		UserDto user  = userMapper.getUser(userDto);
		String  msg   = "<b class='red'>사용할 수 없는 아이디 입니다.</b>";
		if(user == null)
			    msg  = "<b class='green'>사용 가능한 아이디 입니다.</b>";
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/idcheck");
		mv.addObject("msg", msg);
		return mv;
	}
}