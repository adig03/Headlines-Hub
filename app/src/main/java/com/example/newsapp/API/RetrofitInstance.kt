import com.example.newsapp.API.NewsApi
import com.example.newsapp.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {


    // Lazy initialization to create a Retrofit instance only when needed
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // Base URL of your API
            .addConverterFactory(GsonConverterFactory.create())  // Use Gson for JSON conversion
            .build()
    }

 val api by lazy{
     retrofit.create(NewsApi::class.java)
 }

}