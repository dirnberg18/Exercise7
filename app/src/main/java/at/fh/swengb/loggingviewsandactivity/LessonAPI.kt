package at.fh.swengb.loggingviewsandactivity

import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object LessonApi {
    const val baseurl = "https://lessons.bloder.xyz"
    const val accessToken = "2dd23246-c955-4d69-b331-845d00e7f83a"
    val retrofit: Retrofit
    val retrofitService: LessonApiService
    init {
        val moshi = Moshi.Builder().build()
        retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://lessons.bloder.xyz")
            .build()
        retrofitService = retrofit.create(LessonApiService::class.java)
    }
}
interface LessonApiService {

    @GET("/${LessonApi.accessToken}/lessons")
    fun lessons(): Call<List<Lesson>>

    @POST("/${LessonApi.accessToken}/lessons/{id}/rate")
    fun rateLesson(@Path("id") lessonId: String, @Body rating: LessonRating): Call<Unit>

    @GET("${LessonApi.baseurl}/${LessonApi.accessToken}/lessons/{id}")
    fun lessonById(@Query("id") lessonId: String): Call<Lesson>

}