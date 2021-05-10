package com.st.exercise

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.net.URL
import java.util.*

@WebMvcTest
class GithubControllerTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    lateinit var githubService: GithubService

    @Test
    fun testGetGithubUserInfo() {
        val expectedRepo = GithubRepo("repo1", URL("https://api.github.com/users/octocat/repos"))
        val expectedUser = GithubUser(
                "user-login",
                "user-name",
                "avatar-url",
                "location",
                "email-address",
                URL("https://api.github.com/users/octocat"),
                Date(1234567),
                listOf(expectedRepo)
        )

        every { githubService.getUser("octocat") }.returns(expectedUser)
        mockMvc.perform(get(GITHUB_USER_REPORT_PATH.replace("{username}", "octocat")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("\$.user_name").value(expectedUser.userName))
                .andExpect(jsonPath("\$.display_name").value(expectedUser.displayName))
                .andExpect(jsonPath("\$.avatar").value(expectedUser.avatarURL))
                .andExpect(jsonPath("\$.geo_location").value(expectedUser.geoLocation))
                .andExpect(jsonPath("\$.email").value(expectedUser.email))
                .andExpect(jsonPath("\$.url").value(expectedUser.url.toString()))
                .andExpect(jsonPath("\$.created_at").value("1970-01-01 00:20:34"))
                .andExpect(jsonPath("\$.repos.[0].name").value(expectedRepo.name))
                .andExpect(jsonPath("\$.repos.[0].url").value(expectedRepo.url.toString()))
    }

    @Test
    fun testGetGithubUserInfoNotFound() {
        every { githubService.getUser("octocat") }.throws(GithubUserNotFoundException())
        mockMvc.perform(get(GITHUB_USER_REPORT_PATH.replace("{username}", "octocat")).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
    }
}