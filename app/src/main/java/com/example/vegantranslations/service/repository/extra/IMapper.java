package com.example.vegantranslations.service.repository.extra;

public interface IMapper<From, To> {

    To map(From from);
}
