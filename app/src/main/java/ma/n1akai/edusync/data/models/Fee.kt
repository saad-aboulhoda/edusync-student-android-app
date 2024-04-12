package ma.n1akai.edusync.data.models

data class Fee(
    val fee_id: Int,
    val student: Int,
    val fee_description: String,
    val total_fee: Double,
    val fee_date: String,
    val is_paid: Int
)