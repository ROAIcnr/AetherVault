package com.aetherium.genesis.domain.intent

// Universal Command ขนานแท้: บริสุทธิ์และสามารถส่งผ่านเครือข่ายข้ามแพลตฟอร์มได้
data class UniversalIntentPayload(
    val intentId: String,
    val actionType: ActionType,
    val source: TargetDescriptor?,
    val destination: TargetDescriptor?,
    val parameters: Map<String, String> = emptyMap()
)
