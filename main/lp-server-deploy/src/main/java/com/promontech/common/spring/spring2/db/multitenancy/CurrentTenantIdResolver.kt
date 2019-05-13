package com.promontech.common.spring.spring2.db.multitenancy

import com.promontech.loanapp.config.User
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class CurrentTenantIdResolver : CurrentTenantIdentifierResolver {
    override fun resolveCurrentTenantIdentifier(): String {
        val context = SecurityContextHolder.getContext()
        return if (context.authentication != null) {
            val user = context.authentication.details as User
            user.tenantId
        } else {
            "0" // Hibernate multi tenancy requires this value to be non-empty, non-null
        }
    }

    override fun validateExistingCurrentSessions(): Boolean {
        return true
    }
}
