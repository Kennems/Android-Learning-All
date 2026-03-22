package com.zhouguan.learntips.entity

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize


//class Person(val name: String = "", val age: Int = 0) : Serializable {
//
//    override fun toString(): String {
//        return "Person(name=$name, age=$age)"
//    }
//
//}

@Parcelize
class Person(var name: String = "", var age: Int = 0) : Parcelable {
//    companion object CREATOR : Parcelable.Creator<Person> {
//        override fun createFromParcel(parcel: Parcel): Person {
//            val person = Person()
//            person.name = parcel.readString() ?: "" // 读取name
//            person.age = parcel.readInt() // 读取age
//            return person
//        }
//        override fun newArray(size: Int): Array<Person?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//
//    override fun toString(): String {
//        return "Person(name=$name, age=$age)"
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name) // 写出name
//        parcel.writeInt(age) // 写出age
//    }
}