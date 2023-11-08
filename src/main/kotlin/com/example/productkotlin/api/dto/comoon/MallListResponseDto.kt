package com.example.productkotlin.api.dto.comoon

class MallListResponseDto : ResponseDto {
    val malls: List<Mall>

    data class Mall(
        val mallId: Long,
        val mallName: String,
    )

    constructor(pageSize: Int, pageNumber: Int, totalCount: Long, malls: List<MallListResponseDto.Mall>) :
        super(pageSize, pageNumber, totalCount) {
        this.malls = malls
    }
}
