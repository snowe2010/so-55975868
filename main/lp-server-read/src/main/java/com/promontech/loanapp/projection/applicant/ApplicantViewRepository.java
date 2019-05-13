package com.promontech.loanapp.projection.applicant;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promontech.loanapp.common.loan.ApplicantId;
import com.promontech.loanapp.common.applicant.viewmodel.ApplicantViewDao;

public interface ApplicantViewRepository extends JpaRepository<ApplicantViewDao, ApplicantId> {
}
