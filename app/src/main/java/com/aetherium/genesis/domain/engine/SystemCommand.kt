package com.aetherium.genesis.domain.engine

import com.aetherium.genesis.domain.intent.UniversalIntentPayload
import com.aetherium.genesis.domain.permission.ActionResult

// อินเตอร์เฟสคำสั่งสากล (Command Pattern Pattern)
interface SystemCommand {
    suspend fun execute(payload: UniversalIntentPayload): ActionResult
}
