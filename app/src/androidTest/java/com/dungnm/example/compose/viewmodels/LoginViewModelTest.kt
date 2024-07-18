package com.dungnm.example.compose.viewmodels

import com.dungnm.example.compose.MainCoroutineRule
import com.dungnm.example.compose.base.Error
import com.dungnm.example.compose.network.repo.ILoginRepo
import com.dungnm.example.compose.ui.activity.login.LoginViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class LoginViewModelTest {
    private lateinit var viewModel: LoginViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule: RuleChain = RuleChain.outerRule(hiltRule).around(coroutineRule)

    @Inject
    lateinit var loginRepo: ILoginRepo

    @Before
    fun setUp() {
        hiltRule.inject()
        viewModel = LoginViewModel(loginRepo)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun testLoginSuccess() = runTest {
        viewModel.login("admin", "admin")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.loginResLiveData.value == true)
    }

    @Test
    fun testLoginFail() = runTest {
        viewModel.login("a", "a")
        advanceUntilIdle()
        Assert.assertTrue(viewModel.errCode.value is Error)
    }


}