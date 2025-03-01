package com.example.medimate

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.medimate.API.response.GetAllOrderDetailsItem
import com.example.medimate.API.response.GetAllProductItem
import com.example.medimate.API.response.GetAllUserItem
import com.example.medimate.API.response.GetAvailableProductsByUserIdItem
import com.example.medimate.API.response.GetSellHistoryByUserIdItem
import com.example.medimate.API.response.GetSpecificUserItem
import com.example.medimate.pref.PreferenceKeys
import com.example.medimate.pref.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_id")

class AllViewModel(context: Context) : ViewModel() {

    var state = mutableStateOf("")

    var data = mutableStateOf<List<GetAllProductItem>>(emptyList())
    var allUser = mutableStateOf<List<GetAllUserItem>>(emptyList())
    var specificUser = mutableStateOf<List<GetSpecificUserItem>>(emptyList())
    var allOrder = mutableStateOf<List<GetAllOrderDetailsItem>>(emptyList())
    var sellHistory = mutableStateOf<List<GetSellHistoryByUserIdItem>>(emptyList())
    var availableProducts = mutableStateOf<List<GetAvailableProductsByUserIdItem>>(emptyList())

    private val dataStore = context.dataStore

    val preferenceData: StateFlow<UserData> = dataStore.data.catch {
        if (it is IOException) {
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map {
        UserData(
            userId = it[PreferenceKeys.USER_ID] ?: "",
            name = it[PreferenceKeys.USER_NAME] ?: "",
            pasword = it[PreferenceKeys.USER_PASSWORD] ?: "",
            email = it[PreferenceKeys.USER_EMAIL] ?: "",
            address = it[PreferenceKeys.USER_ADDRESS] ?: "",
            phone = it[PreferenceKeys.USER_PHONE] ?: "",
            pinCode = it[PreferenceKeys.USER_PINCODE] ?: ""
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UserData()
    )

    init {
        state.value = State.DEFAULT.name
        viewModelScope.launch {
            preferenceData.collect {userData->
                userData.userId?.let {
                    getSpecificUser(it)
                    getAvailableProductsByUserId(it)
                    getSellHistory(it)
                    getAllOrderDetails()
                    getALlProduct()
                    getAllUser()
                }?: run {

                }
            }

        }

    }

    private fun savePref(
        userId: String? = null,
        name: String? = null,
        password: String? = null,
        email: String? = null,
        address: String? = null,
        phone: String? = null,
        pinCode: String? = null
    ) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                userId?.let { preferences[PreferenceKeys.USER_ID] = it }
                name?.let { preferences[PreferenceKeys.USER_NAME] = it }
                password?.let { preferences[PreferenceKeys.USER_PASSWORD] = it }
                email?.let { preferences[PreferenceKeys.USER_EMAIL] = it }
                address?.let { preferences[PreferenceKeys.USER_ADDRESS] = it }
                phone?.let { preferences[PreferenceKeys.USER_PHONE] = it }
                pinCode?.let { preferences[PreferenceKeys.USER_PINCODE] = it }
            }
        }
    }

    fun createUser(
        name: String,
        password: String,
        email: String,
        address: String,
        phone: String,
        pinCode: String
    ) {
        state.value = State.LOADING.name
        viewModelScope.launch {
            val result =
                RetrofitInstance.api.createUser(
                    name,
                    password,
                    email,
                    "123",
                    address,
                    phone,
                    pinCode
                )

            if (result.isSuccessful) {
                if (result.body()?.status == 200) {
                    savePref(
                        result.body()!!.message,
                        name = name,
                        password = password,
                        email = email,
                        address = address,
                        phone = phone,
                        pinCode = pinCode
                    )
                    state.value = State.SUCCESS.name
                } else {
                    state.value = State.FAILED.name
                }
            } else {
                state.value = State.FAILED.name
            }

        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            state.value = State.LOADING.name
            val result = RetrofitInstance.api.login(email, password)
            if (result.isSuccessful) {
                if (result.body()?.status == 200) {
                    val userId = result.body()?.message
                    userId?.let {
                        savePref(userId = it)
                        state.value = State.SUCCESS.name
                    }
                }
                else {
                    state.value = State.FAILED.name
                }
            } else{
                state.value = State.FAILED.name
            }
        }
    }

    fun updateUser(
        userId: String,
        name: String? = null,
        password: String? = null,
        email: String? = null,
        phone: String? = null,
        address: String? = null,
        applicationContext: Context
    ){
        viewModelScope.launch {
            val result = RetrofitInstance.api.updateUserAllDetails(userId, name, password, address, email, phone)
            if (result.isSuccessful){
                if (result.body()?.status == 200){
                    Toast.makeText(applicationContext, "User Update Complete", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getAllUser(){
        viewModelScope.launch {
            val result = RetrofitInstance.api.getAllUser()
            if (result.isSuccessful){
                val dataBody = result.body()!!
                allUser.value = dataBody
            }
        }
    }

    fun getSpecificUser(userId: String) {
        viewModelScope.launch {
            val resultForSpecificUser =
                RetrofitInstance.api.getSpecificUser(userId)

            if (resultForSpecificUser.isSuccessful) {
                val dataBody = resultForSpecificUser.body()!!
                specificUser.value = dataBody

            }
        }
    }

    fun getAvailableProductsByUserId(userId: String) {
        viewModelScope.launch {
            val result = RetrofitInstance.api.getAvailableProductsByUserId(userId)
            if (result.isSuccessful) {
                val dataBody = result.body()!!
                availableProducts.value = dataBody
            }
        }
    }

    fun sell(
        productId: String,
        productQuantity: String,
        remainingStock: String,
        totalAmount: Double,
        productPrice: Double,
        productName: String,
        name: String,
        userId: String,
        applicationContext: Context
    ) {
        viewModelScope.launch {
            val result = RetrofitInstance.api.Sell(
                productId,
                productQuantity,
                remainingStock,
                totalAmount.toString(),
                productPrice.toString(),
                productName,
                name,
                userId
            )
            if (result.isSuccessful) {
                Toast.makeText(applicationContext, "Sell Complete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getSellHistory(userId: String) {
        viewModelScope.launch {
            val result = RetrofitInstance.api.sellHistory(userId)
            if (result.isSuccessful) {
                val dataBody = result.body()!!
                sellHistory.value = dataBody
            }
        }
    }

    fun updateAvailableProducts(productId: String, remainingStock: Int) {
        viewModelScope.launch {
            val result =
                RetrofitInstance.api.updateAvailableProducts(productId, remainingStock)
            if (result.isSuccessful) {

            }
        }
    }

    fun getALlProduct() {
        viewModelScope.launch {
            val result = RetrofitInstance.api.getAllProduct()
            if (result.isSuccessful) {
                val dataBody = result.body()!!
                data.value = dataBody
            }
        }
    }

    fun addOrder(
        applicationContext: Context,
        productId: String,
        name: String,
        userId: String,
        productName: String,
        category: String,
        totalAmount: String,
        productQuantity: String,
        price: String,
        certified: String
    ) {
        viewModelScope.launch {
            val result = RetrofitInstance.api.addOrder(
                productId,
                userId,
                productName,
                name,
                totalAmount,
                productQuantity,
                "It's Urgent",
                price,
                certified,
                category
            )
            if (result.isSuccessful) {
                if (result.body()?.status == 200) {
                    Toast.makeText(
                        applicationContext,
                        "Order Successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Order Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(applicationContext, "Order Failed", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    fun getAllOrderDetails() {
        viewModelScope.launch {
            val result = RetrofitInstance.api.getAllOrderDetails()
            if (result.isSuccessful) {
                val dataBody = result.body()!!
                allOrder.value = dataBody
            }
        }
    }


}

sealed class State(var name: String) {
    data object DEFAULT : State("DEFAULT")
    data object SUCCESS : State("SUCCESS")
    data object LOADING : State("LOADING")
    data object FAILED : State("FAILED")
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AllViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return AllViewModel(context) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

