package at.fh.swengb.loggingviewsandactivity

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object LessonApi {
    const val accessToken = "your_access_token"
    val retrofit: Retrofit
    val retrofitService: LessonApiService
    init {
        val moshi = Moshi.Builder().build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("your_base_url")
            .build()
        retrofitService = retrofit.create(LessonApiService::class.java)
    }
}
interface LessonApiService {
    @GET("/${LessonApi.accessToken}/lessons")
    fun lessons(): Call<List<Lesson>>
    @POST("/${LessonApi.accessToken}/lessons/{id}/rate")
    fun rateLesson(@Path("id") lessonId: String, @Body rating: LessonRating): Call<Unit>
}