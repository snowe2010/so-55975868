package com.promontech.loanapp.common.loan;

/**
 * The list of Spring profiles used to bootstrap the application. It is NOT essential that all potential profiles be registered in this enum, but if one intends to use
 * the <code>@Profiles</code> annotation, it is best to use an enum reference as the name. This will ensure a compile time consistency of the values used.
 */
public enum Profiles {
    ;

    public class Names {
        private Names() {
            //keep sonar happy
        }

        public static final String INTEGRATION_TEST = "integration-test";
    }

    private final String profileName;

    Profiles(String profileName) {
        this.profileName = profileName;
    }

    @Override
    public String toString() {
        return profileName;
    }
}
