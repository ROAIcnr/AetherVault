package com.aetherium.genesis.domain.permission

import com.aetherium.genesis.domain.intent.TargetDescriptor

// แพลตฟอร์มปลายทาง (Android/Linux) จะต้องมา Implement อินเตอร์เฟสนี้แยกกัน
interface PermissionManager {
    fun checkPhysicalPermission(target: TargetDescriptor?): PermissionStatus
}
