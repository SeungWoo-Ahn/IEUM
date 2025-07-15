package com.ieum.data.mapper

import com.ieum.data.network.model.user.DiagnoseDto
import com.ieum.data.network.model.user.RegisterRequestBody
import com.ieum.data.network.model.user.UserDto
import com.ieum.domain.model.user.AgeGroup
import com.ieum.domain.model.user.CancerStage
import com.ieum.domain.model.user.ColonCancer
import com.ieum.domain.model.user.Diagnose
import com.ieum.domain.model.user.LiverTransplant
import com.ieum.domain.model.user.Others
import com.ieum.domain.model.user.RegisterRequest
import com.ieum.domain.model.user.RentalCancer
import com.ieum.domain.model.user.User
import com.ieum.domain.model.user.UserType

internal object UserTypeMapper : KeyAble<String, UserType> {
    private const val PATIENT_KEY = "patient"
    private const val CAREGIVER_KEY = "caregiver"

    override fun toKey(value: UserType): String {
        return when (value) {
            UserType.PATIENT -> PATIENT_KEY
            UserType.CAREGIVER -> CAREGIVER_KEY
        }
    }

    override fun fromKey(key: String): UserType {
        return when (key) {
            PATIENT_KEY -> UserType.PATIENT
            CAREGIVER_KEY -> UserType.CAREGIVER
            else -> throw IllegalArgumentException("Invalid UserType key: $key")
        }
    }
}

internal object CancerStageMapper : KeyAble<String, CancerStage> {
    private const val STAGE_1_KEY = "stage1"
    private const val STAGE_2_KEY = "stage2"
    private const val STAGE_3_KEY = "stage3"
    private const val STAGE_4_KEY = "stage4"

    override fun toKey(value: CancerStage): String {
        return when (value) {
            CancerStage.STAGE_1 -> STAGE_1_KEY
            CancerStage.STAGE_2 -> STAGE_2_KEY
            CancerStage.STAGE_3 -> STAGE_3_KEY
            CancerStage.STAGE_4 -> STAGE_4_KEY
        }
    }

    override fun fromKey(key: String): CancerStage {
        return when (key) {
            STAGE_1_KEY -> CancerStage.STAGE_1
            STAGE_2_KEY -> CancerStage.STAGE_2
            STAGE_3_KEY -> CancerStage.STAGE_3
            STAGE_4_KEY -> CancerStage.STAGE_4
            else -> throw IllegalArgumentException("Invalid CancerStage key: $key")
        }
    }
}

internal object DiagnoseMapper : KeyAble<DiagnoseDto, Diagnose> {
    private const val RENTAL_CANCER_KEY = "rental_cancer"
    private const val COLON_CANCER_KEY = "colon_cancer"
    private const val LIVER_TRANSPLANT_KEY = "liver_transplant"
    private const val OTHERS_KEY = "others"

    override fun toKey(value: Diagnose): DiagnoseDto {
        return when (value) {
            is RentalCancer -> DiagnoseDto(
                name = RENTAL_CANCER_KEY,
                cancerStage = value.cancerStage?.let { CancerStageMapper.toKey(it) }
            )
            is ColonCancer -> DiagnoseDto(
                name = COLON_CANCER_KEY,
                cancerStage = value.cancerStage?.let { CancerStageMapper.toKey(it) }
            )
            LiverTransplant -> DiagnoseDto(
                name = LIVER_TRANSPLANT_KEY,
                cancerStage = null
            )
            Others -> DiagnoseDto(
                name = OTHERS_KEY,
                cancerStage = null
            )
        }
    }

    override fun fromKey(key: DiagnoseDto): Diagnose {
        return when (key.name) {
            RENTAL_CANCER_KEY -> RentalCancer(
                cancerStage = key.cancerStage?.let { CancerStageMapper.fromKey(it) }
            )
            COLON_CANCER_KEY -> ColonCancer(
                cancerStage = key.cancerStage?.let { CancerStageMapper.fromKey(it) }
            )
            LIVER_TRANSPLANT_KEY -> LiverTransplant
            OTHERS_KEY -> Others
            else -> throw IllegalArgumentException("Invalid Diagnose key: ${key.name}")
        }
    }
}

internal object AgeGroupMapper : KeyAble<String, AgeGroup> {
    private const val UNDER_THIRTY_KEY = "under30"
    private const val FORTIES_KEY = "40s"
    private const val FIFTIES_KEY = "50s"
    private const val SIXTIES_KEY = "60s"
    private const val OVER_SEVENTY_KEY = "over70"

    override fun toKey(value: AgeGroup): String {
        return when (value) {
            AgeGroup.UNDER_THIRTY -> UNDER_THIRTY_KEY
            AgeGroup.FORTIES -> FORTIES_KEY
            AgeGroup.FIFTIES -> FIFTIES_KEY
            AgeGroup.SIXTIES -> SIXTIES_KEY
            AgeGroup.OVER_SEVENTY -> OVER_SEVENTY_KEY
        }
    }

    override fun fromKey(key: String): AgeGroup {
        return when (key) {
            UNDER_THIRTY_KEY -> AgeGroup.UNDER_THIRTY
            FORTIES_KEY -> AgeGroup.FORTIES
            FIFTIES_KEY -> AgeGroup.FIFTIES
            SIXTIES_KEY -> AgeGroup.SIXTIES
            OVER_SEVENTY_KEY -> AgeGroup.OVER_SEVENTY
            else -> throw IllegalArgumentException("Invalid AgeGroup key: $key")
        }
    }
}

fun RegisterRequest.asBody(): RegisterRequestBody =
    RegisterRequestBody(
        userType = UserTypeMapper.toKey(userType),
        nickname = nickName,
        diagnoses = diagnoses.map(DiagnoseMapper::toKey),
        ageGroup = ageGroup?.let { AgeGroupMapper.toKey(it) },
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )

fun UserDto.toDomain(): User =
    User(
        id = userId,
        oAuthProvider = OAuthProviderMapper.fromKey(oauthProvider),
        email = email,
        userType = UserTypeMapper.fromKey(userType),
        nickName = nickname,
        diagnoses = diagnoses.map(DiagnoseMapper::fromKey),
        ageGroup = ageGroup?.let { AgeGroupMapper.fromKey(it) },
        residenceArea = residenceArea,
        hospitalArea = hospitalArea,
    )

