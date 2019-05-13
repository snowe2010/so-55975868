package com.promontech.mapping

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "map2")
class Map2(
        @Id @GeneratedValue var id: Long
) : Serializable

