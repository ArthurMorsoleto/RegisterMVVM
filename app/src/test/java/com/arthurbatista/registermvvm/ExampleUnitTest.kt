package com.arthurbatista.registermvvm

import android.app.Application
import com.arthurbatista.registermvvm.viewmodel.ClientViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private lateinit var clientViewModel: ClientViewModel

    @Test
    fun testFindCepRequest() {
        /*clientViewModel = ClientViewModel(this)*/
        val returnedCep = clientViewModel.findCEP("02441-170")
        if (returnedCep != null) {
            assert(returnedCep.bairro != null)
        }

    }
}
