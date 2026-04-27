## 이음

'이음'은 암환자 치유 커뮤니티입니다. <br>
환자의 기분이나 상태를 기록하고 공유하는 것만으로 치료에 유의미한 도움이 되고, 서비스로 환자들을 지원하기 위해 시작했습니다.<br>
환자의 치료 기록과 일상 기록을 공유하고, 캘린더로 상태를 관리할 수 있는 서비스입니다. <br>
팀 프로젝트로 6인 팀(PM, BE, Android, iOS, Admin, 디자인)에서 기획, Android 앱 개발 및 배포를 담당했습니다. <br>
<br>

### 한 눈에 보기

|<img height="600" alt="ieum_login" src="https://github.com/user-attachments/assets/e2b675e1-5b41-4535-956c-1fbe4cb241a9" />|<img height="600" alt="ieum_feed" src="https://github.com/user-attachments/assets/6c0a00a8-9eb8-4a8e-a6ca-da1f0952850f" />|<img height="600" alt="ieum_posting" src="https://github.com/user-attachments/assets/5238e876-c446-430f-bb29-13c3fd702d33" />|<img height="600" alt="ieum_calendar" src="https://github.com/user-attachments/assets/f2b7a817-dc54-4021-92cb-4e823cc3abdf" />|
|:-:|:-:|:-:|:-:|
|로그인|피드|치료 기록 작성|캘린더|

<br>

### 아키텍처
<img width="1440" height="1024" alt="아키텍처" src="https://github.com/user-attachments/assets/bd0746f8-e77b-447d-a966-2115bade37d0" />

<br>

### 기술 스택

| 카테고리 | 스택 |
| :--- | :--- |
| **Language** | Kotlin |
| **Asynchronous** | Coroutines, Flow |
| **Project Architecture** | Clean App Architecture, Multi Module |
| **Jetpack** | Compose, ViewModel, Navigation, DataStore, Paging3, Room |
| **DI** | Hilt |
| **Network** | Ktor |

<br>

### 주요 구현 사항

#### 이미지 압축 로직 개선
- 평균 5Mb의 갤러리 이미지 파일 전송 시, 서버 응답 시간이 2초 이상 지연되는 문제가 발생
- 이미지 크기와 사용될 UI 크기를 기반으로 비율을 계산해 Bitmap 다운샘플링 적용
- 다운샘플링 후 발생한 회전 문제를 해결하기 위해 메타데이터 방향 정보를 얻어 역회전 적용
- 메인 스레드 Block 문제를 해결하기 위해 각 로직을 적합한 스레드(IO, Default)에 할당
- Bimap 사용이 끝난 시점(전송 성공, 예외 발생)에 메모리에서 해제해서 OOM 방지
- 5Mb 이미지를 300Kb 수준으로 줄이고, 서버 응답 시간을 2초에서 500ms 이내로 단축

<a href="https://github.com/SeungWoo-Ahn/IEUM/blob/main/presentation/src/main/java/com/ieum/presentation/util/ImageUtil.kt">ImageUtil.kt</a>
```kotlin
@Singleton
class ImageUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun compressUriToFile(uri: Uri, reqWidth: Int, reqHeight: Int): Result<File> =
        withContext(Dispatchers.IO) {
            runCatching {
                val downSampledBitmap = getDownSampledBitmap(uri, reqWidth, reqHeight).getOrThrow()
                val correctedBitmap = rotateBitmapIfRequired(downSampledBitmap, uri)
                    .getOrElse { t ->
                        downSampledBitmap.recycle()
                        throw t
                    }
                val (suffix, compressFormat) = getSuffixAndCompressFormatByVersion()
                val tempFile = createTempImageFile(suffix)
                    .apply { deleteOnExit() }
                compressBitmapToFile(correctedBitmap, tempFile, compressFormat)
                    .also {
                        downSampledBitmap.recycle()
                        correctedBitmap.recycle()
                    }
                    .getOrThrow()
                tempFile
            }
        }
...
}
```
<br>

### Paging 데이터 RoomDB 캐시
- readOnly인 Paging 데이터에 ‘좋아요' 등 상호작용이 생길 때마다 refresh해야 하는 문제가 발생
- 검색 필터에 따라 Paging 데이터가 달라지기 때문에 RoomDB를 사용하되 캐시로 활용하기로 결정
- RemoteMediator를 사용해 LoadType에 따라 동작을 분리하고, 트랜잭션을 통해 데이터 수정 적용
- ‘좋아요' 기능은 ‘낙관적 업데이트'를 적용해 캐시를 수정하고, API 응답 결과에 따라 롤백 적용
- Paging 데이터의 refresh 없이 상호작용이 가능하고, RoomDB에 데이터가 모여 관리가 용이

<a href="https://github.com/SeungWoo-Ahn/IEUM/blob/main/data/src/main/java/com/ieum/data/repository/PostRepositoryImpl.kt">PostRepositoryImpl.kt</a>
```kotlin
@Singleton
class PostRepositoryImpl @Inject constructor(
    private val db: IeumDatabase,
    private val postDataSource: PostDataSource,
    private val postDao: PostDao,
    private val commentDao: CommentDao,
) : PostRepository {
  override suspend fun postWellness(request: PostWellnessRequest) {
      postDataSource
          .postWellness(
              body = request.asBody(),
              fileList = request.imageList.map(ImageSource.Local::file)
          )
          .also {
              postDao.insert(it.toEntity())
          }
  }

  @OptIn(ExperimentalPagingApi::class)
  override fun getAllPostListFlow(
      diagnosis: Diagnosis?,
      getMyId: suspend () -> Result<Int>
  ): Flow<PagingData<Post>> =
      Pager(
          config = PagingConfig(pageSize = 10, enablePlaceholders = false),
          pagingSourceFactory = { postDao.getAllPostPagingSource(diagnosis?.key) },
          remoteMediator = AllPostMediator(
              db = db,
              getMyId = getMyId,
              getAllPostList = { page, size ->
                  postDataSource.getAllPostList(
                      page = page,
                      size = size,
                      diagnosis = diagnosis?.key
                  )
              },
              deleteAllPostList = postDao::deleteAllPostList,
              insertAll = postDao::insertAll,
          )
      )
          .flow
          .map { pagingData ->
              pagingData.map(PostEntity::toDomain)
          }
}
```
<br>

### 커스텀 캘린더
- 필터에 따라 캘린더 내 아이콘이 바뀌는 요구사항이 존재해 커스텀 캘린더가 필요
- abstract class 기반 다형성 모델로 SDK 버전에 따라 적합한 date 기능을 제공
- UI로는 HorizontalPager를 선택하고, 페이지 수로 yearRange를 사용, index로 월별 정보 역산
- pagerState.currentPage를 snapshotFlow로 collect 후에 콜백 함수로 API 요청
- List로 제공되는 API 데이터를 groupBy와 UIModel을 사용해서 일별 데이터, 월별 요약 기능 제공
 
<a href="https://github.com/SeungWoo-Ahn/IEUM/blob/main/presentation/src/main/java/com/ieum/presentation/model/calendar/CalendarModel.kt">CalendarModel.kt</a>
```kotlin
abstract class CalendarModel {
    abstract val today: CalendarDate

    abstract val firstDayOfWeek: Int

    val yearRange: IntRange get() = 2025..today.year

    val currentMonth: CalendarMonth get() = getMonth(today.year, today.month)

    abstract fun getMonth(year: Int, month: Int): CalendarMonth

    abstract fun plusMonths(from: CalendarMonth, addedMonthsCount: Int): CalendarMonth
}

fun createCalendarModel(locale: CalendarLocale): CalendarModel {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        CalendarModelImpl(locale)
    } else {
        LegacyCalendarModelImpl(locale)
    }
}
```

<a href="https://github.com/SeungWoo-Ahn/IEUM/blob/main/presentation/src/main/java/com/ieum/presentation/screen/main/home/calendar/CalendarUiState.kt">CalendarUiState.kt</a>
```kotlin
data class CalendarUiState(
    val displayedMonth: CalendarMonth,
    val selectedFilter: CalendarFilter,
    private val wellnessList: List<CalendarWellnessUiModel>,
) {
    val dateUiStateByDayOfMonth: Map<Int, CalendarDateUiState> =
        wellnessList
            .groupBy { it.dayOfMonth }
            .mapValues { (_, list) ->
                CalendarDateUiState(wellnessList = list)
            }

    val monthSummaryUiState: CalendarMonthSummaryUiState =
        CalendarMonthSummaryUiState(
            dayOfMonth = displayedMonth.numberOfDays,
            postDayCount = dateUiStateByDayOfMonth.size,
            averageMood = wellnessList.map { it.mood }.calcAverage()
        )

    companion object {
        fun getIdleState(currentMonth: CalendarMonth): CalendarUiState =
            CalendarUiState(
                displayedMonth = currentMonth,
                selectedFilter = CalendarFilter.WELLNESS,
                wellnessList = emptyList(),
            )
    }
}
```
<br>

### 전역 Exception 처리
- API 호출, DataStore, Room 등 다양한 상황에서 예외가 발생할 수 있고 관리가 필요
- UseCase의 비즈니스 로직에서 runCatching을 통해 예외를 안전하게 wrapping
- 싱글톤 클래스에 Channel로 발생하는 예외를 수집하고 Timber에 로깅
- API 응답 실패 시, ktor handleResponseException에서 커스텀 예외로 변환했고, Channel에 수집된 예외를 when 분기로 적절한 메세지로 변환
- Toast로 사용자에게 메세지를 전달하고, 에러 로그는 firebase crashlytics에 전달돼 추적 가능

<a href="https://github.com/SeungWoo-Ahn/IEUM/blob/main/data/src/main/java/com/ieum/data/network/di/NetworkModule.kt">NetworkModule.kt</a>
```kotlin
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
  @Provides
  @Singleton
  @NetworkSource(IEUMNetwork.Default)
  fun providesDefaultClient(
      preferenceRepository: PreferenceRepository,
  ): HttpClient =
      createKtorClient().config {
          expectSuccess = true
          defaultRequest {
              url(BuildConfig.BASE_URL)
              header(HttpHeaders.ContentType, ContentType.Application.Json)
          }
          HttpResponseValidator {
              handleResponseException { cause, _ ->
                  when (cause) {
                      is IOException -> throw NetworkException.ConnectionException()
                      is ResponseException -> {
                          val errorResponse = cause.response.body<ErrorResponse>()
                          val message = errorResponse.details?.firstOrNull()?.message
                              ?: errorResponse.message
                          throw NetworkException.ResponseException(message)
                      }
                      else -> throw NetworkException.UnknownException(cause)
                  }
              }
          }
...
}
```
<br>

<a href="https://github.com/SeungWoo-Ahn/IEUM/blob/main/presentation/src/main/java/com/ieum/presentation/util/ExceptionCollector.kt">ExceptionCollector.kt</a>
```kotlin
object ExceptionCollector {
    private val exceptionChannel = Channel<Throwable>(Channel.BUFFERED)
    val exceptionMessageFlow: Flow<String> =
        exceptionChannel
            .receiveAsFlow()
            .map {
                when (it) {
                    is NetworkException -> {
                        when (it) {
                            is NetworkException.ConnectionException -> "네트워크 연결이 불안정합니다"
                            is NetworkException.ResponseException -> it.message
                            is NetworkException.UnknownException -> "알 수 없는 네트워크 문제가 발생했습니다"
                        }
                    }
                    is CustomException -> it.message
                    else -> "알 수 없는 문제가 발생했습니다"
                }
            }

    suspend fun sendException(t: Throwable) {
        exceptionChannel.send(t)
        Timber.e(t)
    }
}
```

