package com.ieum.data.network.model.user

import kotlinx.serialization.Serializable

interface BaseProfileDto {
    val id: Int
    val oauthProvider: String
    val userType: String
    val nickname: String
    val sex: String?
    val diagnoses: List<DiagnoseDto>?
    val surgery: List<SurgeryDto>?
    val chemotherapy: List<ChemotherapyDto>?
    val radiationTherapy: List<RadiationTherapyDto>?
    val ageGroup: String?
    val residenceArea: String?
    val hospitalArea: String?
    val sexVisible: Boolean
    val diagnosesVisible: Boolean
    val surgeryVisible: Boolean
    val chemotherapyVisible: Boolean
    val radiationTherapyVisible: Boolean
    val ageGroupVisible: Boolean
    val residenceAreaVisible: Boolean
    val hospitalAreaVisible: Boolean
}

@Serializable
data class MyProfileDto(
    override val id: Int,
    val email: String,
    override val oauthProvider: String,
    override val userType: String,
    override val nickname: String,
    override val sex: String,
    override val diagnoses: List<DiagnoseDto>,
    override val surgery: List<SurgeryDto>?,
    override val chemotherapy: List<ChemotherapyDto>?,
    override val radiationTherapy: List<RadiationTherapyDto>?,
    override val ageGroup: String?,
    override val residenceArea: String?,
    override val hospitalArea: String?,
    override val sexVisible: Boolean,
    override val diagnosesVisible: Boolean,
    override val surgeryVisible: Boolean,
    override val chemotherapyVisible: Boolean,
    override val radiationTherapyVisible: Boolean,
    override val ageGroupVisible: Boolean,
    override val residenceAreaVisible: Boolean,
    override val hospitalAreaVisible: Boolean,
) : BaseProfileDto

@Serializable
data class OthersProfileDto(
    override val id: Int,
    override val oauthProvider: String,
    override val userType: String,
    override val nickname: String,
    override val sex: String?,
    override val diagnoses: List<DiagnoseDto>?,
    override val surgery: List<SurgeryDto>?,
    override val chemotherapy: List<ChemotherapyDto>?,
    override val radiationTherapy: List<RadiationTherapyDto>?,
    override val ageGroup: String?,
    override val residenceArea: String?,
    override val hospitalArea: String?,
    override val sexVisible: Boolean,
    override val diagnosesVisible: Boolean,
    override val surgeryVisible: Boolean,
    override val chemotherapyVisible: Boolean,
    override val radiationTherapyVisible: Boolean,
    override val ageGroupVisible: Boolean,
    override val residenceAreaVisible: Boolean,
    override val hospitalAreaVisible: Boolean
) : BaseProfileDto