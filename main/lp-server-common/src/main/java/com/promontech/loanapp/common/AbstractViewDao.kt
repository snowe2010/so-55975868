package com.promontech.loanapp.common

import com.promontech.loanapp.common.loan.TenantId

/**
 * Every view saved in the database extends from the AbstractViewDao.
 *
 * Every view at least contains an TenantId.
 */
interface AbstractViewDao {
    var tenantId: TenantId
}
