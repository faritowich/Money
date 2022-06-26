package com.example.a2022_nabiullin_azat.di

import com.example.a2022_nabiullin_azat.domain.CurrencyRepositoryImpl
import com.example.a2022_nabiullin_azat.domain.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun CurrencyRepositoryImpl.bindRepo(): CurrencyRepository
}