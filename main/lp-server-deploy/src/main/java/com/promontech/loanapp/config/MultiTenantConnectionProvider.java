package com.promontech.loanapp.config;


import java.util.HashMap;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class MultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private DatasourceConnectionProviderImpl dataSourceConnectionProvider;

    private static final long serialVersionUID = -1796618421575501644L;

    public MultiTenantConnectionProvider(DataSource dataSource) {
        dataSourceConnectionProvider = new DatasourceConnectionProviderImpl();
        dataSourceConnectionProvider.setDataSource(dataSource);
        // triggers the 'available' flag
        dataSourceConnectionProvider.configure(new HashMap<>());
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return getDatasourceConnectionProvider();
    }

    /**
     * All of the logic to select a given datasource based on the identifier is already handled in our implementation of
     * the AbstractRoutingDataSource, so we always just return that data source here.
     *
     * @param tenantIdentifier unused in this implementation
     */
    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return getDatasourceConnectionProvider();
    }

    protected DatasourceConnectionProviderImpl getDatasourceConnectionProvider() {
        return dataSourceConnectionProvider;
    }
}
