package com.st.exercise

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.kohsuke.github.GHUser
import org.kohsuke.github.GitHub
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.net.URL
import java.util.*
import org.assertj.core.api.Assertions.*
import org.kohsuke.github.GHFileNotFoundException

@SpringBootTest
class GithubServiceTest {
	@MockkBean
	private lateinit var mockGithub: GitHub

	@Autowired
	private lateinit var githubService: GithubService

	@Test
	fun testGetUser() {
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

		val apiUser = mockk<GHUser> {
			every { login } returns expectedUser.userName
			every { name } returns expectedUser.displayName
			every { avatarUrl } returns expectedUser.avatarURL
			every { location } returns expectedUser.geoLocation
			every { email } returns expectedUser.email
			every { url } returns expectedUser.url
			every { createdAt } returns expectedUser.createdAt
			every { repositories } returns mapOf(expectedRepo.name to mockk {
				every { url } returns expectedRepo.url
			})
		}
		every { mockGithub.getUser("octocat") }.returns(apiUser)
		val userFromService = githubService.getUser("octocat")
		assertThat(userFromService).isEqualTo(expectedUser)
	}


	@Test
	fun testGetUserNotFound() {
		every { mockGithub.getUser("octocat") }.throws(GHFileNotFoundException())
		assertThatThrownBy { githubService.getUser("octocat") }
				.isInstanceOf(GithubUserNotFoundException::class.java)
	}
}
