package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.Offer;
import com.jkdev.gamebay.repository.IOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements IOfferService{

    @Autowired
    IOfferRepository offerRepository;

    @Override
    public Offer getOfferById(Long id) {
        return this.offerRepository.getOne(id);
    }

    @Override
    public List<Offer> getOffers() {
        return this.offerRepository.findAll();
    }

    @Override
    public void deleteOffer(Long id) {
        this.offerRepository.deleteById(id);
    }

    @Override
    public void updateOffer(Offer offer) {
        this.offerRepository.save(offer);
    }

    @Override
    public void saveOffer(Offer offer) {
        this.offerRepository.save(offer);
    }
}
