package com.aetherium.genesis.domain.intent

enum class TargetType {
    LOCAL_FILE,       // ไฟล์ภายในเครื่อง (Internal / SD Card)
    LOCAL_DIR,        // โฟลเดอร์ภายในเครื่อง
    VIRTUAL_SANDBOX,  // พื้นที่กักกันจำลอง (Shadow Area)
    REMOTE_STREAM,    // อนาคต: ข้อมูลผ่านเน็ตเวิร์ก/Cloud
    SYSTEM_RESERVED   // พื้นที่หวงห้ามระดับรากฐาน
}
