package com.teamsparta.igdbtest.oauth.controller

import com.fasterxml.jackson.databind.JsonNode
import com.teamsparta.igdbtest.oauth.service.AccessTokenHolder
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.view.RedirectView

@Controller
class OauthController(
    private val restTemplate: RestTemplate,
    private val accessTokenHolder: AccessTokenHolder
) {
    @Value("\${twitch.client-id}")
    lateinit var clientId: String

    @Value("\${twitch.client-secret}")
    lateinit var clientSecret: String

    @Value("\${twitch.redirect-uri}")
    lateinit var redirectUri: String

    private val authUrl = "https://id.twitch.tv/oauth2/authorize"
    private val tokenUrl = "https://id.twitch.tv/oauth2/token"



//    @GetMapping("/login")
//    fun login(): RedirectView {
//        val url = "$authUrl?client_id=$clientId&redirect_uri=$redirectUri&response_type=code&scope=user:read:email"
//        return RedirectView(url)
//    }
//
//
//    @GetMapping("/auth/callback")
//    fun authCallback(@RequestParam code: String): ResponseEntity<Map<String, String>> {
//        println("Authorization code: $code")
//
//        val headers = HttpHeaders().apply {
//            contentType = MediaType.APPLICATION_FORM_URLENCODED
//        }
//
//        val body = LinkedMultiValueMap<String, String>().apply {
//            add("client_id", clientId)
//            add("client_secret", clientSecret)
//            add("code", code)
//            add("grant_type", "authorization_code")
//            add("redirect_uri", redirectUri)
//        }
//
//        val entity = HttpEntity(body, headers)
//
//        return try {
//            val response = restTemplate.postForObject(tokenUrl, entity, Map::class.java)
//            val accessToken = response?.get("access_token") as String
//            println("Access token: $accessToken")
//            ResponseEntity.ok(mapOf("access_token" to accessToken))
//        } catch (e: HttpClientErrorException) {
//            println("Error: ${e.statusCode} - ${e.responseBodyAsString}")
//            ResponseEntity.status(e.statusCode).body(mapOf("error" to e.responseBodyAsString))
//        }
//    }

    @PostMapping("/get-token")
    fun getAccessToken(): ResponseEntity<String>{
        val requestBody = mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "grant_type" to "client_credentials"
        )

        val response = restTemplate.postForEntity(tokenUrl, requestBody, JsonNode::class.java)
        val token = response.body?.get("access_token")?.asText()
        val expiresIn = response.body?.get("expires_in")?.asLong()
        println(expiresIn)
        token?.let{ accessTokenHolder.accessToken = it}
        return ResponseEntity.ok(token)
    }


}