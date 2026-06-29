package com.aetherium.genesis.domain.intent

// ตัวชี้เป้าหมายที่ไม่ผูกมัดกับโครงสร้างข้อมูลของ OS ใด ๆ
data class TargetDescriptor(
    val id: String,                  // อัตลักษณ์อ้างอิงคงที่ (เช่น UUID หรือ Hash)
    val logicalPath: String?,        // เส้นทางเชิงตรรกะ (เช่น "/Documents/Report.pdf")
    val targetType: TargetType
)
