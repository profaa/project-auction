package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.LoginService;
import com.auction.flab.application.util.TokenUtil;
import com.auction.flab.application.vo.MemberVo;
import com.auction.flab.application.web.dto.LoginRequestDto;
import com.auction.flab.application.web.dto.MemberRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMybatis
@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LoginService loginService;

    @MockBean
    TokenUtil tokenUtil;

    static MemberVo memberVo;

    @BeforeAll
    static void setUp() {
        memberVo = MemberVo.from(MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build());
    }

    @WithMockUser
    @Test
    void valid_request() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa!1234567")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("test_token"));
    }

    @WithMockUser
    @Test
    void invalid_email_with_null() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email(null)
                .password("Aa!1234567")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_empty() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("")
                .password("Aa!1234567")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_blank() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("     ")
                .password("Aa!1234567")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_length_greater_than_30() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test123456789012345678901234567890@gmail.com")
                .password("Aa!1234567")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_worng_format() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test#gmail.com")
                .password("Aa!1234567")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_with_length_is_less_than_10() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa!123456")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_with_greater_than_20() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa!123456712345671234")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_lack_of_capitalization() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa!1234567".toLowerCase())
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_lack_of_lower_case_letters() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa!1234567".toUpperCase())
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_missing_special_characters() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa12345678")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_missing_number() throws Exception {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@gmail.com")
                .password("Aa!abcdefg")
                .build();
        given(loginService.checkAccount(eq(loginRequestDto))).willReturn(memberVo);
        given(tokenUtil.createNewAccessToken(eq(memberVo))).willReturn("test_token");

        // when
        ResultActions resultActions = mockMvc.perform(post("/sign-in")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
    
}
