package com.auction.flab.application.web.controller;

import com.auction.flab.application.service.MemberService;
import com.auction.flab.application.web.dto.MemberRequestDto;
import com.auction.flab.application.web.dto.MemberResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MemberService memberService;

    @WithMockUser
    @Test
    void valid_request() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @WithMockUser
    @Test
    void invalid_name_with_null() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name(null)
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_name_with_empty() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_name_with_blank() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("     ")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_name_with_length_greater_than_10() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동12345678")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_null() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email(null)
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_empty() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_blank() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("     ")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_length_greater_than_30() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test123456789012345678901234567890@gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_email_with_worng_format() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("tes#gmail.com")
                .password("Aa!1234567")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_with_length_is_less_than_10() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!123456")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_with_greater_than_20() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!123456712345671234")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_lack_of_capitalization() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!1234567".toLowerCase())
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_lack_of_lower_case_letters() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!1234567".toUpperCase())
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_missing_special_characters() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa12345678")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_password_due_to_missing_number() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!abcdefg")
                .mobileNo("01012345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_mobileNo_length_is_greater_than_13() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("0101234567812345")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @Test
    void invalid_mobileNo_due_to_presence_of_non_numeric_characters() throws Exception {
        // given
        MemberRequestDto memberRequestDto = MemberRequestDto.builder()
                .name("홍길동")
                .email("test@gmail.com")
                .password("Aa!1234567")
                .mobileNo("010-12345678")
                .build();
        given(memberService.addMember(eq(memberRequestDto))).willReturn(MemberResponseDto.from(1L));

        // when
        ResultActions resultActions = mockMvc.perform(post("/members")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequestDto)));

        // then
        resultActions
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
