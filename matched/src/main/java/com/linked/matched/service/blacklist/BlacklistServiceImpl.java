package com.linked.matched.service.blacklist;

import com.linked.matched.repository.blacklist.BlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlackListService{
    private final BlacklistRepository blacklistRepository;

    @Override
    public boolean blacklist(String email){
        if(blacklistRepository.findByEmail(email)!=null){
            return false;
        }
        return true;
    }
}
