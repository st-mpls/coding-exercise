package com.st.exercise

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

const val GITHUB_USER_REPORT_PATH = "/github-users/{username}"

@RestController
@Tag(name = "Github User", description = "Representation of a user and their repos")
class GithubController(val githubService: GithubService) {

    @GetMapping(GITHUB_USER_REPORT_PATH)
    @Operation(summary = "Get a user and their repos", description = "Retrieve user and repos given a username")
    fun getGithubUserInfo(@PathVariable username: String): GithubUser {
        return githubService.getUser(username)
    }
}