package com.aetherium.genesis.domain.permission

// โครงสร้างประเภทผลลัพธ์ข้อมูลส่งกลับสากล (Sealed Action Result Matrix)
sealed class ActionResult {
    data class Success(val message: String, val affectedItems: Int = 1) : ActionResult()
    data class Denied(val rationale: String, val isPolicyFailure: Boolean) : ActionResult()
    data class NeedConfirmation(val token: String, val warningMessage: String) : ActionResult()
    data class Progress(val percentage: Float, val currentTask: String) : ActionResult()
    data class Failed(val exception: Throwable, val contextMessage: String) : ActionResult()
}
