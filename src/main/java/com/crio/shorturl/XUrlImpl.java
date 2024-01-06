package com.crio.shorturl;

import java.security.SecureRandom;
import java.util.HashMap;

public class XUrlImpl implements XUrl{

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_URL_LENGTH = 9;
    private HashMap<String,String> longUrlToShortUrl;
    private HashMap<String,String> shortUrlToLongUrl;
    private HashMap<String,Integer> longUrlToHit;

    public XUrlImpl(){
        longUrlToShortUrl = new HashMap<>();
        shortUrlToLongUrl = new HashMap<>();
        longUrlToHit = new HashMap<>();
    }

    @Override
    public String registerNewUrl(String longUrl){
        if(longUrlToShortUrl.containsKey(longUrl)){
            return longUrlToShortUrl.get(longUrl);
        }
        String shortUrl = generateShortUrl(longUrl);
        longUrlToShortUrl.put(longUrl, shortUrl);
        shortUrlToLongUrl.put(shortUrl, longUrl);
        longUrlToHit.put(longUrl, 0);
        return shortUrl;
    }
    @Override
    public String registerNewUrl(String longUrl, String shortUrl){
        if(shortUrlToLongUrl.containsKey(shortUrl)){
            return null;
        }
        longUrlToShortUrl.put(longUrl, shortUrl);
        shortUrlToLongUrl.put(shortUrl, longUrl);
        longUrlToHit.put(longUrl, 0);
        return shortUrl;
    }
    @Override
    public String getUrl(String shortUrl){
        if(shortUrlToLongUrl.containsKey(shortUrl)){
            String longUrl = shortUrlToLongUrl.get(shortUrl);
            longUrlToHit.put(longUrl, longUrlToHit.get(longUrl)+1);
            return longUrl;
        }
        return null;
    }
    @Override
    public Integer getHitCount(String longUrl){
        return longUrlToHit.getOrDefault(longUrl, 0);
    }
    @Override
    public String delete(String longUrl){
        String shortUrl = longUrlToShortUrl.get(longUrl);
        longUrlToShortUrl.remove(longUrl);
        shortUrlToLongUrl.remove(shortUrl);
        return shortUrl;
    }

    private String generateShortUrl(String longUrl){
        StringBuilder sb = new StringBuilder(SHORT_URL_LENGTH);
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int randomIndex = random.nextInt(ALPHANUMERIC.length());
            char randomChar = ALPHANUMERIC.charAt(randomIndex);
            sb.append(randomChar);
        }
        return "http://short.url/" + sb.toString();
    }

}