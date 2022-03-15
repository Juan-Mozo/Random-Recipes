package com.juanimozo.recipesrandomizer.domain.use_case

import com.juanimozo.recipesrandomizer.domain.repository.RecipesRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before

class GetRandomRecipesUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: RecipesRepository

    lateinit var getRandomRecipesUseCase: GetRandomRecipesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomRecipesUseCase = GetRandomRecipesUseCase(repository)
    }

}