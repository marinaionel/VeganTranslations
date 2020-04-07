package com.example.vegantranslations.service.repository;

public class VeganTranslationsRepository implements IVeganTranslationsRepository {
    private DataLoader dataLoader;

    public VeganTranslationsRepository() {
        dataLoader = DataLoader.getInstance();
    }
}
