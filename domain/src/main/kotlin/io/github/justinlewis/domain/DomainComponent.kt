package io.github.justinlewis.domain

import dagger.Component
import io.github.justinlewis.common.UtilComponent
import io.github.justinlewis.data.DataComponent
import io.github.justinlewis.domain.interactor.AuthenticationInteractor
import io.github.justinlewis.domain.interactor.MovieInteractor
import io.github.justinlewis.domain.interactor.UserInteractor

/**
 * Created on 2/7/2018.
 */
@Component(
    modules = [DomainModule::class],
    dependencies = [DataComponent::class, UtilComponent::class]
)
interface DomainComponent {
    fun userInteractor(): UserInteractor
    fun movieInteractor(): MovieInteractor
    fun authenInteractor(): AuthenticationInteractor
}
