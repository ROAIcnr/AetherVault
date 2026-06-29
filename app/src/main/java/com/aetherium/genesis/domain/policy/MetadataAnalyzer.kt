package com.aetherium.genesis.domain.policy

import com.aetherium.genesis.domain.intent.UniversalIntentPayload

// ตัววิเคราะห์เจตจำนงและบริบทเพื่อประเมินความเสี่ยงเชิงความหมาย (Semantic Risk)
class MetadataAnalyzer {
    fun assessDanger(payload: UniversalIntentPayload): DangerLevel {
        // ประเมินจากพารามิเตอร์ระบบและพาร์ทเชิงตรรกะ ไม่ขึ้นตรงกับคำว่า "DCIM" หรือ "Android"
        val isSystemScope = payload.source?.logicalPath?.startsWith("/system_root") == true
        val isMassAction = payload.parameters["scope"] == "MASS_EXECUTION"

        return when {
            payload.actionType.name == "DELETE" && isSystemScope -> DangerLevel.CRITICAL
            payload.actionType.name == "DELETE" && isMassAction  -> DangerLevel.HIGH
            payload.actionType.name == "RENAME" && isSystemScope -> DangerLevel.MEDIUM
            else -> DangerLevel.LOW
        }
    }
}
