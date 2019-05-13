package com.promontech.mapping

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "map1")
class Map1(
        @Id @GeneratedValue var id: Long
) : Serializable
