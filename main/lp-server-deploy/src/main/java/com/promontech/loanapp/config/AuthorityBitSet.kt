package com.promontech.loanapp.config

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue
import java.util.*

abstract class AuthorityBitSet {

    protected var bitSet: BitSet

    constructor(bitset: BitSet) {
        this.bitSet = bitset
    }

    abstract fun getGrantedAuthorities(): Collection<GrantedAuthority>

    abstract fun setAuthorities(authorities: Collection<GrantedAuthority>)

    @JsonValue
    override fun toString(): String {
        return String(Base64.getEncoder()
                .encode(this.bitSet.toByteArray()))
    }

    companion object {

        /**
         * A utility method to deserialize from string
         *
         * @param encodedAuthoritySetString serialized encoded string
         * @return Deserialized Set of WorkforceAuthority
         */
        fun deserialize(encodedAuthoritySetString: String): BitSet {
            return BitSet.valueOf(Base64.getDecoder()
                    .decode(encodedAuthoritySetString))
        }
    }
}

/**
 * A simple way to serialize WorkforceAuthority across the wire.
 */
class WorkforceAuthorityBitSet : AuthorityBitSet {

    constructor(authorities: Collection<GrantedAuthority>) : super(BitSet()) {
        setAuthorities(authorities)
    }

    @JsonIgnore
    override fun getGrantedAuthorities(): Collection<GrantedAuthority> {
        return (0..bitSet.length())
                .filter { bitSet.get(it) }
                .mapNotNull { WorkforceAuthority.valueByIndex(it) }
                .map { GrantedAuthority(it.name) }
    }

    override fun setAuthorities(authorities: Collection<GrantedAuthority>) {
        authorities
                .filter { WorkforceAuthority.valueExists(it.authority) }
                .forEach { this.bitSet.set(WorkforceAuthority.valueOf(it.authority).index) }
    }
}


/**
 * A simple way to serialize ExternalAuthority across the wire.
 */
class ExternalAuthorityBitSet : AuthorityBitSet {

    constructor(authorities: Collection<GrantedAuthority>) : super(BitSet()) {
        setAuthorities(authorities)
    }

    @JsonIgnore
    override fun getGrantedAuthorities(): Collection<GrantedAuthority> {
        return (0..bitSet.length())
                .filter { bitSet.get(it) }
                .mapNotNull { ExternalAuthority.valueByIndex(it) }
                .map { GrantedAuthority(it.name) }
    }

    override fun setAuthorities(authorities: Collection<GrantedAuthority>) {
        authorities
                .filter { ExternalAuthority.valueExists(it.authority) }
                .forEach { this.bitSet.set(ExternalAuthority.valueOf(it.authority).index) }
    }
}
