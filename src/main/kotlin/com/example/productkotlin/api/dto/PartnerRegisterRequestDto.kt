package com.example.productkotlin.api.dto

import jakarta.validation.constraints.NotBlank

data class PartnerRegisterRequestDto (
    @field:NotBlank(message = "파트너 이름은 필수 입력 값입니다.")
    var partnerName: String,
    @field:NotBlank(message = "파트너 사업자 번호는 필수 입력 값입니다.")
    var partnerCompanyNo: String,
    @field:NotBlank(message = "몰 키는 필수 입력 값입니다.")
    var mallKey: String,
)