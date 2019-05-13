package com.promontech.loanapp.common.applicant.viewmodel

import com.promontech.loanapp.common.AbstractView
import com.promontech.loanapp.common.AbstractViewDao
import com.promontech.loanapp.common.loan.ApplicantId
import com.promontech.loanapp.common.loan.LoanId
import com.promontech.loanapp.common.loan.LoanTransactionId
import com.promontech.loanapp.common.loan.TenantId
import com.promontech.loanapp.common.loan.UserId
import org.hibernate.annotations.Parameter
import org.hibernate.annotations.Type
import java.io.Serializable
import javax.persistence.AttributeOverride
import javax.persistence.AttributeOverrides
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

data class ApplicantView(
    var applicantId: ApplicantId,
    var loanTransactionId: LoanTransactionId,
    var userId: UserId? = null,
    var primaryLoanId: LoanId? = null,
    var secondaryLoanId: LoanId? = null,
    override var tenantId: TenantId
) : AbstractView, Serializable {
    companion object {
        const val CLASS_NAME = "com.promontech.loanapp.common.applicant.viewmodel.ApplicantView"
    }
}

@Entity
@Table(name = "applicant")
data class ApplicantViewDao(
    @Id var applicantId: ApplicantId,
    var userId: UserId?,
    override var tenantId: TenantId,

    @Column(name = "data")
    @Type(type = "Json", parameters = arrayOf(Parameter(name = "type", value = ApplicantView.CLASS_NAME)))
    var applicantView: ApplicantView,

    @Type(type = "pg-uuid")
    @AttributeOverrides(AttributeOverride(name = "id", column = Column(name = "primary_loan_id")))
    var primaryLoanId: LoanId? = null,

    @Type(type = "pg-uuid")
    @AttributeOverrides(AttributeOverride(name = "id", column = Column(name = "secondary_loan_id")))
    var secondaryLoanId: LoanId? = null
) : AbstractViewDao {
    companion object
}

