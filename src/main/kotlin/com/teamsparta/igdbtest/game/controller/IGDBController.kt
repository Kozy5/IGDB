package com.teamsparta.igdbtest.game.controller

import com.teamsparta.igdbtest.game.service.IGDBService
import com.teamsparta.igdbtest.oauth.controller.OauthController
import com.teamsparta.igdbtest.oauth.service.AccessTokenHolder
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class IGDBController(
    private val igdbService: IGDBService,
    private val oauthController: OauthController,
    private val accessTokenHolder: AccessTokenHolder
) {
    @Scheduled(fixedRate = 300000) // ms
    fun updateAccessToken() {
        oauthController.getAccessToken()
    }

    @PostMapping("/check-game")
    fun checkGameExists(@RequestBody gameName:String): Boolean{
        val accessToken = accessTokenHolder.accessToken ?: throw IllegalStateException("Access token is not available")
        return igdbService.checkGameExists(accessToken, gameName)
    }

    @PostMapping("/games")
    fun getGamesByName(@RequestBody gameName: String): ResponseEntity<String>{
        val accessToken = accessTokenHolder.accessToken ?: throw IllegalStateException("Access token is not available")
        return igdbService.getGamesByName(accessToken, gameName)
    }


}