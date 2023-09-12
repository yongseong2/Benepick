package com.ssafy.benepick.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.benepick.domain.card.dto.response.CardCompanyResponseDto;
import com.ssafy.benepick.domain.card.repository.CardCompanyRepository;
import com.ssafy.benepick.domain.user.entity.User;
import com.ssafy.benepick.domain.user.entity.UserCardCompany;
import com.ssafy.benepick.domain.user.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserCardCompanyServiceImpl implements UserCardCompanyService{

	private final UserRepository userRepository;
	private final UserService userService;
	private final CardCompanyRepository cardCompanyRepository;

	@Override
	public List<CardCompanyResponseDto> getUserCardCompany(HttpServletRequest request) {
		log.info("UserCardCompanyServiceImpl_getUserCardCompany | 사용자와 연동된 카드사 조회");
		User loginUser = userService.getUserFromRequest(request);
		// User loginUser = userRepository.findById("ex1").get();

		return loginUser.getUserCardCompanyList()
			.stream().map(userCardCompany -> cardCompanyRepository.findById(userCardCompany.getUserCardCompanyId()).get().toCardCompanyResponseDto())
			.collect(Collectors.toList());
	}

	@Override
	public void cancelLinkCardCompany(User user, UserCardCompany userCardCompany) {
		log.info("UserCardCompanyServiceImpl_getUserCardCompany | 카드사 연동 해제 및 관련 데이터 삭제");
		user.getUserCardList().stream()
			.filter(userCard -> userCardCompany.getUserCardCompanyName().equals(userCard.getUserCardCompanyName()))
			.forEach(userCard -> user.removeUserCard(userCard));

		user.cancelLinkCardCompany(userCardCompany);
	}
}
