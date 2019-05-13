package com.promontech.loanapp.utils

import com.promontech.loanapp.common.loan.Profiles
import org.springframework.test.context.ActiveProfilesResolver

class IntegrationTestActiveProfileResolver : ActiveProfilesResolver {
    override fun resolve(aClass: Class<*>): Array<String> {
        return arrayOf(Profiles.Names.INTEGRATION_TEST)
    }
}
