package com.bh.easychatbanked

import com.bh.easychatbanked.request.RegistrationRequest
import com.bh.easychatbanked.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRepository
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.logging.Logger

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class EasyChatBankedApplicationTests {
    @Autowired
    private lateinit var mockMvc: MockMvc


    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var csrfTokenRepository: CsrfTokenRepository

    @Test
    fun registerUserTest() {
        val registrationRequest = RegistrationRequest(
            account = "123456",
            username = "23456",
            password = "123qwe",
            email = "test@example.com",
            phone = "1234567890"
        )

        mockMvc.perform(
            post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())  //启用 csrf() 验证
                .content(ObjectMapper().writeValueAsString(registrationRequest))
        )
            .andExpect(status().isCreated)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(content().string("User registered successfully."))
            .andReturn()
    }
    @Test
    fun testGetCsrfToken() {
        val request = MockHttpServletRequest()
        val token: CsrfToken? = userService.getCsrfToken(request)
        println(token?.token)
    }

}
