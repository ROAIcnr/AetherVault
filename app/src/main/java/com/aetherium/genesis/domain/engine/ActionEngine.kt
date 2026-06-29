package com.aetherium.genesis.domain.engine

import com.aetherium.genesis.domain.intent.UniversalIntentPayload
import com.aetherium.genesis.domain.permission.ActionResult
import com.aetherium.genesis.domain.permission.PermissionManager
import com.aetherium.genesis.domain.permission.PermissionStatus
import com.aetherium.genesis.domain.policy.PolicyDecision
import com.aetherium.genesis.domain.policy.PolicyEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActionEngine(
    private val policyEngine: PolicyEngine,
    private val permissionManager: PermissionManager,
    private val commandRegistry: Map<String, SystemCommand> // เก็บคำสั่งในรูปแบบ Map Container
) {
    // ฟังก์ชันทำหน้าที่เป็นเพียงตัวกระจายและจัดลำดับทิศทางงาน (Pure Dispatcher Context)
    fun processIntent(payload: UniversalIntentPayload): Flow<ActionResult> = flow {
        emit(ActionResult.Progress(0.0f, "Analyzing System Policy..."))

        // 1. ตรวจสอบนโยบายความเสี่ยง (Policy Check)
        when (policyEngine.enforce(payload)) {
            PolicyDecision.DENIED_DANGEROUS_ACTION -> {
                emit(ActionResult.Denied("Policy Veto: Operation blocked due to high system risk.", isPolicyFailure = true))
                return@flow
            }
            PolicyDecision.NEED_USER_CONFIRMATION -> {
                emit(ActionResult.NeedConfirmation(payload.intentId, "Action requires manual user verification."))
                return@flow
            }
            PolicyDecision.ALLOWED -> { /* ผ่านได้ */ }
        }

        emit(ActionResult.Progress(0.5f, "Verifying OS Platform Permissions..."))

        // 2. ตรวจสอบสิทธิ์ระดับฮาร์ดแวร์/OS ปลายทาง (Physical Permission Check)
        if (permissionManager.checkPhysicalPermission(payload.source) == PermissionStatus.DENIED_BY_OS) {
            emit(ActionResult.Denied("OS Error: Storage access permission denied by platform kernel.", isPolicyFailure = false))
            return@flow
        }

        emit(ActionResult.Progress(0.8f, "Executing command pipeline..."))

        // 3. ค้นหาคำสั่งที่ลงทะเบียนไว้ตามประเภทการกระทำ (Command Dispatching)
        val commandKey = payload.actionType.name
        val command = commandRegistry[commandKey]

        if (command != null) {
            val result = command.execute(payload)
            emit(result)
        } else {
            emit(ActionResult.Failed(IllegalArgumentException("Command Unmapped"), "Action Type $commandKey has no registered execution engine."))
        }
    }
}
