package com.aetherium.genesis.domain.policy

import com.aetherium.genesis.domain.intent.UniversalIntentPayload

class PolicyEngine(private val analyzer: MetadataAnalyzer) {
    fun enforce(payload: UniversalIntentPayload): PolicyDecision {
        val danger = analyzer.assessDanger(payload)
        return when (danger) {
            DangerLevel.CRITICAL -> PolicyDecision.DENIED_DANGEROUS_ACTION
            DangerLevel.HIGH     -> PolicyDecision.NEED_USER_CONFIRMATION
            else                 -> PolicyDecision.ALLOWED
        }
    }
}
