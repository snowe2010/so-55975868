package com.promontech.common.spring.spring2.jpa.hibernate


class SpringJpa(
    var databaseDialect: String,
    var ddlAuto: String,
    var showSql: String,
    var formatSql: String
) {
    constructor() : this("", "", "", "")
}
