package com.teamsparta.igdbtest.oauth.service

import org.springframework.stereotype.Component
import sun.jvm.hotspot.oops.CellTypeState.value

@Component
class AccessTokenHolder {
    @Volatile
    var accessToken : String? = null

        @Synchronized set

}