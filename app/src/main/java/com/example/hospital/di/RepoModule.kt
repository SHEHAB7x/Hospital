package com.example.hospital.di

import com.example.hospital.repo.IRepo
import com.example.hospital.repo.Repo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {
    @Binds
    abstract fun bindRepo(repo : Repo) : IRepo
}