package com.promontech.loanapp.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiTenantDataSource extends AbstractRoutingDataSource {
    private final CurrentTenantIdentifierResolver tenantIdResolver;

    public MultiTenantDataSource(CurrentTenantIdentifierResolver tenantIdResolver) {
        this.tenantIdResolver = tenantIdResolver;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantIdResolver.resolveCurrentTenantIdentifier();
    }
}
