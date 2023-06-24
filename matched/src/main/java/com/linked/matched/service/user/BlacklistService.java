package com.linked.matched.service.user;

import com.linked.matched.repository.user.BlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;

    public boolean blacklist(String email){
        if(blacklistRepository.findByEmail(email)!=null){
            return false;
        }
        return true;
    }
}
