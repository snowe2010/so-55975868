package com.promontech.loanapp.common.loan;

import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@Embeddable
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "applicant_id")))
public final class ApplicantId extends BaseId {
    public static final String ID_NAME = "applicantId";

    public ApplicantId() {
        super(UUID.randomUUID());
    }

    public ApplicantId(UUID id) {
        super(id);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ApplicantId(String id) {
        super(id);
    }

    @JsonValue
    @JsonProperty(ID_NAME)
    @Override
    public UUID getId() {
        return id;
    }

}
