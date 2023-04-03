package io.github.justinlewis.data.local.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created on 2/2/2018.
 */
open class UserEntity(
    @PrimaryKey var userId: Int = -1,
    var token: String = ""
) : RealmObject()
