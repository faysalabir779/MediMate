package com.example.medimate

import android.content.Context
import android.util.Log
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
import com.example.medimate.API.response.AllProductItem
import com.example.medimate.API.response.GetAllOrderDetailsItem
import com.example.medimate.API.response.GetSpecificUserItem
import com.example.medimate.pref.PreferenceKeys
import com.example.medimate.pref.UserData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.math.log

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("User_Id")

class AllViewModel(context: Context) : ViewModel() {

    var state = mutableStateOf("")

    var data = mutableStateOf<List<AllProductItem>>(emptyList())
    var specificUser = mutableStateOf<List<GetSpecificUserItem>>(emptyList())
    var allOrder = mutableStateOf<List<GetAllOrderDetailsItem>>(emptyList())

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
            preferenceData.collect{
                Log.d("idkoi", "joafj: ${it.userId}")
                getSpecificUser(it.userId)
            }
        }
    }

    fun savePref(
        userId: String,
        name: String,
        password: String,
        email: String,
        address: String,
        phone: String,
        pinCode: String
    ) {
        viewModelScope.launch {
            dataStore.edit {
                it[PreferenceKeys.USER_ID] = userId
                it[PreferenceKeys.USER_NAME] = name
                it[PreferenceKeys.USER_PASSWORD] = password
                it[PreferenceKeys.USER_EMAIL] = email
                it[PreferenceKeys.USER_ADDRESS] = address
                it[PreferenceKeys.USER_PHONE] = phone
                it[PreferenceKeys.USER_PINCODE] = pinCode
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
            var result =
                RetrofitInstance.api.createUser(name, password, email, address, phone, pinCode)

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

    fun getSpecificUser(userId: String){
        viewModelScope.launch {
            val resultForSpecificUser =
                RetrofitInstance.api.getSpecificUser(userId)

            if (resultForSpecificUser.isSuccessful) {
                val dataBody = resultForSpecificUser.body()!!
                Log.d("dekhbeda", "dekh: $dataBody")
                specificUser.value = dataBody ?: emptyList()

            }
        }
    }


    suspend fun getALlProduct() {
        viewModelScope.launch {
            val result = RetrofitInstance.api.getAllProduct()
            if (result.isSuccessful) {
                val dataBody = result.body()!!
                data.value = dataBody ?: emptyList()
            }
        }
    }

    fun addOrder(
        applicationContext: Context,
        productId: String,
        name: String,
        userId: String,
        address: String,
        phone: String,
        productName: String,
        category: String,
        totalAmount: String,
        productquantity: String,
        status: Int
    ) {
        viewModelScope.launch {
            var result = RetrofitInstance.api.addOrder(
                productId,
                userId,
                name,
                address,
                phone,
                productName,
                category,
                totalAmount,
                productquantity,
                status
            )
            if (result.isSuccessful) {
                if (result.body()?.status == 200) {
                    Toast.makeText(applicationContext, "Order Successfully", Toast.LENGTH_SHORT)
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
                allOrder.value = dataBody ?: emptyList()
            }
        }
    }
}

sealed class State(var name: String) {
    object DEFAULT : State("DEFAULT")
    object SUCCESS : State("SUCCESS")
    object LOADING : State("LOADING")
    object FAILED : State("FAILED")
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