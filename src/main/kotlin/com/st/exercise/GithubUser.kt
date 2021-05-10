package com.st.exercise

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URL
import java.util.*

data class GithubUser(
        @JsonProperty("user_name")
        val userName: String,
        @JsonProperty("display_name")
        val displayName: String?,
        @JsonProperty("avatar")
        val avatarURL: String?,
        @JsonProperty("geo_location")
        val geoLocation: String?,
        val email: String?,
        val url: URL?,
        @JsonProperty("created_at")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val createdAt: Date?,
        val repos: List<GithubRepo>
)

data class GithubRepo(
        val name: String,
        val url: URL
)
