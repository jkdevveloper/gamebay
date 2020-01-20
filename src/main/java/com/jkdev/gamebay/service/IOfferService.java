package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Offer;

import java.util.List;

public interface IOfferService {
    Offer getOfferById(Long id);

    List<Offer> getOffers();

    void deleteOffer(Long id);

    void updateOffer(Offer offer);

    public void saveOffer(Offer offer);
}
