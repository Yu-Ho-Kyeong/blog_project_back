package com.blog.blogback;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.blogback.common.JwtTokenProvider;

@SpringBootTest
@AutoConfigureMockMvc
class BlogBackApplicationTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    // @Test
    // public void testFilter() throws Exception {
    //     mockMvc.perform(post("/api/user/test"))
    //             .andExpect(status().isOk());
    //     // '/api/user/test' 엔드포인트에 대한 요청을 모의로 생성하여,
    //     // JwtAuthenticationFilter를 통과할 수 있는지 확인하고 상태 코드를 검증합니다.
        
    //     // JwtTokenProvider의 getAuthentication 메서드가 호출되었는지 확인
    //     verify(jwtTokenProvider, times(1)).getAuthentication(any(String.class));
    // }
}
