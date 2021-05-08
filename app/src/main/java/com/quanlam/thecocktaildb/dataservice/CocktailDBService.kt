package com.quanlam.thecocktaildb.dataservice

import com.quanlam.thecocktaildb.dataservice.model.CocktailSearchResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val VERSION = "v1"
const val API_KEY = "1"
const val BASE_URL = "https://www.thecocktaildb.com/api/json/$VERSION/$API_KEY/"
interface CocktailDBService {
    @GET("search.php")
    fun getAlphabetCocktails(
        @Query("f") firstLetter: Char
    ): Observable<CocktailSearchResponse>

    @GET("search.php")
    fun searchCocktailByName(
        @Query("s") name: String,
    ): Observable<CocktailSearchResponse>

    @GET("lookup.php")
    fun getCocktailById(
        @Query("i") id: String,
    ): Observable<CocktailSearchResponse>

    companion object {
        private var api: CocktailDBService? = null
        fun getAPI(): CocktailDBService {
            if (api == null) {
                val retrofit: Retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                api = retrofit.create(CocktailDBService::class.java)
            }
            return api!!
        }
    }
}