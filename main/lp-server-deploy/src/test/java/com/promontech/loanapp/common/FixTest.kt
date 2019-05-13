package com.promontech.loanapp.common

import com.promontech.loanapp.IntegrationTestApplicationConfiguration
import com.promontech.loanapp.utils.IntegrationTestActiveProfileResolver
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertTrue

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = IntegrationTestActiveProfileResolver::class)
@ContextConfiguration(classes = [IntegrationTestApplicationConfiguration::class])
class FixTest {
    @Test
    fun `Check that configuration is loaded correctly`() {
        assertTrue(true)
    }
}
