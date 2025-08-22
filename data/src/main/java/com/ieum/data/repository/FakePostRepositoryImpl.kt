package com.ieum.data.repository

import com.ieum.domain.model.image.ImageSource
import com.ieum.domain.model.post.Dietary
import com.ieum.domain.model.post.DietaryStatus
import com.ieum.domain.model.post.Post
import com.ieum.domain.model.post.PostDailyRecordsRequest
import com.ieum.domain.model.post.PostTreatmentRecordsRequest
import com.ieum.domain.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakePostRepositoryImpl @Inject constructor() : PostRepository {
    private val postList = MutableStateFlow(
        listOf(
            Post.TreatmentRecords(
                id = 0,
                specificSymptoms = "식사 후 속쓰림과 소화불량이 느껴짐.",
                takingMedicine = true,
                dietary = Dietary(
                    status = DietaryStatus.EAT_WELL,
                    content = "점심에 샐러드와 닭가슴살을 먹음. 저녁은 잡곡밥에 생선구이."
                ),
                memo = "처방받은 약을 먹으니 조금 나아졌다.",
                imageList = listOf(
                    ImageSource.Remote(url = "https://images.unsplash.com/photo-1546069901-ba9599a7e63c"),
                ),
                shareCommunity = true
            ),
            Post.DailyRecords(
                id = 1,
                title = "오후 산책",
                story = "오늘 오후에 날씨가 너무 좋아서 집 앞 공원을 한 시간 정도 걸었다. 몸이 가벼워지는 기분이다.",
                imageList = listOf(
                    ImageSource.Remote(url = "https://www.visitbusan.net/uploadImgs/files/cntnts/20231026100048315_wufrotr"),
                    ImageSource.Remote(url = "https://tour.jeonju.go.kr/upload_data/board_data/BBS_0000003/171714437330845.jpg"),
                ),
                shareCommunity = true
            ),
            Post.TreatmentRecords(
                id = 2,
                specificSymptoms = "특별한 증상은 없으나 몸이 무겁고 피로함.",
                takingMedicine = false,
                dietary = Dietary(
                    status = DietaryStatus.EAT_SMALL_AMOUNTS,
                    content = "식욕이 없어서 아침에 토스트 한 조각만 먹고 거의 공복 유지."
                ),
                memo = null, // 메모가 없는 경우
                imageList = emptyList(),
                shareCommunity = false
            ),
            Post.DailyRecords(
                id = 3,
                title = "오늘의 일상",
                story = "오전에는 병원 정기 검진을 다녀왔고, 오후에는 밀린 서류 작업을 마무리했다.",
                imageList = emptyList(), // 이미지가 없는 경우
                shareCommunity = false
            ),
        )
    )

    override fun getPostList(): StateFlow<List<Post>> {
        return postList.asStateFlow()
    }

    override suspend fun postTreatmentRecords(request: PostTreatmentRecordsRequest) {
        postList.update { originList ->
            originList + with(request) {
                Post.TreatmentRecords(
                    id = originList.size,
                    specificSymptoms = specificSymptoms,
                    takingMedicine = takingMedicine,
                    dietary = dietary,
                    memo = memo,
                    imageList = imageList,
                    shareCommunity = shareCommunity,
                )
            }
        }
    }

    override suspend fun postDailyRecords(request: PostDailyRecordsRequest) {
        postList.update { originList ->
            originList + with(request) {
                Post.DailyRecords(
                    id = originList.size,
                    title = title,
                    story = story,
                    imageList = imageList,
                    shareCommunity = shareCommunity,
                )
            }
        }
    }
}