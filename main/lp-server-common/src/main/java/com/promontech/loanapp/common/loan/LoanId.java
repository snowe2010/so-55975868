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
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "loan_id")))
public final class LoanId extends BaseId {
    public static final String ID_NAME = "loanId";

    public LoanId() {
        super(UUID.randomUUID());
    }

    public LoanId(UUID id) {
        super(id);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public LoanId(String id) {
        super(id);
    }

    @JsonValue
    @JsonProperty(ID_NAME)
    @Override
    public UUID getId() {
        return id;
    }

}
