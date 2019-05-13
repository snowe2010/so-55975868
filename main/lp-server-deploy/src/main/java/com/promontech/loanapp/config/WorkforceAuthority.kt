package com.promontech.loanapp.config

enum class WorkforceAuthority(val index: Int) {

    CAN_DO_STUFF(0);

    companion object {

        private val map = mutableMapOf<Int, WorkforceAuthority>()
        private val stringMap = mutableMapOf<Int, String>()

        init {
            for (workforceAuthority in values()) {
                if (map.containsKey(workforceAuthority.index) || workforceAuthority.index < 0) {
                    throw IllegalStateException("Invalid index ${workforceAuthority.index} found in WorkforceAuthority!")
                } else if (ExternalAuthority.valueExists(workforceAuthority.name)) {
                    throw IllegalStateException("WorkforceAuthority $workforceAuthority clashes with ExternalAuthority!")
                }
                map[workforceAuthority.index] = workforceAuthority
            }

            if (map.size != values().size || map.keys.toIntArray().max() != (values().size - 1)) {
                throw IllegalStateException("Workforce authority indexes do not match size of enum defined!")
            }
            map.forEach { stringMap[it.key] = it.value.name }
        }

        @JvmStatic
        fun valueByIndex(index: Int): WorkforceAuthority? {
            return map[index]
        }

        @JvmStatic
        fun valueExists(value: String): Boolean {
            return stringMap.containsValue(value)
        }

    }
}

/**
 * NOTE: IF YOU ADD TO THIS ENUM CLASS YOU MUST ALSO ADD THE SAME ENUM IN THE DATABASE,
 * FAILURE TO DO SO WILL RESULT IN IAM SERVICE LOGGING AN ERROR
 *
 * WARNING: DO NOT Modify existing enum and indexes in this class! This will break Security!!
 */
enum class ExternalAuthority(val index: Int) {

    CAN_VIEW(0);

    companion object {

        private val map = mutableMapOf<Int, ExternalAuthority>()
        private val stringMap = mutableMapOf<Int, String>()

        init {
            for (externalAuthority in values()) {
                if (map.containsKey(externalAuthority.index) || externalAuthority.index < 0) {
                    throw IllegalStateException("Invalid index found in ExternalAuthority!")
                } else if (WorkforceAuthority.valueExists(externalAuthority.name)) {
                    throw IllegalStateException("ExternalAuthority $externalAuthority clashes with WorkforceAuthority!")
                }
                map[externalAuthority.index] = externalAuthority
            }

            if (map.size != values().size || map.keys.toIntArray().max() != (values().size - 1)) {
                throw IllegalStateException("External authority indexes do not match size of enum defined!")
            }

            map.forEach { stringMap[it.key] = it.value.name }
        }

        @JvmStatic
        fun valueByIndex(index: Int): ExternalAuthority? {
            return map[index]
        }

        @JvmStatic
        fun valueExists(value: String): Boolean {
            return stringMap.containsValue(value)
        }

    }
}
