package com.example.medimate.API

import com.example.medimate.API.response.AddOrder
import com.example.medimate.API.response.AddToAvailableProducts
import com.example.medimate.API.response.GetAllOrderDetails
import com.example.medimate.API.response.GetAllProduct
import com.example.medimate.API.response.GetAllUser
import com.example.medimate.API.response.GetAvailableProductsByUserId
import com.example.medimate.API.response.GetSellHistoryByUserId
import com.example.medimate.API.response.GetSpecificUser
import com.example.medimate.API.response.Login
import com.example.medimate.API.response.Sell
import com.example.medimate.API.response.UpdateAvailableProduct
import com.example.medimate.API.response.UpdateProductStock
import com.example.medimate.API.response.UpdateUserRes
import com.example.medimate.API.response.UserCreateResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
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
    @POST("/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):Response<Login>

    @GET("/getAllUsers")
    suspend fun getAllUser() : Response<GetAllUser>

    @FormUrlEncoded
    @HTTP(method = "PATCH", path = "updateUserAllDetails", hasBody = true)
    suspend fun updateUserAllDetails(
        @Field("user_id") userId: String,
        @Field("name") name: String? = null,
        @Field("password") password: String? = null,
        @Field("address") address: String? = null,
        @Field("email") email: String? = null,
        @Field("phone_number") phoneNumber: String? = null
    ) : Response<UpdateUserRes>


    @FormUrlEncoded
    @POST("/getSpacificUser")
    suspend fun getSpecificUser(
        @Field("user_id") user_id: String
    ): Response<GetSpecificUser>

    @GET("/getAllProduct")
    suspend fun getAllProduct(): Response<GetAllProduct>

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

    @FormUrlEncoded
    @PATCH("/updateAvailableProducts")
    suspend fun updateAvailableProducts(
        @Field("product_id") product_id: String,
        @Field("stock") stock: Int,
    ):Response<UpdateAvailableProduct>

    @FormUrlEncoded
    @POST("/getAvailableProductsByUserId")
    suspend fun getAvailableProductsByUserId(
        @Field("user_id") user_id: String
    ): Response<GetAvailableProductsByUserId>

    @FormUrlEncoded
    @POST("/craeteSellHistory")
    suspend fun Sell(
        @Field("product_id") product_id: String,
        @Field("quantity") quantity: String,
        @Field("remaining_stock") remaining_stock: String,
        @Field("total_amount") total_amount: String,
        @Field("price") price: String,
        @Field("product_name") product_name: String,
        @Field("user_name") user_name: String,
        @Field("user_id") user_id: String,
    ): Response<Sell>

    @FormUrlEncoded
    @POST("/getSellHistoryByUserId")
    suspend fun sellHistory(
        @Field("user_id") userId : String
    ): Response<GetSellHistoryByUserId>



    @GET("/getAllOrdersDetail")
    suspend fun getAllOrderDetails(): Response<GetAllOrderDetails>

    companion object {
        const val BASE_URL = "https://faysalabir779.pythonanywhere.com"
    }

}