package br.com.solipy.loja.configs;

import org.hashids.Hashids;
import org.springframework.stereotype.Service;

@Service
public class HashidService {

    private Hashids hashid;

    public HashidService(){
        hashid = new Hashids("d41d8cd98f00b204e9800998ecf8427e");
    }

    public Hashids get(){
        return this.hashid;
    }

    public Hashids get(int length){
        return new Hashids("d41d8cd98f00b204e9800998ecf8427e", length);
    }

    public String toStringUserKey(String userKey){
        Long userId = get(32).decode(userKey)[0];
        return  userId.toString();
    }

    public Long toIntUserKey(String userKey){
        Long userId = get(32).decode(userKey)[0];
        return userId;
    }

    public Long toLongUserKey(String userKey){
        Long userId = get(32).decode(userKey)[0];
        return userId;
    }

}
