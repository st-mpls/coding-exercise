package com.st.exercise

import org.kohsuke.github.GHFileNotFoundException
import org.kohsuke.github.GitHub
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@Service
class GithubService(val gh: GitHub) {

    /**
     * Retrieves a Github user and their repositories and collects them into a [GithubUser]
     *
     * @param username Username/login of Github user to retrieve
     * @return A [GithubUser] with information about the user with the given [username] and their repos
     */
    fun getUser(username: String): GithubUser {
        val ghApiUser = try {
            gh.getUser(username)
        } catch (e: GHFileNotFoundException) {
            throw GithubUserNotFoundException()
        }

        return GithubUser(
                ghApiUser.login,
                ghApiUser.name,
                ghApiUser.avatarUrl,
                ghApiUser.location,
                ghApiUser.email,
                ghApiUser.url,
                ghApiUser.createdAt,
                // no try/catch for this request because if it fails we should let spring boot 500
                ghApiUser.repositories.entries.map { GithubRepo(it.key, it.value.url) }
        )
    }
}

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "user not found")
class GithubUserNotFoundException: RuntimeException()
