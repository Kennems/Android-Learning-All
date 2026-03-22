package com.zhouguan.learnjetpack.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.zhouguan.learnjetpack.entity.Repository
import com.zhouguan.learnjetpack.entity.User

//class MainViewModel(countReserved: Int) : ViewModel() {
//    val counter = MutableLiveData<Int>()
//
//    init {
//        counter.value = countReserved
//    }
//
//    fun plusOne() {
//        val count = counter.value ?: 0
//        counter.value = count + 1
//    }
//
//    fun clear() {
//        counter.value = 0
//    }
//}
class MainViewModel(countReserved: Int) : ViewModel() {
    val userLiveData = MutableLiveData<User>()

    val userName: LiveData<String> = userLiveData.map { user ->
        "${user.firstName} ${user.lastName}"
    }

    val count: LiveData<Int>
        get() = _count

    val _count = MutableLiveData<Int>()

    init {
        _count.value = countReserved
    }

    fun plusOne() {
        val count = _count.value ?: 0
        _count.value = count + 1
    }

    fun clear() {
        _count.value = 0
    }

    fun getUser(userId: String): LiveData<User> {
        return Repository.getUser(userId)
    }
}