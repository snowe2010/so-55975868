package com.promontech.loanapp.common

import com.promontech.loanapp.common.loan.TenantId

/**
 * Every view dto extends from the AbstractView
 *
 * Every view at least contains a TenantId
 */
interface AbstractView {
    var tenantId: TenantId
}
