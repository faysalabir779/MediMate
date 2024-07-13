package com.example.medimate.API

import com.example.medimate.API.response.AddOrder
import com.example.medimate.API.response.AddToAvailableProducts
import com.example.medimate.API.response.AllProduct
import com.example.medimate.API.response.GetAllAvailableProducts
import com.example.medimate.API.response.GetAllOrderDetails
import com.example.medimate.API.response.GetSpecificUser
import com.example.medimate.API.response.UserCreateResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface API_Builder {

    @FormUrlEncoded
    @POST("/createUser")
    suspend fun createUser(
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("phone_info") phone_info: String,
        @Field("address") address: String,
        @Field("phone_number") phone_number: String,
        @Field("pinCode") pinCode: String
    ): Response<UserCreateResponse?>


    @FormUrlEncoded
    @POST("/getSpacificUser")
    suspend fun getSpecificUser(
        @Field("user_id") user_id: String
    ): Response<GetSpecificUser>

    @GET("/getAllProduct")
    suspend fun getAllProduct(): Response<AllProduct>

    @FormUrlEncoded
    @POST("/addOrderDetails")
    suspend fun addOrder(
        @Field("product_id") product_id: String,
        @Field("user_id") user_id: String,
        @Field("product_name") product_name: String,
        @Field("user_name") user_name: String,
        @Field("total_amount") total_amount: String,
        @Field("quantity") quantity: String,
        @Field("message") message: String,
        @Field("price") price: String,
        @Field("certified") certified: String,
        @Field("category") category: String,
    ): Response<AddOrder>



    @GET("/getAvailableProducts")
    suspend fun getAvailableProducts(): Response<GetAllAvailableProducts>

    @GET("/getAllOrdersDetail")
    suspend fun getAllOrderDetails(): Response<GetAllOrderDetails>

    companion object {
        const val BASE_URL = "https://faysalabir779.pythonanywhere.com"
    }

}