package com.st.exercise

import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GithubConfig {

    /**
     * Creates a new [GitHub] client
     *
     * @param token OAuth token to access the Github API, optional
     * @return [GitHub] client, authenticated if a token was provided
     */
    @Bean
    fun createGithubClient(@Value("\${github.token:#{null}}") token: String?): GitHub {
        return if (token == null) {
            println("Not authenticated")
            GitHub.connect()
        } else {
            println("Authenticating with OAuth token")
            GitHubBuilder().withOAuthToken(token).build()
        }
    }
}