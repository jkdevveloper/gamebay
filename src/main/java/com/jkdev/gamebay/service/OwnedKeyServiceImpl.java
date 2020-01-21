package com.jkdev.gamebay.service;

import com.jkdev.gamebay.entity.OwnedKey;
import com.jkdev.gamebay.repository.IOwnedKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnedKeyServiceImpl implements IOwnedKeyService {

    @Autowired
    IOwnedKeyRepository ownedKeyRepository;

    @Override
    public void saveOwnedKey(OwnedKey ownedKey) {
        ownedKeyRepository.save(ownedKey);
    }
}
