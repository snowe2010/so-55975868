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
@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "user_id")))
public final class UserId extends BaseId {
    public static final String ID_NAME = "userId";

    public UserId() {
        super(UUID.randomUUID());
    }

    public UserId(UUID id) {
        super(id);
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public UserId(String id) {
        super(id);
    }

    @JsonValue
    @JsonProperty(ID_NAME)
    @Override
    public UUID getId() {
        return id;
    }

}
